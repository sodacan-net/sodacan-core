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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sodacan.compiler.SccParser.AliasNameContext;
import net.sodacan.compiler.SccParser.ConstraintContext;
import net.sodacan.compiler.SccParser.ConstraintExpressionContext;
import net.sodacan.compiler.SccParser.ConstraintIdentifierContext;
import net.sodacan.compiler.SccParser.FullIdContext;
import net.sodacan.compiler.SccParser.ShortIdContext;
import net.sodacan.compiler.SccParser.TopicIdContext;
import net.sodacan.compiler.SccParser.VariableDefContext;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.VariableDef.VariableDefBuilder;
import net.sodacan.module.variable.VariableDefs;

/**
 * Look for variable declarations and add them to a variables list. Also, check for duplicate names.
 * we'll bind variable references to their definitions in a separate Visitor pass.
 * @author John Churin
 *
 */
public class VariableDefVisitor extends SccParserBaseVisitor<Void> {
	// The current builder created during our descent
	private VariableDefBuilder vdb = null;
	
	protected VariableDefs variables = new VariableDefs();
	
	protected SodacanModule module;
	protected SccParser parser;

	public VariableDefVisitor(SodacanModule module,SccParser parser) {
		super();
		this.module = module;
		this.parser = parser;
	}
	
	public VariableDefs getVariables() {
		return variables;
	}

	@Override
	public Void visitFullId(FullIdContext ctx) {
		vdb.nameSpace(ctx.ID(0).getText());
		vdb.topic(ctx.ID(1).getText());
		vdb.name(ctx.ID(2).getText());
		return null;
	}

	@Override
	public Void visitTopicId(TopicIdContext ctx) {
		vdb.topic(ctx.ID(0).getText());
		vdb.name(ctx.ID(1).getText());
		return null;
	}

	@Override
	public Void visitShortId(ShortIdContext ctx) {
		vdb.name(ctx.ID().getText());
		return null;
	}

	@Override
	public Void visitAliasName(AliasNameContext ctx) {
		vdb.alias(ctx.ID().getText());
		return null;
	}
	

	@Override
	public Void visitConstraintExpression(ConstraintExpressionContext ctx) {
		ConstraintVisitor cv = new ConstraintVisitor(parser);
		for (ConstraintContext c : ctx.constraintList().constraint()) {
			vdb.constraint(cv.visit(c));
		}
		return null;
	}

	@Override
	public Void visitVariableDef(VariableDefContext ctx) {
		// Get ready to construct a new variable
		vdb = VariableDef.newVariableDefBuilder();
		visit(ctx.identifier());
		if (ctx.instance()!=null) {
			visit(ctx.instance());
		}
		if (ctx.alias()!=null) {
			visit(ctx.alias());
		}
		if (ctx.constraintExpression()!=null) {
			visit(ctx.constraintExpression());
		}
		// We've collected all the parts, so build it
		VariableDef vd = vdb.build();
		// Add it to collection of variables
		if (!variables.addVariableDef(vd)) {
			parser.notifyErrorListeners(ctx.getStart(), "Variable already defined: " + vd, null);
		}
		List<String> conIds = vd.getConstraintIdentifiers();
		for (String id : conIds) {
			if (null!=variables.find(id)) {
				parser.notifyErrorListeners(ctx.getStart(), "Constraint " + id + " already defined elsewhere. Consider using quotes around constaint value.", null);
			}
		}
		if (!vd.validateConstraintScale()) {
			parser.notifyErrorListeners("Numeric constraints must have the same number of decimal places");
		}
		return null;
	}

	
}
