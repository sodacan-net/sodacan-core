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

import java.io.PrintStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import net.sodacan.SodacanException;
import net.sodacan.config.Config;
/**
 * A sometimes short-lived object that consumes records of a topic.
 * @author John Churin
 *
 */
public class TopicConsumer {
	private String topicName;

	public TopicConsumer( String topicName ) {
		this.topicName = topicName;
	}
	
	/**
	 * Consume records from the topic using "assign semantics", we're not subject to rebalance.
	 * The output is just sent to the specified PrintStream, usually System.out.
	 * @param out PrintStream to receive output
	 * @param follow If true, stay open and show as records arrive. If false, print 'til kast known offset, then return.
	 */
	public void consume(PrintStream out, boolean follow) {
		try {
			String url = Config.getInstance().getKafka().getUrl();
			Properties properties = new Properties();
			properties.setProperty("bootstrap.servers", url);
//			properties.setProperty("group.id", "test");
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
		        	 out.println(record.key() + ": " + record.value());
		        	 if (!follow && endOffsets.get(tp)==record.offset()) break;
		         }
		    }
		    consumer.close();
		} catch (Exception e) {
			throw new SodacanException("Problem getting for topic: " + topicName, e);
		}

	}
}
