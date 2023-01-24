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

import static org.junit.Assert.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import net.sodacan.config.Config;
import net.sodacan.module.expression.datetime.DateExpression;
/**
 * The functions tested here are very low level. See TestDateExpressions.java
 * @author John Churin
 *
 */
public class TestSimpleDateExpressions extends TestConfig {

	@Test
	public void testWeekend() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().weekend().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(de.isMatch(now));
	}

	@Test
	public void testNotWeekend() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().weekend().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(!de.isMatch(now));
	}

	@Test
	public void testWeekday() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().weekday().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(de.isMatch(now));
	}
	
	@Test
	public void testMonday() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().monday().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 23, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(de.isMatch(now));
	}

	@Test
	public void testSunday() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().sunday().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 22, 8, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(de.isMatch(now));
	}
	
	@Test
	public void testMonthInJanuary() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().month(1).build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 22, 8, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(de.isMatch(now));
	}

	@Test
	public void testJanuary() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().january().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 22, 8, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(de.isMatch(now));
	}
	@Test
	public void testNotJanuary() {
		Config config = setupConfig();
		DateExpression de = DateExpression.newDateExpressionBuilder().january().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 3, 1, 7, 45, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(!de.isMatch(now));
	}

}
