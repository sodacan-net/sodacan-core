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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import net.sodacan.module.expression.datetime.DateExpression;
import net.sodacan.module.expression.datetime.TimeExpression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variables;

/**
 * The AT statement responds to the passage of time
 * @author John Churin
 *
 */
public class AtStatement extends Statement {
	TimeExpression timeExpression;
	DateExpression dateExpression;
	List<AndStatement> andStatements = new ArrayList<>();
	List<ThenStatement> thenStatements = new ArrayList<>();

	/**
	 * If the date and time expressions return true and the AndStatements all return true, then 
	 * execute the ThenStatements (ignoring their return value)
	 */
	@Override
	public Value execute(Variables variables, ZonedDateTime now) {
		
		return null;
	}

}
