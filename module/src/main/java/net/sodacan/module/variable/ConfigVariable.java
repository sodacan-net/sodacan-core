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

import net.sodacan.SodacanException;
import net.sodacan.config.Config;
import net.sodacan.module.value.Value;
/**
 * Provide access to selected configuration settings
 * @author John Churin
 *
 */
public class ConfigVariable implements Variable {
	Config config;

	public ConfigVariable( Config config) {
		this.config = config;
	}
	@Override
	public Value getValue() {
		return new Value("<system.config>");
	}

	@Override
	public void setValue(Value value) {
		throw new SodacanException("Cannot set the value of configuration settings");
	}

	@Override
	public Value getAttribute(String attributeName) {
		if ("timezone".equals(attributeName)) {
			return new Value(config.getLocation().getTimezone());
		} else if ("locationName".equals(attributeName)) {
			return new Value(config.getLocation().getName());
		} else if ("locationAddress".equals(attributeName)) {
			return new Value(config.getLocation().getAddress());
		} else if ("latitude".equals(attributeName)) {
			return new Value(config.getLocation().getLatitude());
		} else if ("longitude".equals(attributeName)) {
			return new Value(config.getLocation().getLongitude());
		}
		throw new SodacanException("Unknown attribute name: " + attributeName);
	}

	@Override
	public void setAttribute(String attributeName) {
		throw new SodacanException("Cannot set an attribute of the configuration");
	}

	@Override
	public String getName() {
		return "system.config";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConfigVariable) {
			ConfigVariable other = (ConfigVariable)obj;
			return (getName().equals(other.getName()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		sb.append('=');
		sb.append(getValue());
		return sb.toString();
	}


}
