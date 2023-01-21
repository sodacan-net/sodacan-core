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

public class TestTimeExpression {

	@Test
	public void testHourMinute() {
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().time(16, 30).build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.systemDefault());
		assert(te.isMatch(now));
	}

	@Test
	public void testSunset() {
		Config config = Config.getInstance();
		config.setLocation(new Location());
		config.getLocation().setLatitude(42.557982);
		config.getLocation().setLongitude(-123.393342);
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 17, 13, 0, 0, ZoneId.systemDefault());
		assert(te.isMatch(now));
	}
	/**
	 * The second time this runs should be much faster than the first time (on my machine, 0.5 sec 
	 * the first time and > 0.001 sec on the second run for the same day.
	 */
	@Test
	public void testSunset1() {
		Config config = Config.getInstance();
		config.setLocation(new Location());
		config.getLocation().setLatitude(42.557982);
		config.getLocation().setLongitude(-123.393342);
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 17, 13, 0, 0, ZoneId.systemDefault());
		assert(te.isMatch(now));
	}

	@Test
	public void testSunsetWithOffset() {
		Config config = Config.getInstance();
		config.setLocation(new Location());
		config.getLocation().setLatitude(42.557982);
		config.getLocation().setLongitude(-123.393342);
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset(1,ChronoUnit.HOURS).build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 18, 13, 0, 0, ZoneId.systemDefault());
		assert(te.isMatch(now));
	}

	@Test
	public void testSunsetWithNegativeOffset() {
		Config config = Config.getInstance();
		config.setLocation(new Location());
		config.getLocation().setLatitude(42.557982);
		config.getLocation().setLongitude(-123.393342);
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunset(-1,ChronoUnit.HOURS).build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 13, 0, 0, ZoneId.systemDefault());
		assert(te.isMatch(now));
	}

	@Test
	public void testSunrise() {
		Config config = Config.getInstance();
		config.setLocation(new Location());
		config.getLocation().setLatitude(42.557982);
		config.getLocation().setLongitude(-123.393342);
		TimeExpression te = TimeExpression.newTimeExpressionBuilder().sunrise().build();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 7, 37, 0, 0, ZoneId.systemDefault());
		assert(te.isMatch(now));
	}


}
