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
	| atStatement
	| onStatement
	;
// Statements, which start with a key word and end at the end of line (or EOF)	
topicStatement
	: TOPIC variableDef (EOL|EOF)
	;

publishStatement
	: PUBLISH variableDef (EOL|EOF)
	;

subscribeStatement
	: SUBSCRIBE variableDef (EOL|EOF)
	;

privateStatement
	: PRIVATE variableDef (EOL|EOF)
	;

timerStatement
	: TIMER identifier instance? (EOL|EOF)
	;
	
atStatement
	: AT atTimeExpression atDateExpression? atDateRange (AtEOL|EOF) andStatement? (thenStatement)+
	;

onStatement
	: ON event (OnEOL|EOF)  andStatement? (thenStatement)+
	;

andStatement
	: AND condition (WithEOL|EOF)
	;

thenStatement
	: THEN thenExpression (ThenEOL|EOF)
	;

// Event is limited to inbound messages (variables). The 
event
	: aliasName (eventCondition)?
	;
	
eventCondition
	: EQ condition				#Equality	// a == b
	| DOT ID					#Dot		// a.b
	;
		
// Define a variable used by PUBLISH SUBSCRIBE TOPIC and PRIVATE
variableDef
	: identifier instance? alias? constraint? initialValue?
	;
	
identifier
	: ID (DOT ID)*
	;

alias
	: AS aliasName
	;
	
aliasName
	: ID
	;
		
instance
	: LBRACKET name=ID RBRACKET
	;

initialValue
	: EQ rhsExpression
	;

constraint
	: LBRACE constraintList RBRACE
	;

constraintList
	: constraint (COMMA constraint)*
	;

constraint
	: numberRange
	| number
	| id=ID
	| STRING
	;

numberRange
	: number MINUS number
	;
	
literal
	: number
	| string
	| bool
	| ID
	;
	
number		// The scale of the decimal here is also the scale of the variable when the initial value is set
	: sign? INT (DOT INT)?
	;

sign
	: PLUS
	| MINUS
	;
	
bool
	: TRUE
	| FALSE
	;

string
	: STRING
	;

function
	: identifier LPAREN parameterList? RPAREN
	;
	
parameterList
	: rhsExpression (COMMA rhsExpression)*
	;
	
condition
    : NOT rhsExpression						  		# Not
	| rhsExpression op=(AND|OR) rhsExpression 		# AndOr
    | LPAREN rhsExpression RPAREN	      			# Paren
	| identifier							  		# Id
	;
	
rhsExpression
    : MINUS rhsExpression						  	# Minus
	| rhsExpression op=(MUL|DIV) rhsExpression 		# MulDiv
	| rhsExpression op=(ADD|SUB) rhsExpression 		# AddSub
	| condition 									# Condition
	;

thenExpression
	: rhsExpression
	| function
	;
	
// At statement elements from this point down
atDateRange
	: (AtFROM atFromDate)? (AtTHROUGH atToDate)?
	;
	
atFromDate
	: atSpecificDate
	;
	
atToDate
	: atSpecificDate
	;

atTimeExpression
	: atOffsetExpression? atSpecificTimeExpression
	;

atOffsetExpression
	: atQantity atTimeUnitExpression atRelativeTimeExpression 
	;

atQantity
	: AtINT
	;
	
atTimeUnitExpression
	: HOUR
	| MINUTE
	;
	
atRelativeTimeExpression
	: BEFORE
	| AFTER
	;

atSpecificTimeExpression
	: atTime
	| atTimeShortcut
	;

atTimeShortcut
	: AtSUNRISE 
	| AtSUNSET
	| AtMIDNIGHT
	| AtNOON
	;
atTime
	: hr=AtINT AtCOLON mi=AtINT ap=AtAMPM
	;
	
atDateExpression
	: ON (atDate)+
	;
	
atDate
	: atDow
	| atSeason
	| atHoliday
	| atSpecificDate
	;

atSpecificDate
	: atMonth atDay (AtCOMMA atYear)?
	;		

atYear
	: AtINT
	;

atMonth
	: AtMONTH
	;

atDow
	: AtDOW;

atHoliday
	: AtCHRISTMAS
	;

atDay
	: AtINT
	;

atSeason
	: AtSEASON
	;
	
