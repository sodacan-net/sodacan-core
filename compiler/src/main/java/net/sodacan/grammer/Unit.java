package net.sodacan.grammer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sodacan.grammer.LanguageParser.UnitContext;
import net.sodacan.grammer.LanguageParser.WhenStatementContext;
/**
 * An iot component. A lamp, switch, tank, or pump
 * @author John Churin
 *
 */
public class Unit {
	private String name;
	private String likeName;
	private Map<String,Definition> definitions = new HashMap<>();
	private List<WhenStatementContext> whens = new ArrayList<>();
	
	protected Map<String,Value> values = new HashMap<>();
	
	// The parseTree that defines this Unit
	private UnitContext unitCtx;

	public Unit( UnitContext unitCtx) {
		this.unitCtx = unitCtx;
	}
	
	public UnitContext getUnitCtx() {
		return unitCtx;
	}

	public void setUnitCtx(UnitContext unitCtx) {
		this.unitCtx = unitCtx;
	}

	public Definition getDefinition(String name) {
		return definitions.get(name);
	}
	
	public Value addDefinition(String name, Definition definition) {
		definitions.put(name, definition);
		Value v;
		if ("event".contentEquals(name)) {
			v= new Value();
		} else {
			v = getDefault(name);
			values.put(name, v);
		}
		return v;
	}
	
	public void setWhens(List<WhenStatementContext> whens) {
		this.whens = whens;
	}

	/**
	 * The first value in an enumeration (such as state and event variables) is the default value
	 * @param variable
	 * @return
	 */
	protected Value getDefault(String variable) {
		Definition definition = definitions.get(variable);
		if (definition instanceof EnumDefinition) {
			EnumDefinition ed = (EnumDefinition)definition;
			return new Value(ed.getFirstOption());
		}
		return new Value();
	}

	/**
	 * We return a boolean value after looking for a variable and comparing its value to the value supplied
	 * @param variable
	 * @param value
	 * @return
	 */
	public Value istValueMatch(String variable, String value) {
		// Since everything is resolved, we can safely assume that the variable is valid
		// But may not be instantiate as a runtime value yet
		Value v = getValue( variable );
		return new Value(v.toString().equals(value));
	}

	public String getValues() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		sb.append(": ");
		for (Entry<String,Value> entry : values.entrySet()) {
        	sb.append(entry.getKey());
        	sb.append("=");
        	sb.append(entry.getValue());
        	sb.append(", ");
        }
		return sb.toString();
	}
	public Value getValue(String variable) {
		Value v = values.get(variable);
//		if (v==null) {
//			// Initialize the value to the first (default) value
//			v = getDefault(variable);
//			values.put(variable, v);
//		}
		return v;
	}
		/**
		 * 
		 * @param variable
		 * @param value
		 */
	public void setValue(String variable, Value value) {
		values.put(variable, value);
	}
	
	/**
	 * This unit is composed of elements of another unit. We simply copy the elements
	 * At runtime, the contents of the liked unit are inside this unit. 
	 * DEV NOTE: It's probable not a simple as just copying the contents.
	 * For example, cross-unit references could be confusing. 
	 * @param like The unit that is to be copied into this unit
	 */
	public void copyFrom(Unit like) {
		for (Entry<String,Definition> entry : like.definitions.entrySet()) {
			definitions.put(entry.getKey(),entry.getValue());
		}
		whens.addAll(like.getWhens());
//		System.out.println(getName() + " States: " + states + " including those copied from " + likeName);
	}
	
	/**
	 * Verify that the supplied name and value are valid
	 * @param variable
	 * @param value If non-null, value must match list of valid values
	 * @return
	 */
	public boolean isValidDeclaration( String variable, String value) {
		Definition d = definitions.get(variable);
		if (d==null) return false;
		if (!(d instanceof EnumDefinition)) return true;
		if (value==null) return true;
		if (((EnumDefinition)d).hasOption(value)) return true;
		return false;
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
		return name + ": " + definitions;
	}
	
}
