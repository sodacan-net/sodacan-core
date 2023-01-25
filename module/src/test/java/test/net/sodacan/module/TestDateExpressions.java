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
import net.sodacan.module.variables.SystemVariables;

public class TestDateExpressions extends TestConfig {
	static final BigDecimal NUMBER1 = new BigDecimal("2023");
	static final BigDecimal NUMBER2 = new BigDecimal("1");

	@Test
	public void testYear() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables variables = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#year");
		Value result = ex1.resolve(variables);
//		System.out.println(result);
		assert(result.getNumber().equals(NUMBER1));
	}

	@Test
	public void testMonth() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables variables = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#month");
		Value result = ex1.resolve(variables);
//		System.out.println(result);
		assert(result.getNumber().equals(NUMBER2));
	}

	@Test
	public void testSunset() {
		Config config = setupConfig();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		ZonedDateTime sunset = ZonedDateTime.of(2023, 1, 20, 17, 11, 52, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables variables = new SystemVariables(now,config);
		Expression ex1 = new VariableRefExpression("system.clock#sunset");
		Value result = ex1.resolve(variables);
//		System.out.println(result);
		assert(result.getDateTime().equals(sunset));
	}

}
