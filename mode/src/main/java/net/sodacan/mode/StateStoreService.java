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
package net.sodacan.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.mode.spi.ModeProvider;
import net.sodacan.mode.spi.StateStoreProvider;
import net.sodacan.mode.spi.VariablePayload;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variables.ModuleVariables;
import net.sodacan.module.variables.Variables;
/**
 * <p>StateStore could be called Variable Store because the collection of variables for a module
 * comprise the totality of state for that module.</p>
 * <p>Save Variable State to all providers. Even though we're passed the entire set of variables for a module, our job is to pick
 * through the variables and save only those that have changed. We leave behind the class structure of variables and serialize to
 * json which in turn is what we pass to the interested plugin(s) for storage. This approach limits the scope of the plugin to IO 
 * rather than having to deal with a lot of Sodacan internals.</p>
 * <p>When restoring state, we'll collect all variables from all plugins interested. If a plugin just wants to lurk, that's fine. 
 * It should then return zero variables when asked to return stored variables.</p>
 * 
 * @author John Churin
 *
 */
public class StateStoreService extends ModeService {
	private final static Logger logger = LoggerFactory.getLogger(StateStoreService.class);
	protected List<StateStoreProvider> providers = new ArrayList<>();

	public StateStoreService(Mode mode) {
		super(mode, StateStoreProvider.class);
	}

	@Override
	public void loadProviders(Set<String> types) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(types)) {
				providers.add((StateStoreProvider) provider);
				provider.setMode(getMode().getName());
				logger.info("Mode: " + getMode().getName() + " Types: " + types + " Provider: " + provider.getClass().getName());
			}
		}
	}

	@Override
	protected List<StateStoreProvider> getProviders() {
		return providers;
	}
	
	/**
	 * <p>Save the state of a (Module)Variable to all services. We only consider the set of variables for a module that have changed during a cycle. 
	 * The plugin will only see individual variables.
	 * Here we leave behind the class structure of the variable(s) and serialize each to
	 * json which in turn is what we pass to the interested plugin(s) for storage.</p>
	 * </p>
	 * <p>These fields are added to a small object, VariablePayload for conveyance to the plugin(s).</p>
	 * <p>State store should only keep the most recent version of whatever it saves. It is used strictly to recover state quickly, such as 
	 * when a module is evicted from memory due to inactivity or during a system or agent restart. 
	 * If a module needs to restore to an earlier state, that can be done by the much slower method of replaying the input stream.</p>

	 * @param variables The collection of variables, some of which may need saving.
	 */
	public void save(SodacanModule module, Variables variables) {
		for (Variable variable : variables.getListOfChangedVariables()) {
			VariablePayload p = newVariablePayload(module, variable);
			if (p!=null) {
				// Send to the interested plugin(s)
				for (StateStoreProvider provider : getProviders()) {
					provider.save(p);
				}
			}
		}
	}
	/**
	 * Grab the saved payloads and deserialize into a ModuleVariables structure.
	 * @param module The name of the module to return
	 * @return
	 */
	public ModuleVariables restoreAll(SodacanModule module) {
		ModuleVariables variables = new ModuleVariables();
		for (StateStoreProvider provider : getProviders()) {
			List<VariablePayload> vps = provider.restoreAll(module.getName());
			for (VariablePayload vp : vps) {
				ModuleVariable mv = (ModuleVariable)this.jsonToVariable(vp.getContent());
				variables.addVariable(mv);
			}
		}
		return variables;
	}
	
}
