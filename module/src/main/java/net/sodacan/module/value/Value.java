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
import java.time.ZonedDateTime;
import java.util.List;

import net.sodacan.SodacanException;


/**
 * Values are passed around during execution of a module. A value may, eventually, be stored in a variable.
 * A value is immutable after construction.
 * @author John Churin
 *
 */
public class Value {
	private static final String NUMBER = "number-";
	private static final String STRING = "string-";
	private static final String IDENTIFIER = "identifier-";
	private static final String DATETIME = "dateTime-";
	private static final String BOOLEAN = "boolean-";
	private static final String ARRAY = "array-";
	private static final String NULL = "null-";
	
	private BigDecimal number = null;
	private String string = null;
	private String identifier = null;
	private Boolean bool = null;
	private List<Value> array = null;
	private ZonedDateTime dateTime = null;

	public Value() {
		
	}
	public Value(List<Value> array) {
		this.array = array;
	}
	public Value(int integer) {
		this.number = new BigDecimal(integer);
	}
	
	public Value( BigDecimal number) {
		this.number = number;
	}

	public Value( double number) {
		this.number = new BigDecimal(number);
	}

	public Value(String string) {
		this.string = string;
	}
	public Value(String string, boolean variable) {
		this.identifier = string;
	}
	public Value(Boolean bool) {
		this.bool = bool;
	}

	public Value(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isDateTime() {
		return (dateTime!=null);
	}
	
	public boolean isArray( ) {
		if (isVariable()) {
			throw new SodacanException("Type cannot be determined for a variable until resolved");
		}
		if (array!=null) return true;
		return false;
	}

	public boolean isNumber() {
		if (isVariable()) {
			throw new SodacanException("Type cannot be determined for a variable until resolved");
		}
		if (number!=null) return true;
		// A String containing an number is a number
		if (string!=null) {
			try {
				new BigDecimal(string);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	public boolean isString() {
		if (isVariable()) {
			throw new SodacanException("Type cannot be determined for a variable until resolved");
		}
		if (string!=null) return true;
		// An integer can be expressed as a string
		return isNumber();
	}
	
	public boolean isBoolean() {
		if (bool!=null) return true;
		// If a string contains true or false, then it's boolean
		if (string!=null) {
			return ("true".equals(string) || "false".equals(string));
		}
		// If it's a number, 0.0 is false, anything else is true.
		if (number!=null) {
			return true;
		}
		return false;
	}
	/**
	 * Is this value a reference to a variable?
	 * @return true if is a variable reference
	 */
	public boolean isVariable() {
		return (identifier!=null);
	}

	public boolean isNull() {
		if (number==null && string==null && bool==null && array==null) return true;
		return false;
	}
	
	public BigDecimal getNumber() {
		if (isVariable()) {
			throw new SodacanException("Value cannot be determined for a variable until resolved");
		}
		if (number!=null) return number;
		if (string!=null) return new BigDecimal(string);
		throw new RuntimeException("Wrong type");
	}
	
	public Boolean getBoolean() {
		if (isVariable()) {
			throw new SodacanException("Type cannot be determined for a variable until resolved");
		}
		if (bool!=null) return bool;
		if (number!=null) return !number.equals(BigDecimal.ZERO);
		if (string!=null) {
			if ("true".equals(string)) return true;
			if ("false".equals(string)) return false;
			throw new SodacanException( " A string must contain either true or false to be considered a boolean");
		}

		return bool;
	}
	
	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Return the string value of a Value. But this does not resolve the variable which must be done first.
	 * @return
	 */
	public String getString() {
		return string;
//		if (isVariable()) {
//			throw new SodacanException("Type cannot be determined for a variable until resolved");
//		}
//		return toString();
	}
	/**
	 * Return identifier when this value is a variableReference
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}
	public List<Value> getArray() {
		return array;
	}
	/**
	 * This serialize and deserialize methods guarantee compatibility of a value over time represented by a string
	 * @return
	 */
	public String serialize() {
		StringBuffer sb = new StringBuffer();
		if (number!=null) {
			sb.append(NUMBER);
			sb.append(number.toPlainString());
		} else if (string!=null) {
			sb.append(STRING);
			sb.append(string);
		} else if (identifier!=null) {
			sb.append(IDENTIFIER);
			sb.append(identifier);
		} else if (dateTime!=null) {
			sb.append(DATETIME);
			sb.append(dateTime.toString());
		} else if (bool!=null) {
			sb.append(BOOLEAN);
			sb.append(Boolean.toString(bool));
		} else if (array!=null) { 
			return array.toString();
		} else {
			sb.append(NULL);
		}
		return sb.toString();
		
	}
	public static Value deserialize( String value ) {
		if (value.startsWith(STRING)) {
			return new Value(value.substring(STRING.length()));
		}
		if (value.startsWith(IDENTIFIER)) {
			return new Value(value.substring(IDENTIFIER.length()),true);
		}
		if (value.startsWith(NUMBER)) {
			return new Value(new BigDecimal(value.substring(NUMBER.length())));
		}
		if (value.startsWith(DATETIME)) {
			return new Value(ZonedDateTime.parse(value.substring(DATETIME.length())));
		}
		if (value.equals("boolean-true") ) {
			return new Value(true);
		}
		if (value.equals("boolean-false") ) {
			return new Value(false);
		}
		// Must be null
		return new Value();
	}
	@Override
	public String toString() {
		if (number!=null) return number.toString();
		if (string!=null) return string;
		if (bool!=null) return Boolean.toString(bool);
		if (dateTime!=null) return dateTime.toString();
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
	/**
	 * Compare two values and return -1 if the other is lower, 0 if they are equal, and 1 if the other is greater.
	 * Not all combinations are comparable. An unresolved identifier throws an exception.
	 * two numbers = numeric compare, two string = alphanumberic compare, two booleans = and
	 * A string and number 
	 * A number and string
	 * A string and boolean
	 * @param other
	 * @return
	 */
	public int compare( Value other) {
		if (isNumber() && other.isNumber()) {
			return getNumber().compareTo(other.getNumber());
		}
		if (isBoolean() && other.isBoolean()) {
			if (getBoolean() && other.getBoolean()) {
				return 0;
			} else {
				return 1;
			}
		}
		if (isString() && other.isString()) {
			return toString().compareTo(other.toString());
		}
		throw new SodacanException("These two values cannot be compared");
	}
	
}
