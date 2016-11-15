package org.t3.farm.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Echo Device Parameter Change events as device state changes back to the SodaCan.
 * @author john
 */
public class DeviceEmulator {
	private KafkaConsumer<String, String> consumer = null;
	private Producer<String, String> producer;
	static Logger logger = LoggerFactory.getLogger(DeviceEmulator.class);
	List<String> topics = new ArrayList<String>();

	public void setupConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "shop1:9092");
		props.put("group.id", "EmulatorGroup");
		props.put("enable.auto.commit", "true");		// Offset is saved automatically
		props.put("auto.commit.interval.ms", 10*1000);	// at this interval
		props.put("auto.offset.reset", "earliest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		// Setup a consumer
		consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(topics);
	}
	
	public void setupProducer() {
		Properties props = new Properties();
		try {
			props.load(GenerateEvents.class.getClassLoader().getResourceAsStream("event.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Unable to open properties file", e);
		}
		producer = new KafkaProducer<String, String>(props);
	}
	public DeviceEmulator() {
		topics.add("dpc");
		setupConsumer();
		setupProducer();
	}
	
	public void startHeartbeat() {
		new Thread() {
	        @Override
	        public void run() {
	        	while (true) {
		        	try {
//						Long.toString(new Date().getTime())
						producer.send(new ProducerRecord<String, String>("e", "test-button1-heartbeat", "heartbeat"));
						Thread.sleep(5000);
					} catch (Exception e) {
						throw new RuntimeException("Exception in heartbeat", e);
					}
	        	}
	        }
	    }.start();
		
	}
	public void sendDevParam( DevParam dp ) {
		producer.send(new ProducerRecord<String, String>("dp", dp.getKey(), dp.getValue()));
//		producer.send(new ProducerRecord<String, String>("dp", dp.getKey(), dp.getValue()),new Callback() {
//			@Override
//			public void onCompletion(RecordMetadata metadata, Exception e) {
//                if(e != null) e.printStackTrace();
//                if (metadata!=null) {
//                    System.out.println("The offset of the record we just sent is: " + metadata.offset());
//                }
//           }
//		});
	}	
		
	public void run() {
		boolean go = true;
		logger.info("Device Emulator Polling");
		while (go) {
			ConsumerRecords<String, String> records = consumer.poll(1000);
//					logger.info("Process any messages we got");
			for (ConsumerRecord<String, String> record : records) {
				// Device Parameter received, update working memory by sending back a parameter update
				if ("dpc".equals(record.topic())) {
					DevParam dp = new DevParam(record.key(), record.value());
					logger.debug("ECHO event as DevParam: " + dp + " offset: " + record.offset());
					sendDevParam( dp);
				}
			}
		}
		
	}
	public static void main(String[] args) {
		
		DeviceEmulator de = new DeviceEmulator();
		de.startHeartbeat();
		de.run();
	}

}
