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
package net.sodacan.message.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sodacan.SodacanException;

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
	private String messageBusType;
	private MessageBusProvider messageBusProvider;
	private String clockType;
	private ClockProvider clock;
	private List<String> loggerTypes; 
	private List<LoggerProvider> loggers;
	private String stateStoreType;
	private StateStoreProvider stateStoreProvider;
	
//	private functionProvider funtionProvider;	// Include namespace and fn name

	private static Map<String,Mode> instances = new ConcurrentHashMap<>();

	private static ThreadLocal<Mode> threadMode = new ThreadLocal<>();
	
	private Mode(ModeBuilder mb) {	
		this.name = mb.name;
		this.messageBusType = mb.messageBusType;
		this.clockType = mb.clockType;
		this.loggerTypes = mb.loggerTypes; 
		this.stateStoreType = mb.stateStoreType;
		// Locate the requested service(s)
		/////////
	}

	/**
	 * Find the named mode and set it in thread local storage.
	 * Access the "current" mode using the static method getInstance()
	 * @param modeName
	 * @return mode
	 */
	public static void setupThreadMode(String modeName) {
		Mode mode = instances.get(modeName);
		if (mode==null) {
			throw new SodacanException("Missing mode: " + modeName);
		}
		threadMode.set(mode);
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

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Create a new, empty, builder for a Mode
	 */
	public static ModeBuilder newModeBuilder() {
		return new ModeBuilder();
	}

	public static class ModeBuilder {
		private String name = null;
		private String messageBusType = null;
		private String clockType = null;
		private List<String> loggerTypes = new ArrayList<>(); 
		private String stateStoreType = null;

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
			this.messageBusType = messageBusType;
		}

		public ModeBuilder clock( String clockType ) {
			this.clockType = clockType;
		}

		public ModeBuilder addLogger( String loggerType ) {
			this.loggerTypes.add(loggerType);
		}
		
		public Mode build( ) {
			Mode mode = new Mode(this);
		}
	}

}
