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
import net.sodacan.module.variable.Variables;
/**
 * A Then Statement contains a simple expression, or an assignment expression.
 * @author John Churin
 *
 */
public class ThenStatement extends Statement {
	Expression expression;
	public ThenStatement() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Value execute(Variables variables, ZonedDateTime now) {
		// TODO Auto-generated method stub
		return null;
	}

}
