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
import java.util.List;

import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variable;
/**
 * A composite list of Variable collections
 * @author John Churin
 *
 */
public class CompositeVariables implements Variables {
	List<Variables> variabless = new ArrayList<>();

	public CompositeVariables(Variables... variabless) {
		for (Variables v : variabless) {
			this.variabless.add(v);
		}
	}

	@Override
	public Variable find(String name) {
		for (Variables vs : variabless) {
			Variable v = vs.find(name);
			if (v!=null) {
				return v;
			}
		}
		return null;
	}

	@Override
	public Value findValue(String name) {
		for (Variables vs : variabless) {
			Value va = vs.findValue(name);
			if (va!=null) {
				return va;
			}
		}
		return null;
	}

	/**
	 * Reset all variables at the start of a cycle.
	 * At the composite-level, we simply tell out composite variables to reset.
	 * we use this flag at the end to determine which producer variables will
	 * result in a message being broadcast.
	 */
	@Override
	public void resetChanged() {
		variabless.forEach((variables)-> { variables.resetChanged();});

	}

	@Override
	public List<Variable> getListOfChangedVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable setVariable(ModuleMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVariable(String variableName, Value value) {
		// TODO Auto-generated method stub

	}

}
