parser grammar SccParser;

options { tokenVocab = SccLexer; }
start
	:  module EOF
	;

module
	: MODULE moduleName statements 
	;

moduleName
	: name=ID moduleInstance? EOL+
	;
	
moduleInstance
	: LBRACKET ID RBRACKET
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
	: TOPIC variableDef EOL+
	;

publishStatement
	: PUBLISH variableDef EOL+
	;

subscribeStatement
	: SUBSCRIBE variableDef EOL+
	;

privateStatement
	: PRIVATE variableDef EOL+
	;

timerStatement
	: TIMER identifier instance? EOL+
	;
	
atStatement
	: AT atTimeExpression atDateExpression? atDateRange AtEOL+ andStatement? thenStatement+
	;

onStatement
	: ON event EOL+  andStatement? thenStatement+
	;

andStatement
	: AND condition EOL+
	;

thenStatement
	: THEN thenExpression EOL+
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
	: identifier instance? alias? constraintExpression? initialValue?
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

constraintExpression
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
	:rhsExpression
	;
	
rhsExpression
    : rhsExpression op=(AND|OR) rhsExpression 		# AndOr
    | LPAREN rhsExpression RPAREN	      			# Paren
	| identifier							  		# Id
	| NOT rhsExpression						  		# Not
    | MINUS rhsExpression						  	# Minus
	| rhsExpression op=(MUL|DIV) rhsExpression 		# MulDiv
	| rhsExpression op=(PLUS|MINUS) rhsExpression 	# AddSub
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
	: AtHOUR
	| AtMINUTE
	;
	
atRelativeTimeExpression
	: AtBEFORE
	| AtAFTER
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
	
