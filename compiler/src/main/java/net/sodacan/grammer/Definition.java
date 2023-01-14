package net.sodacan.grammer;
/**
 * Defines a single variable used in a unit
 * @author John Churin
 *
 */
public class Definition {
	private String name;
	public Definition(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public String toString() {
		return name;
	}
	
	
}
