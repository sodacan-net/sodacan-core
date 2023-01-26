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
package net.sodacan.compiler.visitor;

import net.sodacan.compiler.SccParser;
import net.sodacan.compiler.SccParser.AtDayContext;
import net.sodacan.compiler.SccParser.AtDowContext;
import net.sodacan.compiler.SccParser.AtEndingContext;
import net.sodacan.compiler.SccParser.AtFromContext;
import net.sodacan.compiler.SccParser.AtFullDateContext;
import net.sodacan.compiler.SccParser.AtHolidayContext;
import net.sodacan.compiler.SccParser.AtInMonthContext;
import net.sodacan.compiler.SccParser.AtInSeasonContext;
import net.sodacan.compiler.SccParser.AtInYearContext;
import net.sodacan.compiler.SccParser.AtOnAnnualDateContext;
import net.sodacan.compiler.SccParser.AtOnDateContext;
import net.sodacan.compiler.SccParser.AtStartingContext;
import net.sodacan.compiler.SccParserBaseVisitor;
import net.sodacan.module.expression.datetime.DateExpression.DateExpressionBuilder;
import net.sodacan.module.statement.SodacanModule;

public class DateExpressionVisitor extends SccParserBaseVisitor<Void> {

	protected SodacanModule module;
	protected SccParser parser;
	protected DateExpressionBuilder deb;
	
	public DateExpressionVisitor(SodacanModule module,SccParser parser, DateExpressionBuilder deb) {
		super();
		this.module = module;
		this.parser = parser;
		this.deb = deb;
	}

	@Override
	public Void visitAtOnDate(AtOnDateContext ctx) {
		for (AtFullDateContext fdc : ctx.atFullDate()) {
			deb.monthDayYear(
					fdc.atMonth().getText(), 
					fdc.atDay().getText(), 
					fdc.atYear().getText());
		}
		return null;
	}


	@Override
	public Void visitAtHoliday(AtHolidayContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAtHoliday(ctx);
	}

	@Override
	public Void visitAtDow(AtDowContext ctx) {
		deb.day(ctx.getText());
		return super.visitAtDow(ctx);
	}

	@Override
	public Void visitAtInYear(AtInYearContext ctx) {
		deb.year(Integer.valueOf(ctx.getText()));
		return null;
	}

	@Override
	public Void visitAtInMonth(AtInMonthContext ctx) {
		deb.month(ctx.getText());
		return null;
	}


	@Override
	public Void visitAtInSeason(AtInSeasonContext ctx) {
		deb.season(ctx.getText());
		return null;
	}

	/**
	 * month and possibly a day for an annual on date
	 */
	@Override
	public Void visitAtOnAnnualDate(AtOnAnnualDateContext ctx) {
		String monthString = ctx.atMonth().getText();
		AtDayContext dayNode = ctx.atDay();
		if (dayNode!=null) {
			String dayString = dayNode.getText();
			deb.monthDay(monthString, dayString);
		} else {
			deb.month(monthString);
		}
		return null;
	}

	@Override
	public Void visitAtStarting(AtStartingContext ctx) {
		deb.starting(ctx.atFullDate().atMonth().getText(), ctx.atFullDate().atDay().getText(),ctx.atFullDate().atYear().getText());
		return null;
	}

	@Override
	public Void visitAtEnding(AtEndingContext ctx) {
		deb.ending(ctx.atFullDate().atMonth().getText(), ctx.atFullDate().atDay().getText(),ctx.atFullDate().atYear().getText());
		return null;
	}

	@Override
	public Void visitAtFrom(AtFromContext ctx) {
		String fromMonth = ctx.atOnAnnualDate(0).atMonth().getText();
		String fromDay;
		if (ctx.atOnAnnualDate(0).atDay()!=null) {
			fromDay = ctx.atOnAnnualDate(0).atDay().getText();
		} else {
			fromDay = null;		// Beginning of month
		}
		String throughMonth = ctx.atOnAnnualDate(1).atMonth().getText();
		String throughDay;
		if (ctx.atOnAnnualDate(1).atDay()!=null) {
			throughDay = ctx.atOnAnnualDate(1).atDay().getText();
		} else {
			throughDay = null;	// End of month
		}
		deb.annualRange(fromMonth,fromDay,throughMonth, throughDay );
		return null;
	}

}
