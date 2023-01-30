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

import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.SodacanException;
import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ModeProvider;
import net.sodacan.mode.spi.VariablePayload;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.Variable;
/**
 * <p>A Mode instance has one ModeService instance per class of service: Logger, Clock, message, and StateStore.
 * The subclasses of this class have methods for coordinating access to a specific provider function.
 * For example, to generate a log entry, the Sodacan Runtime will ask the mode for the logging service.</p>
 * 
 * <p>The logging service will satisfy the logging request by iterating through each of the available providers
 * and calling each one in turn.</p>
 * 
 * <p>Because Sodacan may have multiple modes at the same time, the mode name is always supplied in provider calls.
 * The name of the mode shouldn't be important to a provider other than to distinguish one mode from the next. So,
 * in the case of a file-based logger, the mode could be used as a folder name to distinguish log output for different modes.</p>
 * 
 * @author John Churin
 *
 */
public abstract class ModeService {
	private Mode mode;
	private ServiceLoader<? extends ModeProvider> loader = null;
	ObjectMapper mapper;

	public ModeService(Mode mode, Class<? extends ModeProvider> providerClass) {
		this.mode = mode;
		// Create a loader if needed (we only need one per class of service)
		if (loader==null) {
			loader = ServiceLoader.load(providerClass);
		}
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);

	}

	/**
	 * Create a list of providers that satisfy at least one of the requested types.
	 * For example, if the mode wants a memory-based logger, then file or message
	 * based loggers are skipped (for this mode).
	 * @param types A set of one or more types requested
	 */
	abstract void loadProviders( Set<String> types);

	public Mode getMode() {
		return mode;
	}

	public ServiceLoader<? extends ModeProvider> getLoader() {
		return loader;
	}

	protected abstract List<? extends ModeProvider> getProviders();

	/**
	 * Serialize a variable to Json
	 * @param variable
	 * @return Json string representing Variable
	 */
	protected String variableToJson( Variable variable ) {
		try {
			String json;
			json = mapper
						.writerWithDefaultPrettyPrinter()
						.writeValueAsString(variable);
			return json;
		} catch (JsonProcessingException e) {
			throw new SodacanException("Error serializing variable: " + variable, e);
		}
	}
	
	/**
	 * Return a new VariablePayload for use by MessageBus and StateStore plugins
	 * @return A new VariablePayload or null if no payload possible (we only do ModuleVariables)
	 */
	public VariablePayload newVariablePayload(Module module,  Variable variable) {
		if (!(variable instanceof ModuleVariable)) {
			return null;
		}
		ModuleVariable mv = (ModuleVariable)variable;
		VariablePayload p = VariablePayload.newVariablePayloadBuilder()
				.mode(this.getMode().getName())
				.topic(null)
				.variableName(mv.getVariableDef().getFullName())
				.instanceKey(mv.getVariableDef().getInstance())
				.content(variableToJson(mv))
				.build();
		return p;
	}

}
