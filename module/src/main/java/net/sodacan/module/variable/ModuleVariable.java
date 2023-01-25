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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.sodacan.SodacanException;
import net.sodacan.module.value.Value;
import net.sodacan.module.value.ValueDeserializer;
import net.sodacan.module.value.ValueSerializer;

public class ModuleVariable implements Variable {

	private VariableDef variableDef;
	
	@JsonProperty("value")
	@JsonSerialize(using = ValueSerializer.class)
	@JsonDeserialize(using = ValueDeserializer.class)
	private Value value;
	@JsonIgnore
	private boolean changedInCycle;

	private ModuleVariable() {
		
	}

	public ModuleVariable(VariableDef variableDef, Value value) {
		this();	// Avoid warning, needed for Jackson deserialization
		this.variableDef = variableDef;
		this.value = value;
	}
	
	@JsonIgnore
	public Value getValue() {
		return value;
	}
	
	@JsonIgnore
	public void setValue(Value value) {
		if(!variableDef.validateAgainstConstraints(value)) {
			throw new SodacanException("Value " + variableDef.getName() + " does not match any constraints");
		}
		if (0!=this.value.compare(value)) {
			this.value = value;
			changedInCycle = true;
		}
	}
	
	public VariableDef getVariableDef() {
		return variableDef;
	}
	
	public void resetChanged() {
		changedInCycle = false;
	}

	public boolean isChangedInCycle() {
		return changedInCycle;
	}

	public void setChangedInCycle(boolean changedInCycle) {
		this.changedInCycle = changedInCycle;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(variableDef.toString());
		sb.append('=');
		sb.append(value.toString());
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModuleVariable) {
			ModuleVariable other = (ModuleVariable)obj;
			return (getName().equals(other.getName()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public Value getAttribute(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String attributeName) {
		// TODO Auto-generated method stub
		
	}

	@JsonIgnore
	@Override
	public String getName() {
		return getVariableDef().getName();
	}

}
