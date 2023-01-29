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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.sodacan.SodacanException;
import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.ShortcutVariable;
import net.sodacan.module.variable.SubscribeVariable;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.VariableDef.VariableType;
/**
 * A collection of variables defined within a module.
 * @author John Churin
 *
 */
public class ModuleVariables extends BaseVariables implements Variables {
	// This map can contain alias entries meaning more than one entry for a single variable.
	// This becomes important for serialize/deserialize
	@JsonIgnore
	private transient Map<String,ModuleVariable> variables = new TreeMap<>();

	private List<ModuleVariable> uniqueVariables = new ArrayList<>();
	private Map<String,ShortcutVariable> shortcutVariables = new TreeMap<>();
	
	public ModuleVariables() {
		
	}
	
	/**
	 * The number of unique variables
	 * @return
	 */
	public int uniqueVariableCount() {
		return uniqueVariables.size();
	}

	/**
	 * The number of names pointing to variables
	 * @return
	 */
	public int nameCount() {
		return variables.size();
	}

	/**
	 * Lookup a variable in this module's variables.
	 * @param name
	 * @return
	 */
	public Variable find(String name) {
		Variable v = variables.get(name);
		return v;
	}
	public Value findShortcutValue(String name) {
		// If it's a shortcut, then we need to do one more lookup
		ShortcutVariable sv = findShortcut(name);
		if (sv==null) {
			return null;	// Shortcut not found
		}
		String un = sv.getUnderlyingName();
		Variable uv = find(un);
		if (uv==null) {
			return null;	// Underlying not there - weird
		}
		Value uval = uv.getValue();
		return new Value((sv.getConstraint().equals(uval.toString())));

	}

	public ShortcutVariable findShortcut(String name) {
		ShortcutVariable v = shortcutVariables.get(name);
		return v;
	}
	
	public List<ModuleVariable> getUniqueVariables() {
		return uniqueVariables;
	}

	public List<ShortcutVariable> getShortcutVariables() {
		return new ArrayList<ShortcutVariable>(shortcutVariables.values());
	}
	
	public void setUniqueVariables(List<ModuleVariable> uniqueVariables) {
		uniqueVariables.forEach((variable)->{
			this.uniqueVariables.add(variable);
			variables.put(variable.getName(), variable);
			if (variable.getVariableDef().getAlias()!=null) {
				variables.put(variable.getVariableDef().getAlias(), variable);
			}
		});
	}

	public void setShortcutVariables(List<ShortcutVariable> shortcutVariables) {
		shortcutVariables.forEach((ShortcutVariable)->{this.shortcutVariables.put(ShortcutVariable.getName(), ShortcutVariable);});
	}

	/**
	 * Add a list of variables, as from persistence
	 * @param variables
	 */
	public void addAllVariables( List<ModuleVariable> variables) {
		variables.forEach((variable) -> {addVariable(variable);});
	}
	
	/**
	 * Add a variable to this collection of variables. Should be called
	 * at the start of execution, not along the way. All variables should be 
	 * defined prior to execution. This may add a second entry for an alias.
	 * In any case, we maintain two collections of variables. The dictionary of
	 * variables by alias and full name and a unique list of variable suitable for
	 * serialization.
	 * 
	 * For any variableDef with constraints, we also create shorcut variables.
	 * Because of this, we can end up with more than one variable created for a
	 * single variable definition. The shortcut variables are conditional expressions,
	 * returning a boolean.
	 */
	public void addVariable(ModuleVariable v) {
		String alias = v.getVariableDef().getAlias();
		String fullName = v.getVariableDef().getFullName();
		if (variables.containsKey(fullName)) {
			throw new SodacanException("Duplicate variable name: " + fullName);
		}
		if (alias!=null) {
			if (variables.containsKey(alias)) {
				throw new SodacanException("Duplicate variable (alias) name: " + alias);
			}
			// If alias is same a full name, don't bother
			if (!alias.equals(fullName)) {
				variables.put(alias, v);
			}
		}
		variables.put(fullName, v);
		// In any case, keep a list of unique variables.
		uniqueVariables.add(v);
		// Add some extra variables based on constraints
		addConstraintVariables(v);
	}
	/**
	 * x {y,z} becomes x.y meaning x==y and x.z meaning x==z
	 * @param v
	 */
	protected void addConstraintVariables( ModuleVariable v) {
		String alias = v.getVariableDef().getAlias();
		String fullName = v.getVariableDef().getFullName();
		// Make the ShortcutVariable
		for (String id : v.getVariableDef().getConstraintIdentifiers()) {
			if (alias!=null) {
				StringBuffer sb = new StringBuffer();
				sb.append(alias);
				sb.append('.');
				sb.append(id);
				String name = sb.toString();
				shortcutVariables.put(name, new ShortcutVariable(name, id, alias));
			}
			if (fullName!=null) {
				StringBuffer sb = new StringBuffer();
				sb.append(fullName);
				sb.append('.');
				sb.append(id);
				String name = sb.toString();
				shortcutVariables.put(name, new ShortcutVariable(name, id, fullName));
			}
		}
	}

	/**
	 * Add a variable to this collection of variables. Should be called
	 * at the start of execution, not along the way. All variables should be 
	 * defined prior to execution.
	 * @param vd ValueDefinition
	 * @param v Initial Value
	 */
	public Variable addVariable(VariableDef vd, Value v) {
		if (vd.getVariableType()==VariableType.subscribeVariable) {
			SubscribeVariable sv = new SubscribeVariable(vd, v);
			addVariable( sv );
			return sv;
		} else {
			ModuleVariable mv = new ModuleVariable(vd, v);
			addVariable( mv );
			return mv;
		}
	}
	
	/**
	 * Add a variable using it's initial value as the value
	 * @param vd
	 * @param v
	 */
	public Variable addVariable(VariableDef vd) {
		return addVariable(vd, vd.getInitialValue());
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
	public List<Variable> getListOfChangedVariables() {
		List<Variable> selected = new ArrayList<>();
		variables.forEach((name,variable)-> {if (variable.isChangedInCycle()) selected.add(variable);});
		return selected;
	}
	
	/**
	 * Find and update a variable. It must be a subscribe variable, the only kind that makes sense to 
	 * be updated by the arrival of a message.
	 * We also hang onto the message in the variable allowing access to, for example, when the message driving this variable arrived.
	 * @param message
	 * @return The variable we updated
	 */
	public Variable setVariable (ModuleMessage message) {
		ModuleVariable vm = variables.get(message.getName());
		if (vm==null) {
			// If we're uninterested in this message, we should probably ignore it rather than throw an error
//			return null;
			throw new SodacanException("Variable from message from " + message.getProducer() + " not found in this module");
		}
		// Only allowed to update subscription variables
		if (!(vm.getVariableDef().getVariableType()==VariableType.subscribeVariable)) {
			throw new SodacanException("Only messages should modify subscription variables");
		}
		SubscribeVariable v;
		if( vm instanceof SubscribeVariable) {
			v = (SubscribeVariable)vm;
		} else {
			throw new SodacanException("A subscribe variable is the only kind of variable that can receive a message");
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
		ModuleVariable v = variables.get(variableName);
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
		StringBuffer sb = new StringBuffer();
		sb.append("Variables: " );
		sb.append(variables.toString());
		sb.append("\nShortcuts: " );
		sb.append(shortcutVariables.values());
		return sb.toString();
	}

}
