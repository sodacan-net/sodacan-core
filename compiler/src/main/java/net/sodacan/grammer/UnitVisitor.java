package net.sodacan.grammer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.antlr.v4.runtime.tree.TerminalNode;

import net.sodacan.grammer.LanguageParser.EnumStatementContext;
import net.sodacan.grammer.LanguageParser.EnumerationConstraintContext;
import net.sodacan.grammer.LanguageParser.EnumerationContext;
import net.sodacan.grammer.LanguageParser.IntegerStatementContext;
import net.sodacan.grammer.LanguageParser.NullContstraintContext;
import net.sodacan.grammer.LanguageParser.NumericRangeContext;
import net.sodacan.grammer.LanguageParser.ProgContext;
import net.sodacan.grammer.LanguageParser.RangeContstraintContext;
import net.sodacan.grammer.LanguageParser.UnitContext;
import net.sodacan.grammer.LanguageParser.WhenStatementContext;

public class UnitVisitor extends LanguageBaseVisitor<Void> {
	private Map<String,Unit> units = new HashMap<>();
	private Unit unit;
	private List<String> sortedList;
	private String variable;
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
	
	
	@Override
	public Void visitEnumStatement(EnumStatementContext ctx) {
		variable = ctx.ID().getText();
		visit(ctx.constraints());
		return super.visitEnumStatement(ctx);
	}

	
	@Override
	public Void visitEnumeration(EnumerationContext ctx) {
		EnumDefinition ed = new EnumDefinition(variable); 
		for (TerminalNode node : ctx.ID()) {
			ed.addOption(node.getText());
		}
		unit.addDefinition(variable, ed);
		return null;
	}

	@Override
	public Void visitIntegerStatement(IntegerStatementContext ctx) {
		variable = ctx.n.getText();
		Value v  = unit.addDefinition(variable, new IntegerDefinition(variable));
		if (ctx.v !=null) {
			unit.setValue(variable, new Value(ctx.v.getText()));
		}
		return super.visitIntegerStatement(ctx);
	}

	@Override
	public Void visitNumericRange(NumericRangeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitNumericRange(ctx);
	}

	@Override
	public Void visitEnumerationConstraint(EnumerationConstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEnumerationConstraint(ctx);
	}

	@Override
	public Void visitRangeContstraint(RangeContstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRangeContstraint(ctx);
	}

	@Override
	public Void visitNullContstraint(NullContstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitNullContstraint(ctx);
	}

	@Override
	public Void visitWhenStatement(WhenStatementContext ctx) {
		unit.addWhen(ctx);
		return super.visitWhenStatement(ctx);
	}

}
