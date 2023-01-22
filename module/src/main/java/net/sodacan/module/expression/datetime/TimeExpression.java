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
package net.sodacan.module.expression.datetime;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sodacan.SodacanException;
import net.sodacan.module.expression.Expression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDefs;
/**
 * A TimeExpression has a number of constraints. In general, if
 * no time is specified, then it means midnight on the specified day.
 * Times can be specific, 10:00am or shortcuts such as sunset, noon, etc.
 * All times can have an offset ie 1 hour before sunrise.
 * 
 * @author John Churin
 *
 */
public class TimeExpression extends Expression {
	static final private Set<String> validUnits = Set.of("hours","minutes");
	static final private Set<String> validShortcuts = Set.of("noon","midnight","sunrise","sunset");
	private List<Criteria> criteria = new ArrayList<>();
	
	private TimeExpression(TimeExpressionBuilder builder) {
		this.criteria = builder.criteria;
	}
	
	@Override
	public Value execute(VariableDefs variables, ZonedDateTime now) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * You give me a datetime and I'll tell you if it's included in the set.
	 * @param now
	 * @return
	 */
	public boolean isMatch( ZonedDateTime now) {
		for (Criteria criterium : criteria) {
			if (criterium.isMatch(now)) return true;
		}
		return false;
	}
	/**
	 * Create a new, empty, builder for a TimeExpression
	 */
	public static TimeExpressionBuilder newTimeExpressionBuilder() {
		return new TimeExpressionBuilder();
	}

	/**
	 * Builder class for TimeExpressions
	 *
	 */
	public static class TimeExpressionBuilder {
		private List<Criteria> criteria = new ArrayList<>();

		protected TimeExpressionBuilder() {
			
		}
		
		public TimeExpressionBuilder noon() {
			criteria.add(new NoonCriteria());
			return this;
		}

		public TimeExpressionBuilder midnight() {
			criteria.add(new MidnightCriteria());
			return this;
		}

		public TimeExpressionBuilder sunset() {
			criteria.add(new SunsetCriteria(0,null));
			return this;
		}
		
		public TimeExpressionBuilder sunset(int offset, ChronoUnit units) {
			criteria.add(new SunsetCriteria(offset,units));
			return this;
		}

		public TimeExpressionBuilder sunrise() {
			criteria.add(new SunriseCriteria(0,null));
			return this;
		}
		public TimeExpressionBuilder sunrise(int offset, ChronoUnit units) {
			criteria.add(new SunsetCriteria(offset,units));
			return this;
		}

		public TimeExpressionBuilder time(LocalTime time) {
			criteria.add(new TimeOfDayCriteria(time.getHour(),time.getMinute()));
			return this;
		}
		/**
		 * Time in 24 hour format
		 * @param hour 0-23
		 * @param minute 0-59
		 * @return
		 */
		public TimeExpressionBuilder time(int hour, int minute) {
			criteria.add(new TimeOfDayCriteria(hour,minute));
			return this;
		}
		
		/**
		 * Time in am/pm format
		 * @param hour
		 * @param minute
		 * @param ampm String containing am or pm
		 * @return
		 */
		public TimeExpressionBuilder time(int hour, int minute, String ampm) {
			return this;
		}
		public TimeExpression build() {
			return new TimeExpression(this);
		}

	}


}
