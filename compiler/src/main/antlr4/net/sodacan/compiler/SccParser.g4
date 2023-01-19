parser grammar SccParser;

options { tokenVocab = SccLexer; }
scc
	:  module EOF
	;

module
	: MODULE moduleName statements 
	;

moduleName
	: name=ID moduleInstance? EOL+
	;
	
moduleInstance
	: LBRACKET name=ID RBRACKET
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
	| ifStatement
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

ifStatement
	: IF expr EOL+  andStatement? thenStatement+
	;
	
andStatement
	: AND expr EOL+
	;

thenStatement
	: THEN thenExpr EOL+
	;

// Event is limited to inbound messages (variables). The 
event
	: aliasName (eventCondition)?
	;
	
eventCondition
	: EQ expr					#Equality	// a == b
	| DOT ID					#Dot		// a.b
	;
		
// Define a variable used by PUBLISH SUBSCRIBE TOPIC and PRIVATE
variableDef
	: identifier instance? alias? constraintExpression? initialValue?
	;
	
identifier
	: ID (DOT ID)*
	;

idRef
	: ID (DOT ID)?
	;

identifierFun
	: ID
	;

assignmentTarget
	: ID
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
	: EQ expr
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
	| STRING
	| constraintIdentifier
	;

constraintIdentifier
	: ID
	;
numberRange
	: number MINUS number
	;
	
literal
	: number
	| string
	| bool
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

thenExpr
	: expr								# Exp
	| idRef ASSIGN expr					# Ass
	;
		
expr
	: literal							# Lit
	| idRef								# Id
    | LPAREN expr RPAREN	      		# Paren
    | idRef LPAREN expr (COMMA expr)* RPAREN	# Fun
    | <assoc=right> PLUS expr			# UPlus
    | <assoc=right> MINUS expr			# UMinus
    | <assoc=right> NOT expr			# Not
	| expr PLUS expr 					# Add
	| expr MINUS expr					# Sub
	| expr MUL expr 					# Mul
	| expr DIV expr						# Div
	| expr LT expr						# Lt
	| expr LE expr						# Le
	| expr GT expr						# Gt
	| expr GE expr						# Ge
	| expr EQ expr						# Eq
	| expr NE expr						# Ne
	| expr AND expr						# And
	| expr OR expr						# Or
	;
		
// At (time and date) statement elements from this point down
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
	: AtON (atDate)+
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
	
