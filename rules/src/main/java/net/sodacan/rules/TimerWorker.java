package net.sodacan.rules;

/**
 *  Internal only, created when a timer is active
 */
public class TimerWorker {
	private String state;
	private int time;
	private String toValue;
	
	public TimerWorker(String state, int time, String toValue) {
		super();
		this.state = state;
		this.time = time;
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

	@Override
	public int hashCode() {
		return getState().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TimerWorker) {
			TimerWorker other = (TimerWorker)obj;
			if (getState().equals(other.getState()) && getToValue().equals(other.getToValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "TimeWorker %s to %s in %d".formatted(getState(),getToValue(),getTime());
	}

}
