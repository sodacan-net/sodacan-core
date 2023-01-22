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
import net.sodacan.module.value.Value;

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
		v.setValue(value);
	}
}
