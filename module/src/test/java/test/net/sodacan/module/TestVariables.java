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
import net.sodacan.module.expression.Expression;
import net.sodacan.module.terminal.VariableRefExpression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.CompositeVariables;
import net.sodacan.module.variable.ModuleVariables;
import net.sodacan.module.variable.SystemVariables;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.Variables;

public class TestVariables extends TestConfig {
	static final BigDecimal NUMBER1 = new BigDecimal("123.4");
	static final String STRING1 = "123.4";
	static final BigDecimal NUMBER2 = new BigDecimal("21");
	static final String STRING2 = "21";
	static final BigDecimal NUMBER3 = new BigDecimal("42764.11154");
	static final String STRING3 = "42764.11154";

	@Test
	public void testClockVariable() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#day");
		Value result = ex1.resolve(cvs);
//		System.out.println(result);
		assert(NUMBER2.equals(result.getNumber()));
	}
	
	@Test
	public void testNullAttribute() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#");
		Value result = ex1.resolve(cvs);
//		System.out.println(result);
		assert("2023-01-21T16:30-08:00[America/Los_Angeles]".equals(result.getString()));
	}

	@Test
	public void testNowAttribute() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#now");
		Value result = ex1.resolve(cvs);
//		System.out.println(result);
		assert(now.equals(result.getDateTime()));
	}
	

	@Test
	public void testSimpleCollectionOfVariables() {
		ModuleVariables v1 = new ModuleVariables();
		VariableDef variableDef = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(NUMBER1)).build();
		v1.addVariable(variableDef);
		// Make sure the Variable interface works correctly
		Variables v1a = v1;
		Variable v1av = v1a.find("x");
		Value v1avv = v1av.getValue();
//		System.out.println(v1av);
		assert(STRING1.equals(v1avv.getString()));
	}

	@Test
	public void testSimpleCollectionOfComplexVariables() {
		// Build a collection of module variables
		ModuleVariables vc = new ModuleVariables();
		vc.addVariable(VariableDef.newVariableDefBuilder().name("short").initialValue(new Value(NUMBER1)).build());
		vc.addVariable(VariableDef.newVariableDefBuilder().name("medium.short").initialValue(new Value(NUMBER2)).build());
		vc.addVariable(VariableDef.newVariableDefBuilder().name("long.medium.short").initialValue(new Value(NUMBER3)).build());
		// Make sure the following code is behind a Variable interface
		Variables variables = vc;
		// Get the short variable
		Variable shortVariable = variables.find("short");
		Value shortValue = shortVariable.getValue();
//		System.out.println(shortValue);
		assert(STRING1.equals(shortValue.getString()));
		// Get the medium variable
		Variable mediumVariable = variables.find("medium.short");
		Value mediumValue = mediumVariable.getValue();
//		System.out.println(mediumValue);
		assert(STRING2.equals(mediumValue.getString()));
		// Get the long variable
		Variable longVariable = variables.find("long.medium.short");
		Value longValue = longVariable.getValue();
//		System.out.println(longValue);
		assert(STRING3.equals(longValue.getString()));
	}


	@Test
	public void testTimezoneAttribute() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.config#timezone");
		Value result = ex1.resolve(cvs);
//		System.out.println(result);
		assert("America/Los_Angeles".equals(result.getString()));
	}

	@Test
	public void testCompositeCollectionOfVariables() {
		// Build the system variables
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables v1 = new SystemVariables(now,config);
		// A (small) collection of module variables
		ModuleVariables v2 = new ModuleVariables();
		VariableDef variableDef = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(NUMBER1)).build();
		v2.addVariable(variableDef);
		CompositeVariables composite = new CompositeVariables(v1,v2);
		// Change the composite to plain Variables interface so we can't cheat.
		Variables variables = composite;
		// Now test finding some variables.
		Variable x = variables.find("x");
		// Print the variable
//		System.out.println(x);
		assert(x.getValue().getNumber().equals(NUMBER1));
		// And a system variable
		Variable clock = variables.find("system.clock");
		// Print the variable
//		System.out.println(clock);
		assert(clock.getValue().getDateTime().equals(now));
		// And again through an expression, this time, system variable first
		Expression ex1 = new VariableRefExpression("system.clock");
		Value result1 = ex1.resolve(variables);
		// Print the variable
//		System.out.println(result1);
		assert(result1.getDateTime().equals(now));
		// And get the module variable
		Expression ex2 = new VariableRefExpression("x");
		Value result2 = ex2.resolve(variables);
		// Print the variable
//		System.out.println(result2);
		assert(result2.getNumber().equals(NUMBER1));
	}


}
