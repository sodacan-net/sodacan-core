package net.sodacan.rules;

import java.time.ZonedDateTime;

public class Tick extends Event {
	private ZonedDateTime now;
	
	public Tick(ZonedDateTime now) {
		super(null, null);
		this.now = now;
	}
	
	@Override
	public String getName() {
		return toString();
	}
	
	public ZonedDateTime getNow() {
		return now;
	}

	public void setNow(ZonedDateTime now) {
		this.now = now;
	}


	@Override
	public String toString() {
		return "Tick";
	}
	
}
