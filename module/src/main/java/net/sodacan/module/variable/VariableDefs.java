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

import java.util.Map;
import java.util.TreeMap;

import net.sodacan.SodacanException;
import net.sodacan.module.variables.ModuleVariables;
import net.sodacan.module.variables.Variables;

/**
 * A structure holding all variables for a module
 * @author John Churin
 *
 */
public class VariableDefs {

	protected Map<String,VariableDef> variablesDefs = new TreeMap<>();
	
	protected Map<String,String> shortNameToFullName = new TreeMap<>();

	/**
	 * Is the supplied name already known, and if so, return that
	 * @return
	 */
	public VariableDef find( String name ) {
		VariableDef v = variablesDefs.get(name);
		if (v!=null) {
			return v;
		}
		String fullName = shortNameToFullName.get(name);
		if (fullName==null) {
			return null;
		}
		v = variablesDefs.get(fullName);
		if (v==null) {
			throw new SodacanException("Should never happen: A variable cross reference from alias to full name is dangling");
		}
		return v;
	}
	/**
	 * Add a variable definition to the list of variables.
	 * This checks both fullNames and aliases (shortNames) for duplicates.
	 * @param variableDef The variable to be added
	 * @return Returns true if a new, unique variable was added, otherwise false
	 */
	public boolean addVariableDef( VariableDef variableDef) {
		// Short name must be unique
		String priorShortName = shortNameToFullName.put(variableDef.getShortName(),variableDef.getFullName());
		if (priorShortName!=null) {
			return false;
		}
		VariableDef prior = variablesDefs.put(variableDef.getFullName(), variableDef);
		if (prior!=null) {
			return false;
		}
		return true;
	}
	/**
	 * At runtime, find each variable definition and create a set of variables.
	 * @return A map of variables
	 */
	public Variables createVariablesMap() {
		ModuleVariables vs = new ModuleVariables();
		for (String key : variablesDefs.keySet()) {
			VariableDef vd = variablesDefs.get(key);
			vs.addVariable(vd, vd.getInitialValue());
		}
		return vs;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		boolean first = true;
		for (String key : variablesDefs.keySet()) {
			VariableDef vd = variablesDefs.get(key);
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append(vd);
		}
		sb.append(']');
		return sb.toString();
	}
	
	
}
