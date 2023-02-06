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
/**
 * <p>Get the current list of modules known to the system. They are kept in a set in memory.
 * A module is known by a key composed of the module name and an optional instance name. 
 * A brand new module will have no instances.</p>
 * @author John Churin
 *
 */

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import net.sodacan.SodacanException;
import net.sodacan.config.Config;

/**
 * Consume records from the Modules topic using "assign semantics", we're not subject to rebalance.
 * The output is stored in a set. We also can notify property change listener as changes to the set occur.

 * @author John Churin
 *
 */
public class ModuleConsumer implements Runnable {
	private String topicName;
	private Set<String> moduleKeys = new HashSet<>();
	private Set<PropertyChangeListener> listeners = new HashSet<>();

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}

	public void firePropertyChange​(String propertyName, Object oldValue, Object newValue) {
		this.pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	public ModuleConsumer(String topicName) {
		this.topicName = topicName;
	}
	
	/**
	 * We're called for each record found. If the value is null, it means the module[key] is deleted,
	 * removed from the set.
	 * @param key
	 * @param value
	 */
	protected void processRecord( String key, String value) {
		if (value==null) {
			moduleKeys.remove(key);
		} else {
			moduleKeys.add(key);
		}
	}

	/**
	 * Return a snapshot of moduleKeys that we know about.
	 * @return A list of moduleKeys
	 */
	public List<String> getListOfModuleKeys() {
		return List.copyOf(moduleKeys);
	}

	/**
	 * Return a snapshot of unique module Names that we know about. This is a reduction of the moduleKeys set.
	 * @return A list of moduleNames
	 */
	public List<String> getListOfModuleNames() {
		Set<String> names = new HashSet<>(moduleKeys.size());
		for (String name : moduleKeys) {
			int index = name.indexOf('[');
			if (index<0) {
				names.add(name);
			} else {
				names.add(name.substring(0,index));
			}
		}
		return List.copyOf(names);
	}
	
	/**
	 * @param follow If true, stay open and show as records arrive. If false, just load names until the last known offset, then return.
	 */
	public void consume(boolean follow) {
		try {
			String url = Config.getInstance().getKafka().getUrl();
			Properties properties = new Properties();
			properties.setProperty("bootstrap.servers", url);
//					properties.setProperty("group.id", "test");
			properties.setProperty("enable.auto.commit", "false");
			properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			Consumer<String, String> consumer = new KafkaConsumer<>(properties);
			TopicPartition partition = new TopicPartition(topicName, 0);
			List<TopicPartition> partitions = Arrays.asList(partition);
			consumer.assign(partitions);
			consumer.seekToBeginning(partitions);
			Map<TopicPartition,Long> endOffsets = consumer.endOffsets(partitions);
		    while (true) {
		         ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
		         if (!follow && records.count()==0) break;
		         for (ConsumerRecord<String, String> record : records) {
		        	 TopicPartition tp = new TopicPartition(record.topic(),record.partition());
		        	 processRecord( record.key(), record.value());
		        	 if (!follow && endOffsets.get(tp)==record.offset()) break;
		         }
		    }
		    consumer.close();
		} catch (Exception e) {
			throw new SodacanException("Problem consuming from topic: " + topicName, e);
		}
	}

	@Override
	public void run() {
		consume(true);
	}
}
