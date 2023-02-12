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

import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variable;

public abstract class BaseVariables implements Variables {
	public static final char ATTRIBUTE = '#';
	public BaseVariables() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get a value from the named variable.
	 * In almost all cases, a variable name consists of everything up to a hash (#).
	 * The #attribute is satisfied by the variable itself. We take care of all that right here so the subclasses don't have to.
	 * We also extract the value from the attribute in whatever way the subclass prescribes.
	 */
	@Override
	public Value findValue(String name) {
		int hashLoc = name.indexOf(ATTRIBUTE);
		String variableName;
		String attributeName;
		if (hashLoc < 0) {
			variableName = name;
			attributeName = null;
		} else {
			variableName = name.substring(0, hashLoc);
			attributeName = name.substring(hashLoc+1, name.length());
		}
		Variable variable = find(variableName);
		if (variable==null) {
			return findShortcutValue(variableName);
		}
		// A regular variable can access attributes
		if (attributeName==null || attributeName.isEmpty()) {
			return variable.getValue();
		}
		return variable.getAttribute(attributeName);
	}
	public Value findShortcutValue(String name) {
		return null;
	}
}