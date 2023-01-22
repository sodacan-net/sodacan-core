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

import java.time.ZonedDateTime;

import net.sodacan.module.expression.Expression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variable.VariableDefs;
import net.sodacan.module.variable.Variables;
/**
 * Terminal expressions just have a value and of course there are no children to execute.
 * Our subclasses just make construction easy. But they always result in a value.
 * @author John Churin
 *
 */
public abstract class TerminalExpression extends Expression {
	Value value;
	public TerminalExpression(Value value) {
		this.value = value;
	}
	
	/**
	 * To execute is to just return the value, which may still be a reference to a variable, not an actionable value.
	 * See Resolve in AvariableRefExpression
	 */
	@Override
	public Value execute(Variables variables, ZonedDateTime now) {
		return value;	
	}

}
