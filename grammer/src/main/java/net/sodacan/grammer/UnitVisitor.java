package net.sodacan.grammer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sodacan.grammer.LanguageParser.EventContext;
import net.sodacan.grammer.LanguageParser.ProgContext;
import net.sodacan.grammer.LanguageParser.StateContext;
import net.sodacan.grammer.LanguageParser.UnitContext;
import net.sodacan.grammer.LanguageParser.WhenStatementContext;

public class UnitVisitor extends LanguageBaseVisitor<Void> {
	private Map<String,Unit> units = new HashMap<>();
	private Unit unit;
	private List<String> sortedList;
	/**
	 * Return the map of units discovered
	 * @return
	 */
	public Map<String, Unit> getUnits() {
		return units;
	}

	/**
	 * Return a list of units discovered
	 * @return
	 */
	public List<Unit> getUnitList() {
		if (sortedList==null) {
			throw new RuntimeException("Sorted list is not available until dereferenceLikes has been called");
		}
		List<Unit> unitList = new ArrayList<>();
		for (String name : sortedList) {
			unitList.add(units.get(name));
		}
		return unitList;
	}
	/**
	 * A unit containing a Like will inherit from the liked unit(s).
	 * We first check for cycles. If none, we topologically sort the Units and process them in top-down order.
	 */
	public void dereferenceLikes() {
		// Load up our the Graph with the known nodes (verticies)
		Graph graph = new Graph(getUnits().keySet());
		// Now load up the edges 
		for (Entry<String,Unit> entry : units.entrySet()) {
			Unit unit = entry.getValue();
			if (unit.getLikeName()!=null) {
				graph.addEdge(unit.getLikeName(), unit.getName());
			}
		}
		// If there are cycles, throw an exception
	    String r = graph.isCyclic();
	    if(r!=null) {
            throw new RuntimeException("Unit contains cycle at: " + r);
	    } 
	    // Sort so that we dereference from broad to narrow
	    // Some units have no likes but they could still be liked.
	    sortedList = graph.topologicalSort();
//	    System.out.println(sortedList);
	    // Now copy elements from liked units to liking units
	    for (String unitName: sortedList) {
	    	Unit u = units.get(unitName);
	    	String ln = u.getLikeName();
	    	if (ln!=null) {
	    		Unit l = units.get(ln);
	    		u.copyFrom(l);
	    	}
	    }
	    

//	  	for (Unit unit : getSortedList()) {
//	  		if (unit.getLikeName()!=null) {
//	  			Unit likeUnit = units.get(unit.getLikeName());
//	  			if (likeUnit==null) {
//	  				throw new RuntimeException("Reference to like unit not found - unit" + unit.getName());
//	  			}
//	  		}
//	  	}
	  	// Dereference "like" units
	  	// 
	}
	@Override
	public Void visitProg(ProgContext ctx) {
		// TODO Auto-generated method stub
		return super.visitProg(ctx);
	}

	@Override
	public Void visitUnit(UnitContext ctx) {
		unit = new Unit(ctx);
		unit.setName(ctx.ID(0).getText());
//		String likeName = "all";
		if (ctx.ID(1)!=null) {
			unit.setLikeName(ctx.ID(1).getText());
		}
//		if (units.containsKey(unit.getName())) {
//			int line = ctx.start.getLine();
////			int index = ctx.start.getStartIndex();
//			throw new RuntimeException("Line " + line + " Duplicate unit name: " + unit.getName());
//		}
		units.put(unit.getName(), unit);
//		// A unit can be empty, if not, evaluate statements
//		for (DeclarationContext dc : ctx.declaration()) {
//			visit(dc);
//		}
//		for (StatementContext sc : ctx.statement()) {
//			visit(sc);
//		}
//		return null;
		return super.visitUnit(ctx);
	}
	
	/**
	 * Add an event to the unit
	 */
	@Override
	public Void visitEvent(EventContext ctx) {
		unit.addEvent(ctx.getText());
		return super.visitEvent(ctx);
	}

	@Override
	public Void visitState(StateContext ctx) {
		String state = ctx.getText();
		if ("next".contentEquals(state)) {
			int line = ctx.start.getLine();
			throw new RuntimeException("Line " + line + "reserved word 'next' cannot be a state ");
		}
		unit.addState(state);
		return super.visitState(ctx);
	}

	@Override
	public Void visitWhenStatement(WhenStatementContext ctx) {
		unit.addWhen(ctx);
		return super.visitWhenStatement(ctx);
	}

}
