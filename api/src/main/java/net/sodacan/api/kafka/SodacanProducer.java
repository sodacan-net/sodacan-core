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
package net.sodacan.api.kafka;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import net.sodacan.SodacanException;
import net.sodacan.config.Config;
/**
 * <p>This is a generic message producer. It can be instantiated and be left open 
 * or created and closed as needed. The topics can vary, from call to call, but must exist before
 * sending.</p>
 * <p>There are some utility classes for dealing with compound keys in ModuleMethods.java</p>
 * <p>Configuration parameters are taken from the singleton configuration.</p>
 * 
 * @author John Churin
 */
public class SodacanProducer implements Closeable {
	Producer<String, String> producer;

	public SodacanProducer( ) {
		Properties props = new Properties();
		String url = Config.getInstance().getKafka().getUrl();
		Long lingerMs = Config.getInstance().getKafka().getProducer().getLingerMs();
		props.put("bootstrap.servers", url);
		props.put("linger.ms", lingerMs);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
	}
	/**
	 * Send a key and value to the named topic
	 * @param topic
	 * @param key
	 * @param value
	 */
	public void put(String topic, String key, String value) {
		ProducerRecord<String, String> pr = new ProducerRecord<>(topic, key, value);
		producer.send(pr);
	}

	@Override
	public void close() {
		try {
			producer.close();
		} catch (Exception e) {
			throw new SodacanException("Error closing producer", e);
		}
		producer = null;
	}


}
