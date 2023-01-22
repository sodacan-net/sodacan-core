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
package net.sodacan.module.expression;

import java.time.ZonedDateTime;

import net.sodacan.SodacanException;
import net.sodacan.module.terminal.VariableRefExpression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDefs;
import net.sodacan.module.variable.Variables;
/**
 * An assignment expression is a bit different from other binary operators in that
 * only the right-hand-side of the assignment is "resolved" and then executed and the
 * resulting value is assigned to the variable on the left side of the assignment.
 * @author John Churin
 *
 */
public class AssignmentExpression extends BinaryOperator {

	AssignmentExpression(Expression left,Expression right) {
		super(left, right);
	}

	@Override
	public Value execute(Variables variables, ZonedDateTime now) {
		Value resolvedRightValue = resolve(variables, right.execute(variables,now));
		if (!(left instanceof VariableRefExpression)) {
			throw new SodacanException("Left side of assignment must be a variable");
		}
		VariableRefExpression vre = (VariableRefExpression)left;
		variables.setVariable(vre.toString(), resolvedRightValue);
		return resolvedRightValue;
	}
	
	/**
	 * Ignore this, not used here.
	 */
	@Override
	protected Value evaluate(Value leftValue, Value rightValue) {
		return null;
	}

}
