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

import net.sodacan.compiler.SccParser.ModuleContext;
import net.sodacan.compiler.SccParser.StatementContext;
import net.sodacan.compiler.SccParser.StatementListContext;
import net.sodacan.compiler.SccParser.ThenStatementContext;
import net.sodacan.module.statement.ModuleComponent;
import net.sodacan.module.statement.SodacanModule;
/**
 * Visit the parse tree and create an AST (Module in our case)
 * @author John Churin
 *
 */
public class SccModuleVisitor extends SccParserBaseVisitor<ModuleComponent> {

	protected SodacanModule module;

	public SccModuleVisitor(SodacanModule module) {
		this.module = module;
	}
	
	public SodacanModule getModule() {
		return module;
	}

	@Override
	public ModuleComponent visitModule(ModuleContext ctx) {
		if (ctx.moduleName().moduleInstance()!=null) {
			module.setInstanceName(ctx.moduleName().moduleInstance().name.getText());
		}
		System.out.println("Building: " + module);
		return super.visitModule(ctx);
	}

	@Override
	public ModuleComponent visitStatementList(StatementListContext ctx) {
		for (StatementContext sc : ctx.statement()) {
			visit(sc);
		}
		return null;
	}

//	@Override
//	public ModuleComponent visitAtStatement(AtStatementContext ctx) {
//		System.out.print("AT");
//		visit(ctx.dayExpression());
//		if (ctx.dateExpression()!=null) {
//			visit(ctx.dateExpression());
//		}
//		if (ctx.dateRange()!=null) {
//			visit(ctx.dateRange());
//		}
//		System.out.println();
//		return null;
//	}
//	
//	@Override
//	public ModuleComponent visitWithStatement(WithStatementContext ctx) {
//		System.out.print("  WITH");
//		visit(ctx.withExpression());
//		System.out.println();
//		return null;
//	}
//
	@Override
	public ModuleComponent visitThenStatement(ThenStatementContext ctx) {
		visit(ctx.thenExpression());
		return null;
	}

//	@Override
//	public ModuleComponent visitSendStatement(SendStatementContext ctx) {
//		System.out.print("  SEND");
//		visit(ctx.sendExpression());
//		System.out.println();
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitWithIdentifier(WithIdentifierContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitOnIdentifier(OnIdentifierContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitDayExpression(DayExpressionContext ctx) {
//		if (ctx.durationExpression()!=null) {
//			visit(ctx.durationExpression());
//		}
//		System.out.print(" " );
//		visit(ctx.specificTimeExpression());
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitRelativeTimeExpression(RelativeTimeExpressionContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return super.visitRelativeTimeExpression(ctx);
//	}
//
//	@Override
//	public ModuleComponent visitDurationExpression(DurationExpressionContext ctx) {
//		System.out.print(" ");
//		System.out.print(ctx.quantity().getText());
//		System.out.print(" ");
//		visit(ctx.timeUnitExpression());
//		visit(ctx.relativeTimeExpression());
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitTimeUnitExpression(TimeUnitExpressionContext ctx) {
//		System.out.print(ctx.getText());
//		return null;
//	}
//
//
//	@Override
//	public ModuleComponent visitTime(TimeContext ctx) {
////		System.out.print(ctx.getText());
//		return null;
//	}
//
//
//	@Override
//	public ModuleComponent visitSpecificTimeExpression(SpecificTimeExpressionContext ctx) {
//		System.out.print(ctx.getText());
//		return super.visitSpecificTimeExpression(ctx);
//	}
//
//	@Override
//	public ModuleComponent visitDateExpression(DateExpressionContext ctx) {
//		if (ctx.date().size()!=0) {
//			System.out.print(" ON");
//		}
//		for (DateContext dc : ctx.date()) {
//			visit(dc);
//		}
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitFromDate(FromDateContext ctx) {
//		System.out.print(" FROM");
//		return super.visitFromDate(ctx);
//	}
//
//	@Override
//	public ModuleComponent visitToDate(ToDateContext ctx) {
//		System.out.print(" THROUGH");
//		return super.visitToDate(ctx);
//	}
//
//	@Override
//	public ModuleComponent visitYear(YearContext ctx) {
//		System.out.print(", " + ctx.AtINT().getText());
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitMonth(MonthContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitDay(DayContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return null;
//	}
//
//	@Override
//	public ModuleComponent visitDate(DateContext ctx) {
//		return super.visitDate(ctx);
//	}
//
//	@Override
//	public ModuleComponent visitSpecificDate(SpecificDateContext ctx) {
//		visit(ctx.month());
//		if (ctx.day()!=null) {
//			visit(ctx.day());
//		}
//		if (ctx.year()!=null) {
//			visit(ctx.year());
//		}
//		return null;
//	}
//
//
//	@Override
//	public ModuleComponent visitDow(DowContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return null;
//	}
//
//
//	@Override
//	public ModuleComponent visitHoliday(HolidayContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return null;
//	}
//
//
//	@Override
//	public ModuleComponent visitSeason(SeasonContext ctx) {
//		System.out.print(" " + ctx.getText());
//		return null;
//	}
//	
}
