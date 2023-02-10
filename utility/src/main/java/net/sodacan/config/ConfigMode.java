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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

/**
 * One of these per mode that we want to provide without the aid of looking into a database of modes.
 * @author John Churin
 *
 */
public class ConfigMode {
	private String name;
	private Map<String,String> messageBus;
	private Map<String,String> clock;
	private Map<String,String> logger;
	private Map<String,String> stateStore;
	
	public ConfigMode() {
		
	}

	public ConfigMode(ConfigModeBuilder cmb) {
		this.name = cmb.name;
		this.messageBus = cmb.messageBus;
		this.clock = cmb.clock;
		this.logger = cmb.logger;
		this.stateStore = cmb.stateStore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@JsonAnyGetter
	public Map<String,String> getMessageBus() {
		return messageBus;
	}

	public void setMessageBus(Map<String,String> messageBus) {
		this.messageBus = messageBus;
	}

	@JsonAnyGetter
	public Map<String, String> getClock() {
		return clock;
	}

	public void setClock(Map<String, String> clock) {
		this.clock = clock;
	}

	@JsonAnyGetter
	public Map<String, String> getLogger() {
		return logger;
	}

	public void setLogger(Map<String, String> logger) {
		this.logger = logger;
	}

	@JsonAnyGetter
	public Map<String, String> getStateStore() {
		return stateStore;
	}

	public void setStateStore(Map<String, String> stateStore) {
		this.stateStore = stateStore;
	}

	public static ConfigModeBuilder newConfiguModeBuilder() {
		return new ConfigModeBuilder();
	}
	
	public static class ConfigModeBuilder {
		private String name;
		private Map<String,String> messageBus;
		private Map<String,String> clock;
		private Map<String,String> logger;
		private Map<String,String> stateStore;
		
		private ConfigModeBuilder() {
			messageBus = new HashMap<String,String>();
			clock = new HashMap<String,String>();
			logger = new HashMap<String,String>();
			stateStore = new HashMap<String,String>();
		}
		
		public ConfigModeBuilder name( String name) {
			this.name = name;
			return this;
		}
		
		public ConfigModeBuilder messageBusType( String pluginType) {
			this.messageBus.put("pluginType", pluginType);
			return this;
		}
		
		public ConfigModeBuilder clockType( String pluginType) {
			this.clock.put("pluginType", pluginType);
			return this;
		}
		
		public ConfigModeBuilder loggerType( String pluginType) {
			this.logger.put("pluginType", pluginType);
			return this;
		}
		
		public ConfigModeBuilder stateStoreType( String pluginType) {
			this.stateStore.put("pluginType", pluginType);
			return this;
		}

		public ConfigModeBuilder messageBusProperty( String key, String value) {
			this.messageBus.put(key, value);
			return this;
		}
		
		public ConfigModeBuilder clockProperty( String key, String value) {
			this.clock.put(key, value);
			return this;
		}
		
		public ConfigModeBuilder loggerProperty( String key, String value) {
			this.logger.put(key, value);
			return this;
		}
		
		public ConfigModeBuilder stateStoreProperty( String key, String value) {
			this.stateStore.put(key, value);
			return this;
		}
		
		public ConfigMode build() {
			return new ConfigMode(this);
		}
	}
}
