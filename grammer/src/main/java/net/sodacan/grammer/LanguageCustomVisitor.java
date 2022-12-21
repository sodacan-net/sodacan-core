package net.sodacan.grammer;

import java.util.HashMap;
import java.util.Map;

import net.sodacan.grammer.LanguageParser.AddSubExprContext;
import net.sodacan.grammer.LanguageParser.AssignStatementContext;
import net.sodacan.grammer.LanguageParser.EqualsExprContext;
import net.sodacan.grammer.LanguageParser.EventContext;
import net.sodacan.grammer.LanguageParser.EventStatementContext;
import net.sodacan.grammer.LanguageParser.EventsContext;
import net.sodacan.grammer.LanguageParser.ExpressionStatementContext;
import net.sodacan.grammer.LanguageParser.FalseKeywordContext;
import net.sodacan.grammer.LanguageParser.IntegerLiteralContext;
import net.sodacan.grammer.LanguageParser.MulDivExprContext;
import net.sodacan.grammer.LanguageParser.ParenExprContext;
import net.sodacan.grammer.LanguageParser.ProgContext;
import net.sodacan.grammer.LanguageParser.PropContext;
import net.sodacan.grammer.LanguageParser.PropertyDeclarationContext;
import net.sodacan.grammer.LanguageParser.PropertyListContext;
import net.sodacan.grammer.LanguageParser.StateContext;
import net.sodacan.grammer.LanguageParser.StateStatementContext;
import net.sodacan.grammer.LanguageParser.StatementContext;
import net.sodacan.grammer.LanguageParser.StatementListContext;
import net.sodacan.grammer.LanguageParser.StatesContext;
import net.sodacan.grammer.LanguageParser.StringLiteralContext;
import net.sodacan.grammer.LanguageParser.TrueKeywordContext;
import net.sodacan.grammer.LanguageParser.UnitContext;
import net.sodacan.grammer.LanguageParser.UnitsContext;
import net.sodacan.grammer.LanguageParser.VariableExprContext;

public class LanguageCustomVisitor extends LanguageBaseVisitor<Value> {
	int indent = 0;
	private Map<String,Value> properties = new HashMap<>();
	private Map<String,Value> variables = new HashMap<>();
	private Map<String,Unit> units = new HashMap<>();
	private Unit unit;

	public Map<String,Value> getProperties() {
		return properties;
	}
	@Override
	public Value visitProg(ProgContext ctx) {
		visit(ctx.props());
		visit(ctx.units());
		return new Value();
	}

	@Override
	public Value visitPropertyList(PropertyListContext ctx) {
		for (PropContext pc : ctx.prop()) {
			visit(pc);
		}
		return new Value();
	}

	@Override
	public Value visitPropertyDeclaration(PropertyDeclarationContext ctx) {
		String name = ctx.ID().getText();
		Value value = visit(ctx.expression());
//		System.out.println("Name: " + name + " Value: " + value);
		properties.put(name, value);
		return value;
	}

	@Override
	public Value visitUnit(UnitContext ctx) {
		unit = new Unit();
		unit.setName(ctx.ID(0).getText());
		String likeName = "all";
		if (ctx.ID(1)!=null) {
			likeName = ctx.ID(1).getText();
		}
		unit.setLikeName(likeName);
		if (units.containsKey(unit.getName())) {
			int line = ctx.start.getLine();
//			int index = ctx.start.getStartIndex();
			throw new RuntimeException("Line " + line + " Duplicate unit name: " + unit.getName());
		}
		units.put(unit.getName(), unit);
		// A unit can be empty, if not, evaluation statements
		if (ctx.statements()!=null) {
			visit(ctx.statements());
		}
		System.out.println(unit);
		return new Value();
	}
	
	@Override
	public Value visitStatementList(StatementListContext ctx) {
		for (StatementContext sc : ctx.statement()) {
			visit(sc);
		}
		return new Value();
	}

	@Override
	public Value visitEventStatement(EventStatementContext ctx) {
		visit(ctx.events());
		return new Value();
	}

	@Override
	public Value visitEvents(EventsContext ctx) {
		for (EventContext ec : ctx.event()) {
			visit(ec);
		}
		return new Value();
	}

	/**
	 * Add an event to the unit
	 */
	@Override
	public Value visitEvent(EventContext ctx) {
		unit.addEvent(ctx.getText());
		return new Value();
	}

	@Override
	public Value visitStateStatement(StateStatementContext ctx) {
		visit(ctx.states());
		return new Value();
	}

	@Override
	public Value visitStates(StatesContext ctx) {
		for (StateContext sc : ctx.state()) {
			visit(sc);
		}
		return new Value();
	}

	@Override
	public Value visitState(StateContext ctx) {
		unit.addState(ctx.getText());
		return new Value();
	}

	@Override
	public Value visitUnits(UnitsContext ctx) {
		for (UnitContext unit : ctx.unit()) {
			visit(unit);
		}
		return new Value();
	}

	@Override
	public Value visitVariableExpr(VariableExprContext ctx) {
		String id = ctx.getText();
		Value r = variables.get(id);
		if (r==null) throw new RuntimeException("Undefined variable " + id);
		return r;
	}

	@Override
	public Value visitExpressionStatement(ExpressionStatementContext ctx) {
		return visit(ctx.expression());
	}

	@Override
	public Value visitParenExpr(ParenExprContext ctx) {
		return visit(ctx.expression());
	}

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
	public Value visitStringLiteral(StringLiteralContext ctx) {
		// Remove the quotes
		String x = ctx.STRING().getText();
		x = x.substring(1, x.length()-1);
		return new Value(x);
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

	@Override
	public Value visitAssignStatement(AssignStatementContext ctx) {
		String id = ctx.ID().getText();
		Value expr = visit(ctx.expression());
		variables.put(id, expr);
		return expr;
	}

}
