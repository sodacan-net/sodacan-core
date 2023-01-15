package net.sodacan.message.kafka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements ConsumerRebalanceListener {
	private KafkaConsumer<String, String> consumer = null;
	private final static Logger logger = LogManager.getLogger();
	List<String> topics = new ArrayList<String>();
	List<String> rewindTopics = new ArrayList<String>();

	public Consumer( ) {
		topics.add("e");
		topics.add("dp");
		rewindTopics.add("dp");
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
		// We don't actually get started until we've been assigned our topics/partitions
		consumer.subscribe(topics, this);
	}
	
	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		if (!partitions.isEmpty()) {
			logger.debug("Topic assignment(s) revoked: " + partitions + " stopping rules");
//			if (engine!=null) {
//				engine.close();
//				engine = null;
//			}
		}
	}
	/**
	 * Once we are assigned partitions, then we can start up the rule engine
	 * @param partitions
	 */
	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		// When we are assigned a DP topic, we must start at the beginning of the topic
		if (!partitions.isEmpty()) {
			logger.debug("Assigned to topic(s): " + partitions );
			partitions.forEach((tp)->{ if (rewindTopics.contains(tp.topic())) {
				consumer.seek(tp, 0);
				logger.debug("Rewinding " + tp);
				}});
		}
	}

	public void run() {
//		long startTime = System.currentTimeMillis();
		boolean go = true;
		while (go) {
//					logger.info("Polling");
			ConsumerRecords<String, String> records = consumer.poll(1000);
//					logger.info("Process any messages we got");
			for (ConsumerRecord<String, String> record : records) {
				// Main loop
			}
		}
		
	}
	public static void main(String[] args) {
		Consumer sc = new Consumer();
		sc.connect();
		sc.run();
	}
	
}
