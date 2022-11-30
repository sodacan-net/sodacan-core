package net.sodacan.rules;

public class Day {
	private int value;

	public Day(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Day) {
			Day other = (Day) obj;
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
