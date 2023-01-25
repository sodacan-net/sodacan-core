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
package net.sodacan.module.statement;

import net.sodacan.module.expression.datetime.DateExpression;
import net.sodacan.module.expression.datetime.TimeExpression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variables.Variables;

/**
 * The AT statement responds to the passage of time
 * @author John Churin
 *
 */
public class AtStatement extends ActionStatement {
	TimeExpression timeExpression;
	DateExpression dateExpression;

	/**
	 * If the date and time expressions return true and the AndStatements all return true, then 
	 * execute the ThenStatements (ignoring their return value)
	 */
	@Override
	public Value execute(Variables variables) {
		Value timeValue;
		Value dateValue;
		
		if (timeExpression!=null) {
			timeValue = timeExpression.execute(variables);
		} else {
			timeValue = new Value(false);
		}
		if (dateExpression!=null) {
			dateValue = dateExpression.execute(variables);
		} else {
			dateValue = new Value(false);
		}
		// If no match on date/time, we're done
		if (!dateValue.getBoolean() || !timeValue.getBoolean()) {
			return new Value(false);
		}
		// The rest is handled by our superclass
		return super.execute(variables);
	}

}
