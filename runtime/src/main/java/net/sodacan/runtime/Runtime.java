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
package net.sodacan.runtime;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.sodacan.api.module.ModuleContext;
import net.sodacan.api.module.VariableContext;
import net.sodacan.messagebus.MBTopic;
import net.sodacan.mode.Mode;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.Variable;
/**
 * <p>Each module gets its own runtime which contains the a Mode, The compiled Module structure, the Variabled associated with the module, and a Ticker. </p>
 * <p>The current time is stored as a variable in the Variable list so that it is available should the module need to restart from SateStore. 
 * The same goes for the module source code.</p>
 * <p>If the runtime cannot restore from StateStore, then it "rewinds" the input queue and processes events from the beginning. 
 * The first (or near first) item in the input queue will be a variable containing the (initial) source code of the module.</p>
 * <p>During a recovery operation, the Ticker needs to be "pushed" along. Essentially, the clock is set to the timestamp of each message which 
 * allows the ticker to add Ticks into the stream in the same order they were received the first time through. 
 * This avoids having to fill the message queue with thousands if not millions of "tick" messages.</p>
 * @author John Churin
 *
 */
public class Runtime implements Runnable {
	@JsonIgnore
	Instant timestamp;	// Set and used by Runtime

	protected Mode mode;
	protected String fullModuleName;
	protected ModuleContext moduleContext;
	protected VariableContext variableContext;
	protected List<String> subscriberTopicNames;
	protected List<MBTopic> subscriberTopics;
	
	/**
	 * Setup a runtime for a single module
	 * @param mode
	 * @param fullModuleName
	 */
	public Runtime( Mode mode, String fullModuleName) {
		this.mode = mode;
		this.fullModuleName = fullModuleName;
		this.moduleContext = new ModuleContext(mode);
		moduleContext.fetchModule(fullModuleName);
		this.variableContext = moduleContext.getVariableContext();
		variableContext.restoreAll();
		subscriberTopicNames = variableContext.getListOfSubscriberTopics();
	}
	
	/**
	 * For each subscriber, open it's corresponding topic. Don't "follow" the topics yet.
	 * 
	 */
	protected void openTopics() {
		subscriberTopics = new LinkedList<>();
		for (String topicName : subscriberTopicNames) {
			subscriberTopics.add(variableContext.getMb().openTopic(topicName, 0));	// <<<<<<<<<<< OFFSET s/b from state.
		}
	}
	
	/**
	 * The Timestamp of a module originates in a clock plugin and is set by the Runtime system. 
	 * It represents the time "now" as far as the current processing cycle of the module is concerned.
	 * This property is also accessible through system variables, which are also not persisted.
	 * It is a transient attribute, not persisted.
	 * @return
	 */
	@JsonIgnore
	public Instant getTimestamp() {
		return timestamp;
	}

	@JsonIgnore
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public void run() {
		
		
	}

}
