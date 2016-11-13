package org.t3.farm.control;

import org.t3.farm.control.DevParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t3.farm.control.Engine;
import org.t3.farm.control.ParameterChangeSender;

public class SodaCan {
	private Engine engine = null;
	private KafkaConsumer<String, String> consumer = null;
	static Logger logger = LoggerFactory.getLogger(SodaCan.class);
	List<TopicPartition> topics = new ArrayList<TopicPartition>();
	List<TopicPartition> rewindTopics = new ArrayList<TopicPartition>();

	public SodaCan( ) {
		topics.add(new TopicPartition("e",0));
		topics.add(new TopicPartition("dp",0));
		rewindTopics.add(new TopicPartition("dp",0));
	}
	
	public void connect() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "shop1:9092");
		props.put("group.id", "SodaGroup");
		props.put("enable.auto.commit", "true");		// Offset is saved automatically
		props.put("auto.commit.interval.ms", 10*1000);	// at this interval
		props.put("auto.offset.reset", "earliest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		// Setup a consumer
		consumer = new KafkaConsumer<String, String>(props);
		consumer.assign(topics);
		consumer.seekToBeginning(rewindTopics);
	}
	
	public void run() {
		engine = new Engine();
		engine.addEventListener(new ParameterChangeSender());
//		long startTime = System.currentTimeMillis();
		boolean go = true;
		while (go) {
//					logger.info("Polling");
			ConsumerRecords<String, String> records = consumer.poll(1000);
//					logger.info("Process any messages we got");
			for (ConsumerRecord<String, String> record : records) {
				// Device Parameter received, update working memory
				if ("dp".equals(record.topic())) {
					DevParam dp = new DevParam(record.key(), record.value());
					engine.insertDevParam(dp);
				} else if ("e".equals(record.topic())) {
					if ("button".equals(record.value())) {
						engine.insertDevEvent(new ButtonEvent(record.key()));
//						System.out.println("Heartbeat count: " + engine.getHearbeatCount());
					} else if ("heartbeat".equals(record.value())) {
						engine.insertDevEvent (new HeartbeatEvent(record.key()));
					} else {
						logger.warn("invalid event type: " + record.value());
					}
				}
			}
		}
		
	}
	public static void main(String[] args) {
		SodaCan sc = new SodaCan();
		sc.connect();
		sc.run();
	}
	
}
