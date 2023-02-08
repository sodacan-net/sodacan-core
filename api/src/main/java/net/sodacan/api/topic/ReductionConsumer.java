/*
 * Copyright 2023 John M Churin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sodacan.api.topic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.InterruptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.SodacanException;
import net.sodacan.api.Followable;
import net.sodacan.config.Config;

/**
 * <p>Consume messages from a topic into a Set, duplicates removed, last in wins. Tombstones (record value is null)
 * deletes the key from the set.
 * The follow method starts the consumer in a thread and remains active until stopped. The snapshot method creates the map as a snapshot and
 * then control returns.</p>
 * <p>For follow, it is common to want to know when changes are made to the reduction map. A propertyChangelistener can request notification on each change to the map.</p>
 * <p>To use this class, call the constructor with topic name. Then either call the follow method or the snapshot 
 * method.</p>
 * <p>Before calling follow, you can addPropertyChangeListeners to receive callbacks as the map changes.</p>
 * <p>The connection information needed for this class is gleaned from the singleton Config object.</p>
 * @author John Churin
 *
 */
public class ReductionConsumer<K,V> implements Runnable, Followable {
	private final static Logger logger = LoggerFactory.getLogger(ReductionConsumer.class);
	private Thread thread = null;
	private ConcurrentMap<K, V> reductionMap = new ConcurrentHashMap<>();
	
	private String topicName;
	private boolean follow;
	private boolean preload;
	private boolean preloading;
	
	/**
	 * We're called for each record found. If the value is null, it means the module[key] is deleted,
	 * and is removed from the reduction map. Property change listeners are called at this time.
	 * @param key
	 * @param newValue
	 */
	protected void processRecord( K key, V newValue) {
		V oldValue;
		if (newValue==null) {
			oldValue = reductionMap.remove(key);
		} else {
			oldValue = reductionMap.put(key, newValue);
		}
		// Let everyone know we got a record unless we're preloading
		if (!preloading) {
			pcs.firePropertyChange(key.toString(), oldValue, newValue);
		}
	}

	/**
	 * 
	 * @param topicName
	 * @param follow If true, stay open and show as records arrive. If false, just load names until the last known offset, then return.
	 */
	public ReductionConsumer(String topicName) {
		this.topicName = topicName;
	}
	
	/**
	 * <p>Normally, a preload is done. During a preload, the listener callbacks are not called. After the 
	 * preload is complete, each unique remaining  record in the map is presented as a callback. This eliminate the callback for a row that has long been "deleted".
	 * After the preload, all records are included even if the record is already in the map.
	 */
	public Thread follow() {
		this.follow = true;
		this.preload = true;
		this.preloading = true;
		thread = new Thread(this);
		thread.setName("Follow-" + topicName);
		thread.start();
		return thread;
	}

	/**
	 * This method calls the listeners for each record, including deleted keys. It's not usually that useful 
	 * because duplicate records are arbitrarily compacted away so the results are not generally reproducible.
	 * Good for debugging.
	 */
	public Thread followAll() {
		this.follow = true;
		this.preload = false;
		this.preloading = false;
		thread = new Thread(this);
		thread.setName("Follow-" + topicName);
		thread.start();
		return thread;
	}

	/**
	 * For a snapshot, the assumption is that preload is done. Therefore, no callbacks are never called because
	 * at the point that they would be called, after preload, the finished, compacted map is complete and control returns.
	 */
	public void snapshot() {
		this.follow = false;
		this.preload = true;
		this.preloading = true;
		consume();
	}

	public void stop() {
		if (thread!=null) {
			thread.interrupt();
		}
	}

	/**
	 * You can call the get method at any time, but it will only have useful data after calling either
	 * follow or snapshot.
	 * @param key key requested
	 * @return The value
	 */
	public V get(K key) {
		return this.reductionMap.get(key);
	}

	/**
	 * Return an immutable copy of the reduced keys (no duplicates)
	 * @return
	 */
	public List<K> getListOfKeys() {
		List<K> keys = List.copyOf(reductionMap.keySet());
		return keys;
	}
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	/**
	 * Get the server address and port
	 * @return A string containing one or more comma-separated address:port pairs
	 */
	protected String getServers() {
		String url = Config.getInstance().getKafka().getUrl();
		return url;
	}

	/**
	 * Send all rows in the reduction map to the registered property change listeners.
	 */
	protected void sendAll() {
		if (preload) {
			reductionMap.forEach((key,value)-> pcs.firePropertyChange(key.toString(), null, value));
		}
		preloading = false;		// Done preloading when this is called
	}
	/**
	 * 
	 */
	protected void consume() {
		Consumer<K, V> consumer = null;
		try {
			Properties properties = new Properties();
			properties.setProperty("bootstrap.servers", getServers());
//					properties.setProperty("group.id", "test");
			properties.setProperty("enable.auto.commit", "false");
			properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			consumer = new KafkaConsumer<>(properties);
			TopicPartition partition = new TopicPartition(topicName, 0);
			List<TopicPartition> partitions = Arrays.asList(partition);
			consumer.assign(partitions);
			consumer.seekToBeginning(partitions);
			// The endOffset will tell us where we end the preload phase
			Map<TopicPartition,Long> endOffsets = consumer.endOffsets(partitions);
		    while (true) {
		         ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(500));
		         if (!follow && records.count()==0) {
		        	 sendAll();
		        	 break;
		         }
		         for (ConsumerRecord<K, V> record : records) {
		        	 TopicPartition tp = new TopicPartition(record.topic(),record.partition());
		        	 logger.debug("Record from topic: " + topicName + " Offset: " + record.offset() + " key: " + record.key());
//		        	 Instant timestamp = Instant.ofEpochMilli(record.timestamp());
//		        	 System.out.println(timestamp);
		        	 // Are we done with preload phase
		        	 if (record.offset() >=(endOffsets.get(tp)-1)) {
			        	 processRecord( record.key(), record.value());
		        		 sendAll();
		        		 if (follow==false) return;
		        	 }
		        	 processRecord( record.key(), record.value());
		         }
		    }
		} catch (Throwable e) {
			if (!(e instanceof InterruptedException || e instanceof InterruptException)) {
				throw new SodacanException("Problem consuming from topic: " + topicName, e);
			}
		} finally {
			if (consumer!=null) {
			    consumer.close();
			}
		}
	}

	@Override
	public void run() {
		consume();
	}

	@Override
	public String toString() {
		return this.topicName;
	}

	@Override
	public String getName() {
		return this.topicName;
	}
	
	

}
