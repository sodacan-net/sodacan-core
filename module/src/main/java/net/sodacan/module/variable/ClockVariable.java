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
package net.sodacan.module.variable;

import java.time.ZonedDateTime;

import net.sodacan.SodacanException;
import net.sodacan.module.value.Value;
import net.sodacan.utility.SunriseSunset;

public class ClockVariable implements Variable {
	protected ZonedDateTime dateTime;
	public ClockVariable(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public Value getValue() {
		return new Value(dateTime.toString());
	}
	
	@Override
	public void setValue(Value value) {
		throw new SodacanException("Cannot set the value of the clock");
	}
	
	@Override
	public Value getAttribute(String attributeName) {
		if ("now".equals(attributeName)) {
			return new Value(dateTime);
		} else if ("sunrise".equals(attributeName)) {
			ZonedDateTime sst = SunriseSunset.getInstance().getSunrise(dateTime);
			return new Value(sst);
		} else if ("sunset".equals(attributeName)) {
			ZonedDateTime sst = SunriseSunset.getInstance().getSunset(dateTime);
			return new Value(sst);
		} else if ("month".equals(attributeName)) {
			return new Value(dateTime.getMonthValue());
		} else if ("day".equals(attributeName)) {
			return new Value(dateTime.getDayOfMonth());
		} else if ("year".equals(attributeName)) {
			return new Value(dateTime.getYear());
		} else if ("hour".equals(attributeName)) {
			return new Value(dateTime.getHour());
		} else if ("minute".equals(attributeName)) {
			return new Value(dateTime.getMinute());
		} else if ("second".equals(attributeName)) {
			return new Value(dateTime.getSecond());
		}
		throw new SodacanException("Unknown attribute name: " + attributeName);
	}
	@Override
	public void setAttribute(String attributeName) {
		throw new SodacanException("Cannot set an attribute of the clock");
		
	}

}
