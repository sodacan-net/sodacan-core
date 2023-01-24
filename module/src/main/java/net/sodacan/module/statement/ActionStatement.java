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
package net.sodacan.module.statement;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variables;
/**
 * All action statements have AndStatements and ThenStatements
 * @author John Churin
 *
 */
public abstract class ActionStatement extends Statement {
	List<AndStatement> andStatements = new ArrayList<>();
	List<ThenStatement> thenStatements = new ArrayList<>();

	public ActionStatement() {
	}

	public void addAndStatement(AndStatement andStatement) {
		andStatements.add(andStatement);
	}

	public void addThenStatement(ThenStatement thenStatement) {
		thenStatements.add(thenStatement);
	}


	@Override
	public Value execute(Variables variables) {
		// Verify that all "and" statements are satisfied
		for (AndStatement andStatement : andStatements) {
			if (!andStatement.execute(variables).getBoolean()) {
				return new Value(false);
			}
		}
		// If we made it this far, do all of the then statements
		for (ThenStatement thenStatement : thenStatements) {
			thenStatement.execute(variables);
		}
		return new Value(true);
	}

}
