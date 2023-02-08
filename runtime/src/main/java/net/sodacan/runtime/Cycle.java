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
 * <p>The runtime provides the main cycle for one module. it begins be subscribing to a number of topics</p>
 * <ul>
 * <li>Mode ticker. All modules for a mode listen to the same topic. If the clock is "real", there will be one tick per minute, with no skips. If a static clock, same one-per minute, 
 * but the advancement is controlled by the user, not the wall clock. </li>
 * <li>Each of the topics subscribed to by the module.</li>
 * <li>This module's admin topic containing source code updates and other admin functions. This is an implied subscription. The module does not have to declare it.</li>
 * </ul>
 * <p>The event merge process controls the flow of events listed above into a cycle (one event at a time).
 * In short, the merge considers the oldest message first among the subscribed topics.</p>
 * <p>When a cycle completes, the affected variables are published to the module's publish topic. Also, all changed variables (including private ones, 
 * are published to the module's state topic.
 * The offset(s) from each subscribed topic is stored with the topics in the state store.</p>
 * 
 * <p>We then receive messages (each has one variable). Here's the steady-state happy path through the cycle. 
 * All of the following is synchronous, sequential, single threaded, but in a separate thread from all other module runtimes.
 * An in-memory collection of variables exists (extracted from state store on startup. The current source code, received via `admin-mode-moduleName` topic 
 * has been compiled into a runtime structure. </p>
 * <ul>
 * <li>Select the oldest message among the topics we subscribe to. Process it; Constituting the start of one "cycle". </li>
 * <li>If the incoming message is module source code, compile it and set the resulting SodacanModule in this runtime object. Process the new module 
 * (at this time, nothing should match, but in the future, we may have new predicates that are sensitive to a code change).</li>
 * <li>Ask the SodacanModule structure for the list of variables that it knows about.
 * For each variable from the (new) module, if it is not already in the reduced list, add the variable
 * and mark the variable changed so that it will be saved in module state topic. This allows a previously unknown variable to be added to the state
 * of the module without losing other variables.</li>
 * <li>If the incoming message is a variable (from another module), the variable in our in-memory variables is update. process with this message being the current message.</li>
 * <li>If the incoming message is a clock tick, then process with that tick, only: No other messages are considered when processing a clock tick.</li>
 * <li>At the end of each processing cycle, run through variables and send the ones that have changed that are publish type variables to the publish topic for this module. 
 * Reminder: Sodacan does not send messages to other modules. Rather, it publishes to a topic that other modules can subscribe to.</li>
 * <li>Also, send any variables (of any kind) that have changed in this cycle to the state store to facilitate recovery. Message offsets are also stored in the state topic.</li>
 * </ul>
 * <p>During startup, the state store is used to recover the various memory structures for the module: variables and the source code, compile anew from the module's state.
 * At that point, the module's state topic consumer can be closed. (We continue to publish to it of course.</p>
 * <p>Each runtime cycle runner gets its own thread, and we get our own instance of plugins. This satisfies the requirement for isolation between mode-modules.</p>
 * <h3>Implementation Notes</h3>
 * <p>We probably need to queue a number of records from each topic because Kafka may deliver a batch of one topic before sending any entries of another topic. This 
 * would appear that the variables from the first topic all go first to the cycle even though we don't yet know if other topics have older message.</p>
 * <p>ReductionConsumer is needed for state (no "follow") to load up state, which can stay in the ReductionConsumer - but that's only json!!</p>
 * <p>The main processing queue needs a single interface or class. We've got clock and module variables covered under variable. Source could be there, too. This allows the cycle to
 *  just see a smooth flow of variables and process accordingly, perhaps a simple dispatch based on `instanceof`.</p>
 *  <p>Does the state recovery topic hold inbound traffic (subscription variables) as well as changes made by the module (local and publish)? Answer is NO for now. 
 *  It is critical that the state topic keep the offsets for each of the input topics. Easy: Produce a "topic-offset-<topic-name>" key with the value being the offset.
 *  This will all get resolve on the next startup from the snapshot reductionConsumer on the state topic which presents these settings in a map for easy lookup. </p>
 *  <p></p>
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
		// Build a mode from the saved mode we found.
		mode = new Mode( modePayload );
		mode.initialize();
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
