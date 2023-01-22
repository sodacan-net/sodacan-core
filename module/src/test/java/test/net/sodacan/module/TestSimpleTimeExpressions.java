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
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import net.sodacan.config.Config;
import net.sodacan.config.Location;
import net.sodacan.module.expression.datetime.TimeExpression;

public class TestSimpleTimeExpressions extends TestConfig {
	@Test
	public void testHourMinute() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().time(16, 30).build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}

	@Test
	public void testSunset() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 17, 13, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}
	/**
	 * The second time this runs should be much faster than the first time (on my machine, 0.5 sec 
	 * the first time and > 0.001 sec on the second run for the same day.
	 */
	@Test
	public void testSunset1() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 17, 13, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}

	@Test
	public void testSunsetWithOffset() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset(1,ChronoUnit.HOURS).build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 18, 13, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}

	@Test
	public void testSunsetWithNegativeOffset() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset(-1,ChronoUnit.HOURS).build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 13, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}

	@Test
	public void testSunrise() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunrise().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 7, 37, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}

	@Test
	public void testNoon() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().noon().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 12, 0, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}

	@Test
	public void testNotNoon() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().noon().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 12, 1, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(!te.isMatch(now));
	}

	@Test
	public void testMidnight() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().midnight().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 0, 0, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(te.isMatch(now));
	}

	@Test
	public void testNotMidnight() {
		Config config = setupConfig();
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().midnight().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 0, 1, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		assert(!te.isMatch(now));
	}


}
