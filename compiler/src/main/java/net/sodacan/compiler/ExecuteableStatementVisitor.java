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
import net.sodacan.compiler.SccParser.ModuleInstanceContext;
import net.sodacan.compiler.SccParser.ModuleNameContext;
import net.sodacan.compiler.SccParser.SccContext;
import net.sodacan.compiler.SccParser.StatementListContext;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.value.Value;
/**
 * After collecting the variable definitions, we need to visit the module tree to construct the executable portion of the module.
 * Compile errors generated in this phase include references to non-existing variables.
 * @author John Churin
 *
 */
public class ExecuteableStatementVisitor extends SccParserBaseVisitor<Value> {
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
	public Value visitScc(SccContext ctx) {
		return super.visitScc(ctx);
	}
	/**
	 * Module definition
	 */
	@Override
	public Value visitModule(ModuleContext ctx) {
		// TODO Auto-generated method stub
		return super.visitModule(ctx);
	}

	@Override
	public Value visitModuleName(ModuleNameContext ctx) {
		// TODO Auto-generated method stub
		return super.visitModuleName(ctx);
	}

	@Override
	public Value visitModuleInstance(ModuleInstanceContext ctx) {
		// TODO Auto-generated method stub
		return super.visitModuleInstance(ctx);
	}

	@Override
	public Value visitStatementList(StatementListContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStatementList(ctx);
	}

	
}
