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
package net.sodacan.api.variable;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.SodacanException;
import net.sodacan.messagebus.MB;
import net.sodacan.messagebus.MBRecord;
import net.sodacan.messagebus.MBTopic;
import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.VariablePayload;
import net.sodacan.module.ModuleMethods;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variables.Variables;
/**
 * <p>Mode-specific functions for saving and restoring variables to/from the MessageBus.
 * An instance of this class is good for at least one cycle for a single module[instance] in a single mode.
 * This class is normally paired with an instance of the ModuleLoader class.</p>
 * @author John Churin
 *
 */
public class VariableLoader {
	protected static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
	}
	private final static Logger logger = LoggerFactory.getLogger(VariableLoader.class);

	private Mode mode;
	private SodacanModule module;
	private String modeName;
	private String moduleName;
	private String instanceName;
	private MB mb;
	private Variables variables;
	private String stateTopicName;
	// Holds offsets as loaded from state topic, if any.
	private Map<String, Long> offsets = new HashMap<>();
	// Holds recent offsets which we compare to offsets to know if we need to update the state table.
	private Map<String, Long> newOffsets = new HashMap<>();
	
	
	/**
	 * Construct a VariableLoader based on the specified mode and module
	 * @param mode
	 */
	public VariableLoader(Mode mode, SodacanModule module) {
		this.mode = mode;
		this.module = module;
		this.modeName = mode.getModeName();
		this.moduleName = module.getName();
		this.instanceName = module.getInstanceName();
		this.variables = module.createVariablesMap();
		this.stateTopicName = ModuleMethods.getModuleStateTopicName(modeName, moduleName, instanceName);
		// We'll need a message bus
		mb = mode.getMB();
 	}

	/**
	 * Serialize a ModuleVariable to Json
	 * @param variable
	 * @return Json string representing Variable
	 */
	public static String variableToJson( Variable variable ) {
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
	 * Deserialize a json string into a single ModuleVariable
	 * @param json
	 * @return A ModuleVariable
	 */
	public static ModuleVariable jsonToVariable( String json) {
		ModuleVariable variable;
		try {
			variable = mapper.readValue(json, ModuleVariable.class);
		} catch (JsonProcessingException e) {
			throw new SodacanException("Error deserializing variable from string: " + json, e);
		}
		return variable;
	}

	/**
	 * Return a new VariablePayload for use by plugins. The VariablePayload has limited dependencies
	 * and therefore maps nicely to/from json.
	 * @return A new VariablePayload or null if no payload possible (we only do ModuleVariables)
	 */
	public VariablePayload newVariablePayload(SodacanModule module,  Variable variable) {
		if (!(variable instanceof ModuleVariable)) {
			return null;
		}
		ModuleVariable mv = (ModuleVariable)variable;
		VariablePayload p = VariablePayload.newVariablePayloadBuilder()
				.mode(mode.getModeName())
				.topic(module.getName())
				.variableName(mv.getVariableDef().getFullName())
				.instanceKey(mv.getVariableDef().getInstance())
				.content(variableToJson(mv))
				.build();
		return p;
	}

	/**
	 * <p>Get the current list of variables stored in the module's state topic. We normally only do this when a module is loaded.
	 * The variables can stay in memory as long as we don't crash. However, we should be saving the variables
	 * that have changed at the end of each cycle, along with the offset of the topic we last read from.
	 * </p>
	 */
	protected Map<String,MBRecord> fetchModuleVariables() {
		MBTopic topic = mb.openTopic(stateTopicName, 0);
		Map<String,MBRecord> mbrs = topic.snapshot();
		return mbrs;
	}


	/**
	 * <p>Call this after completing a cycle specifying the input (subscription) topic processed and
	 * the new offset from that topic.</p>
	 * @param eventTopic
	 * @param eventOffset
	 */
	public void saveOffset(String eventTopic, long eventOffset ) {
		mb.produce(stateTopicName, 
				ModuleMethods.getEventKeyName(eventTopic), 
				Long.toString(eventOffset));
	}
	
	/**
	 * <p>Save the state of a (Module)Variable to MessageBus. We only consider the set of 
	 * variables for a module that have changed during a cycle. The plugin will only see 
	 * individual variables. Construct a topic name we use for this process.
	 * We also need to construct a key that identifies the specified variable.
	 * Leave behind the class structure of the variable(s) and serialize each to
	 * json which in turn is what we pass to the MessageBus for storage.</p>
	 * </p>
	 * <p>These fields are added to a small object, VariablePayload for conveyance to the plugin(s).</p>
	 * <p>State store should only keep the most recent version of whatever it saves. It is used strictly to recover state quickly, such as 
	 * when a module is evicted from memory due to inactivity or during a system or agent restart. 
	 * If a module needs to restore to an earlier state, that can be done by the much slower method of replaying the input stream.</p>

	 * @param variables The collection of variables, some of which may need saving.
	 */
	public void save(Variables variables) {
		for (Variable variable : variables.getListOfChangedVariables()) {
			VariablePayload p = newVariablePayload(module, variable);
			if (p!=null) {
				mb.produce(stateTopicName, 
							ModuleMethods.getVariableKeyName(variable.getName()), 
							variable.getValue().serialize());
			}
		}
	}

	/**
	 * The fetchModuleVariables method does the heavy lifting, we simple
	 * take the resulting values and set them in the corresponding variables map.
	 * @return
	 */
	public void restoreAll() {
		if (variables==null) {
			throw new SodacanException("Module should be loaded before restoreAll (variables) is called");
		}
		Map<String,MBRecord> records = fetchModuleVariables();
		for (MBRecord record : records.values()) {
			if (record.getKey().startsWith("v-")) {
				String variableName = record.getKey().substring(2);
				Variable variable = variables.find(variableName);
				Value value = Value.deserialize(record.getValue());
				variable.setValue(value);
			} else if (record.getKey().startsWith("t-")) {
				String eventName = record.getKey().substring(2);
				this.offsets.put(eventName, Long.valueOf(record.getValue()));
			} else {
				logger.error("Invalid record in module state topic " + record.getKey());
			}
		}
	}


}
