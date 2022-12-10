package net.sodacan.rules;

/**
 *  Internal only, created when a timer is active
 */
public class Countdown {
	private String state;
	private int time;
	private int maxTime;
	private String toValue;
	
	public Countdown(String state, int time, int maxTime, String toValue) {
		super();
		this.state = state;
		this.time = time;
		this.maxTime = maxTime;
		this.toValue = toValue;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getToValue() {
		return toValue;
	}
	public void setToValue(String toValue) {
		this.toValue = toValue;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	@Override
	public int hashCode() {
		return getState().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Countdown) {
			Countdown other = (Countdown)obj;
			if (getState().equals(other.getState()) && getToValue().equals(other.getToValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Countdown %s to %s in %d".formatted(getState(),getToValue(),getTime());
	}

}
