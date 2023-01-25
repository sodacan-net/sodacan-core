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

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.sodacan.module.value.Value;

/**
 * A Shortcut variable is like any other variable except that it also contains a
 * condition as well. So it returns a boolean value rather than the contents of the variable.
 * @author John Churin
 *
 */
public class ShortcutVariable implements Variable {
	private String name;
	private String constraint;
	private String underlyingName;
	
	@SuppressWarnings("unused")
	private ShortcutVariable() {}
	
	public ShortcutVariable(String name, String constraint, String underlyingName) {
		super();
		this.name = name;
		this.constraint = constraint;
		this.underlyingName = underlyingName;
	}
	
	@JsonIgnore
	@Override
	public Value getValue() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setValue(Value value) {
		// TODO Auto-generated method stub
		
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}

	public String getUnderlyingName() {
		return underlyingName;
	}

	public void setUnderlyingName(String underlyingName) {
		this.underlyingName = underlyingName;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Value getAttribute(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String attributeName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ShortcutVariable) {
			ShortcutVariable other = (ShortcutVariable)obj;
			if (name.equals(other.name)) {
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
		StringBuffer sb = new StringBuffer();
		sb.append(this.name);
		sb.append('(');
		sb.append(this.constraint);
		sb.append(':');
		sb.append(this.underlyingName);
		sb.append(')');
		return sb.toString();
	}
	

}
