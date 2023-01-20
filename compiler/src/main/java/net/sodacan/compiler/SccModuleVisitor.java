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

import net.sodacan.compiler.SccParser.AddContext;
import net.sodacan.compiler.SccParser.AliasContext;
import net.sodacan.compiler.SccParser.AliasNameContext;
import net.sodacan.compiler.SccParser.AndContext;
import net.sodacan.compiler.SccParser.AndStatementContext;
import net.sodacan.compiler.SccParser.AssContext;
import net.sodacan.compiler.SccParser.AssignmentTargetContext;
import net.sodacan.compiler.SccParser.AtDateContext;
import net.sodacan.compiler.SccParser.AtDateExpressionContext;
import net.sodacan.compiler.SccParser.AtDateRangeContext;
import net.sodacan.compiler.SccParser.AtDayContext;
import net.sodacan.compiler.SccParser.AtDowContext;
import net.sodacan.compiler.SccParser.AtFromDateContext;
import net.sodacan.compiler.SccParser.AtHolidayContext;
import net.sodacan.compiler.SccParser.AtMonthContext;
import net.sodacan.compiler.SccParser.AtOffsetExpressionContext;
import net.sodacan.compiler.SccParser.AtQantityContext;
import net.sodacan.compiler.SccParser.AtRelativeTimeExpressionContext;
import net.sodacan.compiler.SccParser.AtSeasonContext;
import net.sodacan.compiler.SccParser.AtSpecificDateContext;
import net.sodacan.compiler.SccParser.AtSpecificTimeExpressionContext;
import net.sodacan.compiler.SccParser.AtStatementContext;
import net.sodacan.compiler.SccParser.AtTimeContext;
import net.sodacan.compiler.SccParser.AtTimeExpressionContext;
import net.sodacan.compiler.SccParser.AtTimeShortcutContext;
import net.sodacan.compiler.SccParser.AtTimeUnitExpressionContext;
import net.sodacan.compiler.SccParser.AtToDateContext;
import net.sodacan.compiler.SccParser.AtYearContext;
import net.sodacan.compiler.SccParser.BoolContext;
import net.sodacan.compiler.SccParser.ConstraintContext;
import net.sodacan.compiler.SccParser.ConstraintExpressionContext;
import net.sodacan.compiler.SccParser.ConstraintIdentifierContext;
import net.sodacan.compiler.SccParser.ConstraintListContext;
import net.sodacan.compiler.SccParser.DivContext;
import net.sodacan.compiler.SccParser.DotContext;
import net.sodacan.compiler.SccParser.EqContext;
import net.sodacan.compiler.SccParser.EqualityContext;
import net.sodacan.compiler.SccParser.EventContext;
import net.sodacan.compiler.SccParser.ExpContext;
import net.sodacan.compiler.SccParser.FunContext;
import net.sodacan.compiler.SccParser.GeContext;
import net.sodacan.compiler.SccParser.GtContext;
import net.sodacan.compiler.SccParser.IdContext;
import net.sodacan.compiler.SccParser.IdRefContext;
import net.sodacan.compiler.SccParser.IdentifierContext;
import net.sodacan.compiler.SccParser.IdentifierFunContext;
import net.sodacan.compiler.SccParser.IfStatementContext;
import net.sodacan.compiler.SccParser.InitialValueContext;
import net.sodacan.compiler.SccParser.InstanceContext;
import net.sodacan.compiler.SccParser.LeContext;
import net.sodacan.compiler.SccParser.LitContext;
import net.sodacan.compiler.SccParser.LiteralContext;
import net.sodacan.compiler.SccParser.LtContext;
import net.sodacan.compiler.SccParser.ModuleContext;
import net.sodacan.compiler.SccParser.ModuleInstanceContext;
import net.sodacan.compiler.SccParser.ModuleNameContext;
import net.sodacan.compiler.SccParser.MulContext;
import net.sodacan.compiler.SccParser.NeContext;
import net.sodacan.compiler.SccParser.NotContext;
import net.sodacan.compiler.SccParser.NumberContext;
import net.sodacan.compiler.SccParser.NumberRangeContext;
import net.sodacan.compiler.SccParser.OnStatementContext;
import net.sodacan.compiler.SccParser.OrContext;
import net.sodacan.compiler.SccParser.ParenContext;
import net.sodacan.compiler.SccParser.PrivateStatementContext;
import net.sodacan.compiler.SccParser.PublishStatementContext;
import net.sodacan.compiler.SccParser.SccContext;
import net.sodacan.compiler.SccParser.SignContext;
import net.sodacan.compiler.SccParser.StatementContext;
import net.sodacan.compiler.SccParser.StatementListContext;
import net.sodacan.compiler.SccParser.StringContext;
import net.sodacan.compiler.SccParser.SubContext;
import net.sodacan.compiler.SccParser.SubscribeStatementContext;
import net.sodacan.compiler.SccParser.ThenStatementContext;
import net.sodacan.compiler.SccParser.TimerStatementContext;
import net.sodacan.compiler.SccParser.TopicStatementContext;
import net.sodacan.compiler.SccParser.UMinusContext;
import net.sodacan.compiler.SccParser.UPlusContext;
import net.sodacan.compiler.SccParser.VariableDefContext;
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
