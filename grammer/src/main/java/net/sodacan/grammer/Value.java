package net.sodacan.grammer;

public class Value {
	private Integer integer = null;
	private String string = null;
	private Boolean bool = null;

	public Value() {
		
	}
	public Value(int integer) {
		this.integer = integer;
	}
	public Value(String string) {
		this.string = string;
	}
	public Value(Boolean bool) {
		this.bool = bool;
	}

	public boolean isInteger() {
		if (integer!=null) return true;
		// A String containing an integer is an integer
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
		return isInteger();
	}
	
	public boolean isBoolean() {
		if (bool!=null) return true;
		return false;
	}
	
	public boolean isNull() {
		if (integer==null && string==null && bool==null) return true;
		return false;
	}
	
	public Integer getInteger() {
		if (integer!=null) return integer;
		if (string!=null) return Integer.parseInt(string);
		throw new RuntimeException("Wrong type");
	}
	
	public Boolean getBoolean() {
		if (bool!=null) return bool;
		return bool;
	}

	public String getValue() {
		return toString();
	}
	
	@Override
	public String toString() {
		if (integer!=null) return Integer.toString(integer);
		if (string!=null) return string;
		if (bool!=null) return Boolean.toString(bool);
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
