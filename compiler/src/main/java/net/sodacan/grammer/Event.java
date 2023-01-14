package net.sodacan.grammer;
import java.util.Date;

public class Event {
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

	public String getFullName() {
		return name + ".event." + value;
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
		return  getName() + "." + getValue();
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}
	
}
