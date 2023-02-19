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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.sodacan.api.module.ModuleContext;
import net.sodacan.api.module.VariableContext;
import net.sodacan.messagebus.MB;
import net.sodacan.messagebus.MBRecord;
import net.sodacan.messagebus.MBTopic;
import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ClockProvider;
import net.sodacan.module.ModuleMethods;
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
	
	private final static Logger logger = LoggerFactory.getLogger(Runtime.class);
	public static final int QUEUE_SIZE = 100;

	@JsonIgnore
	long timestamp;	// Set and used by Runtime

	protected Mode mode;
	protected String fullModuleName;
	protected ModuleContext moduleContext;
	protected VariableContext variableContext;
	protected List<String> subscriberTopicNames;
	protected String adminTopic;
	protected ClockProvider clockProvider;
	protected Future<?> topicFuture;
	protected MB mb;
	
//	protected BlockingQueue<MBRecord> queue = new LinkedBlockingQueue<MBRecord>(QUEUE_SIZE); 
	/**
	 * Setup a runtime for a single mode/module combination
	 * @param mode
	 * @param fullModuleName
	 */
	public Runtime( Mode mode, String fullModuleName) {
		// Remember the mode/module
		this.mode = mode;
		mb = mode.getMB();
		this.fullModuleName = fullModuleName;
		// Setup a context with the module structure and variables are available
		this.moduleContext = new ModuleContext(mode);
		moduleContext.fetchModule(fullModuleName);
		this.variableContext = moduleContext.getVariableContext();
		// Restore variable and offset context
		variableContext.restoreAll();
		// As soon as we get started, we listen for events from these subscriptions.
		subscriberTopicNames = variableContext.getListOfSubscriberTopics();
		this.adminTopic = ModuleMethods.getModuleAdminTopicName(mode.getModeName(), fullModuleName);
	}
	
	/**
	 * Create a map of topic names with starting offsets then open the topics
	 * 
	 */
	protected void openTopics() {
		Map<String,Long> topics = new HashMap<>();
		for (String topicName : subscriberTopicNames) {
			topics.put(topicName, variableContext.getOffset(topicName));
		}
		topics.put(adminTopic, variableContext.getOffset(adminTopic));
		MBTopic topic = mb.openTopics(topics);
		topicFuture = topic.follow((t) -> processRecord(t));
	}

	/**
	 * For each module, a single-stream of events arrive and call this method synchronously.
	 * We generally have three things to handle: a tick event, a variable (from a subscribed topic),
	 * and an admin event, typically a new module source code. 
	 * @param record
	 */
	protected void processRecord( MBRecord record) {
		variableContext.saveOffset(record.getTopic(), record.getOffset());
		logger.debug("Processing " + record);
//		if (record.getKey())
	}

	/**
	 * Setup for a flow of clock ticks
	 */
	protected void openClock() {
		clockProvider = mode.getClockProvider();
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
		return Instant.ofEpochMilli(timestamp);
	}

	@JsonIgnore
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp.toEpochMilli();
	}

	/**
	 * This is where control comes when the runtime is started, typically in a separate thread.
	 * 
	 */
	@Override
	public void run() {
		try {
			openTopics();
			openClock();
			// We run until interrupted
			while (!Thread.currentThread().isInterrupted()) {
				Thread.sleep(60*1000);
			}
		} catch (Throwable e) {
			logger.error("Exception in runtime for " + fullModuleName );
			logger.error(e.getMessage());
			Throwable t = e.getCause();
			while (t!=null) {
				logger.error("  " + t.getMessage());
				t = t.getCause();
			}
		} finally {
			topicFuture.cancel(true);
		}
	}

}
