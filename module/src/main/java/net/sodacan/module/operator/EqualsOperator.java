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
package net.sodacan.module.operator;

import net.sodacan.module.expression.BinaryOperator;
import net.sodacan.module.expression.Expression;
import net.sodacan.module.value.Value;

public class EqualsOperator extends BinaryOperator {

	public EqualsOperator(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	protected Value evaluate(Value resolvedLeftValue, Value resolvedRightValue) {
		return new Value( 0==resolvedLeftValue.compare(resolvedRightValue) );
	}

}
