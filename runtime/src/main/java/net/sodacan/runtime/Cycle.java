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

import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ModePayload;
import net.sodacan.mode.spi.ModulePayload;
import net.sodacan.module.statement.SodacanModule;

/**
 * <p>We provide the main cycle for one module. The main flow is to start the ticker clock and start asking for messages destined for this module.
 * NOTE: We may not have a module yet, ie nothing to "run". However, an early if not the first message should contain a variable with the source code which we compile 
 * and set. We can ask the module to make a "variables" collection if it doesn't yet exist. We then receive messages (each has one variable). Here's what we 
 * do for a happy path. 
 * All of the following is synchronous, sequential, single threaded, but in a separate thread from all other module runtimes.</p>
 * <ul>
 * <li>Populate the variables collection with new value. </li>
 * <li>If the new value is module source code, compile it and set the resulting SodacanModule in this object.</li>
 * <li>Pause until the timestamp of the message is behind the "timestamp" of the clock. </li>
 * >li>When a timestamp is ready, process one cycle.</li>
 * <li>When a message is ready, process one cycle.</li>
 * <li>At the end of each cycle, run through variables and send the ones that have changed that are publish type variables to the message bus plugin(s).</li>
 * <li>Also, send any variables (of any kind) that have changed this cycle to the state store.</li>
 * </ul>
 * <p>Each runtime cycle runner gets its own thread, and we get our own instance of plugins. This satisfies the requirement for isolation between mode-modules.</p>
 * @author John Churin
 *
 */
public class Cycle implements Runnable {
	private ModePayload modePayload;
	private ModulePayload modulePayload;
	private SodacanModule module = null;
	private Mode mode = null;
	
	public Cycle(ModePayload modePayload, ModulePayload modulePayload ) {
		this.modePayload = modePayload;
		this.modulePayload = modulePayload;
		// Build a mode from the save mode we found.
		mode = new Mode( modePayload );
	}

	@Override
	public void run() {
		
	}

	public String getModeName() {
		return modulePayload.getModeName();
	}

	public String getModuleName() {
		return modulePayload.getModuleName();
	}
	
	public String getInstanceName() {
		return modulePayload.getInstanceName();
	}
	
	public ModePayload getModePayload() {
		return modePayload;
	}

	public ModulePayload getModulePayload() {
		return modulePayload;
	}

	public SodacanModule getModule() {
		return module;
	}

	public Mode getMode() {
		return mode;
	}	
	
}
