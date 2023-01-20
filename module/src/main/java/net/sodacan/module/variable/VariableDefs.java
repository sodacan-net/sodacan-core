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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sodacan.SodacanException;

/**
 * A structure holding all variables for a module
 * @author John Churin
 *
 */
public class VariableDefs {

	protected Map<String,VariableDef> variables = new TreeMap<>();
	
	protected Map<String,String> shortNameToFullName = new TreeMap<>();

	/**
	 * Reset the changed flag in all variables
	 */
	public void resetChanged() {
		variables.forEach((name,variable)-> variable.resetChanged());
	}
	/**
	 * Is the supplied name already known, and if so, return that
	 * @return
	 */
	public VariableDef find( String name ) {
		VariableDef v = variables.get(name);
		if (v!=null) {
			return v;
		}
		String fullName = shortNameToFullName.get(name);
		if (fullName==null) {
			return null;
		}
		v = variables.get(fullName);
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
		VariableDef prior = variables.put(variableDef.getFullName(), variableDef);
		if (prior!=null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Return a list of variables that have changed during the current cycle
	 * @return
	 */
	public List<VariableDef> getListOfChangedVariableNames() {
		List<VariableDef> selected = new ArrayList<VariableDef>();
		variables.forEach((name,variable)-> {if (variable.isChangedInCycle()) selected.add(variable);});
		return selected;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		boolean first = true;
		for (String key : variables.keySet()) {
			VariableDef vd = variables.get(key);
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
