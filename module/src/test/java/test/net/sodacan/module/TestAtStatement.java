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

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import net.sodacan.config.Config;
import net.sodacan.module.expression.datetime.DateExpression;
import net.sodacan.module.expression.datetime.TimeExpression;
import net.sodacan.module.statement.AtStatement;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variables.CompositeVariables;
import net.sodacan.module.variables.ModuleVariables;
import net.sodacan.module.variables.SystemVariables;
import net.sodacan.module.variables.Variables;

public class TestAtStatement extends TestConfig {

	public Variables setupVariables(int year, int month, int day, int hour, int minute) {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables v1 = new SystemVariables(now,config);
		// A (very small) collection of module variables
		ModuleVariables v2 = new ModuleVariables();
		VariableDef variableDef = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(25)).build();
		v2.addVariable(variableDef);
		CompositeVariables composite = new CompositeVariables(v1,v2);
		return composite;
	}

	@Test
	public void testHourMinute() {
		// Setup variables (Date Jan 25, 2023 at 16:30)
		Variables variables = setupVariables(2023,1,25,16,30);
		// Create an AT statement
		AtStatement as = new AtStatement();
		// Add date and time expressions
		as.setTimeExpression(TimeExpression.newTimeExpressionBuilder().time(16, 30).build());
		as.setDateExpression(DateExpression.newDateExpressionBuilder().build());
		Value result = as.execute(variables);
//		System.out.println(result);
		assert(result.getBoolean());
	}

	@Test
	public void testNoonOnWednesday() {
		// Create an AT statement
		AtStatement as = new AtStatement();
		// Add date and time expressions
		as.setTimeExpression(TimeExpression.newTimeExpressionBuilder().noon().build());
		as.setDateExpression(DateExpression.newDateExpressionBuilder().wednesday().build());
		// Setup variables (Now is: Date Jan 25, 2023 at 12:00)
		Value r1 = as.execute(setupVariables(2023,1,25,12,00));
		assert(r1.getBoolean());
		// If it's not noon, then it should return false
		Value r2 = as.execute(setupVariables(2023,1,25,13,00));
		assert(!r2.getBoolean());
		// If it is noon but the wrong day, then should return false
		Value r3 = as.execute(setupVariables(2023,1,24,12,00));
		assert(!r3.getBoolean());
	}

	@Test
	public void testMultipleDays() {
		// Create an AT statement
		AtStatement as = new AtStatement();
		// Add date and time expressions
		as.setTimeExpression(TimeExpression.newTimeExpressionBuilder().noon().build());
		as.setDateExpression(DateExpression.newDateExpressionBuilder().tuesday().wednesday().build());
		// Setup variables (Now is: Date Jan 25, 2023 at 12:00)
		Value r1 = as.execute(setupVariables(2023,1,25,12,00));
		assert(r1.getBoolean());
		// If it's not noon, then it should return false
		Value r2 = as.execute(setupVariables(2023,1,25,13,00));
		assert(!r2.getBoolean());
		// If it is noon Tuesday, then should return true
		Value r3 = as.execute(setupVariables(2023,1,24,12,00));
		assert(r3.getBoolean());
	}

	@Test
	public void testSeason() {
		// Create an AT statement
		AtStatement as = new AtStatement();
		// Add date and time expressions
		as.setTimeExpression(TimeExpression.newTimeExpressionBuilder().noon().build());
		as.setDateExpression(DateExpression.newDateExpressionBuilder().winter().build());
		// Setup variables (Now is: Date Jan 25, 2023 at 12:00 - winter)
		Value r1 = as.execute(setupVariables(2023,1,25,12,00));
		assert(r1.getBoolean());
		// If it's noon but not in winter, then it should return false
		Value r2 = as.execute(setupVariables(2023,6,25,12,00));
		assert(!r2.getBoolean());
		// If it is noon in the winter, then should return true
		Value r3 = as.execute(setupVariables(2023,1,24,12,00));
		assert(r3.getBoolean());
	}

	@Test
	public void testDateRange() {
		// Create an AT statement
		AtStatement as = new AtStatement();
		// Add date and time expressions
		as.setTimeExpression(TimeExpression.newTimeExpressionBuilder().midnight().build());
		as.setDateExpression(DateExpression.newDateExpressionBuilder().annualRange(1,4, 2, 15).build());
		// The datetimes below are the "current" time 
		Value r1 = as.execute(setupVariables(2023,1,5,00,00));
		assert(r1.getBoolean());
		Value r2 = as.execute(setupVariables(2023,1,3,00,00));	// Too early
		assert(!r2.getBoolean());
		Value r3 = as.execute(setupVariables(2023,2,4,00,00));
		assert(r3.getBoolean());
		Value r4 = as.execute(setupVariables(2023,2,16,00,00));	// Too late
		assert(!r4.getBoolean());
	}

}
