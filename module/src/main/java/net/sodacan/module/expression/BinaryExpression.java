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

import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDefs;
/**
 * Binary expressions have two sub-expressions
 * @author John Churin
 *
 */
public abstract class BinaryExpression extends Expression {
	Expression left;
	Expression right;

	/**
	 * Construct a binary expression
	 * @param left
	 * @param right
	 */
	public BinaryExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	abstract protected Value evaluate(Value leftValue, Value rightValue);

	@Override
	public Value execute(VariableDefs variables) {
		Value resolvedLeftValue = resolve(variables, left.execute(variables));
		Value resolvedRightValue = resolve(variables, right.execute(variables));
		return evaluate( resolvedLeftValue, resolvedRightValue);
	}

}
