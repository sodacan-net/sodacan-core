package net.sodacan.compiler;

import org.antlr.v4.runtime.ParserRuleContext;

import net.sodacan.compiler.SccParser.AndWithContext;
import net.sodacan.compiler.SccParser.AtStatementContext;
import net.sodacan.compiler.SccParser.DateExpressionContext;
import net.sodacan.compiler.SccParser.DateRangeContext;
import net.sodacan.compiler.SccParser.DayContext;
import net.sodacan.compiler.SccParser.DowContext;
import net.sodacan.compiler.SccParser.EofStatementContext;
import net.sodacan.compiler.SccParser.FromDateContext;
import net.sodacan.compiler.SccParser.HolidayContext;
import net.sodacan.compiler.SccParser.ModuleContext;
import net.sodacan.compiler.SccParser.ModuleIdentifierContext;
import net.sodacan.compiler.SccParser.OnIdentifierContext;
import net.sodacan.compiler.SccParser.OnStatementContext;
import net.sodacan.compiler.SccParser.OrWithContext;
import net.sodacan.compiler.SccParser.PrivateStatementContext;
import net.sodacan.compiler.SccParser.PublishStatementContext;
import net.sodacan.compiler.SccParser.QuantityContext;
import net.sodacan.compiler.SccParser.RelativeTimeExpressionContext;
import net.sodacan.compiler.SccParser.SeasonContext;
import net.sodacan.compiler.SccParser.SendIdentifierContext;
import net.sodacan.compiler.SccParser.SendStatementContext;
import net.sodacan.compiler.SccParser.SpecificDateContext;
import net.sodacan.compiler.SccParser.SpecificTimeExpressionContext;
import net.sodacan.compiler.SccParser.StatementContext;
import net.sodacan.compiler.SccParser.StatementListContext;
import net.sodacan.compiler.SccParser.SubscribeStatementContext;
import net.sodacan.compiler.SccParser.ThenIdentifierContext;
import net.sodacan.compiler.SccParser.ThenStatementContext;
import net.sodacan.compiler.SccParser.TimeContext;
import net.sodacan.compiler.SccParser.TimeShortcutContext;
import net.sodacan.compiler.SccParser.TimeUnitExpressionContext;
import net.sodacan.compiler.SccParser.ToDateContext;
import net.sodacan.compiler.SccParser.VarIdentifierContext;
import net.sodacan.compiler.SccParser.WithIdentifierContext;
import net.sodacan.compiler.SccParser.WithStatementContext;

public class SccListener extends SccParserBaseListener {

	SccParser parser;
	String filename;
	public SccListener( SccParser parser, String filename ) {
		this.parser = parser;
		this.filename = filename;
	}

	
	@Override
	public void enterModule(ModuleContext ctx) {
		System.out.print("MODULE");
		super.enterModule(ctx);
	}


	@Override
	public void exitModuleIdentifier(ModuleIdentifierContext ctx) {
		System.out.print(" ");
		final String moduleName = ctx.getText();
		final String extendedModuleName = moduleName + ".scc";
		System.out.print(moduleName);
		System.out.println();
		if (!extendedModuleName.contentEquals(filename)) {
			parser.notifyErrorListeners("The file name " + filename + " and module name " + moduleName + " must be the same");
		}
		super.exitModuleIdentifier(ctx);
	}

	@Override
	public void exitEofStatement(EofStatementContext ctx) {
		System.out.println("\nEnd Of Module");
		super.exitEofStatement(ctx);
	}


	@Override
	public void exitModule(ModuleContext ctx) {
		// Build the module here
		super.exitModule(ctx);
	}


	@Override
	public void exitStatement(StatementContext ctx) {
		System.out.println();
		super.exitStatement(ctx);
	}

	@Override
	public void exitStatementList(StatementListContext ctx) {
		// TODO Auto-generated method stub
		super.exitStatementList(ctx);
	}

	
	@Override
	public void enterPublishStatement(PublishStatementContext ctx) {
		System.out.print("PUBLISH");
		super.enterPublishStatement(ctx);
	}
	@Override
	public void enterSubscribeStatement(SubscribeStatementContext ctx) {
		System.out.print("SUBSCRIBE");
		super.enterSubscribeStatement(ctx);
	}


	@Override
	public void enterPrivateStatement(PrivateStatementContext ctx) {
		System.out.print("PRIVATE");
		super.enterPrivateStatement(ctx);
	}


	@Override
	public void exitVarIdentifier(VarIdentifierContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitVarIdentifier(ctx);
	}


