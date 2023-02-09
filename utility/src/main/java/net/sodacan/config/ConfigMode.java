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
package net.sodacan.config;

import java.util.Set;

/**
 * One of these per mode that we want to provide without the aid of looking into a database of modes.
 * @author John Churin
 *
 */
public class ConfigMode {
	private String name;
	private String messageBus;
	private String clock;
	private String logger; 
	private String stateStore;
	
	public ConfigMode() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessageBus() {
		return messageBus;
	}

	public void setMessageBus(String messageBus) {
		this.messageBus = messageBus;
	}

	public String getClock() {
		return clock;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public String getStateStore() {
		return stateStore;
	}

	public void setStateStore(String stateStore) {
		this.stateStore = stateStore;
	}
	
	
}
