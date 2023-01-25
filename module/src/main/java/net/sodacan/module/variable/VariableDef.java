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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.sodacan.module.value.Value;
import net.sodacan.module.value.ValueSerializer;

/**
 * A Single Variable. In SodaCan, all messages begin (publish) as variables or end (subscribe) as variables.
 * A variable definition is immutable once created.
 * @author John Churin
 */
public class VariableDef {
	public enum VariableType {topicVariable, publishVariable,subscribeVariable,privateVariable};
	public final String version = "1.1";
	private String name;
	private String alias;
	private String instance;
	private VariableType variableType;

	@JsonProperty("initialValue")
	@JsonSerialize(using = ValueSerializer.class)
	private Value initialValue;
	
	private List<Constraint> constraints = null;	// Null unless there are constraints

	private VariableDef() { }
	
	private VariableDef(VariableDefBuilder builder) {
		this.name = builder.name;
		this.alias = builder.alias;
		this.instance = builder.instance;
		this.constraints = builder.constraints;
		this.variableType = builder.variableType;
		this.initialValue = builder.initialValue;
	}
	
	@JsonIgnore
	public String getFullName( ) {
		StringBuffer sb = new StringBuffer();
		if (name!=null) {
			sb.append(name);
		}
		if (instance!=null) {
			sb.append('[');
			sb.append(instance);
			sb.append(']');
		}
		return sb.toString();
	}
	
	@JsonIgnore
	public String getShortName() {
		if (alias!=null) {
			return alias;
		}
		return name;
	}
	
	public String getVersion() {
		return version;
	}
	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public String getInstance() {
		return instance;
	}

	public VariableType getVariableType() {
		return variableType;
	}
	
	public Value getInitialValue() {
		return initialValue;
	}
	
	/**
	 * Return a list of those constraints that are identifiers (ignore numbers and quoted strings)
	 * Used by the Variabledef visitor to check for duplicate identifiers
	 * @return
	 */
	public List<String> getConstraintIdentifiers() {
		List<String> ids = new ArrayList<>();
		// No constraints means return empty list
		if (constraints==null) {
			return ids;
		}
		for (Constraint constraint : constraints) {
			String cid = constraint.getIdentifierName();
			if (cid!=null) {
				ids.add(cid);
			}
		}
		return ids;
	}
	/**
	 * Run through each constraint and, if it is a number, make sure it's scale is the same as all the others
	 * @return
	 */
	public boolean validateConstraintScale() {
		Integer scale = null;
		if (constraints==null) {
			return true;
		}
		for (Constraint constraint : constraints) {
			if (constraint instanceof NumberConstraint) {
				NumberConstraint nc = (NumberConstraint)constraint;
				if (scale==null) {
					scale = nc.getNumberScale();
				} else {
					if (scale!= nc.getNumberScale()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * Validate the supplied value against constraints. If no constraints, then returns true (valid).
	 * @param value the value to be validated
	 * @return true if the value is valid
	 */
	public boolean validateAgainstConstraints( Value value) {
		if (constraints==null) {
			return true;
		}

		if (constraints.size()==0) {
			return true;
		}
		for (Constraint constraint : constraints) {
			if (constraint.isMatch(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Two variable defs are equal if their namespace, topic, name, and instance are equal.
	 * They are also equal (in a bad way) if their aliases are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof VariableDef)) {
			return false;
		}
		VariableDef other = (VariableDef)obj;
		if (alias!=null && other.alias!=null && !alias.equalsIgnoreCase(other.alias)) {
			return false;
		}
		if (!getFullName().equalsIgnoreCase(other.getFullName())) {
			return false;
		}
		return true;
	}
	@Override
	public int hashCode() {
		return getFullName().hashCode();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(variableType.toString());
		sb.append(':');
		sb.append(getName());
		if (constraints!=null) {
			sb.append('{');
			boolean first = true;
			for (Constraint constraint : constraints) {
				if (first) {
					first=false;
				} else {
					sb.append(',');
				}
				sb.append(constraint);
			}
			sb.append('}');
		}
		return sb.toString();
	}
	
	public static VariableDefBuilder newVariableDefBuilder() {
		return new VariableDefBuilder();
	}
	public static class VariableDefBuilder {
		private String name;
		private String alias;
		private String instance;
		private List<Constraint> constraints;
		private VariableType variableType;
		private Value initialValue;

		protected VariableDefBuilder() {
			
		}
		
		public VariableDefBuilder name(String name) {
			this.name = name;
			return this;
		}
		public VariableDefBuilder alias(String alias) {
			this.alias = alias;
			return this;
		}
		
		public VariableDefBuilder type(String type) {
			this.variableType = VariableType.valueOf(type);
			return this;
		}

		public VariableDefBuilder type(VariableType type) {
			this.variableType = type;
			return this;
		}
		
		public VariableDefBuilder constraint(Constraint constraint) {
			if (null==this.constraints) {
				this.constraints = new ArrayList<>();
			}
			this.constraints.add(constraint);
			return this;
		}
		public VariableDefBuilder initialValue(Value value) {
			this.initialValue = value;
			return this;
		}
		public VariableDef build() {
			// If no initial value, then start with a null
			if (initialValue==null) {
				initialValue = new Value();
			}
			// If no variable type, assume private (probably due to testing)
			if (variableType==null) {
				variableType = VariableType.privateVariable;
			}
			return new VariableDef(this);
		}

	}
	
}
