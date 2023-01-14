package net.sodacan.compiler;

import net.sodacan.compiler.SccParser.AtStatementContext;
import net.sodacan.compiler.SccParser.DateContext;
import net.sodacan.compiler.SccParser.DateExpressionContext;
import net.sodacan.compiler.SccParser.DayContext;
import net.sodacan.compiler.SccParser.DayExpressionContext;
import net.sodacan.compiler.SccParser.DowContext;
import net.sodacan.compiler.SccParser.DurationExpressionContext;
import net.sodacan.compiler.SccParser.FromDateContext;
import net.sodacan.compiler.SccParser.HolidayContext;
import net.sodacan.compiler.SccParser.MonthContext;
import net.sodacan.compiler.SccParser.OnIdentifierContext;
import net.sodacan.compiler.SccParser.RelativeTimeExpressionContext;
import net.sodacan.compiler.SccParser.SeasonContext;
import net.sodacan.compiler.SccParser.SendStatementContext;
import net.sodacan.compiler.SccParser.SpecificDateContext;
import net.sodacan.compiler.SccParser.SpecificTimeExpressionContext;
import net.sodacan.compiler.SccParser.StatementContext;
import net.sodacan.compiler.SccParser.StatementListContext;
import net.sodacan.compiler.SccParser.ThenStatementContext;
import net.sodacan.compiler.SccParser.TimeContext;
import net.sodacan.compiler.SccParser.TimeUnitExpressionContext;
import net.sodacan.compiler.SccParser.ToDateContext;
import net.sodacan.compiler.SccParser.WithIdentifierContext;
import net.sodacan.compiler.SccParser.WithStatementContext;
import net.sodacan.compiler.SccParser.YearContext;

public class SccVisitor extends SccParserBaseVisitor<Void> {

	@Override
	public Void visitStatementList(StatementListContext ctx) {
		for (StatementContext sc : ctx.statement()) {
			visit(sc);
		}
		return null;
	}

//	@Override
//	public Void visitEventStatement(EventStatementContext ctx) {
//		System.out.print("Event ");
//		System.out.println();
//		return null;
//	}
//
//	
//	@Override
//	public Void visitEventBooleanExpression(EventBooleanExpressionContext ctx) {
//		System.out.print(ctx.getText());
//		return null;
//	}

	@Override
	public Void visitAtStatement(AtStatementContext ctx) {
		System.out.print("AT");
		visit(ctx.dayExpression());
		if (ctx.dateExpression()!=null) {
			visit(ctx.dateExpression());
		}
		if (ctx.dateRange()!=null) {
			visit(ctx.dateRange());
		}
		System.out.println();
		return null;
	}
	
	@Override
	public Void visitWithStatement(WithStatementContext ctx) {
		System.out.print("  WITH");
		visit(ctx.withExpression());
		System.out.println();
		return null;
	}

	@Override
	public Void visitThenStatement(ThenStatementContext ctx) {
		System.out.print("  THEN");
		visit(ctx.thenExpression());
		System.out.println();
		return null;
	}

	@Override
	public Void visitSendStatement(SendStatementContext ctx) {
		System.out.print("  SEND");
		visit(ctx.sendExpression());
		System.out.println();
		return null;
	}

	@Override
	public Void visitWithIdentifier(WithIdentifierContext ctx) {
		System.out.print(" " + ctx.getText());
		return null;
	}

	@Override
	public Void visitOnIdentifier(OnIdentifierContext ctx) {
		System.out.print(" " + ctx.getText());
		return null;
	}

	@Override
	public Void visitDayExpression(DayExpressionContext ctx) {
		if (ctx.durationExpression()!=null) {
			visit(ctx.durationExpression());
		}
		System.out.print(" " );
		visit(ctx.specificTimeExpression());
		return null;
	}

	@Override
	public Void visitRelativeTimeExpression(RelativeTimeExpressionContext ctx) {
		System.out.print(" " + ctx.getText());
		return super.visitRelativeTimeExpression(ctx);
	}

	@Override
	public Void visitDurationExpression(DurationExpressionContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.quantity().getText());
		System.out.print(" ");
		visit(ctx.timeUnitExpression());
		visit(ctx.relativeTimeExpression());
		return null;
	}

	@Override
	public Void visitTimeUnitExpression(TimeUnitExpressionContext ctx) {
		System.out.print(ctx.getText());
		return null;
	}


	@Override
	public Void visitTime(TimeContext ctx) {
//		System.out.print(ctx.getText());
		return null;
	}


	@Override
	public Void visitSpecificTimeExpression(SpecificTimeExpressionContext ctx) {
		System.out.print(ctx.getText());
		return super.visitSpecificTimeExpression(ctx);
	}

	@Override
	public Void visitDateExpression(DateExpressionContext ctx) {
		if (ctx.date().size()!=0) {
			System.out.print(" ON");
		}
		for (DateContext dc : ctx.date()) {
			visit(dc);
		}
		return null;
	}

	@Override
	public Void visitFromDate(FromDateContext ctx) {
		System.out.print(" FROM");
		return super.visitFromDate(ctx);
	}

	@Override
	public Void visitToDate(ToDateContext ctx) {
		System.out.print(" THROUGH");
		return super.visitToDate(ctx);
	}

	@Override
	public Void visitYear(YearContext ctx) {
		System.out.print(", " + ctx.AtINT().getText());
		return null;
	}

	@Override
	public Void visitMonth(MonthContext ctx) {
		System.out.print(" " + ctx.getText());
		return null;
	}

	@Override
	public Void visitDay(DayContext ctx) {
		System.out.print(" " + ctx.getText());
		return null;
	}

	@Override
	public Void visitDate(DateContext ctx) {
		return super.visitDate(ctx);
	}

	@Override
	public Void visitSpecificDate(SpecificDateContext ctx) {
		visit(ctx.month());
		if (ctx.day()!=null) {
			visit(ctx.day());
		}
		if (ctx.year()!=null) {
			visit(ctx.year());
		}
		return null;
	}


	@Override
	public Void visitDow(DowContext ctx) {
		System.out.print(" " + ctx.getText());
		return null;
	}


	@Override
	public Void visitHoliday(HolidayContext ctx) {
		System.out.print(" " + ctx.getText());
		return null;
	}


	@Override
	public Void visitSeason(SeasonContext ctx) {
		System.out.print(" " + ctx.getText());
		return null;
	}
	
}
