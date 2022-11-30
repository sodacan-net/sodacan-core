package net.sodacan.rules;

public class Month implements Element {
	private int value;

	public Month(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Month) {
			Month other = (Month) obj;
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
