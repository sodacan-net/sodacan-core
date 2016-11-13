package org.t3.farm.control;

public abstract class DevEvent extends DeviceBase {
	boolean processed;
	
	/**
	 * Construct an event
	 * @param key
	 */
	protected DevEvent(String key) {
		super(key);
		this.processed = false;
	}
	
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	@Override
	public String toString() {
		return "Event: " + getKey();
	}
	
	/**
	 * Equality can be determined by "identity", ie the address of the 
	 * object since the exact same event can occur multiple times. Another approach would be to
	 * provide equality base on a timestamp
	 */
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	

}
