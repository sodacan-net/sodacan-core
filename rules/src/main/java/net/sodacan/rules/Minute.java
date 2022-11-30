package net.sodacan.rules;

public class Minute {
	private int value;

	public Minute(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Minute) {
			Minute other = (Minute) obj;
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
