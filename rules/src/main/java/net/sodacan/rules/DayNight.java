package net.sodacan.rules;

public class DayNight {
	private String value;

	public DayNight(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DayNight) {
			DayNight other = (DayNight) obj;
			if (getValue().equals(other.getValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
