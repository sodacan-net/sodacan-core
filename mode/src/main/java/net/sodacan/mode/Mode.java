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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sodacan.SodacanException;
import net.sodacan.mode.service.ClockService;
import net.sodacan.mode.service.LoggerService;
import net.sodacan.mode.service.MessageBusService;
import net.sodacan.mode.service.StateStoreService;

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
	
	private String name;
	private Set<String> messageBusTypes;
	private Set<String> clockTypes;
	private Set<String> loggerTypes; 
	private Set<String> stateStoreTypes;
	
	private MessageBusService messageBusService = new MessageBusService(this);
	private ClockService clockService = new ClockService(this);
	private LoggerService loggerService = new LoggerService(this);
	private StateStoreService stateStoreService = new StateStoreService(this);
	
//	private functionService funtionService;	// Include namespace and fn name

	private static Map<String,Mode> instances = new ConcurrentHashMap<>();

	private static ThreadLocal<Mode> threadMode = new ThreadLocal<>();

	/**
	 * Setup the mode, services, and providers underneath mode.
	 * @param mb
	 */
	private Mode(ModeBuilder mb) {	
		this.name = mb.name;
		this.messageBusTypes = mb.messageBusTypes;
		this.clockTypes = mb.clockTypes;
		this.loggerTypes = mb.loggerTypes; 
		this.stateStoreTypes = mb.stateStoreTypes;
		this.messageBusService.loadProviders(messageBusTypes);
		this.clockService.loadProviders(clockTypes);
		this.loggerService.loadProviders(loggerTypes);
		this.stateStoreService.loadProviders(stateStoreTypes);
		instances.put(mb.name, this);
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
		if (threadMode.get()!=null) {
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
		private Set<String> messageBusTypes = new HashSet<String>();
		private Set<String> clockTypes = new HashSet<String>();
		private Set<String> loggerTypes = new HashSet<String>(); 
		private Set<String> stateStoreTypes = new HashSet<String>();

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
		
		public Mode build( ) {
			Mode mode = new Mode(this);
			return mode;
		}
	}

}
