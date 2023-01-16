parser grammar SccParser;

options { tokenVocab = SccLexer; }
start
	:  module statements EOF	#ModuleStatement
	;

module
	: MODULE moduleName moduleInstance? (ModEOL|EOL)
	;

moduleName
	: ModID
	;
moduleInstance
	: ModLBRACKET ModID ModRBRACKET
	;
				
statements
	: statement* 				#statementList
	; 

statement
	: topicStatement
	| timerStatement
	| subscribeStatement
	| publishStatement
	| privateStatement
	| atStatement withStatement? thenStatement? sendStatement?
	| onStatement withStatement? thenStatement? sendStatement?
	;
	
topicStatement
	: TOPIC varIdentifier varType (VarEOL|EOF)
	;

timerStatement
	: TIMER varIdentifier (VarEOL|EOF)
	;
	
publishStatement
	: PUBLISH varIdentifier varType (VarAS varIdentifier)? (VarEOL|EOF)
	;

subscribeStatement
	: SUBSCRIBE varIdentifier varType (VarAS varIdentifier)? (VarEOL|EOF)
	;

privateStatement
	: PRIVATE varIdentifier varInstance? varType (VarEOL|EOF)
	;

varIdentifier
	: VarID (VarDOT VarID)*
	;

varInstance
	: VarLBRACKET VarID VarRBRACKET
	;
	
varType
	: varEnum
	| varInt
	| varBool
	| varEVENT
	;
	
varEnum
	: VarLBRACE varEnumList+ VarRBRACE
	;

varEnumList
	: VarID (VarCOMMA VarID)*
	;

varInt
	: VarINT
	;

varBool
	: VarTRUE
	| VarFALSE
	;

varEVENT
	: VarEVENT
	;
	
atStatement
	: AT dayExpression dateExpression? dateRange (AtEOL|EOF) 
	;

onStatement
	: ON onExpression (OnEOL|EOF) 
	;

onExpression
	: onIdentifier
	;

onIdentifier
	: OnID (OnDOT OnID)*
	;
		
withStatement
	: WITH withExpression (WithEOL|EOF)
	;

withExpression
	: withIdentifier							  		# WithId
    | (WithLPAREN withExpression WithRPAREN)      		# ParenWith
    | WithNOT withExpression						  	# NotWith
	| withExpression WithAND withExpression 			# AndWith
	| withExpression WithOR withExpression 				# OrWith
	;
	
withIdentifier
	: WithID (WithDOT WithID)*
	;

thenStatement
	: THEN thenExpression (ThenEOL|EOF)
	;

thenExpression
	: thenIdentifier							  		# thenId
    | (ThenLPAREN thenExpression ThenRPAREN)      		# ParenThen
    | ThenNOT thenExpression						  	# NotThen
	| thenExpression ThenAND thenExpression 			# AndThen
	| thenExpression ThenOR thenExpression 				# OrThen
	;
	
thenIdentifier
	: ThenID (ThenDOT ThenID)*
	;

sendStatement
	: SEND sendExpression (SendEOL|EOF)
	;

sendExpression
	: sendIdentifier
	;
	
sendIdentifier
	: SendID (SendDOT SendID)*
	;

// At statement
dateRange
	: (FROM fromDate)? (THROUGH toDate)?
	;
	
fromDate
	: specificDate;
toDate
	: specificDate;

dayExpression
	: durationExpression? specificTimeExpression
	;

durationExpression
	: quantity timeUnitExpression relativeTimeExpression 
	;

quantity
	: AtINT
	;
	
timeUnitExpression
	: HOUR
	| MINUTE
	;
	
relativeTimeExpression
	: BEFORE
	| AFTER
	;

specificTimeExpression
	: time
	| timeShortcut
	;

timeShortcut
	: SUNRISE 
	| SUNSET
	| MIDNIGHT
	| NOON
	;
time
	: hr=AtINT COLON mi=AtINT ap=AMPM
	;
	
dateExpression
	: ATON (date)+
	;
	
specificDate
	: month day (COMMA year)?
	;
		
date
	: dow
	| season
	| holiday
	| specificDate
	;		

year
	: AtINT
	;

month
	: MONTH
	;
dow
	: DOW;

holiday
	: CHRISTMAS
	;
		
day
	: AtINT
	;

season
	: SEASON
	;
	
