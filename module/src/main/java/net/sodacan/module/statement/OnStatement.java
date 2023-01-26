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
import net.sodacan.module.variables.Variables;

/**
 * An ON statement contains one expression that checks if it is interesting in the incoming event,
 * and if so, continues evaluating its AND Statements, if any. If they all pass (or there are no AND statements) then
 * execute each of the THEN statements.
 * 
 * Executing On statements happens due to the arrival of a message.  
 * 
 */
public class OnStatement  extends ActionStatement {
	/**
	 * Just a normal expression, but we execute it with a one-entry variables list containing the message/variable.
	 */
	Expression selectExpression;

	public OnStatement( ) {
		
	}
	
	public void setSelectExpression(Expression expression) {
		this.selectExpression = expression;
	}
	
	public Expression getSelectExpression() {
		return selectExpression;
	}

	protected boolean isMessageMatch(Variables variables) {
		return false;
	}
	
	@Override
	public Value execute(Variables variables) {
		return new Value();
	}

	public boolean processMessage(Variables variables) {
		return false;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ON " );
		sb.append(this.getSelectExpression());
		return sb.toString();
	}

}
