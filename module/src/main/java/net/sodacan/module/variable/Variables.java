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

import java.util.HashMap;
import java.util.Map;

/**
 * A structure holding all variables for a module
 * @author John Churin
 *
 */
public class Variables {
	Map<String,Variable> variables = new HashMap<>();
	/**
	 * Reset the changed flag in all variables
	 */
	public void resetChanged() {
		variables.forEach((name,variable)-> variable.resetChanged());
	}
}
