package net.sodacan.rules;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Key;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

public class Event implements Element {
	private String name;
	private String value;
	private double level;
	private Date ts;
	
	public Event(String name, String value) {
		this.name = name;
		this.value = value;
		this.ts = new Date();
	}

	public Event(String name, double level) {
		this.name = name;
		this.level = level;
		this.ts = new Date();
	}

	@Override
	public int hashCode() {
		return ts.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Event) {
			Event other = (Event) obj;
			if (this.name.equals(other.name) ) return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}
//	public void setName(String name) {
//		this.name = name;
//	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public double getLevel() {
		return level;
	}

	public void setLevel(double level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Event: " + getName();
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}
	
}
