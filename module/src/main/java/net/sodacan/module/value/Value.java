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
package net.sodacan.module.value;

import java.math.BigDecimal;
import java.util.List;


/**
 * Values are passed around during execution of a module. A value may, eventually, be stored in a variable.
 * A value is immutable after construction.
 * @author John Churin
 *
 */
public class Value {
	
	private BigDecimal number = null;
	private String string = null;
	private Boolean bool = null;
	private List<Value> array = null;
	private boolean variable = false;
	public Value() {
		
	}
	public Value(List<Value> array) {
		this.array = array;
	}
	public Value(int integer) {
		this.number = new BigDecimal(integer);
	}
	public Value(String string) {
		this.string = string;
	}
	public Value(String string, boolean variable) {
		this.string = string;
		this.variable = variable;
	}
	public Value(Boolean bool) {
		this.bool = bool;
	}

	public boolean isArray( ) {
		if (array!=null) return true;
		return false;
	}

	public boolean isNumber() {
		if (number!=null) return true;
		// A String containing an number is a number
		if (string!=null) {
			try {
				Integer.parseInt(string);
				return true;
			} catch (Throwable e) {
				return false;
			}
		}
		return false;
	}

	public boolean isString() {
		if (string!=null) return true;
		// An integer can be expressed as a string
		return isNumber();
	}
	
	public boolean isBoolean() {
		if (bool!=null) return true;
		return false;
	}

	public boolean isVariable() {
		return variable;
	}
	public boolean isNull() {
		if (number==null && string==null && bool==null && array==null) return true;
		return false;
	}
	
	public BigDecimal getNumber() {
		if (number!=null) return number;
		if (string!=null) return new BigDecimal(string);
		throw new RuntimeException("Wrong type");
	}
	
	public Boolean getBoolean() {
		if (bool!=null) return bool;
		return bool;
	}

	public String getValue() {
		return toString();
	}

	public List<Value> getArray() {
		return array;
	}
	@Override
	public String toString() {
		if (number!=null) return number.toString();
		if (string!=null) return string;
		if (bool!=null) return Boolean.toString(bool);
		if (array!=null) return array.toString();
		return "null";
	}
	
	@Override
	public int hashCode() {
		return string.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Value) {
			Value other = (Value)obj;
			return other.toString().contentEquals(this.toString());
		}
		return false;
	}

	
}
