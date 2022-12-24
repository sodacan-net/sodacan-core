package net.sodacan.grammer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sodacan.grammer.LanguageParser.WhenStatementContext;

public class Unit {
	private String name;
	private String likeName;
	private List<String> events = new ArrayList<>();
	private List<String> states = new ArrayList<>();
	private List<WhenStatementContext> whens = new ArrayList<>();

	/**
	 * Verify that the supplied name and value are valid
	 * @param variable
	 * @param value If non-null, value must match list of valid values
	 * @return
	 */
	public boolean isValidDeclaration( String variable, String value) {
		if ("state".contentEquals(variable)) {
			if (value==null || states.contains(value)) {
				return true;
			}
		}
		if ("event".contentEquals(variable)) {
			if (value==null || events.contains(value)) {
				return true;
			}
		}
		return false;
	}

	public void addEvent(String event) {
		events.add(event);
	}
	public List<String> getStates() {
		return events;
	}

	public void addState(String state) {
		states.add(state);
	}
	public List<String> getEvents() {
		return events;
	}

	public List<WhenStatementContext> getWhens() {
		return whens;
	}
	
	public void addWhen(WhenStatementContext ctx) {
		whens.add(ctx);
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
