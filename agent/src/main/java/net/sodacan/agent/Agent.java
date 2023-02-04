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
package net.sodacan.agent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sodacan.mode.spi.ModePayload;
import net.sodacan.mode.spi.ModulePayload;

/**
 * 
 * <p>In a separate thread, ask MessageBus for the list of modes. As modes arrive add them to our collection of ModePayloads. 
 * This list can grow over time. So the thread remains active.</p>
 * <p>In another thread, ask message bus for the list of modules.As module names arrive, find the correct mode, and launch a new runtime thread .</p>
 * <p>Individual runtimes will request Instant(s) from the clock plugin and module messages from the message bus plugin. So, the agent is responsible for starting
 * runtimes. </p>
 * <p>The agent also reacts to runtime shutdowns. This happens when the underlying message bus shuffles and decides that a module's 
 * messages should be delivered to a different agent. At that point we gracefully shut down the runtime. This is an optional step since the message 
 * bus will no longer deliver messages to those modules. However, if the message bus should again send messages for a module to this agent, then we
 * need to get the current state since we didn't receive the intervening messages.</p>
 * @author John Churin
 *
 */
public class Agent {
	private String name;
	// A list of modes that we know about. Note, this list is updated during operation so we need to keep an eye on additions.
	private Map<String,ModePayload> modes = new ConcurrentHashMap<>();
	// A list of modules that we know about. Note, this list is updated during operation so we need to keep an eye on additions.
	private Map<String,ModulePayload> modules = new ConcurrentHashMap<>();

	/**
	 * The name of an agent is not strictly required except when a specific agent requires one or more modules to
	 * run on that agent such as when the underlying computer has GPIO pins. 
	 * @return the name of this agent
	 */
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public static void main(String[] args) {
	}

}
