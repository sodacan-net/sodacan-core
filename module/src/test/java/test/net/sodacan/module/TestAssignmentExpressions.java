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
package test.net.sodacan.module;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import net.sodacan.config.Config;
import net.sodacan.module.expression.AssignmentExpression;
import net.sodacan.module.expression.Expression;
import net.sodacan.module.terminal.LiteralExpression;
import net.sodacan.module.terminal.VariableRefExpression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.ModuleVariables;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.Variables;

public class TestAssignmentExpressions extends TestConfig {
	static final BigDecimal NUMBER1 = new BigDecimal("0.0");
	static final BigDecimal NUMBER2 = new BigDecimal("123.4");

	@Test
	public void testNumberAssignment() {
		Config config = setupConfig();
		ModuleVariables variables = new ModuleVariables();
		// Variable x starts at 0.0
		VariableDef variableDef = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(NUMBER1)).build();
		variables.addVariable(variableDef);
		// Assign 123.4 to x
		Expression ex1 = new VariableRefExpression("x");
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new AssignmentExpression(ex1,ex2);
		ex3.execute(variables);
		// Get the (new) value of x
		Value result = ex1.resolve(variables);		// Get the current value of x
		Value result2 = ex1.execute(variables);	// Get the variable name x (unresolved)
		assert(0==NUMBER2.compareTo(result.getNumber()));
		assert("x".equals(result2.getIdentifier()));
	}

}
