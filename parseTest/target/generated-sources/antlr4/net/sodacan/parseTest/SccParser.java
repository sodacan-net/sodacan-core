// Generated from java-escape by ANTLR 4.11.1
package net.sodacan.parseTest;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SccParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		MODULE=1, PUBLIC=2, PRIVATE=3, PUBLISH=4, SUBSCRIBE=5, AT=6, ON=7, WITH=8, 
		THEN=9, SEND=10, INT=11, ID=12, COMMENT=13, WS=14, VarCOMMA=15, VarLBRACE=16, 
		VarRBRACE=17, VarTRUE=18, VarAS=19, VarEVENT=20, VarINT=21, VarID=22, 
		VarCOMMENT=23, VarEOL=24, VarWS=25, ModID=26, ModCOMMENT=27, ModEOL=28, 
		ModWS=29, PubID=30, PubCOMMENT=31, PubEOL=32, PubWS=33, SubDOT=34, SubID=35, 
		SubCOMMENT=36, SubEOL=37, SubWS=38, MONTH=39, AMPM=40, DOW=41, SUNRISE=42, 
		SUNSET=43, SEASON=44, FROM=45, THROUGH=46, HOUR=47, MINUTE=48, BEFORE=49, 
		AFTER=50, CHRISTMAS=51, MIDNIGHT=52, NOON=53, ATON=54, COMMA=55, SEMICOLON=56, 
		COLON=57, AtINT=58, AtCOMMENT=59, AtEOL=60, AtWS=61, OnDOT=62, OnID=63, 
		OnCOMMENT=64, OnEOL=65, OnWS=66, WithLPAREN=67, WithRPAREN=68, WithAND=69, 
		WithOR=70, WithNOT=71, WithDOT=72, WithID=73, WithCOMMENT=74, WithEOL=75, 
		WithWS=76, ThenLPAREN=77, ThenRPAREN=78, ThenAND=79, ThenOR=80, ThenNOT=81, 
		ThenDOT=82, ThenID=83, ThenCOMMENT=84, ThenEOL=85, ThenWS=86, SendDOT=87, 
		SendID=88, SendCOMMENT=89, SendEOL=90, SendWS=91, VarFALSE=92, SubLBRACE=93, 
		SubRBRACE=94, SubCOMMA=95, SubTRUE=96, SubFALSE=97;
	public static final int
		RULE_start = 0, RULE_module = 1, RULE_moduleIdentifier = 2, RULE_statements = 3, 
		RULE_statement = 4, RULE_publicStatement = 5, RULE_privateStatement = 6, 
		RULE_varIdentifier = 7, RULE_varType = 8, RULE_varEnum = 9, RULE_varEnumList = 10, 
		RULE_varInt = 11, RULE_varBool = 12, RULE_varEVENT = 13, RULE_subscribeStatement = 14, 
		RULE_subType = 15, RULE_subEnum = 16, RULE_subEnumList = 17, RULE_subInt = 18, 
		RULE_subBool = 19, RULE_subIdentifier = 20, RULE_atStatement = 21, RULE_onStatement = 22, 
		RULE_onExpression = 23, RULE_onIdentifier = 24, RULE_withStatement = 25, 
		RULE_withExpression = 26, RULE_withIdentifier = 27, RULE_thenStatement = 28, 
		RULE_thenExpression = 29, RULE_thenIdentifier = 30, RULE_sendStatement = 31, 
		RULE_sendExpression = 32, RULE_sendIdentifier = 33, RULE_dateRange = 34, 
		RULE_fromDate = 35, RULE_toDate = 36, RULE_dayExpression = 37, RULE_durationExpression = 38, 
		RULE_quantity = 39, RULE_timeUnitExpression = 40, RULE_relativeTimeExpression = 41, 
		RULE_specificTimeExpression = 42, RULE_timeShortcut = 43, RULE_time = 44, 
		RULE_dateExpression = 45, RULE_specificDate = 46, RULE_date = 47, RULE_year = 48, 
		RULE_month = 49, RULE_dow = 50, RULE_holiday = 51, RULE_day = 52, RULE_season = 53;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "module", "moduleIdentifier", "statements", "statement", "publicStatement", 
			"privateStatement", "varIdentifier", "varType", "varEnum", "varEnumList", 
			"varInt", "varBool", "varEVENT", "subscribeStatement", "subType", "subEnum", 
			"subEnumList", "subInt", "subBool", "subIdentifier", "atStatement", "onStatement", 
			"onExpression", "onIdentifier", "withStatement", "withExpression", "withIdentifier", 
			"thenStatement", "thenExpression", "thenIdentifier", "sendStatement", 
			"sendExpression", "sendIdentifier", "dateRange", "fromDate", "toDate", 
			"dayExpression", "durationExpression", "quantity", "timeUnitExpression", 
			"relativeTimeExpression", "specificTimeExpression", "timeShortcut", "time", 
			"dateExpression", "specificDate", "date", "year", "month", "dow", "holiday", 
			"day", "season"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'MODULE'", "'PUBLIC'", "'PRIVATE'", "'PUBLISH'", "'SUBSCRIBE'", 
			"'AT'", "'ON'", "'WITH'", "'THEN'", "'SEND'", null, null, null, null, 
			null, "'{'", "'}'", null, "'AS'", "'EVENT'", null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "'sunrise'", "'sunset'", null, "'FROM'", 
			"'THROUGH'", null, null, "'before'", "'after'", null, "'midnight'", "'noon'", 
			null, null, "';'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "MODULE", "PUBLIC", "PRIVATE", "PUBLISH", "SUBSCRIBE", "AT", "ON", 
			"WITH", "THEN", "SEND", "INT", "ID", "COMMENT", "WS", "VarCOMMA", "VarLBRACE", 
			"VarRBRACE", "VarTRUE", "VarAS", "VarEVENT", "VarINT", "VarID", "VarCOMMENT", 
			"VarEOL", "VarWS", "ModID", "ModCOMMENT", "ModEOL", "ModWS", "PubID", 
			"PubCOMMENT", "PubEOL", "PubWS", "SubDOT", "SubID", "SubCOMMENT", "SubEOL", 
			"SubWS", "MONTH", "AMPM", "DOW", "SUNRISE", "SUNSET", "SEASON", "FROM", 
			"THROUGH", "HOUR", "MINUTE", "BEFORE", "AFTER", "CHRISTMAS", "MIDNIGHT", 
			"NOON", "ATON", "COMMA", "SEMICOLON", "COLON", "AtINT", "AtCOMMENT", 
			"AtEOL", "AtWS", "OnDOT", "OnID", "OnCOMMENT", "OnEOL", "OnWS", "WithLPAREN", 
			"WithRPAREN", "WithAND", "WithOR", "WithNOT", "WithDOT", "WithID", "WithCOMMENT", 
			"WithEOL", "WithWS", "ThenLPAREN", "ThenRPAREN", "ThenAND", "ThenOR", 
			"ThenNOT", "ThenDOT", "ThenID", "ThenCOMMENT", "ThenEOL", "ThenWS", "SendDOT", 
			"SendID", "SendCOMMENT", "SendEOL", "SendWS", "VarFALSE", "SubLBRACE", 
			"SubRBRACE", "SubCOMMA", "SubTRUE", "SubFALSE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SccParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
	 
		public StartContext() { }
		public void copyFrom(StartContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EofStatementContext extends StartContext {
		public ModuleContext module() {
			return getRuleContext(ModuleContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(SccParser.EOF, 0); }
		public EofStatementContext(StartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterEofStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitEofStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitEofStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			_localctx = new EofStatementContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			module();
			setState(109);
			statements();
			setState(110);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModuleContext extends ParserRuleContext {
		public TerminalNode MODULE() { return getToken(SccParser.MODULE, 0); }
		public ModuleIdentifierContext moduleIdentifier() {
			return getRuleContext(ModuleIdentifierContext.class,0);
		}
		public TerminalNode ModEOL() { return getToken(SccParser.ModEOL, 0); }
		public ModuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_module; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterModule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitModule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitModule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleContext module() throws RecognitionException {
		ModuleContext _localctx = new ModuleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_module);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(MODULE);
			setState(113);
			moduleIdentifier();
			setState(114);
			match(ModEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModuleIdentifierContext extends ParserRuleContext {
		public TerminalNode ModID() { return getToken(SccParser.ModID, 0); }
		public ModuleIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_moduleIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterModuleIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitModuleIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitModuleIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleIdentifierContext moduleIdentifier() throws RecognitionException {
		ModuleIdentifierContext _localctx = new ModuleIdentifierContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_moduleIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(ModID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementsContext extends ParserRuleContext {
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
	 
		public StatementsContext() { }
		public void copyFrom(StatementsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StatementListContext extends StatementsContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementListContext(StatementsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitStatementList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitStatementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statements);
		int _la;
		try {
			_localctx = new StatementListContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 228L) != 0) {
				{
				{
				setState(118);
				statement();
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public SubscribeStatementContext subscribeStatement() {
			return getRuleContext(SubscribeStatementContext.class,0);
		}
		public PublicStatementContext publicStatement() {
			return getRuleContext(PublicStatementContext.class,0);
		}
		public PrivateStatementContext privateStatement() {
			return getRuleContext(PrivateStatementContext.class,0);
		}
		public AtStatementContext atStatement() {
			return getRuleContext(AtStatementContext.class,0);
		}
		public WithStatementContext withStatement() {
			return getRuleContext(WithStatementContext.class,0);
		}
		public ThenStatementContext thenStatement() {
			return getRuleContext(ThenStatementContext.class,0);
		}
		public SendStatementContext sendStatement() {
			return getRuleContext(SendStatementContext.class,0);
		}
		public OnStatementContext onStatement() {
			return getRuleContext(OnStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statement);
		int _la;
		try {
			setState(147);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				subscribeStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				publicStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				privateStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(127);
				atStatement();
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH) {
					{
					setState(128);
					withStatement();
					}
				}

				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==THEN) {
					{
					setState(131);
					thenStatement();
					}
				}

				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEND) {
					{
					setState(134);
					sendStatement();
					}
				}

				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(137);
				onStatement();
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH) {
					{
					setState(138);
					withStatement();
					}
				}

				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==THEN) {
					{
					setState(141);
					thenStatement();
					}
				}

				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEND) {
					{
					setState(144);
					sendStatement();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PublicStatementContext extends ParserRuleContext {
		public TerminalNode PUBLIC() { return getToken(SccParser.PUBLIC, 0); }
		public List<VarIdentifierContext> varIdentifier() {
			return getRuleContexts(VarIdentifierContext.class);
		}
		public VarIdentifierContext varIdentifier(int i) {
			return getRuleContext(VarIdentifierContext.class,i);
		}
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public TerminalNode VarEOL() { return getToken(SccParser.VarEOL, 0); }
		public TerminalNode VarAS() { return getToken(SccParser.VarAS, 0); }
		public PublicStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_publicStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterPublicStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitPublicStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitPublicStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PublicStatementContext publicStatement() throws RecognitionException {
		PublicStatementContext _localctx = new PublicStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_publicStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(PUBLIC);
			setState(150);
			varIdentifier();
			setState(151);
			varType();
			{
			setState(152);
			match(VarAS);
			setState(153);
			varIdentifier();
			}
			setState(155);
			match(VarEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrivateStatementContext extends ParserRuleContext {
		public TerminalNode PUBLIC() { return getToken(SccParser.PUBLIC, 0); }
		public VarIdentifierContext varIdentifier() {
			return getRuleContext(VarIdentifierContext.class,0);
		}
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public TerminalNode VarEOL() { return getToken(SccParser.VarEOL, 0); }
		public PrivateStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_privateStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterPrivateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitPrivateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitPrivateStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrivateStatementContext privateStatement() throws RecognitionException {
		PrivateStatementContext _localctx = new PrivateStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_privateStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			match(PUBLIC);
			setState(158);
			varIdentifier();
			setState(159);
			varType();
			setState(160);
			match(VarEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarIdentifierContext extends ParserRuleContext {
		public TerminalNode VarID() { return getToken(SccParser.VarID, 0); }
		public VarIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterVarIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitVarIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitVarIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarIdentifierContext varIdentifier() throws RecognitionException {
		VarIdentifierContext _localctx = new VarIdentifierContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_varIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(VarID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarTypeContext extends ParserRuleContext {
		public VarEnumContext varEnum() {
			return getRuleContext(VarEnumContext.class,0);
		}
		public VarIntContext varInt() {
			return getRuleContext(VarIntContext.class,0);
		}
		public VarBoolContext varBool() {
			return getRuleContext(VarBoolContext.class,0);
		}
		public VarEVENTContext varEVENT() {
			return getRuleContext(VarEVENTContext.class,0);
		}
		public VarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterVarType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitVarType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitVarType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarTypeContext varType() throws RecognitionException {
		VarTypeContext _localctx = new VarTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_varType);
		try {
			setState(168);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VarLBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(164);
				varEnum();
				}
				break;
			case VarINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(165);
				varInt();
				}
				break;
			case VarTRUE:
			case VarFALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(166);
				varBool();
				}
				break;
			case VarEVENT:
				enterOuterAlt(_localctx, 4);
				{
				setState(167);
				varEVENT();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarEnumContext extends ParserRuleContext {
		public TerminalNode VarLBRACE() { return getToken(SccParser.VarLBRACE, 0); }
		public TerminalNode VarRBRACE() { return getToken(SccParser.VarRBRACE, 0); }
		public List<VarEnumListContext> varEnumList() {
			return getRuleContexts(VarEnumListContext.class);
		}
		public VarEnumListContext varEnumList(int i) {
			return getRuleContext(VarEnumListContext.class,i);
		}
		public VarEnumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varEnum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterVarEnum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitVarEnum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitVarEnum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarEnumContext varEnum() throws RecognitionException {
		VarEnumContext _localctx = new VarEnumContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_varEnum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(VarLBRACE);
			setState(172); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(171);
				varEnumList();
				}
				}
				setState(174); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==VarID );
			setState(176);
			match(VarRBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarEnumListContext extends ParserRuleContext {
		public List<TerminalNode> VarID() { return getTokens(SccParser.VarID); }
		public TerminalNode VarID(int i) {
			return getToken(SccParser.VarID, i);
		}
		public List<TerminalNode> VarCOMMA() { return getTokens(SccParser.VarCOMMA); }
		public TerminalNode VarCOMMA(int i) {
			return getToken(SccParser.VarCOMMA, i);
		}
		public VarEnumListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varEnumList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterVarEnumList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitVarEnumList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitVarEnumList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarEnumListContext varEnumList() throws RecognitionException {
		VarEnumListContext _localctx = new VarEnumListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_varEnumList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(VarID);
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VarCOMMA) {
				{
				{
				setState(179);
				match(VarCOMMA);
				setState(180);
				match(VarID);
				}
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarIntContext extends ParserRuleContext {
		public TerminalNode VarINT() { return getToken(SccParser.VarINT, 0); }
		public VarIntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varInt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterVarInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitVarInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitVarInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarIntContext varInt() throws RecognitionException {
		VarIntContext _localctx = new VarIntContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_varInt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(VarINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarBoolContext extends ParserRuleContext {
		public TerminalNode VarTRUE() { return getToken(SccParser.VarTRUE, 0); }
		public TerminalNode VarFALSE() { return getToken(SccParser.VarFALSE, 0); }
		public VarBoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varBool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterVarBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitVarBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitVarBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarBoolContext varBool() throws RecognitionException {
		VarBoolContext _localctx = new VarBoolContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_varBool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			_la = _input.LA(1);
			if ( !(_la==VarTRUE || _la==VarFALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarEVENTContext extends ParserRuleContext {
		public TerminalNode VarEVENT() { return getToken(SccParser.VarEVENT, 0); }
		public VarEVENTContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varEVENT; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterVarEVENT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitVarEVENT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitVarEVENT(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarEVENTContext varEVENT() throws RecognitionException {
		VarEVENTContext _localctx = new VarEVENTContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_varEVENT);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(VarEVENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubscribeStatementContext extends ParserRuleContext {
		public TerminalNode SUBSCRIBE() { return getToken(SccParser.SUBSCRIBE, 0); }
		public SubIdentifierContext subIdentifier() {
			return getRuleContext(SubIdentifierContext.class,0);
		}
		public SubTypeContext subType() {
			return getRuleContext(SubTypeContext.class,0);
		}
		public TerminalNode SubEOL() { return getToken(SccParser.SubEOL, 0); }
		public SubscribeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subscribeStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSubscribeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSubscribeStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSubscribeStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubscribeStatementContext subscribeStatement() throws RecognitionException {
		SubscribeStatementContext _localctx = new SubscribeStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_subscribeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(SUBSCRIBE);
			setState(193);
			subIdentifier();
			setState(194);
			subType();
			setState(195);
			match(SubEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubTypeContext extends ParserRuleContext {
		public SubEnumContext subEnum() {
			return getRuleContext(SubEnumContext.class,0);
		}
		public SubIntContext subInt() {
			return getRuleContext(SubIntContext.class,0);
		}
		public SubBoolContext subBool() {
			return getRuleContext(SubBoolContext.class,0);
		}
		public SubTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSubType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSubType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSubType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubTypeContext subType() throws RecognitionException {
		SubTypeContext _localctx = new SubTypeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_subType);
		try {
			setState(200);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SubLBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(197);
				subEnum();
				}
				break;
			case VarINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(198);
				subInt();
				}
				break;
			case SubTRUE:
			case SubFALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(199);
				subBool();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubEnumContext extends ParserRuleContext {
		public TerminalNode SubLBRACE() { return getToken(SccParser.SubLBRACE, 0); }
		public TerminalNode SubRBRACE() { return getToken(SccParser.SubRBRACE, 0); }
		public List<SubEnumListContext> subEnumList() {
			return getRuleContexts(SubEnumListContext.class);
		}
		public SubEnumListContext subEnumList(int i) {
			return getRuleContext(SubEnumListContext.class,i);
		}
		public SubEnumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subEnum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSubEnum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSubEnum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSubEnum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubEnumContext subEnum() throws RecognitionException {
		SubEnumContext _localctx = new SubEnumContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_subEnum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(SubLBRACE);
			setState(204); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(203);
				subEnumList();
				}
				}
				setState(206); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SubID );
			setState(208);
			match(SubRBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubEnumListContext extends ParserRuleContext {
		public List<TerminalNode> SubID() { return getTokens(SccParser.SubID); }
		public TerminalNode SubID(int i) {
			return getToken(SccParser.SubID, i);
		}
		public List<TerminalNode> SubCOMMA() { return getTokens(SccParser.SubCOMMA); }
		public TerminalNode SubCOMMA(int i) {
			return getToken(SccParser.SubCOMMA, i);
		}
		public SubEnumListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subEnumList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSubEnumList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSubEnumList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSubEnumList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubEnumListContext subEnumList() throws RecognitionException {
		SubEnumListContext _localctx = new SubEnumListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_subEnumList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(SubID);
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SubCOMMA) {
				{
				{
				setState(211);
				match(SubCOMMA);
				setState(212);
				match(SubID);
				}
				}
				setState(217);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubIntContext extends ParserRuleContext {
		public TerminalNode VarINT() { return getToken(SccParser.VarINT, 0); }
		public SubIntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subInt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSubInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSubInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSubInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubIntContext subInt() throws RecognitionException {
		SubIntContext _localctx = new SubIntContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_subInt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(VarINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubBoolContext extends ParserRuleContext {
		public TerminalNode SubTRUE() { return getToken(SccParser.SubTRUE, 0); }
		public TerminalNode SubFALSE() { return getToken(SccParser.SubFALSE, 0); }
		public SubBoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subBool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSubBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSubBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSubBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubBoolContext subBool() throws RecognitionException {
		SubBoolContext _localctx = new SubBoolContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_subBool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			_la = _input.LA(1);
			if ( !(_la==SubTRUE || _la==SubFALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubIdentifierContext extends ParserRuleContext {
		public List<TerminalNode> SubID() { return getTokens(SccParser.SubID); }
		public TerminalNode SubID(int i) {
			return getToken(SccParser.SubID, i);
		}
		public List<TerminalNode> SubDOT() { return getTokens(SccParser.SubDOT); }
		public TerminalNode SubDOT(int i) {
			return getToken(SccParser.SubDOT, i);
		}
		public SubIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSubIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSubIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSubIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubIdentifierContext subIdentifier() throws RecognitionException {
		SubIdentifierContext _localctx = new SubIdentifierContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_subIdentifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(SubID);
			setState(227);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SubDOT) {
				{
				{
				setState(223);
				match(SubDOT);
				setState(224);
				match(SubID);
				}
				}
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtStatementContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(SccParser.AT, 0); }
		public DayExpressionContext dayExpression() {
			return getRuleContext(DayExpressionContext.class,0);
		}
		public DateRangeContext dateRange() {
			return getRuleContext(DateRangeContext.class,0);
		}
		public TerminalNode AtEOL() { return getToken(SccParser.AtEOL, 0); }
		public DateExpressionContext dateExpression() {
			return getRuleContext(DateExpressionContext.class,0);
		}
		public AtStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterAtStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitAtStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitAtStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtStatementContext atStatement() throws RecognitionException {
		AtStatementContext _localctx = new AtStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_atStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(AT);
			setState(231);
			dayExpression();
			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ATON) {
				{
				setState(232);
				dateExpression();
				}
			}

			setState(235);
			dateRange();
			setState(236);
			match(AtEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OnStatementContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(SccParser.ON, 0); }
		public OnExpressionContext onExpression() {
			return getRuleContext(OnExpressionContext.class,0);
		}
		public TerminalNode OnEOL() { return getToken(SccParser.OnEOL, 0); }
		public OnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_onStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterOnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitOnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitOnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OnStatementContext onStatement() throws RecognitionException {
		OnStatementContext _localctx = new OnStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_onStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(ON);
			setState(239);
			onExpression();
			setState(240);
			match(OnEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OnExpressionContext extends ParserRuleContext {
		public OnIdentifierContext onIdentifier() {
			return getRuleContext(OnIdentifierContext.class,0);
		}
		public OnExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_onExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterOnExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitOnExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitOnExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OnExpressionContext onExpression() throws RecognitionException {
		OnExpressionContext _localctx = new OnExpressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_onExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			onIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OnIdentifierContext extends ParserRuleContext {
		public List<TerminalNode> OnID() { return getTokens(SccParser.OnID); }
		public TerminalNode OnID(int i) {
			return getToken(SccParser.OnID, i);
		}
		public List<TerminalNode> OnDOT() { return getTokens(SccParser.OnDOT); }
		public TerminalNode OnDOT(int i) {
			return getToken(SccParser.OnDOT, i);
		}
		public OnIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_onIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterOnIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitOnIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitOnIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OnIdentifierContext onIdentifier() throws RecognitionException {
		OnIdentifierContext _localctx = new OnIdentifierContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_onIdentifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(OnID);
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OnDOT) {
				{
				{
				setState(245);
				match(OnDOT);
				setState(246);
				match(OnID);
				}
				}
				setState(251);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WithStatementContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(SccParser.WITH, 0); }
		public WithExpressionContext withExpression() {
			return getRuleContext(WithExpressionContext.class,0);
		}
		public TerminalNode WithEOL() { return getToken(SccParser.WithEOL, 0); }
		public WithStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterWithStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitWithStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitWithStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithStatementContext withStatement() throws RecognitionException {
		WithStatementContext _localctx = new WithStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_withStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(WITH);
			setState(253);
			withExpression(0);
			setState(254);
			match(WithEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WithExpressionContext extends ParserRuleContext {
		public WithExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withExpression; }
	 
		public WithExpressionContext() { }
		public void copyFrom(WithExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WithIdContext extends WithExpressionContext {
		public WithIdentifierContext withIdentifier() {
			return getRuleContext(WithIdentifierContext.class,0);
		}
		public WithIdContext(WithExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterWithId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitWithId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitWithId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotWithContext extends WithExpressionContext {
		public TerminalNode WithNOT() { return getToken(SccParser.WithNOT, 0); }
		public WithExpressionContext withExpression() {
			return getRuleContext(WithExpressionContext.class,0);
		}
		public NotWithContext(WithExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterNotWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitNotWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitNotWith(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrWithContext extends WithExpressionContext {
		public List<WithExpressionContext> withExpression() {
			return getRuleContexts(WithExpressionContext.class);
		}
		public WithExpressionContext withExpression(int i) {
			return getRuleContext(WithExpressionContext.class,i);
		}
		public TerminalNode WithOR() { return getToken(SccParser.WithOR, 0); }
		public OrWithContext(WithExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterOrWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitOrWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitOrWith(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndWithContext extends WithExpressionContext {
		public List<WithExpressionContext> withExpression() {
			return getRuleContexts(WithExpressionContext.class);
		}
		public WithExpressionContext withExpression(int i) {
			return getRuleContext(WithExpressionContext.class,i);
		}
		public TerminalNode WithAND() { return getToken(SccParser.WithAND, 0); }
		public AndWithContext(WithExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterAndWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitAndWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitAndWith(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenWithContext extends WithExpressionContext {
		public TerminalNode WithLPAREN() { return getToken(SccParser.WithLPAREN, 0); }
		public WithExpressionContext withExpression() {
			return getRuleContext(WithExpressionContext.class,0);
		}
		public TerminalNode WithRPAREN() { return getToken(SccParser.WithRPAREN, 0); }
		public ParenWithContext(WithExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterParenWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitParenWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitParenWith(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithExpressionContext withExpression() throws RecognitionException {
		return withExpression(0);
	}

	private WithExpressionContext withExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		WithExpressionContext _localctx = new WithExpressionContext(_ctx, _parentState);
		WithExpressionContext _prevctx = _localctx;
		int _startState = 52;
		enterRecursionRule(_localctx, 52, RULE_withExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WithID:
				{
				_localctx = new WithIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(257);
				withIdentifier();
				}
				break;
			case WithLPAREN:
				{
				_localctx = new ParenWithContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				{
				setState(258);
				match(WithLPAREN);
				setState(259);
				withExpression(0);
				setState(260);
				match(WithRPAREN);
				}
				}
				break;
			case WithNOT:
				{
				_localctx = new NotWithContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(262);
				match(WithNOT);
				setState(263);
				withExpression(3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(274);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(272);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new AndWithContext(new WithExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_withExpression);
						setState(266);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(267);
						match(WithAND);
						setState(268);
						withExpression(3);
						}
						break;
					case 2:
						{
						_localctx = new OrWithContext(new WithExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_withExpression);
						setState(269);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(270);
						match(WithOR);
						setState(271);
						withExpression(2);
						}
						break;
					}
					} 
				}
				setState(276);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WithIdentifierContext extends ParserRuleContext {
		public List<TerminalNode> WithID() { return getTokens(SccParser.WithID); }
		public TerminalNode WithID(int i) {
			return getToken(SccParser.WithID, i);
		}
		public List<TerminalNode> WithDOT() { return getTokens(SccParser.WithDOT); }
		public TerminalNode WithDOT(int i) {
			return getToken(SccParser.WithDOT, i);
		}
		public WithIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterWithIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitWithIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitWithIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithIdentifierContext withIdentifier() throws RecognitionException {
		WithIdentifierContext _localctx = new WithIdentifierContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_withIdentifier);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(WithID);
			setState(282);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(278);
					match(WithDOT);
					setState(279);
					match(WithID);
					}
					} 
				}
				setState(284);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ThenStatementContext extends ParserRuleContext {
		public TerminalNode THEN() { return getToken(SccParser.THEN, 0); }
		public ThenExpressionContext thenExpression() {
			return getRuleContext(ThenExpressionContext.class,0);
		}
		public TerminalNode ThenEOL() { return getToken(SccParser.ThenEOL, 0); }
		public ThenStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thenStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterThenStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitThenStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitThenStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThenStatementContext thenStatement() throws RecognitionException {
		ThenStatementContext _localctx = new ThenStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_thenStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(THEN);
			setState(286);
			thenExpression(0);
			setState(287);
			match(ThenEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ThenExpressionContext extends ParserRuleContext {
		public ThenExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thenExpression; }
	 
		public ThenExpressionContext() { }
		public void copyFrom(ThenExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ThenIdContext extends ThenExpressionContext {
		public ThenIdentifierContext thenIdentifier() {
			return getRuleContext(ThenIdentifierContext.class,0);
		}
		public ThenIdContext(ThenExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterThenId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitThenId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitThenId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndThenContext extends ThenExpressionContext {
		public List<ThenExpressionContext> thenExpression() {
			return getRuleContexts(ThenExpressionContext.class);
		}
		public ThenExpressionContext thenExpression(int i) {
			return getRuleContext(ThenExpressionContext.class,i);
		}
		public TerminalNode ThenAND() { return getToken(SccParser.ThenAND, 0); }
		public AndThenContext(ThenExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterAndThen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitAndThen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitAndThen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotThenContext extends ThenExpressionContext {
		public TerminalNode ThenNOT() { return getToken(SccParser.ThenNOT, 0); }
		public ThenExpressionContext thenExpression() {
			return getRuleContext(ThenExpressionContext.class,0);
		}
		public NotThenContext(ThenExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterNotThen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitNotThen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitNotThen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenThenContext extends ThenExpressionContext {
		public TerminalNode ThenLPAREN() { return getToken(SccParser.ThenLPAREN, 0); }
		public ThenExpressionContext thenExpression() {
			return getRuleContext(ThenExpressionContext.class,0);
		}
		public TerminalNode ThenRPAREN() { return getToken(SccParser.ThenRPAREN, 0); }
		public ParenThenContext(ThenExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterParenThen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitParenThen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitParenThen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrThenContext extends ThenExpressionContext {
		public List<ThenExpressionContext> thenExpression() {
			return getRuleContexts(ThenExpressionContext.class);
		}
		public ThenExpressionContext thenExpression(int i) {
			return getRuleContext(ThenExpressionContext.class,i);
		}
		public TerminalNode ThenOR() { return getToken(SccParser.ThenOR, 0); }
		public OrThenContext(ThenExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterOrThen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitOrThen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitOrThen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThenExpressionContext thenExpression() throws RecognitionException {
		return thenExpression(0);
	}

	private ThenExpressionContext thenExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ThenExpressionContext _localctx = new ThenExpressionContext(_ctx, _parentState);
		ThenExpressionContext _prevctx = _localctx;
		int _startState = 58;
		enterRecursionRule(_localctx, 58, RULE_thenExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ThenID:
				{
				_localctx = new ThenIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(290);
				thenIdentifier();
				}
				break;
			case ThenLPAREN:
				{
				_localctx = new ParenThenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				{
				setState(291);
				match(ThenLPAREN);
				setState(292);
				thenExpression(0);
				setState(293);
				match(ThenRPAREN);
				}
				}
				break;
			case ThenNOT:
				{
				_localctx = new NotThenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(295);
				match(ThenNOT);
				setState(296);
				thenExpression(3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(307);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(305);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new AndThenContext(new ThenExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_thenExpression);
						setState(299);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(300);
						match(ThenAND);
						setState(301);
						thenExpression(3);
						}
						break;
					case 2:
						{
						_localctx = new OrThenContext(new ThenExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_thenExpression);
						setState(302);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(303);
						match(ThenOR);
						setState(304);
						thenExpression(2);
						}
						break;
					}
					} 
				}
				setState(309);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ThenIdentifierContext extends ParserRuleContext {
		public List<TerminalNode> ThenID() { return getTokens(SccParser.ThenID); }
		public TerminalNode ThenID(int i) {
			return getToken(SccParser.ThenID, i);
		}
		public List<TerminalNode> ThenDOT() { return getTokens(SccParser.ThenDOT); }
		public TerminalNode ThenDOT(int i) {
			return getToken(SccParser.ThenDOT, i);
		}
		public ThenIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thenIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterThenIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitThenIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitThenIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThenIdentifierContext thenIdentifier() throws RecognitionException {
		ThenIdentifierContext _localctx = new ThenIdentifierContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_thenIdentifier);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			match(ThenID);
			setState(315);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(311);
					match(ThenDOT);
					setState(312);
					match(ThenID);
					}
					} 
				}
				setState(317);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SendStatementContext extends ParserRuleContext {
		public TerminalNode SEND() { return getToken(SccParser.SEND, 0); }
		public SendExpressionContext sendExpression() {
			return getRuleContext(SendExpressionContext.class,0);
		}
		public TerminalNode SendEOL() { return getToken(SccParser.SendEOL, 0); }
		public SendStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sendStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSendStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSendStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSendStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SendStatementContext sendStatement() throws RecognitionException {
		SendStatementContext _localctx = new SendStatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_sendStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			match(SEND);
			setState(319);
			sendExpression();
			setState(320);
			match(SendEOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SendExpressionContext extends ParserRuleContext {
		public SendIdentifierContext sendIdentifier() {
			return getRuleContext(SendIdentifierContext.class,0);
		}
		public SendExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sendExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSendExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSendExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSendExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SendExpressionContext sendExpression() throws RecognitionException {
		SendExpressionContext _localctx = new SendExpressionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_sendExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			sendIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SendIdentifierContext extends ParserRuleContext {
		public List<TerminalNode> SendID() { return getTokens(SccParser.SendID); }
		public TerminalNode SendID(int i) {
			return getToken(SccParser.SendID, i);
		}
		public List<TerminalNode> SendDOT() { return getTokens(SccParser.SendDOT); }
		public TerminalNode SendDOT(int i) {
			return getToken(SccParser.SendDOT, i);
		}
		public SendIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sendIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSendIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSendIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSendIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SendIdentifierContext sendIdentifier() throws RecognitionException {
		SendIdentifierContext _localctx = new SendIdentifierContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_sendIdentifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(SendID);
			setState(329);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SendDOT) {
				{
				{
				setState(325);
				match(SendDOT);
				setState(326);
				match(SendID);
				}
				}
				setState(331);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DateRangeContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(SccParser.FROM, 0); }
		public FromDateContext fromDate() {
			return getRuleContext(FromDateContext.class,0);
		}
		public TerminalNode THROUGH() { return getToken(SccParser.THROUGH, 0); }
		public ToDateContext toDate() {
			return getRuleContext(ToDateContext.class,0);
		}
		public DateRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterDateRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitDateRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitDateRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateRangeContext dateRange() throws RecognitionException {
		DateRangeContext _localctx = new DateRangeContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_dateRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FROM) {
				{
				setState(332);
				match(FROM);
				setState(333);
				fromDate();
				}
			}

			setState(338);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==THROUGH) {
				{
				setState(336);
				match(THROUGH);
				setState(337);
				toDate();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FromDateContext extends ParserRuleContext {
		public SpecificDateContext specificDate() {
			return getRuleContext(SpecificDateContext.class,0);
		}
		public FromDateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fromDate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterFromDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitFromDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitFromDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FromDateContext fromDate() throws RecognitionException {
		FromDateContext _localctx = new FromDateContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_fromDate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			specificDate();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ToDateContext extends ParserRuleContext {
		public SpecificDateContext specificDate() {
			return getRuleContext(SpecificDateContext.class,0);
		}
		public ToDateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_toDate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterToDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitToDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitToDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ToDateContext toDate() throws RecognitionException {
		ToDateContext _localctx = new ToDateContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_toDate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			specificDate();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DayExpressionContext extends ParserRuleContext {
		public SpecificTimeExpressionContext specificTimeExpression() {
			return getRuleContext(SpecificTimeExpressionContext.class,0);
		}
		public DurationExpressionContext durationExpression() {
			return getRuleContext(DurationExpressionContext.class,0);
		}
		public DayExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dayExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterDayExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitDayExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitDayExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DayExpressionContext dayExpression() throws RecognitionException {
		DayExpressionContext _localctx = new DayExpressionContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_dayExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(344);
				durationExpression();
				}
				break;
			}
			setState(347);
			specificTimeExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DurationExpressionContext extends ParserRuleContext {
		public QuantityContext quantity() {
			return getRuleContext(QuantityContext.class,0);
		}
		public TimeUnitExpressionContext timeUnitExpression() {
			return getRuleContext(TimeUnitExpressionContext.class,0);
		}
		public RelativeTimeExpressionContext relativeTimeExpression() {
			return getRuleContext(RelativeTimeExpressionContext.class,0);
		}
		public DurationExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_durationExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterDurationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitDurationExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitDurationExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DurationExpressionContext durationExpression() throws RecognitionException {
		DurationExpressionContext _localctx = new DurationExpressionContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_durationExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			quantity();
			setState(350);
			timeUnitExpression();
			setState(351);
			relativeTimeExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuantityContext extends ParserRuleContext {
		public TerminalNode AtINT() { return getToken(SccParser.AtINT, 0); }
		public QuantityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterQuantity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitQuantity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitQuantity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantityContext quantity() throws RecognitionException {
		QuantityContext _localctx = new QuantityContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_quantity);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			match(AtINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TimeUnitExpressionContext extends ParserRuleContext {
		public TerminalNode HOUR() { return getToken(SccParser.HOUR, 0); }
		public TerminalNode MINUTE() { return getToken(SccParser.MINUTE, 0); }
		public TimeUnitExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeUnitExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterTimeUnitExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitTimeUnitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitTimeUnitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeUnitExpressionContext timeUnitExpression() throws RecognitionException {
		TimeUnitExpressionContext _localctx = new TimeUnitExpressionContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_timeUnitExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			_la = _input.LA(1);
			if ( !(_la==HOUR || _la==MINUTE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelativeTimeExpressionContext extends ParserRuleContext {
		public TerminalNode BEFORE() { return getToken(SccParser.BEFORE, 0); }
		public TerminalNode AFTER() { return getToken(SccParser.AFTER, 0); }
		public RelativeTimeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeTimeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterRelativeTimeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitRelativeTimeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitRelativeTimeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeTimeExpressionContext relativeTimeExpression() throws RecognitionException {
		RelativeTimeExpressionContext _localctx = new RelativeTimeExpressionContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_relativeTimeExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
			_la = _input.LA(1);
			if ( !(_la==BEFORE || _la==AFTER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SpecificTimeExpressionContext extends ParserRuleContext {
		public TimeContext time() {
			return getRuleContext(TimeContext.class,0);
		}
		public TimeShortcutContext timeShortcut() {
			return getRuleContext(TimeShortcutContext.class,0);
		}
		public SpecificTimeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_specificTimeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSpecificTimeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSpecificTimeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSpecificTimeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpecificTimeExpressionContext specificTimeExpression() throws RecognitionException {
		SpecificTimeExpressionContext _localctx = new SpecificTimeExpressionContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_specificTimeExpression);
		try {
			setState(361);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AtINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(359);
				time();
				}
				break;
			case SUNRISE:
			case SUNSET:
			case MIDNIGHT:
			case NOON:
				enterOuterAlt(_localctx, 2);
				{
				setState(360);
				timeShortcut();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TimeShortcutContext extends ParserRuleContext {
		public TerminalNode SUNRISE() { return getToken(SccParser.SUNRISE, 0); }
		public TerminalNode SUNSET() { return getToken(SccParser.SUNSET, 0); }
		public TerminalNode MIDNIGHT() { return getToken(SccParser.MIDNIGHT, 0); }
		public TerminalNode NOON() { return getToken(SccParser.NOON, 0); }
		public TimeShortcutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeShortcut; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterTimeShortcut(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitTimeShortcut(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitTimeShortcut(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeShortcutContext timeShortcut() throws RecognitionException {
		TimeShortcutContext _localctx = new TimeShortcutContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_timeShortcut);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 13523993021644800L) != 0) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TimeContext extends ParserRuleContext {
		public Token hr;
		public Token mi;
		public Token ap;
		public TerminalNode COLON() { return getToken(SccParser.COLON, 0); }
		public List<TerminalNode> AtINT() { return getTokens(SccParser.AtINT); }
		public TerminalNode AtINT(int i) {
			return getToken(SccParser.AtINT, i);
		}
		public TerminalNode AMPM() { return getToken(SccParser.AMPM, 0); }
		public TimeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitTime(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitTime(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeContext time() throws RecognitionException {
		TimeContext _localctx = new TimeContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_time);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			((TimeContext)_localctx).hr = match(AtINT);
			setState(366);
			match(COLON);
			setState(367);
			((TimeContext)_localctx).mi = match(AtINT);
			setState(368);
			((TimeContext)_localctx).ap = match(AMPM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DateExpressionContext extends ParserRuleContext {
		public TerminalNode ATON() { return getToken(SccParser.ATON, 0); }
		public List<DateContext> date() {
			return getRuleContexts(DateContext.class);
		}
		public DateContext date(int i) {
			return getRuleContext(DateContext.class,i);
		}
		public DateExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterDateExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitDateExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitDateExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateExpressionContext dateExpression() throws RecognitionException {
		DateExpressionContext _localctx = new DateExpressionContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_dateExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(370);
			match(ATON);
			setState(372); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(371);
				date();
				}
				}
				setState(374); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((_la) & ~0x3f) == 0 && ((1L << _la) & 2272140778799104L) != 0 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SpecificDateContext extends ParserRuleContext {
		public MonthContext month() {
			return getRuleContext(MonthContext.class,0);
		}
		public DayContext day() {
			return getRuleContext(DayContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(SccParser.COMMA, 0); }
		public YearContext year() {
			return getRuleContext(YearContext.class,0);
		}
		public SpecificDateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_specificDate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSpecificDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSpecificDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSpecificDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpecificDateContext specificDate() throws RecognitionException {
		SpecificDateContext _localctx = new SpecificDateContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_specificDate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
			month();
			setState(377);
			day();
			setState(380);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(378);
				match(COMMA);
				setState(379);
				year();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DateContext extends ParserRuleContext {
		public DowContext dow() {
			return getRuleContext(DowContext.class,0);
		}
		public SeasonContext season() {
			return getRuleContext(SeasonContext.class,0);
		}
		public HolidayContext holiday() {
			return getRuleContext(HolidayContext.class,0);
		}
		public SpecificDateContext specificDate() {
			return getRuleContext(SpecificDateContext.class,0);
		}
		public DateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateContext date() throws RecognitionException {
		DateContext _localctx = new DateContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_date);
		try {
			setState(386);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOW:
				enterOuterAlt(_localctx, 1);
				{
				setState(382);
				dow();
				}
				break;
			case SEASON:
				enterOuterAlt(_localctx, 2);
				{
				setState(383);
				season();
				}
				break;
			case CHRISTMAS:
				enterOuterAlt(_localctx, 3);
				{
				setState(384);
				holiday();
				}
				break;
			case MONTH:
				enterOuterAlt(_localctx, 4);
				{
				setState(385);
				specificDate();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class YearContext extends ParserRuleContext {
		public TerminalNode AtINT() { return getToken(SccParser.AtINT, 0); }
		public YearContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_year; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterYear(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitYear(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitYear(this);
			else return visitor.visitChildren(this);
		}
	}

	public final YearContext year() throws RecognitionException {
		YearContext _localctx = new YearContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_year);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
			match(AtINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MonthContext extends ParserRuleContext {
		public TerminalNode MONTH() { return getToken(SccParser.MONTH, 0); }
		public MonthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_month; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterMonth(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitMonth(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitMonth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MonthContext month() throws RecognitionException {
		MonthContext _localctx = new MonthContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_month);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(MONTH);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DowContext extends ParserRuleContext {
		public TerminalNode DOW() { return getToken(SccParser.DOW, 0); }
		public DowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterDow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitDow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitDow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DowContext dow() throws RecognitionException {
		DowContext _localctx = new DowContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_dow);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
			match(DOW);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HolidayContext extends ParserRuleContext {
		public TerminalNode CHRISTMAS() { return getToken(SccParser.CHRISTMAS, 0); }
		public HolidayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_holiday; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterHoliday(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitHoliday(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitHoliday(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HolidayContext holiday() throws RecognitionException {
		HolidayContext _localctx = new HolidayContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_holiday);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			match(CHRISTMAS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DayContext extends ParserRuleContext {
		public TerminalNode AtINT() { return getToken(SccParser.AtINT, 0); }
		public DayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_day; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterDay(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitDay(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitDay(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DayContext day() throws RecognitionException {
		DayContext _localctx = new DayContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_day);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			match(AtINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SeasonContext extends ParserRuleContext {
		public TerminalNode SEASON() { return getToken(SccParser.SEASON, 0); }
		public SeasonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_season; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).enterSeason(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SccParserListener ) ((SccParserListener)listener).exitSeason(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SccParserVisitor ) return ((SccParserVisitor<? extends T>)visitor).visitSeason(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeasonContext season() throws RecognitionException {
		SeasonContext _localctx = new SeasonContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_season);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			match(SEASON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 26:
			return withExpression_sempred((WithExpressionContext)_localctx, predIndex);
		case 29:
			return thenExpression_sempred((ThenExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean withExpression_sempred(WithExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean thenExpression_sempred(ThenExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001a\u0191\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0005\u0003x\b\u0003\n\u0003\f\u0003"+
		"{\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004\u0082\b\u0004\u0001\u0004\u0003\u0004\u0085\b\u0004\u0001"+
		"\u0004\u0003\u0004\u0088\b\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u008c"+
		"\b\u0004\u0001\u0004\u0003\u0004\u008f\b\u0004\u0001\u0004\u0003\u0004"+
		"\u0092\b\u0004\u0003\u0004\u0094\b\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00a9\b\b\u0001\t\u0001"+
		"\t\u0004\t\u00ad\b\t\u000b\t\f\t\u00ae\u0001\t\u0001\t\u0001\n\u0001\n"+
		"\u0001\n\u0005\n\u00b6\b\n\n\n\f\n\u00b9\t\n\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00c9\b\u000f"+
		"\u0001\u0010\u0001\u0010\u0004\u0010\u00cd\b\u0010\u000b\u0010\f\u0010"+
		"\u00ce\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0005"+
		"\u0011\u00d6\b\u0011\n\u0011\f\u0011\u00d9\t\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014"+
		"\u00e2\b\u0014\n\u0014\f\u0014\u00e5\t\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0003\u0015\u00ea\b\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u00f8\b\u0018\n\u0018\f\u0018"+
		"\u00fb\t\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0003\u001a\u0109\b\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u0111\b\u001a\n\u001a"+
		"\f\u001a\u0114\t\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0005\u001b"+
		"\u0119\b\u001b\n\u001b\f\u001b\u011c\t\u001b\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u012a\b\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0005"+
		"\u001d\u0132\b\u001d\n\u001d\f\u001d\u0135\t\u001d\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0005\u001e\u013a\b\u001e\n\u001e\f\u001e\u013d\t\u001e\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001"+
		"!\u0001!\u0005!\u0148\b!\n!\f!\u014b\t!\u0001\"\u0001\"\u0003\"\u014f"+
		"\b\"\u0001\"\u0001\"\u0003\"\u0153\b\"\u0001#\u0001#\u0001$\u0001$\u0001"+
		"%\u0003%\u015a\b%\u0001%\u0001%\u0001&\u0001&\u0001&\u0001&\u0001\'\u0001"+
		"\'\u0001(\u0001(\u0001)\u0001)\u0001*\u0001*\u0003*\u016a\b*\u0001+\u0001"+
		"+\u0001,\u0001,\u0001,\u0001,\u0001,\u0001-\u0001-\u0004-\u0175\b-\u000b"+
		"-\f-\u0176\u0001.\u0001.\u0001.\u0001.\u0003.\u017d\b.\u0001/\u0001/\u0001"+
		"/\u0001/\u0003/\u0183\b/\u00010\u00010\u00011\u00011\u00012\u00012\u0001"+
		"3\u00013\u00014\u00014\u00015\u00015\u00015\u0000\u00024:6\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhj\u0000\u0005\u0002\u0000\u0012"+
		"\u0012\\\\\u0001\u0000`a\u0001\u0000/0\u0001\u000012\u0002\u0000*+45\u0185"+
		"\u0000l\u0001\u0000\u0000\u0000\u0002p\u0001\u0000\u0000\u0000\u0004t"+
		"\u0001\u0000\u0000\u0000\u0006y\u0001\u0000\u0000\u0000\b\u0093\u0001"+
		"\u0000\u0000\u0000\n\u0095\u0001\u0000\u0000\u0000\f\u009d\u0001\u0000"+
		"\u0000\u0000\u000e\u00a2\u0001\u0000\u0000\u0000\u0010\u00a8\u0001\u0000"+
		"\u0000\u0000\u0012\u00aa\u0001\u0000\u0000\u0000\u0014\u00b2\u0001\u0000"+
		"\u0000\u0000\u0016\u00ba\u0001\u0000\u0000\u0000\u0018\u00bc\u0001\u0000"+
		"\u0000\u0000\u001a\u00be\u0001\u0000\u0000\u0000\u001c\u00c0\u0001\u0000"+
		"\u0000\u0000\u001e\u00c8\u0001\u0000\u0000\u0000 \u00ca\u0001\u0000\u0000"+
		"\u0000\"\u00d2\u0001\u0000\u0000\u0000$\u00da\u0001\u0000\u0000\u0000"+
		"&\u00dc\u0001\u0000\u0000\u0000(\u00de\u0001\u0000\u0000\u0000*\u00e6"+
		"\u0001\u0000\u0000\u0000,\u00ee\u0001\u0000\u0000\u0000.\u00f2\u0001\u0000"+
		"\u0000\u00000\u00f4\u0001\u0000\u0000\u00002\u00fc\u0001\u0000\u0000\u0000"+
		"4\u0108\u0001\u0000\u0000\u00006\u0115\u0001\u0000\u0000\u00008\u011d"+
		"\u0001\u0000\u0000\u0000:\u0129\u0001\u0000\u0000\u0000<\u0136\u0001\u0000"+
		"\u0000\u0000>\u013e\u0001\u0000\u0000\u0000@\u0142\u0001\u0000\u0000\u0000"+
		"B\u0144\u0001\u0000\u0000\u0000D\u014e\u0001\u0000\u0000\u0000F\u0154"+
		"\u0001\u0000\u0000\u0000H\u0156\u0001\u0000\u0000\u0000J\u0159\u0001\u0000"+
		"\u0000\u0000L\u015d\u0001\u0000\u0000\u0000N\u0161\u0001\u0000\u0000\u0000"+
		"P\u0163\u0001\u0000\u0000\u0000R\u0165\u0001\u0000\u0000\u0000T\u0169"+
		"\u0001\u0000\u0000\u0000V\u016b\u0001\u0000\u0000\u0000X\u016d\u0001\u0000"+
		"\u0000\u0000Z\u0172\u0001\u0000\u0000\u0000\\\u0178\u0001\u0000\u0000"+
		"\u0000^\u0182\u0001\u0000\u0000\u0000`\u0184\u0001\u0000\u0000\u0000b"+
		"\u0186\u0001\u0000\u0000\u0000d\u0188\u0001\u0000\u0000\u0000f\u018a\u0001"+
		"\u0000\u0000\u0000h\u018c\u0001\u0000\u0000\u0000j\u018e\u0001\u0000\u0000"+
		"\u0000lm\u0003\u0002\u0001\u0000mn\u0003\u0006\u0003\u0000no\u0005\u0000"+
		"\u0000\u0001o\u0001\u0001\u0000\u0000\u0000pq\u0005\u0001\u0000\u0000"+
		"qr\u0003\u0004\u0002\u0000rs\u0005\u001c\u0000\u0000s\u0003\u0001\u0000"+
		"\u0000\u0000tu\u0005\u001a\u0000\u0000u\u0005\u0001\u0000\u0000\u0000"+
		"vx\u0003\b\u0004\u0000wv\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000"+
		"yw\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z\u0007\u0001\u0000"+
		"\u0000\u0000{y\u0001\u0000\u0000\u0000|\u0094\u0003\u001c\u000e\u0000"+
		"}\u0094\u0003\n\u0005\u0000~\u0094\u0003\f\u0006\u0000\u007f\u0081\u0003"+
		"*\u0015\u0000\u0080\u0082\u00032\u0019\u0000\u0081\u0080\u0001\u0000\u0000"+
		"\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0084\u0001\u0000\u0000"+
		"\u0000\u0083\u0085\u00038\u001c\u0000\u0084\u0083\u0001\u0000\u0000\u0000"+
		"\u0084\u0085\u0001\u0000\u0000\u0000\u0085\u0087\u0001\u0000\u0000\u0000"+
		"\u0086\u0088\u0003>\u001f\u0000\u0087\u0086\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0001\u0000\u0000\u0000\u0088\u0094\u0001\u0000\u0000\u0000\u0089"+
		"\u008b\u0003,\u0016\u0000\u008a\u008c\u00032\u0019\u0000\u008b\u008a\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u008e\u0001"+
		"\u0000\u0000\u0000\u008d\u008f\u00038\u001c\u0000\u008e\u008d\u0001\u0000"+
		"\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f\u0091\u0001\u0000"+
		"\u0000\u0000\u0090\u0092\u0003>\u001f\u0000\u0091\u0090\u0001\u0000\u0000"+
		"\u0000\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u0094\u0001\u0000\u0000"+
		"\u0000\u0093|\u0001\u0000\u0000\u0000\u0093}\u0001\u0000\u0000\u0000\u0093"+
		"~\u0001\u0000\u0000\u0000\u0093\u007f\u0001\u0000\u0000\u0000\u0093\u0089"+
		"\u0001\u0000\u0000\u0000\u0094\t\u0001\u0000\u0000\u0000\u0095\u0096\u0005"+
		"\u0002\u0000\u0000\u0096\u0097\u0003\u000e\u0007\u0000\u0097\u0098\u0003"+
		"\u0010\b\u0000\u0098\u0099\u0005\u0013\u0000\u0000\u0099\u009a\u0003\u000e"+
		"\u0007\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u009c\u0005\u0018"+
		"\u0000\u0000\u009c\u000b\u0001\u0000\u0000\u0000\u009d\u009e\u0005\u0002"+
		"\u0000\u0000\u009e\u009f\u0003\u000e\u0007\u0000\u009f\u00a0\u0003\u0010"+
		"\b\u0000\u00a0\u00a1\u0005\u0018\u0000\u0000\u00a1\r\u0001\u0000\u0000"+
		"\u0000\u00a2\u00a3\u0005\u0016\u0000\u0000\u00a3\u000f\u0001\u0000\u0000"+
		"\u0000\u00a4\u00a9\u0003\u0012\t\u0000\u00a5\u00a9\u0003\u0016\u000b\u0000"+
		"\u00a6\u00a9\u0003\u0018\f\u0000\u00a7\u00a9\u0003\u001a\r\u0000\u00a8"+
		"\u00a4\u0001\u0000\u0000\u0000\u00a8\u00a5\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a6\u0001\u0000\u0000\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a9"+
		"\u0011\u0001\u0000\u0000\u0000\u00aa\u00ac\u0005\u0010\u0000\u0000\u00ab"+
		"\u00ad\u0003\u0014\n\u0000\u00ac\u00ab\u0001\u0000\u0000\u0000\u00ad\u00ae"+
		"\u0001\u0000\u0000\u0000\u00ae\u00ac\u0001\u0000\u0000\u0000\u00ae\u00af"+
		"\u0001\u0000\u0000\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0\u00b1"+
		"\u0005\u0011\u0000\u0000\u00b1\u0013\u0001\u0000\u0000\u0000\u00b2\u00b7"+
		"\u0005\u0016\u0000\u0000\u00b3\u00b4\u0005\u000f\u0000\u0000\u00b4\u00b6"+
		"\u0005\u0016\u0000\u0000\u00b5\u00b3\u0001\u0000\u0000\u0000\u00b6\u00b9"+
		"\u0001\u0000\u0000\u0000\u00b7\u00b5\u0001\u0000\u0000\u0000\u00b7\u00b8"+
		"\u0001\u0000\u0000\u0000\u00b8\u0015\u0001\u0000\u0000\u0000\u00b9\u00b7"+
		"\u0001\u0000\u0000\u0000\u00ba\u00bb\u0005\u0015\u0000\u0000\u00bb\u0017"+
		"\u0001\u0000\u0000\u0000\u00bc\u00bd\u0007\u0000\u0000\u0000\u00bd\u0019"+
		"\u0001\u0000\u0000\u0000\u00be\u00bf\u0005\u0014\u0000\u0000\u00bf\u001b"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c1\u0005\u0005\u0000\u0000\u00c1\u00c2"+
		"\u0003(\u0014\u0000\u00c2\u00c3\u0003\u001e\u000f\u0000\u00c3\u00c4\u0005"+
		"%\u0000\u0000\u00c4\u001d\u0001\u0000\u0000\u0000\u00c5\u00c9\u0003 \u0010"+
		"\u0000\u00c6\u00c9\u0003$\u0012\u0000\u00c7\u00c9\u0003&\u0013\u0000\u00c8"+
		"\u00c5\u0001\u0000\u0000\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c8"+
		"\u00c7\u0001\u0000\u0000\u0000\u00c9\u001f\u0001\u0000\u0000\u0000\u00ca"+
		"\u00cc\u0005]\u0000\u0000\u00cb\u00cd\u0003\"\u0011\u0000\u00cc\u00cb"+
		"\u0001\u0000\u0000\u0000\u00cd\u00ce\u0001\u0000\u0000\u0000\u00ce\u00cc"+
		"\u0001\u0000\u0000\u0000\u00ce\u00cf\u0001\u0000\u0000\u0000\u00cf\u00d0"+
		"\u0001\u0000\u0000\u0000\u00d0\u00d1\u0005^\u0000\u0000\u00d1!\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d7\u0005#\u0000\u0000\u00d3\u00d4\u0005_\u0000\u0000"+
		"\u00d4\u00d6\u0005#\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6"+
		"\u00d9\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d7"+
		"\u00d8\u0001\u0000\u0000\u0000\u00d8#\u0001\u0000\u0000\u0000\u00d9\u00d7"+
		"\u0001\u0000\u0000\u0000\u00da\u00db\u0005\u0015\u0000\u0000\u00db%\u0001"+
		"\u0000\u0000\u0000\u00dc\u00dd\u0007\u0001\u0000\u0000\u00dd\'\u0001\u0000"+
		"\u0000\u0000\u00de\u00e3\u0005#\u0000\u0000\u00df\u00e0\u0005\"\u0000"+
		"\u0000\u00e0\u00e2\u0005#\u0000\u0000\u00e1\u00df\u0001\u0000\u0000\u0000"+
		"\u00e2\u00e5\u0001\u0000\u0000\u0000\u00e3\u00e1\u0001\u0000\u0000\u0000"+
		"\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4)\u0001\u0000\u0000\u0000\u00e5"+
		"\u00e3\u0001\u0000\u0000\u0000\u00e6\u00e7\u0005\u0006\u0000\u0000\u00e7"+
		"\u00e9\u0003J%\u0000\u00e8\u00ea\u0003Z-\u0000\u00e9\u00e8\u0001\u0000"+
		"\u0000\u0000\u00e9\u00ea\u0001\u0000\u0000\u0000\u00ea\u00eb\u0001\u0000"+
		"\u0000\u0000\u00eb\u00ec\u0003D\"\u0000\u00ec\u00ed\u0005<\u0000\u0000"+
		"\u00ed+\u0001\u0000\u0000\u0000\u00ee\u00ef\u0005\u0007\u0000\u0000\u00ef"+
		"\u00f0\u0003.\u0017\u0000\u00f0\u00f1\u0005A\u0000\u0000\u00f1-\u0001"+
		"\u0000\u0000\u0000\u00f2\u00f3\u00030\u0018\u0000\u00f3/\u0001\u0000\u0000"+
		"\u0000\u00f4\u00f9\u0005?\u0000\u0000\u00f5\u00f6\u0005>\u0000\u0000\u00f6"+
		"\u00f8\u0005?\u0000\u0000\u00f7\u00f5\u0001\u0000\u0000\u0000\u00f8\u00fb"+
		"\u0001\u0000\u0000\u0000\u00f9\u00f7\u0001\u0000\u0000\u0000\u00f9\u00fa"+
		"\u0001\u0000\u0000\u0000\u00fa1\u0001\u0000\u0000\u0000\u00fb\u00f9\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u0005\b\u0000\u0000\u00fd\u00fe\u00034"+
		"\u001a\u0000\u00fe\u00ff\u0005K\u0000\u0000\u00ff3\u0001\u0000\u0000\u0000"+
		"\u0100\u0101\u0006\u001a\uffff\uffff\u0000\u0101\u0109\u00036\u001b\u0000"+
		"\u0102\u0103\u0005C\u0000\u0000\u0103\u0104\u00034\u001a\u0000\u0104\u0105"+
		"\u0005D\u0000\u0000\u0105\u0109\u0001\u0000\u0000\u0000\u0106\u0107\u0005"+
		"G\u0000\u0000\u0107\u0109\u00034\u001a\u0003\u0108\u0100\u0001\u0000\u0000"+
		"\u0000\u0108\u0102\u0001\u0000\u0000\u0000\u0108\u0106\u0001\u0000\u0000"+
		"\u0000\u0109\u0112\u0001\u0000\u0000\u0000\u010a\u010b\n\u0002\u0000\u0000"+
		"\u010b\u010c\u0005E\u0000\u0000\u010c\u0111\u00034\u001a\u0003\u010d\u010e"+
		"\n\u0001\u0000\u0000\u010e\u010f\u0005F\u0000\u0000\u010f\u0111\u0003"+
		"4\u001a\u0002\u0110\u010a\u0001\u0000\u0000\u0000\u0110\u010d\u0001\u0000"+
		"\u0000\u0000\u0111\u0114\u0001\u0000\u0000\u0000\u0112\u0110\u0001\u0000"+
		"\u0000\u0000\u0112\u0113\u0001\u0000\u0000\u0000\u01135\u0001\u0000\u0000"+
		"\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0115\u011a\u0005I\u0000\u0000"+
		"\u0116\u0117\u0005H\u0000\u0000\u0117\u0119\u0005I\u0000\u0000\u0118\u0116"+
		"\u0001\u0000\u0000\u0000\u0119\u011c\u0001\u0000\u0000\u0000\u011a\u0118"+
		"\u0001\u0000\u0000\u0000\u011a\u011b\u0001\u0000\u0000\u0000\u011b7\u0001"+
		"\u0000\u0000\u0000\u011c\u011a\u0001\u0000\u0000\u0000\u011d\u011e\u0005"+
		"\t\u0000\u0000\u011e\u011f\u0003:\u001d\u0000\u011f\u0120\u0005U\u0000"+
		"\u0000\u01209\u0001\u0000\u0000\u0000\u0121\u0122\u0006\u001d\uffff\uffff"+
		"\u0000\u0122\u012a\u0003<\u001e\u0000\u0123\u0124\u0005M\u0000\u0000\u0124"+
		"\u0125\u0003:\u001d\u0000\u0125\u0126\u0005N\u0000\u0000\u0126\u012a\u0001"+
		"\u0000\u0000\u0000\u0127\u0128\u0005Q\u0000\u0000\u0128\u012a\u0003:\u001d"+
		"\u0003\u0129\u0121\u0001\u0000\u0000\u0000\u0129\u0123\u0001\u0000\u0000"+
		"\u0000\u0129\u0127\u0001\u0000\u0000\u0000\u012a\u0133\u0001\u0000\u0000"+
		"\u0000\u012b\u012c\n\u0002\u0000\u0000\u012c\u012d\u0005O\u0000\u0000"+
		"\u012d\u0132\u0003:\u001d\u0003\u012e\u012f\n\u0001\u0000\u0000\u012f"+
		"\u0130\u0005P\u0000\u0000\u0130\u0132\u0003:\u001d\u0002\u0131\u012b\u0001"+
		"\u0000\u0000\u0000\u0131\u012e\u0001\u0000\u0000\u0000\u0132\u0135\u0001"+
		"\u0000\u0000\u0000\u0133\u0131\u0001\u0000\u0000\u0000\u0133\u0134\u0001"+
		"\u0000\u0000\u0000\u0134;\u0001\u0000\u0000\u0000\u0135\u0133\u0001\u0000"+
		"\u0000\u0000\u0136\u013b\u0005S\u0000\u0000\u0137\u0138\u0005R\u0000\u0000"+
		"\u0138\u013a\u0005S\u0000\u0000\u0139\u0137\u0001\u0000\u0000\u0000\u013a"+
		"\u013d\u0001\u0000\u0000\u0000\u013b\u0139\u0001\u0000\u0000\u0000\u013b"+
		"\u013c\u0001\u0000\u0000\u0000\u013c=\u0001\u0000\u0000\u0000\u013d\u013b"+
		"\u0001\u0000\u0000\u0000\u013e\u013f\u0005\n\u0000\u0000\u013f\u0140\u0003"+
		"@ \u0000\u0140\u0141\u0005Z\u0000\u0000\u0141?\u0001\u0000\u0000\u0000"+
		"\u0142\u0143\u0003B!\u0000\u0143A\u0001\u0000\u0000\u0000\u0144\u0149"+
		"\u0005X\u0000\u0000\u0145\u0146\u0005W\u0000\u0000\u0146\u0148\u0005X"+
		"\u0000\u0000\u0147\u0145\u0001\u0000\u0000\u0000\u0148\u014b\u0001\u0000"+
		"\u0000\u0000\u0149\u0147\u0001\u0000\u0000\u0000\u0149\u014a\u0001\u0000"+
		"\u0000\u0000\u014aC\u0001\u0000\u0000\u0000\u014b\u0149\u0001\u0000\u0000"+
		"\u0000\u014c\u014d\u0005-\u0000\u0000\u014d\u014f\u0003F#\u0000\u014e"+
		"\u014c\u0001\u0000\u0000\u0000\u014e\u014f\u0001\u0000\u0000\u0000\u014f"+
		"\u0152\u0001\u0000\u0000\u0000\u0150\u0151\u0005.\u0000\u0000\u0151\u0153"+
		"\u0003H$\u0000\u0152\u0150\u0001\u0000\u0000\u0000\u0152\u0153\u0001\u0000"+
		"\u0000\u0000\u0153E\u0001\u0000\u0000\u0000\u0154\u0155\u0003\\.\u0000"+
		"\u0155G\u0001\u0000\u0000\u0000\u0156\u0157\u0003\\.\u0000\u0157I\u0001"+
		"\u0000\u0000\u0000\u0158\u015a\u0003L&\u0000\u0159\u0158\u0001\u0000\u0000"+
		"\u0000\u0159\u015a\u0001\u0000\u0000\u0000\u015a\u015b\u0001\u0000\u0000"+
		"\u0000\u015b\u015c\u0003T*\u0000\u015cK\u0001\u0000\u0000\u0000\u015d"+
		"\u015e\u0003N\'\u0000\u015e\u015f\u0003P(\u0000\u015f\u0160\u0003R)\u0000"+
		"\u0160M\u0001\u0000\u0000\u0000\u0161\u0162\u0005:\u0000\u0000\u0162O"+
		"\u0001\u0000\u0000\u0000\u0163\u0164\u0007\u0002\u0000\u0000\u0164Q\u0001"+
		"\u0000\u0000\u0000\u0165\u0166\u0007\u0003\u0000\u0000\u0166S\u0001\u0000"+
		"\u0000\u0000\u0167\u016a\u0003X,\u0000\u0168\u016a\u0003V+\u0000\u0169"+
		"\u0167\u0001\u0000\u0000\u0000\u0169\u0168\u0001\u0000\u0000\u0000\u016a"+
		"U\u0001\u0000\u0000\u0000\u016b\u016c\u0007\u0004\u0000\u0000\u016cW\u0001"+
		"\u0000\u0000\u0000\u016d\u016e\u0005:\u0000\u0000\u016e\u016f\u00059\u0000"+
		"\u0000\u016f\u0170\u0005:\u0000\u0000\u0170\u0171\u0005(\u0000\u0000\u0171"+
		"Y\u0001\u0000\u0000\u0000\u0172\u0174\u00056\u0000\u0000\u0173\u0175\u0003"+
		"^/\u0000\u0174\u0173\u0001\u0000\u0000\u0000\u0175\u0176\u0001\u0000\u0000"+
		"\u0000\u0176\u0174\u0001\u0000\u0000\u0000\u0176\u0177\u0001\u0000\u0000"+
		"\u0000\u0177[\u0001\u0000\u0000\u0000\u0178\u0179\u0003b1\u0000\u0179"+
		"\u017c\u0003h4\u0000\u017a\u017b\u00057\u0000\u0000\u017b\u017d\u0003"+
		"`0\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017c\u017d\u0001\u0000\u0000"+
		"\u0000\u017d]\u0001\u0000\u0000\u0000\u017e\u0183\u0003d2\u0000\u017f"+
		"\u0183\u0003j5\u0000\u0180\u0183\u0003f3\u0000\u0181\u0183\u0003\\.\u0000"+
		"\u0182\u017e\u0001\u0000\u0000\u0000\u0182\u017f\u0001\u0000\u0000\u0000"+
		"\u0182\u0180\u0001\u0000\u0000\u0000\u0182\u0181\u0001\u0000\u0000\u0000"+
		"\u0183_\u0001\u0000\u0000\u0000\u0184\u0185\u0005:\u0000\u0000\u0185a"+
		"\u0001\u0000\u0000\u0000\u0186\u0187\u0005\'\u0000\u0000\u0187c\u0001"+
		"\u0000\u0000\u0000\u0188\u0189\u0005)\u0000\u0000\u0189e\u0001\u0000\u0000"+
		"\u0000\u018a\u018b\u00053\u0000\u0000\u018bg\u0001\u0000\u0000\u0000\u018c"+
		"\u018d\u0005:\u0000\u0000\u018di\u0001\u0000\u0000\u0000\u018e\u018f\u0005"+
		",\u0000\u0000\u018fk\u0001\u0000\u0000\u0000!y\u0081\u0084\u0087\u008b"+
		"\u008e\u0091\u0093\u00a8\u00ae\u00b7\u00c8\u00ce\u00d7\u00e3\u00e9\u00f9"+
		"\u0108\u0110\u0112\u011a\u0129\u0131\u0133\u013b\u0149\u014e\u0152\u0159"+
		"\u0169\u0176\u017c\u0182";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}