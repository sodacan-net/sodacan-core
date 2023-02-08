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
import java.util.Set;

import net.sodacan.api.kafka.SodacanProducer;
import net.sodacan.api.kafka.TopicAdmin;
import net.sodacan.mode.Mode;

/**
 * <p>Create the Sodacan top-level topics. All topics involved are preserved if they already exist. 
 * Therefore, this initialize procedure can be run safely at any time without data loss.</p>
 * @author John Churin
 *
 */
public class Initialize {
	public static final String AGENT_STATUS = "Agent-Status";
	public static final String AGENT_CONFIG = "Agent-Config";
	public static final String LOGGER = "Logger";
	public static final String MODES = "Modes";
	public static final String MODULES = "Modules";
	public static final String DEFAULT_MODE = "default";
	public static final String DEFAULT_CLOCK = "real";
	public static final String DEFAULT_LOGGER = "memory";
	public static final String DEFAULT_MESSAGE_BUS = "memory";
	public static final String DEFAULT_STATE_STORE = "memory";
	public static final String EVENT_SUFFIX = "-event";
	public static final String STATE_SUFFIX = "-state";

	private TopicAdmin topicAdmin;

	/**
	 */
	public Initialize() {
		topicAdmin = TopicAdmin.getInstance();

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
	 * <p>A default mode is always available. We create it here. Note: We create the default mode each time we're called.
	 * Duplicates in the mode topic can be compressed out later. </p>
	 * @return True if we created a mode
	 */
	public boolean setupDefaultMode(boolean verbose) {
		SodacanProducer producer = new SodacanProducer();
		try {
			Mode mode = Mode.newModeBuilder()
					.name(DEFAULT_MODE)
					.clock(DEFAULT_CLOCK)
					.messageBus(DEFAULT_MESSAGE_BUS)
					.stateStore(DEFAULT_STATE_STORE)
					.logger(DEFAULT_LOGGER)
					.build();
			String json = mode.getJsonPayload();
			if (verbose) System.out.println("Mode created:\n" + json);
			producer.put(MODES, DEFAULT_MODE, json);
			return true;
		} finally {
			producer.close();
		}
	}
	
}
