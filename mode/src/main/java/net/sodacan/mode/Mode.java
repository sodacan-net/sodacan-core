package net.sodacan.mode;
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


import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.SodacanException;
import net.sodacan.mode.spi.ModePayload;
import net.sodacan.mode.spi.ModePayload.ModePayloadBuilder;

/**
 * <p>Mode is a major operational partitioning mechanism in the Sodacan runtime. All IO is partitioned by mode.
 * For example, in test mode, IO might be to a flat file while in production, data might be in a database.
 * Sodacan core can handle any number of modes simultaneously.</p>
 * <p>A mode instance will seek to establish services needed for that mode. 
 * Mode is not passed as an argument in most cases. Rather, mode is stored in and accessed from thread local storage.</p>
 * <p>On activation of the thread, such as when a REST api occurs, call the static method getInstance with the mode name.
 * In this case, mode is typically in a session or cookie. the latter is preferred because session setting might also
 * be stored by mode.</p>
 * <p>One thread can only be in one mode at a time. However, and number of threads can be in the same mode. Service providers
 * should be aware of this and be prepared to handle thread synchronization if applicable. In the case of calls from modules, 
 * the service provider can be certain of the single thread. Remember that calls of this sort are by module and, if applicable, 
 * module instance.</p>
 * <p>Persisting modes is a bit tricky because of Sodacan rules about no cross-mode interaction. 
 * So, we leave Mode persistence to the individual services. On startup, we poll each of the
 * services asking for which mode(s) they apply to, if any. This allows the service to use its own method for persisting it's state.
 * This process reconstitutes the Mode list.</p>
 * @author John Churin
 *
 */
public class Mode {
	private static ObjectMapper mapper; 
	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
	}

	private String name;
	private Set<String> messageBusTypes;
	private Set<String> clockTypes;
	private Set<String> loggerTypes; 
	private Set<String> stateStoreTypes;
	
	private MessageBusService messageBusService = new MessageBusService(this);
	private ClockService clockService = new ClockService(this);
	private LoggerService loggerService = new LoggerService(this);
	private StateStoreService stateStoreService = new StateStoreService(this);
	private Set<PropertyChangeListener> listeners = new HashSet<>();
	
//	private functionService funtionService;	// Include namespace and fn name

	private static Map<String,Mode> instances = new ConcurrentHashMap<>();

	private static ThreadLocal<Mode> threadMode = new ThreadLocal<>();

	public void initialize() {
		this.messageBusService.loadProviders(messageBusTypes);
		this.clockService.loadProviders(clockTypes);
		this.loggerService.loadProviders(loggerTypes);
		this.stateStoreService.loadProviders(stateStoreTypes);
		// Keep a list of all modes
		instances.put(this.name, this);

		/**
		 * Note: recovered modes don't need listeners, which are used for unit testing.
		 */
		for (PropertyChangeListener listener : listeners) {
			this.messageBusService.addPropertyChangeListener(listener);
			this.clockService.addPropertyChangeListener(listener);
			this.loggerService.addPropertyChangeListener(listener);
			this.stateStoreService.addPropertyChangeListener(listener);
		}
	}
