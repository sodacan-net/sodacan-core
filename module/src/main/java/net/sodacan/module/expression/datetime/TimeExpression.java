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
	private int offsetValue = 0;
	private String offsetUnits = null;	// Hours or minutes
	private String shortcut = null;
	private LocalTime localTime;
	
	private TimeExpression(TimeExpressionBuilder builder) {
		this.offsetValue = builder.offsetValue;
		this.offsetUnits = builder.offsetUnits;
		this.shortcut = builder.shortcut;
		this.localTime = builder.localTime;
	}
	
	@Override
	public Value execute(VariableDefs variables) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * You give me a datetime and I'll tell you if it's included in the set.
	 * @param now
	 * @return
	 */
	public boolean isMatch( ZonedDateTime now) {
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
		private int offsetValue = 0;
		private String offsetUnits = null;	// Hours or minutes
		private String shortcut = null;
		private LocalTime localTime;

		protected TimeExpressionBuilder() {
			
		}
		
		public TimeExpressionBuilder offset(int value, String units) {
			if (!validUnits.contains(units)) {
				throw new SodacanException("Invalid time units: " + units);
			}
			this.offsetValue = value;
			this.offsetUnits = units;
			return this;
		}
		
		public TimeExpressionBuilder shortcut(String shortcut) {
			if (!validShortcuts.contains(shortcut)) {
				throw new SodacanException("Invalid time shortcut: " + shortcut);
			}
			this.shortcut = shortcut;
			return this;
		}

		public TimeExpressionBuilder time(LocalTime time) {
			this.localTime = time;
			return this;
		}
		/**
		 * Time in 24 hour format
		 * @param hour 0-23
		 * @param minute 0-59
		 * @return
		 */
		public TimeExpressionBuilder time(int hour, int minute) {
			this.localTime = LocalTime.of(hour, minute);
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
			int h;
			if ("pm".equalsIgnoreCase(ampm)) {
				h = hour + 12;
			} else {
				h = hour;
			}
			h = h %12;
			this.localTime = LocalTime.of(h, minute);
			return this;
		}
		public TimeExpression build() {
			return new TimeExpression(this);
		}

	}


}
