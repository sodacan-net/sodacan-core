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
package net.sodacan.module.terminal;

import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variables;

public class VariableRefExpression extends TerminalExpression {

	public VariableRefExpression(String variableName) {
		super(new Value(variableName,true));
	}
	
	/**
	 * We do more than execute: If the variable found is an identifier,
	 * then we "dereference" it by returning the underlying value.
	 */
	@Override
	public Value resolve(Variables variables) {
		if (value.isVariable()) {
			return variables.findValue(value.getIdentifier());
		}
		return value;
	}
	@Override
	public String toString() {
		return value.getIdentifier();
	}

}
