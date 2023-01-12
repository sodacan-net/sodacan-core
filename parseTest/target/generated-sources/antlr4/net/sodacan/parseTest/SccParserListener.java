// Generated from java-escape by ANTLR 4.11.1
package net.sodacan.parseTest;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SccParser}.
 */
public interface SccParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code EofStatement}
	 * labeled alternative in {@link SccParser#start}.
	 * @param ctx the parse tree
	 */
	void enterEofStatement(SccParser.EofStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EofStatement}
	 * labeled alternative in {@link SccParser#start}.
	 * @param ctx the parse tree
	 */
	void exitEofStatement(SccParser.EofStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#module}.
	 * @param ctx the parse tree
	 */
	void enterModule(SccParser.ModuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#module}.
	 * @param ctx the parse tree
	 */
	void exitModule(SccParser.ModuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#moduleIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterModuleIdentifier(SccParser.ModuleIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#moduleIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitModuleIdentifier(SccParser.ModuleIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementList}
	 * labeled alternative in {@link SccParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(SccParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementList}
	 * labeled alternative in {@link SccParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(SccParser.StatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SccParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SccParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#topicStatement}.
	 * @param ctx the parse tree
	 */
	void enterTopicStatement(SccParser.TopicStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#topicStatement}.
	 * @param ctx the parse tree
	 */
	void exitTopicStatement(SccParser.TopicStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#publicStatement}.
	 * @param ctx the parse tree
	 */
	void enterPublicStatement(SccParser.PublicStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#publicStatement}.
	 * @param ctx the parse tree
	 */
	void exitPublicStatement(SccParser.PublicStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#privateStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrivateStatement(SccParser.PrivateStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#privateStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrivateStatement(SccParser.PrivateStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#varIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterVarIdentifier(SccParser.VarIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#varIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitVarIdentifier(SccParser.VarIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#varType}.
	 * @param ctx the parse tree
	 */
	void enterVarType(SccParser.VarTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#varType}.
	 * @param ctx the parse tree
	 */
	void exitVarType(SccParser.VarTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#varEnum}.
	 * @param ctx the parse tree
	 */
	void enterVarEnum(SccParser.VarEnumContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#varEnum}.
	 * @param ctx the parse tree
	 */
	void exitVarEnum(SccParser.VarEnumContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#varEnumList}.
	 * @param ctx the parse tree
	 */
	void enterVarEnumList(SccParser.VarEnumListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#varEnumList}.
	 * @param ctx the parse tree
	 */
	void exitVarEnumList(SccParser.VarEnumListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#varInt}.
	 * @param ctx the parse tree
	 */
	void enterVarInt(SccParser.VarIntContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#varInt}.
	 * @param ctx the parse tree
	 */
	void exitVarInt(SccParser.VarIntContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#varBool}.
	 * @param ctx the parse tree
	 */
	void enterVarBool(SccParser.VarBoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#varBool}.
	 * @param ctx the parse tree
	 */
	void exitVarBool(SccParser.VarBoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#varEVENT}.
	 * @param ctx the parse tree
	 */
	void enterVarEVENT(SccParser.VarEVENTContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#varEVENT}.
	 * @param ctx the parse tree
	 */
	void exitVarEVENT(SccParser.VarEVENTContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#subscribeStatement}.
	 * @param ctx the parse tree
	 */
	void enterSubscribeStatement(SccParser.SubscribeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#subscribeStatement}.
	 * @param ctx the parse tree
	 */
	void exitSubscribeStatement(SccParser.SubscribeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#atStatement}.
	 * @param ctx the parse tree
	 */
	void enterAtStatement(SccParser.AtStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#atStatement}.
	 * @param ctx the parse tree
	 */
	void exitAtStatement(SccParser.AtStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#onStatement}.
	 * @param ctx the parse tree
	 */
	void enterOnStatement(SccParser.OnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#onStatement}.
	 * @param ctx the parse tree
	 */
	void exitOnStatement(SccParser.OnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#onExpression}.
	 * @param ctx the parse tree
	 */
	void enterOnExpression(SccParser.OnExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#onExpression}.
	 * @param ctx the parse tree
	 */
	void exitOnExpression(SccParser.OnExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#onIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterOnIdentifier(SccParser.OnIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#onIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitOnIdentifier(SccParser.OnIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#withStatement}.
	 * @param ctx the parse tree
	 */
	void enterWithStatement(SccParser.WithStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#withStatement}.
	 * @param ctx the parse tree
	 */
	void exitWithStatement(SccParser.WithStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WithId}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void enterWithId(SccParser.WithIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WithId}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void exitWithId(SccParser.WithIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void enterNotWith(SccParser.NotWithContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void exitNotWith(SccParser.NotWithContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrWith(SccParser.OrWithContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrWith(SccParser.OrWithContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndWith(SccParser.AndWithContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndWith(SccParser.AndWithContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenWith(SccParser.ParenWithContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenWith(SccParser.ParenWithContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#withIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterWithIdentifier(SccParser.WithIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#withIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitWithIdentifier(SccParser.WithIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#thenStatement}.
	 * @param ctx the parse tree
	 */
	void enterThenStatement(SccParser.ThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#thenStatement}.
	 * @param ctx the parse tree
	 */
	void exitThenStatement(SccParser.ThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thenId}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void enterThenId(SccParser.ThenIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thenId}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void exitThenId(SccParser.ThenIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndThen(SccParser.AndThenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndThen(SccParser.AndThenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void enterNotThen(SccParser.NotThenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void exitNotThen(SccParser.NotThenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenThen(SccParser.ParenThenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenThen(SccParser.ParenThenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrThen(SccParser.OrThenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrThen(SccParser.OrThenContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#thenIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterThenIdentifier(SccParser.ThenIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#thenIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitThenIdentifier(SccParser.ThenIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#sendStatement}.
	 * @param ctx the parse tree
	 */
	void enterSendStatement(SccParser.SendStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#sendStatement}.
	 * @param ctx the parse tree
	 */
	void exitSendStatement(SccParser.SendStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#sendExpression}.
	 * @param ctx the parse tree
	 */
	void enterSendExpression(SccParser.SendExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#sendExpression}.
	 * @param ctx the parse tree
	 */
	void exitSendExpression(SccParser.SendExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#sendIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSendIdentifier(SccParser.SendIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#sendIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSendIdentifier(SccParser.SendIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#dateRange}.
	 * @param ctx the parse tree
	 */
	void enterDateRange(SccParser.DateRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#dateRange}.
	 * @param ctx the parse tree
	 */
	void exitDateRange(SccParser.DateRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#fromDate}.
	 * @param ctx the parse tree
	 */
	void enterFromDate(SccParser.FromDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#fromDate}.
	 * @param ctx the parse tree
	 */
	void exitFromDate(SccParser.FromDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#toDate}.
	 * @param ctx the parse tree
	 */
	void enterToDate(SccParser.ToDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#toDate}.
	 * @param ctx the parse tree
	 */
	void exitToDate(SccParser.ToDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#dayExpression}.
	 * @param ctx the parse tree
	 */
	void enterDayExpression(SccParser.DayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#dayExpression}.
	 * @param ctx the parse tree
	 */
	void exitDayExpression(SccParser.DayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#durationExpression}.
	 * @param ctx the parse tree
	 */
	void enterDurationExpression(SccParser.DurationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#durationExpression}.
	 * @param ctx the parse tree
	 */
	void exitDurationExpression(SccParser.DurationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#quantity}.
	 * @param ctx the parse tree
	 */
	void enterQuantity(SccParser.QuantityContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#quantity}.
	 * @param ctx the parse tree
	 */
	void exitQuantity(SccParser.QuantityContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#timeUnitExpression}.
	 * @param ctx the parse tree
	 */
	void enterTimeUnitExpression(SccParser.TimeUnitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#timeUnitExpression}.
	 * @param ctx the parse tree
	 */
	void exitTimeUnitExpression(SccParser.TimeUnitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#relativeTimeExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelativeTimeExpression(SccParser.RelativeTimeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#relativeTimeExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelativeTimeExpression(SccParser.RelativeTimeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#specificTimeExpression}.
	 * @param ctx the parse tree
	 */
	void enterSpecificTimeExpression(SccParser.SpecificTimeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#specificTimeExpression}.
	 * @param ctx the parse tree
	 */
	void exitSpecificTimeExpression(SccParser.SpecificTimeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#timeShortcut}.
	 * @param ctx the parse tree
	 */
	void enterTimeShortcut(SccParser.TimeShortcutContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#timeShortcut}.
	 * @param ctx the parse tree
	 */
	void exitTimeShortcut(SccParser.TimeShortcutContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#time}.
	 * @param ctx the parse tree
	 */
	void enterTime(SccParser.TimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#time}.
	 * @param ctx the parse tree
	 */
	void exitTime(SccParser.TimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#dateExpression}.
	 * @param ctx the parse tree
	 */
	void enterDateExpression(SccParser.DateExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#dateExpression}.
	 * @param ctx the parse tree
	 */
	void exitDateExpression(SccParser.DateExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#specificDate}.
	 * @param ctx the parse tree
	 */
	void enterSpecificDate(SccParser.SpecificDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#specificDate}.
	 * @param ctx the parse tree
	 */
	void exitSpecificDate(SccParser.SpecificDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(SccParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(SccParser.DateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#year}.
	 * @param ctx the parse tree
	 */
	void enterYear(SccParser.YearContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#year}.
	 * @param ctx the parse tree
	 */
	void exitYear(SccParser.YearContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#month}.
	 * @param ctx the parse tree
	 */
	void enterMonth(SccParser.MonthContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#month}.
	 * @param ctx the parse tree
	 */
	void exitMonth(SccParser.MonthContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#dow}.
	 * @param ctx the parse tree
	 */
	void enterDow(SccParser.DowContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#dow}.
	 * @param ctx the parse tree
	 */
	void exitDow(SccParser.DowContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#holiday}.
	 * @param ctx the parse tree
	 */
	void enterHoliday(SccParser.HolidayContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#holiday}.
	 * @param ctx the parse tree
	 */
	void exitHoliday(SccParser.HolidayContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#day}.
	 * @param ctx the parse tree
	 */
	void enterDay(SccParser.DayContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#day}.
	 * @param ctx the parse tree
	 */
	void exitDay(SccParser.DayContext ctx);
	/**
	 * Enter a parse tree produced by {@link SccParser#season}.
	 * @param ctx the parse tree
	 */
	void enterSeason(SccParser.SeasonContext ctx);
	/**
	 * Exit a parse tree produced by {@link SccParser#season}.
	 * @param ctx the parse tree
	 */
	void exitSeason(SccParser.SeasonContext ctx);
}