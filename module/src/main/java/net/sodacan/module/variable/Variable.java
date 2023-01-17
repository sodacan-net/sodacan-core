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
/**
 * A Single Variable. In SodaCan, all messages are variables.
 * @author John Churin
 */
public abstract class Variable {
	private String name;
	private boolean changedInCycle;
	protected enum variableType {publishVariable,subscribeVariable,privateVariable,module};

	public Variable() {
		super();
	}

	public Variable(String name) {
		super();
		this.name = name;
	}
	public void resetChanged() {
		changedInCycle = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChangedInCycle() {
		return changedInCycle;
	}

	public void setChangedInCycle(boolean changedInCycle) {
		this.changedInCycle = changedInCycle;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			Variable other = (Variable)obj;
			if (name.equalsIgnoreCase(other.name)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
