package org.t3.farm.control;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class GenerateEvents {
	private Producer<String, String> producer;

	public GenerateEvents() {
		Properties props = new Properties();
		try {
			props.load(GenerateEvents.class.getClassLoader().getResourceAsStream("event.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Unable to open properties file", e);
		}
//		long start = System.currentTimeMillis();
		producer = new KafkaProducer<String, String>(props);
	}
	
	void sendDevParam( String key, String value ) {
		producer.send(new ProducerRecord<String, String>("dp", key, value));
	}	
	void sendEvent( String key, String type ) {
		producer.send(new ProducerRecord<String, String>("e", key, type));
//		new Callback() {
//			@Override
//			public void onCompletion(RecordMetadata metadata, Exception e) {
//                if(e != null) e.printStackTrace();
//                if (metadata!=null) {
//                    System.out.println("The offset of the record we just sent is: " + metadata.offset());
//                }
//           }
//		});	
	}	

	public void close() {
		producer.close();
	}
	
	public static void main(String[] args) {
		GenerateEvents ge = new GenerateEvents();
//		ge.sendButtonEvent("fac1-dev1-state");
		ge.sendDevParam("test-button1-desc", "A button");
		ge.sendDevParam("test-light1-desc", "An LED light");
		ge.sendDevParam("test-light1-state", "off");
		// Sending toggles for the same device too rapidly will not always work since the state change needs to make a round trip to the device
		// So we add a small delay between sends.
		for (int x=0; x< 50;x++) {
			ge.sendEvent("test-button1-button", "button");
			try {
		        Thread.sleep(10);
		    } catch (final InterruptedException e) {
		        e.printStackTrace();
		    }
		}
		ge.close();
//	System.out.println(producer.partitionsFor("test101"));
	}
}
