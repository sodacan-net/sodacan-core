/*
 * Copyright 2023 John M Churin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sodacan.compiler;

import org.antlr.v4.runtime.ParserRuleContext;

import net.sodacan.compiler.SccParser.AtStatementContext;
import net.sodacan.compiler.SccParser.ModuleContext;
import net.sodacan.compiler.SccParser.ModuleNameContext;
import net.sodacan.compiler.SccParser.OnStatementContext;
import net.sodacan.compiler.SccParser.PrivateStatementContext;
import net.sodacan.compiler.SccParser.PublishStatementContext;
import net.sodacan.compiler.SccParser.StatementContext;
import net.sodacan.compiler.SccParser.StatementListContext;
import net.sodacan.compiler.SccParser.SubscribeStatementContext;
import net.sodacan.compiler.SccParser.ThenStatementContext;
import net.sodacan.module.statement.SodacanModule;
/**
 * This listener does some semantic checks. In a separate pass, we'll create the AST.
 * However, the nascent SodacanModule is partially populated and passed into this listener to 
 * support the semantic checks.
 * @author 
 *
 */
public class SccListener extends SccParserBaseListener {

	protected SodacanModule module;
	protected SccParser parser;
	public SccListener( SccParser parser, SodacanModule module ) {
		this.parser = parser;
		this.module = module;
	}

	
	@Override
	public void enterModule(ModuleContext ctx) {
		System.out.print("MODULE");
		super.enterModule(ctx);
	}


	@Override
	public void exitModuleName(ModuleNameContext ctx) {
		System.out.print(" ");
		final String moduleName = ctx.name.getText();
		// Load up the module name
		module.setName(moduleName);
		// Do an additional check that the file name is the same as the module name
		if (module.getOriginalFileName()!=null) {
			int sc = module.getOriginalFileName().indexOf('.');
			String fileName;
			if (sc>0) {
				fileName = module.getOriginalFileName().substring(0, sc);
			} else {
				fileName = module.getOriginalFileName();

			}
			if (!module.getName().equalsIgnoreCase(fileName)) {
				parser.notifyErrorListeners("The file name " + fileName + " and module name " + module.getName() + " must be the same");
			}
		}

		System.out.print(moduleName);
		System.out.println();
		super.exitModuleName(ctx);
	}

//	@Override
//	public void exitModuleStatement(ModuleStatementContext ctx) {
//		System.out.println("\nEnd Of Module");
//		super.exitModuleStatement(ctx);
//	}


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


//	@Override
//	public void exitVarIdentifier(VarIdentifierContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitVarIdentifier(ctx);
//	}


	@Override
	public void enterAtStatement(AtStatementContext ctx) {
		System.out.print("    AT");
		super.enterAtStatement(ctx);
	}

	@Override
	public void enterOnStatement(OnStatementContext ctx) {
		System.out.print("    ON");
		super.enterOnStatement(ctx);
	}
	
//	@Override
//	public void enterWithStatement(WithStatementContext ctx) {
//		System.out.print("\n  WITH");
//		super.enterWithStatement(ctx);
//	}
//	
//	@Override
//	public void exitWithIdentifier(WithIdentifierContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitWithIdentifier(ctx);
//	}
//	
//	@Override
//	public void enterAndWith(AndWithContext ctx) {
//		System.out.print(" AND");
//		super.enterAndWith(ctx);
//	}
//	@Override
//	public void enterOrWith(OrWithContext ctx) {
//		System.out.print(" OR");
//		super.enterOrWith(ctx);
//	}
//
	@Override
	public void enterThenStatement(ThenStatementContext ctx) {
		System.out.print("\n      THEN");
		super.enterThenStatement(ctx);
	}
	
//	@Override
//	public void exitThenIdentifier(ThenIdentifierContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitThenIdentifier(ctx);
//	}
//
//	@Override
//	public void enterSendStatement(SendStatementContext ctx) {
//		System.out.print("\n  SEND");
//		super.enterSendStatement(ctx);
//	}
//
//	@Override
//	public void exitSendIdentifier(SendIdentifierContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitSendIdentifier(ctx);
//	}
//	
//	@Override
//	public void exitOnIdentifier(OnIdentifierContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitOnIdentifier(ctx);
//	}
//
//	@Override
//	public void exitDateRange(DateRangeContext ctx) {
//		// TODO Auto-generated method stub
//		super.exitDateRange(ctx);
//	}
//
//	@Override
//	public void enterFromDate(FromDateContext ctx) {
//		System.out.print(" FROM");
//		super.enterFromDate(ctx);
//	}
//
//	@Override
//	public void enterToDate(ToDateContext ctx) {
//		System.out.print(" THROUGH");
//		super.enterToDate(ctx);
//	}
//
//	@Override
//	public void exitQuantity(QuantityContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitQuantity(ctx);
//	}
//	@Override
//	public void exitTime(TimeContext ctx) {
//		int hr = Integer.parseInt(ctx.hr.getText());
//		int min = Integer.parseInt(ctx.mi.getText());
//		String ampm = ctx.ap.getText();
//		System.out.format(" %d:%02d%s", hr, min, ampm);
//		if (hr<1 || hr > 12 ) {
//			parser.notifyErrorListeners("Invalid Time: hour must be between 1 and 12");
//		}
//		if (min > 59) {
//			parser.notifyErrorListeners("Invalid Time: minutes must be between 0 and 59");
//		}
//		super.exitTime(ctx);
//	}
//
//	@Override
//	public void exitTimeShortcut(TimeShortcutContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitTimeShortcut(ctx);
//	}
//
//
//	@Override
//	public void exitRelativeTimeExpression(RelativeTimeExpressionContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitRelativeTimeExpression(ctx);
//	}
//
//	@Override
//	public void exitTimeUnitExpression(TimeUnitExpressionContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitTimeUnitExpression(ctx);
//	}
//
//	@Override
//	public void exitSpecificTimeExpression(SpecificTimeExpressionContext ctx) {
//		// TODO Auto-generated method stub
//		super.exitSpecificTimeExpression(ctx);
//	}
//
//	@Override
//	public void exitSpecificDate(SpecificDateContext ctx) {
//		String mS = ctx.month().getText();
//		System.out.print(" ");
//		System.out.print(mS);
//		if (ctx.day()!=null) {
//			int d = Integer.parseInt(ctx.day().getText());
//			System.out.format(" %d",d);
////			System.out.print(dS);
//		}
//		if (ctx.year()!=null) {
//			String yS = ctx.year().getText();
//			System.out.print(", ");
//			System.out.print(yS);
//		}
//		super.exitSpecificDate(ctx);
//	}

//	@Override
//	public void exitYear(YearContext ctx) {
//		System.out.print(", ");
//		super.exitYear(ctx);
//		System.out.print(ctx.getText());
//	}

//	@Override
//	public void exitSeason(SeasonContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//	super.exitSeason(ctx);
//	}
//
//	@Override
//	public void enterDateExpression(DateExpressionContext ctx) {
//		System.out.print(" ON");
//		super.exitDateExpression(ctx);
//	}
//
//	@Override
//	public void exitDow(DowContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitDow(ctx);
//	}
//
//	@Override
//	public void exitHoliday(HolidayContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.getText());
//		super.exitHoliday(ctx);
//	}
//
//	@Override
//	public void exitDay(DayContext ctx) {
////		System.out.print(" ");
////		System.out.print(ctx.getText());
//		super.exitDay(ctx);
//	}
//
	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		super.exitEveryRule(ctx);
	}


}
