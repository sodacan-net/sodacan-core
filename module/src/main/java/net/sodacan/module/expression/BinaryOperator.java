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

import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDefs;
import net.sodacan.module.variable.Variables;
/**
 * Binary expressions have two sub-expressions
 * @author John Churin
 *
 */
public abstract class BinaryOperator extends Expression {
	Expression left;
	Expression right;

	/**
	 * Construct a binary expression
	 * @param left
	 * @param right
	 */
	public BinaryOperator(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	/**
	 * Our subclasses must implement this method to do the actual math.
	 * @param leftValue
	 * @param rightValue
	 * @return The Value resulting from the operation
	 */
	abstract protected Value evaluate(Value leftValue, Value rightValue);
	/**
	 * All binary operators work the same way: Execute the left and right operands, resolve to concrete values, 
	 * and finally, do the math (or whatever) in a specific subclass such as add two values and return the result value.
	 */
	@Override
	public Value execute(Variables variables, ZonedDateTime now) {
		Value resolvedLeftValue = left.resolve(variables,now );
		Value resolvedRightValue = right.resolve(variables, now);
		// Now actually do the math part in the specific subclass
		return evaluate( resolvedLeftValue, resolvedRightValue);
	}

}
