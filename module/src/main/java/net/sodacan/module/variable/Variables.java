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
package net.sodacan.module.variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sodacan.SodacanException;
import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDef.VariableType;

/**
 * Contains a map of variables.
 * @author John Churin
 *
 */
public class Variables {
	Map<String,Variable> variables = new TreeMap<>();

	public Variables() {
	}
	/**
	 * Lookup a variable. This should never fail. If it does, we throw an exception.
	 * @param name
	 * @return
	 */
	public Variable find(String name) {
		Variable v = variables.get(name);
		if (v==null) {
			throw new SodacanException("Variable not found, should not happen: " + name);
		}
		return v;
	}
	
	/**
	 * Variables are stored by short name so we need to iterate through the list looking be long name. 
	 * If the list gets long, then we should have a second map by full name. Both maps are relatively 
	 * permanent so update would be rare.
	 * @param topic
	 * @param namespace
	 * @param name
	 * @param instance
	 * @return
	 */
	public Variable find(String topic, String namespace, String instance, String name) {
		Variable v = null;
		for (String key : variables.keySet() ) {
			v = variables.get(key);
			if (topic.equals(v.getVariableDef().getTopic()) &&
			namespace.equals(v.getVariableDef().getNamespace()) && 
			(instance==null && v.getVariableDef().getInstance()==null ||
			instance !=null && instance.equals(v.getVariableDef().getInstance())) &&
			name.equals(v.getVariableDef().getName())) 
			{
				return v;
			}
		}
		throw new SodacanException("Variable not found, should not happen: " + name);
	}

	/**
	 * Add a variable to this collection of variable. Should be called
	 * at the start of execution, not along the way. All variables should be 
	 * defined prior to execution.
	 * @param vd ValueDefinition
	 * @param v Initial Value
	 */
	public void addVariable(VariableDef vd, Value v) {
		variables.put(vd.getShortName(), new Variable(vd, v));
	}
	/**
	 * Add a variable using it's initial value as the value
	 * @param vd
	 * @param v
	 */
	public void addVariable(VariableDef vd) {
		addVariable(vd, vd.getInitialValue());
	}

	/**
	 * Reset the changed flag in all variables
	 */
	public void resetChanged() {
		variables.forEach((name,variable)-> variable.resetChanged());
	}
	/**
	 * Return a list of variables that have changed during the current cycle
	 * @return List of variables
	 */
	public List<Variable> getListOfChangedVariableNames() {
		List<Variable> selected = new ArrayList<>();
		variables.forEach((name,variable)-> {if (variable.isChangedInCycle()) selected.add(variable);});
		return selected;
	}
	
	/**
	 * Find and update a variable. It must be a subscribe variable, the only kind that make sense to 
	 * be updated by the arrival of a message.
	 * We also hang onto the message in the variable allowing access to, for example, when the message driving this variable arrived.
	 * @param message
	 * @return The variable we updated
	 */
	public Variable setVariable (ModuleMessage message) {
		Variable v = variables.get(message.getName());
		if (v==null) {
			// If we're uninterested in this message, we should probably ignore it rather than throw an error
//			return null;
			throw new SodacanException("Variable from message from " + message.getProducer() + " not found in this module");
		}
		// Only allowed to update subscription variables
		if (!(v.getVariableDef().getVariableType()==VariableType.subscribeVariable)) {
			throw new SodacanException("Only messages should modify subscription variables");
		}
		// Store the message in the variable
		v.setMessage(message);
		// and finally deserialize the value and store it in the variable
		v.setValue(Value.deserialize(message.getValue()));
		return v;
	}

	/**
	 * Set a value in the variables collection
 	 * Note: This will validate the value against any constraints in the variable definition, also, the
 	 * variable will be marked as modified.
	 * @param variableName
	 * @param value
	 */
	public void setVariable( String variableName, Value value) {
		Variable v = variables.get(variableName);
		if (v==null) {
			throw new SodacanException("Missing variable " + variableName + " at runtime");
		}
		// Make sure its a subscription variable
		if (v.getVariableDef().getVariableType()==VariableType.subscribeVariable) {
			throw new SodacanException("Only messages should modify subscription variables");
		}

		v.setValue(value);
	}
	@Override
	public String toString() {
		return variables.toString();
	}
	
}
