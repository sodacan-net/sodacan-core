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
package net.sodacan.module.terminal;

import java.math.BigDecimal;

import net.sodacan.module.value.Value;
import net.sodacan.module.variables.Variables;

public class LiteralExpression extends TerminalExpression {

	public LiteralExpression(String string) {
		super(new Value(string));
	}

	public LiteralExpression(BigDecimal number) {
		super(new Value(number));
	}

	public LiteralExpression(boolean bool) {
		super(new Value(bool));
	}

	@Override
	public Value execute(Variables variables) {
		return value;
	}

}
