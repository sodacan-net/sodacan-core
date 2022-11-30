package net.sodacan.rules;

public class Year {
	private int value;

	public Year(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Year) {
			Year other = (Year) obj;
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
