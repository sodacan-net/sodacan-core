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
package net.sodacan.mode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.SodacanException;
import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ModeProvider;
import net.sodacan.mode.spi.StateStoreProvider;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variables.Variables;
/**
 * <p>StateStore could be called Variable Store because the collection of variables for a module
 * comprise the totality of state for that module.</p>
 * <p>Save Variable State to all providers. Even though we're passed the entire set of variables for a module, our job is to pick
 * through the variables and save only those that have changed. We leave behind the class structure of variables and serialize to
 * json which in turn is what we pass to the interested plugin(s) for storage.</p>
 * <p>When restoring state, we'll collect all variables from all plugins interested. If a plugin just wants to lurk, that's fine. 
 * It should then return zero variables when asked to return stored variables.</p>
 * 
 * @author John Churin
 *
 */
public class StateStoreService extends ModeService {
	private final static Logger logger = LogManager.getLogger();
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
				logger.info("Mode: {}, Types: {}, Provider: {}",getMode().getName(),types, provider.getClass().getName());
			}

		}
	}

	@Override
	protected List<StateStoreProvider> getProviders() {
		return providers;
	}
	
	/**
	 * <p>Save Variable State to all providers. Even though we're passed the entire set of variables for a module, our job is to pick
	 * through the variables and save only those that have changed. We leave behind the class structure of variables and serialize to
	 * json which in turn is what we pass to the interested plugin(s) for storage.</p>
	 * 
	 * @param msg
	 */
	public void save(Variables variables) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		for (Variable variable : variables.getListOfChangedVariables()) {
			try {
				String json;
				json = mapper
							.writerWithDefaultPrettyPrinter()
							.writeValueAsString(variable);
				System.out.println("Save: " + json);
				
				for (StateStoreProvider provider : getProviders()) {
					provider.save(json);
				}
			} catch (JsonProcessingException e) {
				throw new SodacanException("Error serializing a variable: " + variable, e);
			}
		}

	}
	
}
