package org.t3.farm.control;

public class DevParamChange {
	private String key;
	private String newValue;

	public DevParamChange( String key, String newValue) {
		this.key = key;
		this.newValue = newValue;
	}

	public String getKey() {
		return key;
	}
	public String getNewValue() {
		return newValue;
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public String toString() {
		return "Change " + key + " value to " + newValue;
	}
	
}
