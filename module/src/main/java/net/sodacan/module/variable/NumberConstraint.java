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

import java.math.BigDecimal;

import net.sodacan.module.value.Value;
/**
 * A number-constraint has constraints:
 * A decimal number has a specific precision, such as 0.00, means the precision of a decimal number is 2. 
 * If more than one such number is supplied, then they all must have the same number of decimal places.
 * For example: 0.0,1.0 is valid. 0.0000,1.0000 is valid. 0.0,1.0000 is not valid because the precision of the numbers must be the same.
 * @author John Churin
 *
 */
public class NumberConstraint extends Constraint {
	BigDecimal number;
	
	public NumberConstraint(String value) {
		this.number = new BigDecimal(value);
	}
	
	@Override
	public boolean isMatch(Value value) {
		if (value.isNumber() &&	0==number.compareTo(value.getNumber())) {
			return true;
		}
		return false;
	}
	public int getNumberScale() {
		return this.number.scale();
	}
	@Override
	public String toString() {
		return number.toPlainString();
	}

}
