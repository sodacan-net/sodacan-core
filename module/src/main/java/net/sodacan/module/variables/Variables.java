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
package net.sodacan.module.variables;

import java.util.List;

import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variable;

/**
 * Use this interface to find and modify variables. 
 * To add a variable to a collection of variables, use an implementation
 * of this interface that has methods to add variables.
 * @author John Churin
 *
 */
public interface Variables {
	
	/**
	 * Lookup a variable. This should never fail. If it does, we throw an exception.
	 * @param name
	 * @return
	 */
	public Variable find(String name);
	public Value findValue(String name);
	
	/**
	 * Reset the changed flag in all variables
	 */
	public void resetChanged();
	
	/**
	 * Return a list of variables that have changed during the current cycle
	 * @return List of variables
	 */
	public List<Variable> getListOfChangedVariables();	
	/**
	 * Find and update a variable. It must be a subscribe variable, the only kind that make sense to 
	 * be updated by the arrival of a message.
	 * We also hang onto the message in the variable allowing access to, for example, when the message driving this variable arrived.
	 * @param message
	 * @return The variable we updated
	 */
	public Variable setVariable (ModuleMessage message);
	
	/**
	 * Set a value in the variables collection
 	 * Note: This will validate the value against any constraints in the variable definition, also, the
 	 * variable will be marked as modified.
	 * @param variableName
	 * @param value
	 */
	public void setVariable( String variableName, Value value);
		
}
