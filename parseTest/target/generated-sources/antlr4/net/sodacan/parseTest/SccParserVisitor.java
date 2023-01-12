// Generated from java-escape by ANTLR 4.11.1
package net.sodacan.parseTest;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SccParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SccParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code EofStatement}
	 * labeled alternative in {@link SccParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEofStatement(SccParser.EofStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#module}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule(SccParser.ModuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#moduleIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleIdentifier(SccParser.ModuleIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementList}
	 * labeled alternative in {@link SccParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementList(SccParser.StatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SccParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#topicStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTopicStatement(SccParser.TopicStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#publicStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPublicStatement(SccParser.PublicStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#privateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrivateStatement(SccParser.PrivateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#varIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarIdentifier(SccParser.VarIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(SccParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#varEnum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarEnum(SccParser.VarEnumContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#varEnumList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarEnumList(SccParser.VarEnumListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#varInt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarInt(SccParser.VarIntContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#varBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarBool(SccParser.VarBoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#varEVENT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarEVENT(SccParser.VarEVENTContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#subscribeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscribeStatement(SccParser.SubscribeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#atStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtStatement(SccParser.AtStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#onStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnStatement(SccParser.OnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#onExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnExpression(SccParser.OnExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#onIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnIdentifier(SccParser.OnIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#withStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithStatement(SccParser.WithStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WithId}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithId(SccParser.WithIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotWith(SccParser.NotWithContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrWith(SccParser.OrWithContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndWith(SccParser.AndWithContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenWith}
	 * labeled alternative in {@link SccParser#withExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenWith(SccParser.ParenWithContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#withIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithIdentifier(SccParser.WithIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#thenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThenStatement(SccParser.ThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thenId}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThenId(SccParser.ThenIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndThen(SccParser.AndThenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotThen(SccParser.NotThenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenThen(SccParser.ParenThenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrThen}
	 * labeled alternative in {@link SccParser#thenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrThen(SccParser.OrThenContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#thenIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThenIdentifier(SccParser.ThenIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#sendStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendStatement(SccParser.SendStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#sendExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendExpression(SccParser.SendExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#sendIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendIdentifier(SccParser.SendIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#dateRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateRange(SccParser.DateRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#fromDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromDate(SccParser.FromDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#toDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToDate(SccParser.ToDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#dayExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDayExpression(SccParser.DayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#durationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDurationExpression(SccParser.DurationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#quantity}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantity(SccParser.QuantityContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#timeUnitExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeUnitExpression(SccParser.TimeUnitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#relativeTimeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelativeTimeExpression(SccParser.RelativeTimeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#specificTimeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificTimeExpression(SccParser.SpecificTimeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#timeShortcut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeShortcut(SccParser.TimeShortcutContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#time}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime(SccParser.TimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#dateExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateExpression(SccParser.DateExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#specificDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificDate(SccParser.SpecificDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(SccParser.DateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#year}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYear(SccParser.YearContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#month}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonth(SccParser.MonthContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#dow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDow(SccParser.DowContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#holiday}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHoliday(SccParser.HolidayContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#day}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDay(SccParser.DayContext ctx);
	/**
	 * Visit a parse tree produced by {@link SccParser#season}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeason(SccParser.SeasonContext ctx);
}