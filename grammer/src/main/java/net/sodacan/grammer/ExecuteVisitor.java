package net.sodacan.grammer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import net.sodacan.grammer.LanguageParser.AddSubExprContext;
import net.sodacan.grammer.LanguageParser.AndOrWhenContext;
import net.sodacan.grammer.LanguageParser.AssignExprContext;
import net.sodacan.grammer.LanguageParser.EqualsExprContext;
import net.sodacan.grammer.LanguageParser.ExpressionContext;
import net.sodacan.grammer.LanguageParser.FalseKeywordContext;
import net.sodacan.grammer.LanguageParser.FunctionExprContext;
import net.sodacan.grammer.LanguageParser.IntegerLiteralContext;
import net.sodacan.grammer.LanguageParser.MulDivExprContext;
import net.sodacan.grammer.LanguageParser.NotWhenContext;
import net.sodacan.grammer.LanguageParser.ParameterListContext;
import net.sodacan.grammer.LanguageParser.SimpleContext;
import net.sodacan.grammer.LanguageParser.StringLiteralContext;
import net.sodacan.grammer.LanguageParser.ThenExpressionContext;
import net.sodacan.grammer.LanguageParser.TrueKeywordContext;
import net.sodacan.grammer.LanguageParser.VariableExprContext;
import net.sodacan.grammer.LanguageParser.WhenIdentifierContext;
import net.sodacan.grammer.LanguageParser.WhenStatementContext;
/**
 * When an event arrives, we send it to this visitor which is initiated 
 * with a collection of Unit objects. The parse tree still backs the Unit 
 * object which contains direct references to important parse tree nodes.
 * Thsi class is mostly just a collection of "when statement" subtree visitors.
 * We don't start at the root node any longer because the Unit objects
 * are sorted in topological sort order.
 * @author John Churin
 *
 */
public class ExecuteVisitor extends LanguageBaseVisitor<Value> {
	// This list is in order of how we process units
	protected List<Unit> units;
	// We also populate a map of units for fast lookup
	protected Map<String,Unit> unitMap;

	// These variables are local to the execution context
	protected Unit unit;
	protected Event event;
	
	public ExecuteVisitor( List<Unit> units) {
		this.units = units;
		unitMap = new HashMap<>();
		for (Unit unit : units) {
			unitMap.put(unit.getName(), unit);
		}
	}
	
	/**
	 * Call this with a new event. The units will be evaluated in the context of this event.
	 * We do a nested loop of units and visit their when statements in lexicographic order.
	 * @param event
	 */
	public void processEvent(Event event ) {
		this.event = event;
		System.out.println("\nExecute Visitor for event: " + event);
  	  	// For debugging, print out the units
    	System.out.println("Starting with variables: " );
        for (Unit unit : units) {
        	System.out.println(unit.getValues());
        }
		for (Unit unit : units) {
			this.unit = unit;
			System.out.print("\nUNIT " + unit.getName());
			for (WhenStatementContext ctx : unit.getWhens()) {
				visit(ctx);
			}
		}
    	System.out.println("\nEnding with variables: ");
        for (Unit unit : units) {
        	System.out.println(unit.getValues());
        }
        System.out.println();
	}

	
	/**
	 * An identifier can have two or three segments. If two, the identifier is local to the current unit. 
	 * If three, the first segment is the name of the unit containing the variable.
	 */
	@Override
	public Value visitWhenIdentifier(WhenIdentifierContext ctx) {
		List<String> ids = new ArrayList<>();
		for (TerminalNode node: ctx.ID()) {
			ids.add(node.getText());
		}
		System.out.print( ids + " ");
		// attribute.value is 2 nodes or unit.attribute.value is 3 nodes
		Unit curUnit = unit;
		String variable;
		String value;
		if (ids.size()==3) {
			curUnit = unitMap.get(ids.get(0));
			variable = ids.get(1);
			value = ids.get(2);
		} else if (ids.size()==2) {
			variable = ids.get(0);
			value = ids.get(1);			
		} else {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " - Incorrect number of id segments at runtime");
		}
		// Did this reference match a declaration?
		if (!curUnit.isValidDeclaration(variable,value)) {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " Identifier " + ctx.getText() + " is not declared at runtime");
		}
		// First see if this is an event match
		if ("event".contentEquals(variable)) {
			// Is this event for our unit?
			if (event.getName().contentEquals(curUnit.getName())) {
				return new Value( (event.getValue().contentEquals(value)));
			} else {
				return new Value(false);
			}
		}
		// See if a variable matches in the specified unit (not necessarily this unit)
		return curUnit.istValueMatch(variable,value);
	}

	@Override
	public Value visitWhenStatement(WhenStatementContext ctx) {
		System.out.print("\n  WHEN ");
		Value when = visit(ctx.whenExpression());
		if (when.getBoolean()) {
			Value value = new Value();
			if (ctx.thenExpression()!=null) {
				visit(ctx.thenExpression());
			}
			return value;
		}
		return new Value();
	}
	
	@Override
	public Value visitNotWhen(NotWhenContext ctx) {
		System.out.print("NOT ");
		Value r = visit(ctx.whenExpression());
		return new Value(!r.getBoolean());
	}

