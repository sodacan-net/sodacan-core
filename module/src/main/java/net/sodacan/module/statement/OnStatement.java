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

import net.sodacan.module.expression.Expression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDefs;
import net.sodacan.module.variable.Variables;

/**
 * An ON statement contains one expression that checks if it is interesting in the incoming event,
 * and if so, continues evaluating its AND Statements, if any. If they all pass (or there are none) then
 * execute each of the THEN statements.
 * 
 * Executing an On statement happens when there's an event due to the arrival of a message.  
 * 
 */
public class OnStatement  extends ActionStatement {
	Expression onSelectExpression;

	protected boolean isMessageMatch(Variables variables, ZonedDateTime now) {
		
	}
	
	@Override
	public Value execute(Variables variables, ZonedDateTime now) {
	}

	public boolean processMessage(Variables variables, ZonedDateTime now) {
		return false;
	}
}
