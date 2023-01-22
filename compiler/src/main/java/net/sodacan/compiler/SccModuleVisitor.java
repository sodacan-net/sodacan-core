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
import net.sodacan.module.statement.ModuleComponent;
import net.sodacan.module.statement.SodacanModule;
/**
 * Visit the ANTL4 parse tree and create an AST (Module in our case)
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
		System.out.println("Visiting module: " + module);
		return super.visitModule(ctx);
	}

	
}
