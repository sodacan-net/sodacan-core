package net.sodacan.grammer;

import java.util.HashMap;
import java.util.Map;

import net.sodacan.grammer.LanguageParser.AddSubExprContext;
import net.sodacan.grammer.LanguageParser.AndOrWhenContext;
import net.sodacan.grammer.LanguageParser.DeclarationContext;
import net.sodacan.grammer.LanguageParser.EqualsExprContext;
import net.sodacan.grammer.LanguageParser.EventContext;
import net.sodacan.grammer.LanguageParser.EventStatementContext;
import net.sodacan.grammer.LanguageParser.EventsContext;
import net.sodacan.grammer.LanguageParser.FalseKeywordContext;
import net.sodacan.grammer.LanguageParser.IntegerLiteralContext;
import net.sodacan.grammer.LanguageParser.MulDivExprContext;
import net.sodacan.grammer.LanguageParser.ParenExprContext;
import net.sodacan.grammer.LanguageParser.ProgContext;
import net.sodacan.grammer.LanguageParser.StateContext;
import net.sodacan.grammer.LanguageParser.StateStatementContext;
import net.sodacan.grammer.LanguageParser.StatementContext;

import net.sodacan.grammer.LanguageParser.StatesContext;
import net.sodacan.grammer.LanguageParser.StringLiteralContext;
import net.sodacan.grammer.LanguageParser.TrueKeywordContext;
import net.sodacan.grammer.LanguageParser.UnitContext;
import net.sodacan.grammer.LanguageParser.UnitsContext;
import net.sodacan.grammer.LanguageParser.VariableExprContext;
import net.sodacan.grammer.LanguageParser.WhenStatementContext;

public class ParseVisitor extends LanguageBaseVisitor<Void> {
	int indent = 0;
	private Map<String,Unit> units = new HashMap<>();
	private Unit unit;

	@Override
	public Void visitProg(ProgContext ctx) {
		visit(ctx.units());
		return null;
	}

	@Override
	public Void visitUnit(UnitContext ctx) {
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
		for (DeclarationContext dc : ctx.declaration()) {
			visit(dc);
		}
		for (StatementContext sc : ctx.statement()) {
			visit(sc);
		}
		System.out.println(unit);
		return null;
	}
	
	@Override
	public Void visitEventStatement(EventStatementContext ctx) {
		visit(ctx.events());
		return null;
	}

	@Override
	public Void visitEvents(EventsContext ctx) {
		for (EventContext ec : ctx.event()) {
			visit(ec);
		}
		return null;
	}

	/**
	 * Add an event to the unit
	 */
	@Override
	public Void visitEvent(EventContext ctx) {
		unit.addEvent(ctx.getText());
		return null;
	}

	@Override
	public Void visitStateStatement(StateStatementContext ctx) {
		visit(ctx.states());
		return null;
	}

	@Override
	public Void visitStates(StatesContext ctx) {
		for (StateContext sc : ctx.state()) {
			visit(sc);
		}
		return null;
	}

	@Override
	public Void visitState(StateContext ctx) {
		unit.addState(ctx.getText());
		return null;
	}

	@Override
	public Void visitUnits(UnitsContext ctx) {
		for (UnitContext unit : ctx.unit()) {
			visit(unit);
		}
		return null;
	}

//	@Override
//	public Value visitExpressionStatement(ExpressionStatementContext ctx) {
//		return visit(ctx.expression());
//	}

	@Override
	public Void visitWhenStatement(WhenStatementContext ctx) {
		unit.addWhen(ctx);
		return null;
	}

//	@Override
//	public Value visitAssignStatement(AssignStatementContext ctx) {
//		String id = ctx.ID().getText();
//		Value expr = visit(ctx.expression());
//		variables.put(id, expr);
//		return expr;
//	}

}
