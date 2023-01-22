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

import net.sodacan.compiler.SccParser.AtMidnightContext;
import net.sodacan.compiler.SccParser.AtNoonContext;
import net.sodacan.compiler.SccParser.AtSunriseContext;
import net.sodacan.compiler.SccParser.AtSunsetContext;
import net.sodacan.compiler.SccParser.AtTimeShortcutContext;
import net.sodacan.module.expression.datetime.TimeExpression.TimeExpressionBuilder;
import net.sodacan.module.statement.SodacanModule;
/**
 * This visitor populates a TimeExpression builder which will be built in the larger StatementVisitor
 * @author John Churin
 *
 */
public class TimeExpressionVisitor extends SccParserBaseVisitor<Void> {

	protected SodacanModule module;
	protected SccParser parser;
	protected TimeExpressionBuilder teb;
	
	public TimeExpressionVisitor(SodacanModule module,SccParser parser, TimeExpressionBuilder teb) {
		super();
		this.module = module;
		this.parser = parser;
		this.teb = teb;
	}

	@Override
	public Void visitAtSunrise(AtSunriseContext ctx) {
		teb.sunrise();
		return null;
	}

	@Override
	public Void visitAtSunset(AtSunsetContext ctx) {
		teb.sunset();
		return null;
	}

	@Override
	public Void visitAtMidnight(AtMidnightContext ctx) {
		teb.midnight();
		return null;
	}

	@Override
	public Void visitAtNoon(AtNoonContext ctx) {
		teb.midnight();
		return null;
	}

}
