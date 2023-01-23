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

import net.sodacan.SodacanException;
import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDef.VariableType;

/**
 * One variable: A variable references its variable definition (VariableDef) which is immutable and contains a value which is mutable.
 * @author John Churin
 *
 */
public class Variable {
	private VariableDef variableDef;
	private Value value;
	private boolean changedInCycle;
	private ModuleMessage message; // Only when from a message

	
	public Variable(VariableDef variableDef, Value value) {
		this.variableDef = variableDef;
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}
	
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

	public ModuleMessage getMessage() {
		return message;
	}

	public void setMessage(ModuleMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(variableDef.toString());
		sb.append('=');
		sb.append(value.toString());
		return sb.toString();
	}

	
	
}