//	@Override
//	public Value visitThenIdentifier(ThenIdentifierContext ctx) {
//		List<String> ids = new ArrayList<>();
//		for (TerminalNode node: ctx.ID()) {
//			ids.add(node.getText());
//		}
//		System.out.print(ids + " ");
//		unit.setValue(ids.get(0), new Value(ids.get(1)));
//		return super.visitThenIdentifier(ctx);
//	}

	@Override
	public Value visitAndOrWhen(AndOrWhenContext ctx) {
		Value left = visit(ctx.whenExpression(0));
		if ("AND".contentEquals(ctx.op.getText())) {
			System.out.print("AND "); 
		} else {
			System.out.print("OR ");
		}
		Value right = visit(ctx.whenExpression(1));
		if ("AND".contentEquals(ctx.op.getText())) {
			return new Value(left.getBoolean() && right.getBoolean());
		} else {
			return new Value(left.getBoolean() || right.getBoolean());
		}
	}
	@Override
	public Value visitThenExpression(ThenExpressionContext ctx) {
		System.out.print("\n    THEN ");
		return super.visitThenExpression(ctx);
	}

	public Value nextFunction( FunctionExprContext ctx, Value parameters) {
		// Verify that parameters is an array of values, size - 1 in this case.
		if (!parameters.isArray() || parameters.getArray().size()!=1) {
			throw new RuntimeException("Parameter must name a variable");
		}
		Value p1 = parameters.getArray().get(0);
		if (!p1.isString()) {
			throw new RuntimeException("Parameter must name a variable");
		}
		Definition def = unit.getDefinition(p1.getValue());
		if (def==null) {
			throw new RuntimeException("Parameter must name a variable");
		}
		if (!(def instanceof EnumeratedDefinition)) {
			throw new RuntimeException("Parameter to next function must be an enumerated variable");
		}
		Value val = unit.getValue(def.getName());
		EnumeratedDefinition edef = (EnumeratedDefinition)def;
		val = new Value(edef.getNextOption(val.getValue()));
		// Set the new value of the variable
		unit.setValue(def.getName(), val);
		return val;
	}
	
	/**
	 * If the value provided is the name of a variable, then return the value of the variable.
	 * Otherwise, just return the value as supplied.
	 * @param value
	 * @return
	 */
	public Value resolveVariable(Value value) {
		if (!value.isVariable()) {
			return value;
		}
		return unit.getValue(value.getValue());
	}
	
	public Value printFunction(FunctionExprContext ctx, Value parameters) {
		StringBuffer sb = new StringBuffer();
		if (!parameters.isArray()) {
			throw new RuntimeException("Parameter must name a variable");
		}
		System.out.print("Print From Line ");
		System.out.print(ctx.start.getLine());
		System.out.print(": ");
		for (Value v : parameters.getArray()) {
			// Evaluate, first
			v = resolveVariable(v);
			sb.append(v.toString());
			System.out.print(v);
		}
		System.out.print("\n");
		return new Value(sb.toString());
	}
	public Value executeFunction(FunctionExprContext ctx, String functionName, Value parameters) {
		if ("next".contentEquals(functionName)) {
			return nextFunction(ctx,parameters);
		}
		if ("print".contentEquals(functionName)) {
			return printFunction(ctx,parameters);
		}
		return new Value();
	}

	@Override
	public Value visitFunctionExpr(FunctionExprContext ctx) {
		String functionName = ctx.ID().getText();
		Value p = visit(ctx.parameterList());
		System.out.println("Function:" + functionName + " " + p);
		// Now execute the function
		return executeFunction(ctx, functionName,p);
	}

	/**
	 * Create an array of values comprising a parameter list
	 */
	@Override
	public Value visitParameterList(ParameterListContext ctx) {
		List<Value> array = new ArrayList<>(ctx.expression().size());
		for (ExpressionContext exprCtx : ctx.expression()) {
			Value v = visit(exprCtx);
			array.add(v);
		}
		return new Value(array);
	}
	/**
	 * If we visit a variable, just return the name of the variable, not it's value.
	 * Used in functions
	 */
	@Override
	public Value visitVariableExpr(VariableExprContext ctx) {
		return new Value(ctx.ID().getText(),true);
	}

	@Override
	public Value visitStringLiteral(StringLiteralContext ctx) {
		// Remove the quotes
		String x = ctx.STRING().getText();
		x = x.substring(1, x.length()-1);
//		System.out.print(x + " "); 
		return new Value(x,false);
	}
	
	@Override
	public Value visitIntegerLiteral(IntegerLiteralContext ctx) {
		return new Value(ctx.getText());
	}

	@Override
	public Value visitTrueKeyword(TrueKeywordContext ctx) {
		return new Value(true);
	}

	@Override
	public Value visitFalseKeyword(FalseKeywordContext ctx) {
		return new Value(false);
	}

	@Override
	public Value visitAddSubExpr(AddSubExprContext ctx) {
		Value left = visit(ctx.expression(0));
		Value right = visit(ctx.expression(1));
		if (ctx.op.getType()==LanguageParser.ADD) {
			return new Value(left.getInteger()+right.getInteger());
		}
		if (ctx.op.getType()==LanguageParser.SUB) {
			return new Value(left.getInteger()-right.getInteger());
		}
		return new Value();
	}

	@Override
	public Value visitMulDivExpr(MulDivExprContext ctx) {
		Value left = visit(ctx.expression(0));
		Value right = visit(ctx.expression(1));
		if (ctx.op.getType()==LanguageParser.MUL) {
			return new Value(left.getInteger()*right.getInteger());
		}
		if (ctx.op.getType()==LanguageParser.DIV) {
			return new Value(left.getInteger()/right.getInteger());
		}
		return new Value();
	}
	
