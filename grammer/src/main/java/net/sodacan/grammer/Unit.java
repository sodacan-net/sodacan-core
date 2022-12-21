package net.sodacan.grammer;

import java.util.HashSet;
import java.util.Set;

public class Unit {
	private String name;
	private String likeName;
	private Set<String> events = new HashSet<>();
	private Set<String> states = new HashSet<>();
	
	public void addEvent(String event) {
		events.add(event);
	}
	public Set<String> getStates() {
		return events;
	}

	public void addState(String state) {
		states.add(state);
	}
	public Set<String> getEvents() {
		return events;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLikeName() {
		return likeName;
	}
	public void setLikeName(String likeName) {
		this.likeName = likeName;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Unit) {
			Unit other = (Unit)obj;
			if (name.equals(other.name)) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name + " Events: " + events + " States: " + states;
	}
	
}
