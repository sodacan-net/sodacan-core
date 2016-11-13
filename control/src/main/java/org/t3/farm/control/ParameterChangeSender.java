package org.t3.farm.control;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterChangeSender implements RuleRuntimeEventListener {
	static Logger logger = LoggerFactory.getLogger(ParameterChangeSender.class);
	private Producer<String, String> producer;
	
	public ParameterChangeSender( ) {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("event.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Error loading Kafka producer properties", e);
		}
		producer = new KafkaProducer<String, String>(props);
		
	}
	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		String ruleName;
		if (event.getRule()!=null) {
			ruleName = event.getRule().getName();
		} else {
			ruleName = "Stream";
		}
		logger.debug("Insert from (" + ruleName + ") " + event.getObject());
		if (event.getObject() instanceof DevParamChange) {
			DevParamChange dpc = (DevParamChange) event.getObject();
			producer.send(new ProducerRecord<String, String>("dpc", dpc.getKey(),  dpc.getNewValue()));
//			DevParam dp = new DevParam(dpc.getKey(), dpc.getNewValue());
//			insertDevParam( dp );
		}
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		String ruleName;
		if (event.getRule()!=null) {
			ruleName = event.getRule().getName();
		} else {
			ruleName = "Stream";
		}
		logger.debug("Update from (" + ruleName + ") " + event.getObject());
		
	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		String ruleName;
		if (event.getRule()!=null) {
			ruleName = event.getRule().getName();
		} else {
			ruleName = "Stream";
		}
		logger.debug("Delete from (" + ruleName + ") " + event.getOldObject());
		
	}

}
