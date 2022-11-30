package net.sodacan.rules;

public class Tick extends Event {

	public Tick() {
		super(null, null);
	}

	
	@Override
	public String getName() {
		return toString();
	}


	@Override
	public String toString() {
		return "Tick";
	}
	
}
