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

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.SodacanException;
import net.sodacan.api.kafka.TopicAdmin;
import net.sodacan.api.utility.ModuleMethods;
import net.sodacan.config.Config;
import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ModePayload;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.variable.Variable;

/**
 * Create the Sodacan top-level topics. All topics involved in this are preserved if they already exist. Therefore, this procedure can be run safely at any time.
 * @author John Churin
 *
 */
public class Initialize {
	private static final String AGENT_STATUS = "Agent-Status";
	private static final String AGENT_CONFIG = "Agent-Config";
	private static final String LOGGER = "Logger";
	private static final String MODES = "Modes";
	private static final String MODULES = "Modules";
	private static final String DEFAULT_MODE = "default";
	private static final String DEFAULT_CLOCK = "real";
	private static final String DEFAULT_LOGGER = "memory";
	private static final String DEFAULT_MESSAGE_BUS = "memory";
	private static final String DEFAULT_STATE_STORE = "memory";
	private static final String EVENT_SUFFIX = "-event";
	private static final String STATE_SUFFIX = "-state";

	private TopicAdmin topicAdmin;
	ObjectMapper mapper;

	/**
	 */
	public Initialize() {
		topicAdmin = new TopicAdmin();
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);

	}

	public Set<String> getTopicSet() {
		// Get the list of topics we know about
		List<String> topicList = topicAdmin.listTopics();
		Set<String> topicSet = new HashSet<>(topicList);
		return topicSet;
	}

	/**
	 * Setup the top-level topics needed for Sodacan to operate.
	 * @return true if one or more topics had to be created, false if all topics are in place
	 */
	public boolean setupTopics(boolean verbose) {
		Set<String> topicSet = getTopicSet();
		// Get the set of topics we know about
		boolean r = false;
		if (!topicSet.contains(AGENT_STATUS)) {
			topicAdmin.createTopic(AGENT_STATUS,true);
			if (verbose) System.out.println(AGENT_STATUS + " created");
			r = true;
		}
		if (!topicSet.contains(AGENT_CONFIG)) {
			topicAdmin.createTopic(AGENT_CONFIG,true);
			if (verbose) System.out.println(AGENT_CONFIG + " created");
			r = true;
		}
		if (!topicSet.contains(LOGGER)) {
			topicAdmin.createTopic(LOGGER,true);
			if (verbose) System.out.println(LOGGER + " created");
			r = true;
		}
		if (!topicSet.contains(MODES)) {
			topicAdmin.createTopic(MODES,true);
			if (verbose) System.out.println(MODES + " created");
			r = true;
		}
		if (!topicSet.contains(MODULES)) {
			topicAdmin.createTopic(MODULES,true);
			if (verbose) System.out.println(MODULES + " created");
			r = true;
		}
		return r;
	}
	/**
	 * Setup a producer to use in the API
	 * @return An open producer
	 */
	public Producer<String, String> openProducer() {
		Properties props = new Properties();
		String url = Config.getInstance().getKafka().getUrl();
		Long lingerMs = Config.getInstance().getKafka().getProducer().getLingerMs();
		props.put("bootstrap.servers", url);
		props.put("linger.ms", lingerMs);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		return producer;
	}
	/**
	 * Serialize a mode to Json
	 * @param mode
	 * @return Json string representing the Mode
	 */
	public String modePayloadToJson( ModePayload modePayload ) {
		try {
			String json;
			json = mapper
						.writerWithDefaultPrettyPrinter()
						.writeValueAsString(modePayload);
			return json;
		} catch (JsonProcessingException e) {
			throw new SodacanException("Error serializing mode: " + modePayload, e);
		}
	}
	

	/**
	 * A default mode is always available. We create it here. Note: We create the default mode anytime we're called.
	 * Duplicates in the mode topic can be compressed out later.
	 * @return True if we created a mode
	 */
	public boolean setupDefaultMode(boolean verbose) {
		Producer<String,String> producer = openProducer();
		Mode mode = Mode.newModeBuilder()
				.name(DEFAULT_MODE)
				.clock(DEFAULT_CLOCK)
				.messageBus(DEFAULT_MESSAGE_BUS)
				.stateStore(DEFAULT_STATE_STORE)
				.logger(DEFAULT_LOGGER)
				.build();
		String json = modePayloadToJson( mode.createModePlayload());
		if (verbose) System.out.println("Mode created:\n" + json);
		ProducerRecord<String, String> pr = new ProducerRecord<>(MODES, DEFAULT_MODE, json);
		producer.send(pr);
		return true;
	}
	
	/**
	 * <p>Setup the topics for a single module.</p>
	 * <p>Each module needs two topics, one for messages published from that topic, the other is
	 * for storing the state of the module, which is needed for recovery of a module's state.</p>
	 */
	public boolean setupModule( Mode mode, SodacanModule module) {
		Set<String> topicSet = getTopicSet();
		String topicName = ModuleMethods.getTopicName( mode, module );
		String eventTopicName = topicName + EVENT_SUFFIX;
		String stateTopicName = topicName + STATE_SUFFIX;
		boolean r = false;
		if (!topicSet.contains(eventTopicName)) {
			topicAdmin.createTopic(eventTopicName,false);
			r = true;
		}
		if (!topicSet.contains(stateTopicName)) {
			topicAdmin.createTopic(stateTopicName,false);
			r = true;
		}
		return r;
	}
}
