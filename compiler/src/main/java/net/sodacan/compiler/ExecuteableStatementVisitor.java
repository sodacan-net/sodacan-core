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

import net.sodacan.compiler.SccParser.AndStatementContext;
import net.sodacan.compiler.SccParser.EventConditionContext;
import net.sodacan.compiler.SccParser.ModuleContext;
import net.sodacan.compiler.SccParser.ModuleInstanceContext;
import net.sodacan.compiler.SccParser.ModuleNameContext;
import net.sodacan.compiler.SccParser.OnStatementContext;
import net.sodacan.compiler.SccParser.SccContext;
import net.sodacan.compiler.SccParser.StatementListContext;
import net.sodacan.compiler.SccParser.ThenStatementContext;
import net.sodacan.module.expression.Expression;
import net.sodacan.module.statement.ModuleComponent;
import net.sodacan.module.statement.OnStatement;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.terminal.VariableRefExpression;
/**
 * After collecting the variable definitions, we need to visit the module tree to construct the executable portion of the module.
 * Compile errors generated in this phase include references to non-existing variables.
 * @author John Churin
 *
 */
public class ExecuteableStatementVisitor extends SccParserBaseVisitor<ModuleComponent> {
	protected SodacanModule module;
	protected SccParser parser;

	public ExecuteableStatementVisitor(SodacanModule module,SccParser parser) {
		super();
		this.module = module;
		this.parser = parser;

	}
	
	/**
	 * Top-level module file
	 */
	@Override
	public ModuleComponent visitScc(SccContext ctx) {
		return super.visitScc(ctx);
	}
	/**
	 * Module definition
	 */
	@Override
	public ModuleComponent visitModule(ModuleContext ctx) {
		// TODO Auto-generated method stub
		return super.visitModule(ctx);
	}

	@Override
	public ModuleComponent visitModuleName(ModuleNameContext ctx) {
		// TODO Auto-generated method stub
		return super.visitModuleName(ctx);
	}

	@Override
	public ModuleComponent visitModuleInstance(ModuleInstanceContext ctx) {
		// TODO Auto-generated method stub
		return super.visitModuleInstance(ctx);
	}

	@Override
	public ModuleComponent visitStatementList(StatementListContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStatementList(ctx);
	}
	
	/**
	 * Return one ON statement. It has the following components: event selector, 
	 * zero or more AND statements, and zero or more THEN statements.
	 * The EventSelector is just another Expression but Module Parser rules limits the
	 * depth of the expression. And, unlike the other components, it is really important
	 * to be present. Otherwise, it wouldn't be an ON statement.
	 */
	@Override
	public ModuleComponent visitOnStatement(OnStatementContext ctx) {
		// Build an On statement
		OnStatement os = new OnStatement();
		// Add the EventSelector
		os.setSelectExpression((Expression)visit(ctx.eventCondition()));
		// Add Additional filtering (AND), if any
		for (AndStatementContext tsc : ctx.andStatement()) {
			os.addAndExpression((Expression)visit(tsc.expr()));
		}
		// Add the Then expressions (syntax requires at least one, 
		// semantics doesn't care)
		for (ThenStatementContext tsc : ctx.thenStatement()) {
			os.addThenExpression((Expression)visit(tsc.thenExpr()));
		}
		// Return the on statement
		return os;
	}
	/**
	 * An event creates a small expression used to determine if a message
	 * applies to the ON statement.
	 */
	@Override
	public ModuleComponent visitEventCondition(EventConditionContext ctx) {
		Expression var = new VariableRefExpression(ctx.getText());
		return var;
	}

	
}