	@Override
	public void enterAtStatement(AtStatementContext ctx) {
		System.out.print("AT");
		super.enterAtStatement(ctx);
	}

	@Override
	public void enterOnStatement(OnStatementContext ctx) {
		System.out.print("ON");
		super.enterOnStatement(ctx);
	}
	
	@Override
	public void enterWithStatement(WithStatementContext ctx) {
		System.out.print("\n  WITH");
		super.enterWithStatement(ctx);
	}
	
	@Override
	public void exitWithIdentifier(WithIdentifierContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitWithIdentifier(ctx);
	}
	
	@Override
	public void enterAndWith(AndWithContext ctx) {
		System.out.print(" AND");
		super.enterAndWith(ctx);
	}
	@Override
	public void enterOrWith(OrWithContext ctx) {
		System.out.print(" OR");
		super.enterOrWith(ctx);
	}

	@Override
	public void enterThenStatement(ThenStatementContext ctx) {
		System.out.print("\n  THEN");
		super.enterThenStatement(ctx);
	}
	
	@Override
	public void exitThenIdentifier(ThenIdentifierContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitThenIdentifier(ctx);
	}

	@Override
	public void enterSendStatement(SendStatementContext ctx) {
		System.out.print("\n  SEND");
		super.enterSendStatement(ctx);
	}

	@Override
	public void exitSendIdentifier(SendIdentifierContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitSendIdentifier(ctx);
	}
	
	@Override
	public void exitOnIdentifier(OnIdentifierContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitOnIdentifier(ctx);
	}

	@Override
	public void exitDateRange(DateRangeContext ctx) {
		// TODO Auto-generated method stub
		super.exitDateRange(ctx);
	}

	@Override
	public void enterFromDate(FromDateContext ctx) {
		System.out.print(" FROM");
		super.enterFromDate(ctx);
	}

	@Override
	public void enterToDate(ToDateContext ctx) {
		System.out.print(" THROUGH");
		super.enterToDate(ctx);
	}

	@Override
	public void exitQuantity(QuantityContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitQuantity(ctx);
	}
	@Override
	public void exitTime(TimeContext ctx) {
		int hr = Integer.parseInt(ctx.hr.getText());
		int min = Integer.parseInt(ctx.mi.getText());
		String ampm = ctx.ap.getText();
		System.out.format(" %d:%02d%s", hr, min, ampm);
		if (hr<1 || hr > 12 ) {
			parser.notifyErrorListeners("Invalid Time: hour must be between 1 and 12");
		}
		if (min > 59) {
			parser.notifyErrorListeners("Invalid Time: minutes must be between 0 and 59");
		}
		super.exitTime(ctx);
	}

	@Override
	public void exitTimeShortcut(TimeShortcutContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitTimeShortcut(ctx);
	}


	@Override
	public void exitRelativeTimeExpression(RelativeTimeExpressionContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitRelativeTimeExpression(ctx);
	}

	@Override
	public void exitTimeUnitExpression(TimeUnitExpressionContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitTimeUnitExpression(ctx);
	}

	@Override
	public void exitSpecificTimeExpression(SpecificTimeExpressionContext ctx) {
		// TODO Auto-generated method stub
		super.exitSpecificTimeExpression(ctx);
	}

	@Override
	public void exitSpecificDate(SpecificDateContext ctx) {
		String mS = ctx.month().getText();
		System.out.print(" ");
		System.out.print(mS);
		if (ctx.day()!=null) {
			int d = Integer.parseInt(ctx.day().getText());
			System.out.format(" %d",d);
//			System.out.print(dS);
		}
		if (ctx.year()!=null) {
			String yS = ctx.year().getText();
			System.out.print(", ");
			System.out.print(yS);
		}
		super.exitSpecificDate(ctx);
	}

//	@Override
//	public void exitYear(YearContext ctx) {
//		System.out.print(", ");
//		super.exitYear(ctx);
//		System.out.print(ctx.getText());
//	}

	@Override
	public void exitSeason(SeasonContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
	super.exitSeason(ctx);
	}

	@Override
	public void enterDateExpression(DateExpressionContext ctx) {
		System.out.print(" ON");
		super.exitDateExpression(ctx);
	}

	@Override
	public void exitDow(DowContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitDow(ctx);
	}

	@Override
	public void exitHoliday(HolidayContext ctx) {
		System.out.print(" ");
		System.out.print(ctx.getText());
		super.exitHoliday(ctx);
	}

	@Override
	public void exitDay(DayContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
		super.exitDay(ctx);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		super.exitEveryRule(ctx);
	}


}
