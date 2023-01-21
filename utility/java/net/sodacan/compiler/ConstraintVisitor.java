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

import net.sodacan.compiler.SccParser.ConstraintContext;
import net.sodacan.compiler.SccParser.ConstraintIdentifierContext;
import net.sodacan.compiler.SccParser.ConstraintListContext;
import net.sodacan.compiler.SccParser.FullRangeContext;
import net.sodacan.compiler.SccParser.HighRangeContext;
import net.sodacan.compiler.SccParser.LowRangeContext;
import net.sodacan.compiler.SccParser.NumberContext;
import net.sodacan.compiler.SccParser.StringContext;
import net.sodacan.module.variable.Constraint;
import net.sodacan.module.variable.NumberConstraint;
import net.sodacan.module.variable.RangeConstraint;
import net.sodacan.module.variable.StringConstraint;

/**
 * Visit a subset of the part tree (VariableDef) to collect Constraints
 * @author John Churin
 *
 */
public class ConstraintVisitor extends SccParserBaseVisitor<Constraint> {
	protected SccParser parser;

	public ConstraintVisitor(SccParser parser) {
		super();
		this.parser = parser;
	}

	@Override
	public Constraint visitConstraintList(ConstraintListContext ctx) {
		// TODO Auto-generated method stub
		return super.visitConstraintList(ctx);
	}

	@Override
	public Constraint visitConstraint(ConstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitConstraint(ctx);
	}

	@Override
	public Constraint visitConstraintIdentifier(ConstraintIdentifierContext ctx) {
		String string = ctx.getText();
		return new StringConstraint(string);
	}

	@Override
	public Constraint visitFullRange(FullRangeContext ctx) {
		return new RangeConstraint(ctx.number(0).getText(),ctx.number(1).getText());
	}

	@Override
	public Constraint visitHighRange(HighRangeContext ctx) {
		return new RangeConstraint(null,ctx.number().getText());
	}

	@Override
	public Constraint visitLowRange(LowRangeContext ctx) {
		return new RangeConstraint(ctx.number().getText(),null);
	}

	@Override
	public Constraint visitNumber(NumberContext ctx) {
		return new NumberConstraint(ctx.getText());
	}

	@Override
	public Constraint visitString(StringContext ctx) {
		String quotedString = ctx.getText();
		quotedString = quotedString.substring(1, quotedString.length()-1);
		return new StringConstraint(quotedString);
	}

	
}
