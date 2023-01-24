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
import java.util.List;

import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.value.Value;

/**
 * This collection of variables provides access to the clock used for this cycle.
 * This set of variables is created for each clock tick (AT statements) and 
 * for each message received (ON statements). It is (usually) not persisted.
 * @author John Churin
 *
 */
public class ClockVariables extends BaseVariables implements Variables {
	ZonedDateTime datetime;
	public static final String SYSTEM_CLOCK = "system.clock";
	Variable clockVariable;

	/**
	 * @param datetime
	 */
	public ClockVariables(ZonedDateTime datetime) {
		clockVariable = new ClockVariable(datetime);
	}

	/**
	 * For a clock, the only variable we have is the clock
	 */
	@Override
	public Variable find(String name) {
		// If the name doesn't start with "system.clock" then we're not interested.
		if (name.equals(SYSTEM_CLOCK)) {
			return clockVariable;
		} else {
			return null;
		}
	}

	@Override
	public Variable findByFullName(String fullName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Variable> getListOfChangedVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable setVariable(ModuleMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVariable(String variableName, Value value) {
		// TODO Auto-generated method stub

	}

}
