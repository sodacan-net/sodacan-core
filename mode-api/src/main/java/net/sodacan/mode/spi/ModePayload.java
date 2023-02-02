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
package net.sodacan.mode.spi;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>This class is used to communicate a mode from the message bus to Sodacan. It is (de)serialized from/to Json.</p>
 * @author John Churin
 *
 */
public class ModePayload {
	
	private String name;
	private Set<String> messageBusTypes;
	private Set<String> clockTypes;
	private Set<String> loggerTypes; 
	private Set<String> stateStoreTypes;
	
	public ModePayload() {
		
	}

	public ModePayload( ModePayloadBuilder mpb) {
		this.name = mpb.name;
		
		this.messageBusTypes = mpb.messageBusTypes;
		this.clockTypes = mpb.clockTypes;
		this.loggerTypes = mpb.loggerTypes; 
		this.stateStoreTypes = mpb.stateStoreTypes;

	}

	
	public String getName() {
		return name;
	}

	public Set<String> getMessageBusTypes() {
		return messageBusTypes;
	}

	public Set<String> getClockTypes() {
		return clockTypes;
	}

	public Set<String> getLoggerTypes() {
		return loggerTypes;
	}

	public Set<String> getStateStoreTypes() {
		return stateStoreTypes;
	}

	public static ModePayloadBuilder newModePayloadBuilder() {
		return new ModePayloadBuilder();
	}
	
	public static class ModePayloadBuilder {
		private String name;
		private Set<String> messageBusTypes;
		private Set<String> clockTypes;
		private Set<String> loggerTypes; 
		private Set<String> stateStoreTypes;
		private Set<PropertyChangeListener> listeners = new HashSet<>();

		protected ModePayloadBuilder() {
			
		}
		
		public ModePayloadBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public ModePayloadBuilder messageBus( String messageBusType ) {
			this.messageBusTypes.add(messageBusType);
			return this;
		}

		public ModePayloadBuilder clock( String clockType ) {
			this.clockTypes.add(clockType);
			return this;
		}

		public ModePayloadBuilder stateStore( String stateStoreType ) {
			this.stateStoreTypes.add(stateStoreType);
			return this;
		}

		public ModePayloadBuilder logger( String loggerType ) {
			this.loggerTypes.add(loggerType);
			return this;
		}

		public ModePayloadBuilder listener( PropertyChangeListener listener ) {
			this.listeners.add(listener);
			return this;
		}
		
		public ModePayload build( ) {
			ModePayload mode = new ModePayload(this);
			return mode;
		}
	}

}
