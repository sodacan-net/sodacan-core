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
package net.sodacan.module.variables;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sodacan.config.Config;
import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.ClockVariable;
import net.sodacan.module.variable.ConfigVariable;
import net.sodacan.module.variable.Variable;

/**
 * This collection of variables provides access to the clock used for this cycle and to configuration variables.
 * This set of variables is created for each clock tick (AT statements) and 
 * for each message received (ON statements). It is (usually) not persisted.
 * @author John Churin
 *
 */
public class SystemVariables extends BaseVariables implements Variables {
	ZonedDateTime datetime;
	public static final String SYSTEM_CLOCK = "system.clock";
	public static final String SYSTEM_CONFIG = "system.config";
	protected Map<String,Variable> variables = new TreeMap<>();

	/**
	 * @param datetime
	 */
	public SystemVariables(ZonedDateTime datetime, Config config) {
		variables.put( SYSTEM_CLOCK, new ClockVariable(datetime));
		variables.put(SYSTEM_CONFIG, new ConfigVariable(config));
	}

	/**
	 * Out list of variables is very short
	 */
	@Override
	public Variable find(String name) {
		return variables.get(name);
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
	@Override
	public String toString() {
		return variables.toString();
	}


}
