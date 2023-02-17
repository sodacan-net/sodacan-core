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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.sodacan.SodacanException;
import net.sodacan.api.module.ModuleContext;
import net.sodacan.api.module.VariableContext;
import net.sodacan.messagebus.MBRecord;
import net.sodacan.messagebus.MBTopic;
import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ClockProvider;
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
	
	@JsonIgnore
	long timestamp;	// Set and used by Runtime

	protected Mode mode;
	protected String fullModuleName;
	protected ModuleContext moduleContext;
	protected VariableContext variableContext;
	protected List<String> subscriberTopicNames;
	protected List<MBTopic> subscriberTopics;
	protected ClockProvider clockProvider;
	protected LinkedList<BlockingQueue<MBRecord>> queues; 
	
	/**
	 * Setup a runtime for a single mode/module combination
	 * @param mode
	 * @param fullModuleName
	 */
	public Runtime( Mode mode, String fullModuleName) {
		// Remember the mode/module
		this.mode = mode;
		this.fullModuleName = fullModuleName;
		// Setup a context with the module structure and variables are available
		this.moduleContext = new ModuleContext(mode);
		moduleContext.fetchModule(fullModuleName);
		this.variableContext = moduleContext.getVariableContext();
		variableContext.restoreAll();
		// As soon as we get started, we listen for events from these subscriptions.
		subscriberTopicNames = variableContext.getListOfSubscriberTopics();
		
	}
	
	/**
	 * For each subscriber, open it's corresponding topic. Don't actually "follow" the topics yet.
	 * 
	 */
	protected void openTopics() {
		subscriberTopics = new LinkedList<>();
		for (String topicName : subscriberTopicNames) {
			subscriberTopics.add(variableContext.getMb().openTopic(topicName, 0));	// <<<<<<<<<<< OFFSET s/b from state.
		}
	}
	
	/**
	 * Setup for a flow of clock ticks
	 */
	protected void openClock() {
		clockProvider = mode.getClockProvider();
	}
	/**
	 * OK, we're now going to put together a number of blocking queues.
	 */
	protected void followAll() {
		queues = new LinkedList<>();
		queues.add(clockProvider.follow());		// Ticks
		subscriberTopics.forEach((t) -> queues.add(t.follow())); // All subscriptions
	}

	/**
	 * Look through each of the queues to determine the oldest timestamp. Return a record from that queue.
	 * @return The MBRecord from the queue with the oldest timestamp at the head of the queue.
	 */
	protected MBRecord nextRecord() {
		try {
			MBRecord oldest = null;
			BlockingQueue<MBRecord> oldestQueue = null;
			for (BlockingQueue<MBRecord> q : queues) {
				MBRecord r = q.peek();
				if (r!=null) {	// Queue could be empty
					if (oldest==null) {	// First item in queue
						oldest = r;
						oldestQueue = q;
						timestamp = r.getTimestamp();
					} else {
						if (r.getTimestamp() < oldest.getTimestamp()) {
							oldest = r;
							oldestQueue = q;
						}
					}
				}
			}
			if (oldestQueue!=null) {
					return oldestQueue.take();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new SodacanException("Error in runtime for module " + fullModuleName, e);
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
			followAll();
			// We run until interrupted
			while (!Thread.currentThread().isInterrupted()) {
				MBRecord nextRecord = nextRecord();
				if (nextRecord!=null) {
					logger.debug("Processing " + nextRecord);
				} else {
					Thread.sleep(100);
//					logger.debug("Nothing to process");
				}
			}
		} catch (Throwable e) {
			logger.error("Exception in runtime for " + fullModuleName );
			logger.error(e.getMessage());
			Throwable t = e.getCause();
			while (t!=null) {
				logger.error("  " + t.getMessage());
				t = t.getCause();
			}
		}
	}

}
