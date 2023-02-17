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

import java.util.List;

import net.sodacan.compiler.SccParser;
import net.sodacan.compiler.SccParserBaseVisitor;
import net.sodacan.compiler.SccParser.AliasNameContext;
import net.sodacan.compiler.SccParser.AtStatementContext;
import net.sodacan.compiler.SccParser.ConstraintContext;
import net.sodacan.compiler.SccParser.ConstraintExpressionContext;
import net.sodacan.compiler.SccParser.FullIdContext;
import net.sodacan.compiler.SccParser.InitialValueContext;
import net.sodacan.compiler.SccParser.OnStatementContext;
import net.sodacan.compiler.SccParser.PrivateStatementContext;
import net.sodacan.compiler.SccParser.PublishStatementContext;
import net.sodacan.compiler.SccParser.SubscribeStatementContext;
import net.sodacan.compiler.SccParser.SubscribeVariableDefContext;
import net.sodacan.compiler.SccParser.TopicStatementContext;
import net.sodacan.compiler.SccParser.VariableDefContext;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.VariableDef.VariableDefBuilder;
import net.sodacan.module.variable.VariableDef.VariableType;

/**
 * Look for variable declarations and add them to a variables list. Also, check for duplicate names.
 * we'll bind variable references to their definitions in a separate Visitor pass.
 * @author John Churin
 *
 */
public class VariableDefVisitor extends SccParserBaseVisitor<Void> {
	// The current builder created during our descent
	private VariableDefBuilder vdb = null;
	
	
	protected SodacanModule module;
	protected SccParser parser;
	// We communicate the variable type from statement down to variableDef
	private String variableType = null;
	
	public VariableDefVisitor(SodacanModule module,SccParser parser) {
		super();
		this.module = module;
		this.parser = parser;
	}
	
	@Override
	public Void visitPublishStatement(PublishStatementContext ctx) {
		variableType = "publishVariable";
		return super.visitPublishStatement(ctx);
	}

	@Override
	public Void visitSubscribeStatement(SubscribeStatementContext ctx) {
		variableType = "subscribeVariable";
		return super.visitSubscribeStatement(ctx);
	}

	@Override
	public Void visitPrivateStatement(PrivateStatementContext ctx) {
		variableType = "privateVariable";
		return super.visitPrivateStatement(ctx);
	}

	
	@Override
	public Void visitTopicStatement(TopicStatementContext ctx) {
		variableType = "topicVariable";
		return super.visitTopicStatement(ctx);
	}

	@Override
	public Void visitAtStatement(AtStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAtStatement(ctx);
	}

	@Override
	public Void visitOnStatement(OnStatementContext ctx) {
		return super.visitOnStatement(ctx);
	}

	@Override
	public Void visitFullId(FullIdContext ctx) {
		vdb.name(ctx.getText());
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
	public Void visitInitialValue(InitialValueContext ctx) {
		String literal = ctx.literal().getText();
		Value value = new Value(literal);
		vdb.initialValue(value);
		return null;
	}

	@Override
	public Void visitSubscribeVariableDef(SubscribeVariableDefContext ctx) {
		vdb = VariableDef.newVariableDefBuilder();
		vdb.type(variableType);
		vdb.moduleName(ctx.m.getText());
		if (ctx.instance()!=null) {
			vdb.instance(ctx.instance().getText());
		}
		vdb.name(ctx.v.getText());
		vdb.alias(ctx.alias().aliasName().getText());
		
		VariableDef vd = vdb.build();
		// Add it to collection of variables
		if (!module.addVariableDef(vd)) {
			parser.notifyErrorListeners(ctx.getStart(), "Variable already defined: " + vd, null);
		}
		return null;
	}

	@Override
	public Void visitVariableDef(VariableDefContext ctx) {
		// Get ready to construct a new variable
		vdb = VariableDef.newVariableDefBuilder();
		vdb.type(variableType);
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
		if (ctx.initialValue()!=null) {
			visit(ctx.initialValue());
		}
		// We've collected all the parts, so build it
		VariableDef vd = vdb.build();
		// Add it to collection of variables
		if (!module.addVariableDef(vd)) {
			parser.notifyErrorListeners(ctx.getStart(), "Variable already defined: " + vd, null);
		}
		List<String> conIds = vd.getConstraintIdentifiers();
		for (String id : conIds) {
			if (null!=module.findVariableDef(id)) {
				parser.notifyErrorListeners(ctx.getStart(), "Constraint " + id + " already defined elsewhere. Consider using quotes around constaint value.", null);
			}
		}
		if (!vd.validateConstraintScale()) {
			parser.notifyErrorListeners("Numeric constraints must have the same number of decimal places");
		}
		return null;
	}

	
}
