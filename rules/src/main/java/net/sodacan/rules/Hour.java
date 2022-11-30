package net.sodacan.rules;

public class Hour {
	private int value;

	public Hour(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hour) {
			Hour other = (Hour) obj;
			if (getValue()==other.getValue()) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return value;
	}

}