//	@Override
//	public Value visitVariableExpr(VariableExprContext ctx) {
//		String id = ctx.getText();
//		Value r = unit.getValue(id);
//		if (r==null) throw new RuntimeException("Undefined variable " + id);
//		return r;
//	}

	@Override
	public Value visitEqualsExpr(EqualsExprContext ctx) {
		Value left = visit(ctx.expression(0));
		Value right = visit(ctx.expression(1));
		if (left.isBoolean() && right.isBoolean()) return new Value((left.getBoolean()==right.getBoolean()));
		if (left.isInteger() && right.isInteger()) return new Value((left.getInteger()==right.getInteger()));
		if (left.isString() && right.isString()) return new Value(left.toString().equals(right.toString()));
		if (left.isNull() && right.isNull()) return new Value(true);
		return new Value(false); 
	}

	@Override
	public Value visitSimple(SimpleContext ctx) {
		String variable = ctx.ID(0).getText();
		Value value = new Value(ctx.ID(1).getText());
		unit.setValue(variable, value);
		System.out.print(variable + "." + value);
		return super.visitSimple(ctx);
	}

	@Override
	public Value visitAssignExpr(AssignExprContext ctx) {
		String variable = ctx.ID().getText();
		Value v = visit(ctx.expression());
		unit.setValue(variable, v);
		System.out.print(variable + "=" + v);
		return super.visitAssignExpr(ctx);
	}
	
	
}
