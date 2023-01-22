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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import net.sodacan.compiler.SccParser.AliasNameContext;
import net.sodacan.compiler.SccParser.ConstraintContext;
import net.sodacan.compiler.SccParser.ConstraintListContext;
import net.sodacan.compiler.SccParser.ModuleContext;
import net.sodacan.compiler.SccParser.ModuleNameContext;
import net.sodacan.compiler.SccParser.SubscribeStatementContext;
import net.sodacan.compiler.SccParser.VariableDefContext;
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
	
	protected List<String> variables = new ArrayList<>();
	
	public SccListener( SccParser parser, SodacanModule module ) {
		this.parser = parser;
		this.module = module;
	}

	
	@Override
	public void enterModule(ModuleContext ctx) {
		super.enterModule(ctx);
	}

	@Override
	public void exitModuleName(ModuleNameContext ctx) {
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
				parser.notifyErrorListeners("The file name '" + fileName + "' and module name '" + module.getName() + "' must be the same");
			}
		}

		System.out.print("MODULE ");
		System.out.print(moduleName);
		System.out.println();
		super.exitModuleName(ctx);
	}

	
	@Override
	public void exitSubscribeStatement(SubscribeStatementContext ctx) {
		String id = ctx.variableDef().identifier().getText();
		if (id.contains(".") && ctx.variableDef().alias()==null) {
			parser.notifyErrorListeners("The compound identifer '" + id + "' requires an AS clause");
		}
		super.exitSubscribeStatement(ctx);
	}


//	@Override
//	public void exitAliasName(AliasNameContext ctx) {
//		String name = ctx.getText();
//		if (variables.contains(name)) {
//			parser.notifyErrorListeners("Alias already defined: " + name);
//		}
//		variables.add(name);
//		super.exitAliasName(ctx);
//	}


	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		super.exitEveryRule(ctx);
	}


}
