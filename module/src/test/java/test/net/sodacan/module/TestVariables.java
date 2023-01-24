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
import net.sodacan.module.variable.SystemVariables;
import net.sodacan.module.variable.CompositeVariables;
import net.sodacan.module.variable.ModuleVariables;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.Variables;

public class TestVariables extends TestConfig {
	static final BigDecimal NUMBER1 = new BigDecimal("123.4");
	static final String STRING1 = "123.4";
	static final BigDecimal NUMBER2 = new BigDecimal("21");

	@Test
	public void testClockVariable() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#day");
		Value result = ex1.resolve(cvs);
		assert(NUMBER2.equals(result.getNumber()));
//		System.out.println(result);
	}
	
	@Test
	public void testNullAttribute() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#");
		Value result = ex1.resolve(cvs);
		assert("2023-01-21T16:30-08:00[America/Los_Angeles]".equals(result.getValue()));
		System.out.println(result);
	}

	@Test
	public void testNowAttribute() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#now");
		Value result = ex1.resolve(cvs);
		assert(now.equals(result.getDateTime()));
		System.out.println(result);
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
		assert(STRING1.equals(v1avv.getValue()));
//		System.out.println(v1av);
	}

	@Test
	public void testCollectionOfCollectionOfVariables() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		// A top-level collection of variables
		ModuleVariables v1 = new ModuleVariables();
		VariableDef variableDef = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(NUMBER1)).build();
		v1.addVariable(variableDef);
		SystemVariables v2 = new SystemVariables(now,config);
		CompositeVariables composite = new CompositeVariables(v1,v2);
		// Make sure the Variable interface works correctly
		Variables variables = composite;
		Variable x = variables.find("x");
		assert(x.getValue().getNumber().equals(NUMBER1));
		Variable clock = variables.find("system.clock");
		assert(clock.getValue().getDateTime().equals(now));
		System.out.println(x);
		System.out.println(clock);
//		assert(STRING1.equals(v1avv.getValue()));
	}

	@Test
	public void testTimezoneAttribute() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables cvs = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.config#timezone");
		Value result = ex1.resolve(cvs);
		assert("America/Los_Angeles".equals(result.getValue()));
		System.out.println(result);
	}

}
