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

public class RangeConstraint extends Constraint {
	BigDecimal low = null;
	BigDecimal high = null;
	
	public RangeConstraint(String low, String high) {
		if (low!=null) {
			this.low = new BigDecimal(low);
		}
		if (high!=null) {
			this.high = new BigDecimal( high);
		}
	}

	@Override
	public boolean isMatch(Value value) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (low!=null) {
			sb.append(low.toPlainString());
		}
		sb.append('-');
		if (high!=null) {
			sb.append(high.toPlainString());
		}
		return sb.toString();
	}

}
