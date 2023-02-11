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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.SodacanException;
import net.sodacan.config.Config;
import net.sodacan.config.ConfigMode;
import net.sodacan.messagebus.MB;
import net.sodacan.mode.spi.ModePayload;
import net.sodacan.mode.spi.VariablePayload;
import net.sodacan.mode.spi.ModePayload.ModePayloadBuilder;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.variables.ModuleVariables;
import net.sodacan.module.variables.Variables;
import net.sodacan.mode.spi.StateStoreProvider;

/**
 * <p>Mode is a major operational partitioning mechanism in the Sodacan runtime. All IO is partitioned by mode.
 * For example, in test mode, IO might be to a flat file while in production, data might be in a database.
 * Sodacan core can handle any number of modes simultaneously.</p>
 * <p>A mode instance will seek to establish services needed for that mode. 
 * Mode is not passed as an argument in most cases. Rather, mode is stored in and accessed from thread local storage.</p>
 * <p>On activation of the thread, such as when a REST api occurs, call the static method getInstance with the mode name.
 * In this case, mode is typically in a cookie or session. the latter is preferred because session setting might also
 * be stored by mode.</p>
 * <p>One thread can only be in one mode at a time. However, any number of threads can be in the same mode. Service providers
 * should be aware of this and be prepared to handle thread synchronization if applicable. In the case of calls from modules, 
 * the service provider can be certain of the single thread: Calls of this sort are by module and, 
 * if applicable, module instance.</p>
 * <p>Persisting modes is a bit tricky because of Sodacan rules about no cross-mode interaction. 
 * All modes originate from the configuration file. This configuration provides a specific combination 
 * of plugins for each base mode.
 * All other modes are cloned from one of these base modes. But mode cloning also includes copying 
 * data to the new mode.
 * </p>
 * This process reconstitutes the Mode list.</p>
 * @author John Churin
 *
 */
public class Mode {
	// The list of all known baseModes, created on application startup.
	private static Map<String,BaseMode> baseModes = null;
	// The list of all known modes
	private static Map<String,Mode> modes = null;
	
	private static ThreadLocal<Mode> threadMode = new ThreadLocal<>();
	
	private String modeName;
	private String baseModeName;
	// Denormalize the BaseNode for speed
	private BaseMode baseMode;
	
	private MB mb = null;
	
	/**
	 * Find a Mode
	 * @param baseModeName
	 * @return The BaseNode or null
	 */
	public static Mode findMode(String modeName) {
		return modes.get(modeName);
	}
	
	/**
	 * Return a base Mode of null if not found.
	 * @param baseModeName
	 * @return The BaseNode or null
	 */
	public static BaseMode findBaseMode(String baseModeName) {
		return baseModes.get(baseModeName);
	}
	/**
	 * This crucial first step in the life of a mode begins with the configuration file 
	 * where most BaseModes originate. Each BaseMode get's its own Mode, as well.
	 * This method should be called only once and will return quietly if called again.
	 * @param config
	 */
	public static void configure( Config config) {
		// Been here already, don't do it again
		if (baseModes!=null) {
			return;
		}
		baseModes = new ConcurrentHashMap<>();
		modes = new ConcurrentHashMap<>();
		for (ConfigMode configMode : config.getModes()) {
			String name = configMode.getName();
			baseModes.put(name, new BaseMode(configMode));
			modes.put(name, new Mode(name,name));
		}
	}
	
	public Mode( String modeName, String baseModeName) {
		if (baseModes==null) {
			throw new SodacanException("Modes/BaseModes not setup, see Mode.configure()");
		}
		this.modeName = modeName;
		this.baseModeName = baseModeName;
		baseMode = findBaseMode(baseModeName);
		if (baseMode==null) {
			throw new SodacanException("Base Mode " + baseModeName + " not found for mode " + modeName);
		}
		modes.put(modeName, this);
	}
	/**
	 * Find the named mode and set it in thread local storage.
	 * Access the "current" mode using the static method getInstance()
	 * @param modeName
	 * @return mode
	 */
	public static void setModeInThread(String modeName) {
		Mode mode = modes.get(modeName);
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
	
	public String getModeName() {
		return modeName;
	}

	public String getBaseModeName() {
		return baseModeName;
	}

	public BaseMode getBaseMode() {
		return baseMode;
	}
	
	@Override
	public String toString() {
		return "Mode: " + getModeName() + "{" + getBaseModeName() + "}";
	}
	/**
	 * Convenience method to dig down into plugins to find the correct message bus implementation for this mode.
	 * @return MessageBus interface
	 */
	public MB getMB() {
		if (this.mb==null) {
			MessageBusService mbs = baseMode.getMessageBusService();
			mb = mbs.getMB();
		}
		return mb;
	}

	/**
	 * Convenience method to send something to the configured logger(s) for this mode.
	 * @param msg
	 */
	public void log(String msg) {
		baseMode.getLoggerService().log(msg);
	}

	/**
	 * Convenience method to save the state of a variable
	 * @param payload containing the serialized variable
	 */
	public void saveState( SodacanModule module, Variables variables) {
		baseMode.getStateStoreService().save(module, variables);
	}
	
	/**
	 * Convenience method to restore a module's variables
	 * @param module
	 * @return reconstituted ModuleVariables structure
	 */
	public ModuleVariables restoreAll(SodacanModule module) {
		return baseMode.getStateStoreService().restoreAll(module);
	}
}