//	public String serialize
	public ModePayload createModePayload() {
		ModePayloadBuilder mpb = ModePayload.newModePayloadBuilder().name(name);
		for (String mbt : this.messageBusTypes) mpb.messageBus(mbt);
		for (String ct : this.clockTypes) mpb.clock(ct);
		for (String lt : this.loggerTypes) mpb.logger(lt);
		for (String sst : this.stateStoreTypes) mpb.stateStore(sst);
		return mpb.build();
	}

	public static Mode createModeFromJson( String json ) {
		ModePayload modePayload;
		try {
			modePayload = mapper.readValue(json, ModePayload.class);
		} catch (JsonProcessingException e) {
			throw new SodacanException("Roor mapping json to ModePayload: " + json);
		}
		return new Mode(modePayload);
	}
	
	/**
	 * Serialize a mode to Json
	 * @param mode
	 * @return Json string representing the Mode
	 */
	public String getJsonPayload( ) {
		ModePayload modePayload = createModePayload();
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
	 * Setup the mode, services, and providers underneath mode. Also any listeners requested.
	 * @param mb
	 */
	private Mode(ModeBuilder mb) {	
		this.name = mb.name;
		
		this.messageBusTypes = mb.messageBusTypes;
		this.clockTypes = mb.clockTypes;
		this.loggerTypes = mb.loggerTypes; 
		this.stateStoreTypes = mb.stateStoreTypes;
		this.listeners = mb.listeners;
//		initialize();	// Do this explicitly

	}
	
	/**
	 * Create a mode starting from a mode payload
	 * @param modePayload
	 * @return
	 */
	public Mode( ModePayload modePayload ) {
		this.name = modePayload.getName();
		this.messageBusTypes = modePayload.getMessageBusTypes();
		this.clockTypes = modePayload.getClockTypes();
		this.loggerTypes = modePayload.getLoggerTypes(); 
		this.stateStoreTypes = modePayload.getStateStoreTypes();
//		initialize();
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
	
	public MessageBusService getMessageBusService() {
		return messageBusService;
	}
		
	public StateStoreService getStateStoreService() {
		return stateStoreService;
	}

	public ClockService getClockService() {
		return clockService;
	}

	public LoggerService getLoggerService() {
		return loggerService;
	}
		
	/**
	 * Find the named mode and set it in thread local storage.
	 * Access the "current" mode using the static method getInstance()
	 * @param modeName
	 * @return mode
	 */
	public static void setModeInThread(String modeName) {
		Mode mode = instances.get(modeName);
		if (mode==null) {
			throw new SodacanException("Missing mode: " + modeName);
		}
		Mode currentMode = threadMode.get();
		if (currentMode!=null) {
			if (currentMode.equals(mode)) {
				return;
			}
			throw new SodacanException("Clear the mode in this thread before setting a another mode: " + modeName);
		}
		threadMode.set(mode);
	}
	
	public static void clearModeInThread( ) {
		threadMode.remove();
	}
	
	/**
	 * Return the mode for this thread.
	 * @return Mode of the current thread
	 */
	public static Mode getInstance() {
		Mode mode = threadMode.get();
		if (mode==null) {
			throw new SodacanException("No thread local mode found");
		}
		return mode;
	}
	
	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return "Mode: " + getName();
	}
	/**
	 * Create a new, empty, builder for a Mode
	 */
	public static ModeBuilder newModeBuilder() {
		return new ModeBuilder();
	}

	public static class ModeBuilder {
		private String name = null;
		private Set<String> messageBusTypes = new HashSet<>();
		private Set<String> clockTypes = new HashSet<>();
		private Set<String> loggerTypes = new HashSet<>(); 
		private Set<String> stateStoreTypes = new HashSet<>();
		private Set<PropertyChangeListener> listeners = new HashSet<>();

		protected ModeBuilder() {
		}
		
		/**
		 * Set the name of this mode. When we build, a check for duplicate mode names is made.
		 * @param name
		 * @return
		 */
		public ModeBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public ModeBuilder messageBus( String messageBusType ) {
			this.messageBusTypes.add(messageBusType);
			return this;
		}

		public ModeBuilder clock( String clockType ) {
			this.clockTypes.add(clockType);
			return this;
		}

		public ModeBuilder stateStore( String stateStoreType ) {
			this.stateStoreTypes.add(stateStoreType);
			return this;
		}

		public ModeBuilder logger( String loggerType ) {
			this.loggerTypes.add(loggerType);
			return this;
		}

		public ModeBuilder listener( PropertyChangeListener listener ) {
			this.listeners.add(listener);
			return this;
		}
		
		public Mode build( ) {
			Mode mode = new Mode(this);
			return mode;
		}
	}

}
