package net.sodacan.grammer;

import java.util.HashMap;
import java.util.Map;

import net.sodacan.grammer.LanguageParser.AddSubExprContext;
import net.sodacan.grammer.LanguageParser.AndOrWhenContext;
import net.sodacan.grammer.LanguageParser.AssignExprContext;
import net.sodacan.grammer.LanguageParser.EqualsExprContext;
import net.sodacan.grammer.LanguageParser.ExpressionContext;
import net.sodacan.grammer.LanguageParser.ExpressionsContext;
import net.sodacan.grammer.LanguageParser.FalseKeywordContext;
import net.sodacan.grammer.LanguageParser.IntegerLiteralContext;
import net.sodacan.grammer.LanguageParser.MulDivExprContext;
import net.sodacan.grammer.LanguageParser.ParenExprContext;
import net.sodacan.grammer.LanguageParser.StringLiteralContext;
import net.sodacan.grammer.LanguageParser.TrueKeywordContext;
import net.sodacan.grammer.LanguageParser.VariableExprContext;
import net.sodacan.grammer.LanguageParser.WhenStatementContext;

public class EvaluateVisitor extends LanguageBaseVisitor<Value> {
	private Map<String,Value> variables = new HashMap<>();

	@Override
	public Value visitWhenStatement(WhenStatementContext ctx) {
		Value when = visit(ctx.whenExpression());
		Value then;
		if (when.getBoolean()) {
			then = visit(ctx.expressions());
			return then;
		}
		return new Value();
	}
	
	@Override
	public Value visitExpressions(ExpressionsContext ctx) {
		Value value = new Value();
		for (ExpressionContext ec : ctx.expression()) {
			value = visit(ec);
		}
		return value;
	}

	@Override
	public Value visitAndOrWhen(AndOrWhenContext ctx) {
		Value left = visit(ctx.whenExpression(0));
		Value right = visit(ctx.whenExpression(1));
		return right;
	}

	@Override
	public Value visitParenExpr(ParenExprContext ctx) {
		return visit(ctx.expression());
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
	public Value visitVariableExpr(VariableExprContext ctx) {
		String id = ctx.getText();
		Value r = variables.get(id);
		if (r==null) throw new RuntimeException("Undefined variable " + id);
		return r;
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


}
