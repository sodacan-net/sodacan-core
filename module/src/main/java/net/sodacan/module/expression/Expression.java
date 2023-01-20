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
package net.sodacan.module.expression;

import net.sodacan.module.statement.ModuleComponent;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDefs;

/**
 * Expressions represent the things in  language that get things done. They are primarily used in the THEN statement in a module.
 * @author John Churin
 *
 */
public abstract class Expression extends ModuleComponent {

	protected Value resolve(VariableDefs variables, Value value) {
		return null;
	}
}
