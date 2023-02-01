parser grammar SccParser;

options { tokenVocab = SccLexer; }
scc
	:  module EOF
	;

module
	: MODULE moduleName statements 
	;

moduleName
	: name=ID moduleInstance? moduleAffinity? EOL+
	;
	
moduleInstance
	: LBRACKET name=ID RBRACKET
	;

moduleAffinity
	: AGENT name=ID
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
	: TIMER variableDef EOL+
	;
	
atStatement
	: AT atTimeExpression atDateExpression AtEOL+ andStatement* thenStatement+
	;

onStatement
	: ON eventCondition EOL+  andStatement* thenStatement+
	;

ifStatement
	: IF expr EOL+  andStatement* thenStatement+
	;
	
andStatement
	: AND expr EOL+
	;

thenStatement
	: THEN thenExpr EOL+
	;

// Event is limited to inbound messages (variables).
	
eventCondition
	: ID (DOT ID)*
	;
		
// Define a variable used by PUBLISH SUBSCRIBE TOPIC and PRIVATE
variableDef
	: identifier instance? alias? constraintExpression? initialValue?
	;
	
identifier
	: ID (DOT ID)*		# FullId
	;

idRef
	: ID (DOT ID)* (HASH ID)?
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
	| string
	| constraintIdentifier
	;

constraintIdentifier
	: ID
	;
numberRange
	: number MINUS number				# FullRange
	| MINUS number						# HighRange
	| number MINUS						# LowRange
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
	: AtSUNRISE 	#AtSunrise
	| AtSUNSET		#AtSunset
	| AtMIDNIGHT	#AtMidnight
	| AtNOON		#AtNoon
	;
	
atTime
	: hr=AtINT AtCOLON mi=AtINT ap=AtAMPM
	;
	
atDateExpression
	: atDateSpecs*
	;
	
atDateSpecs
	: AtON atOnSpec+									#AtOn
	| AtIN atInSpec+									#AtIn
	| AtSTARTING atFullDate								#AtStarting
	| AtENDING atFullDate								#AtEnding
	| AtFROM atOnAnnualDate AtTHROUGH atOnAnnualDate	#AtFrom
	;

atOnSpec
	: atOnDaysOfWeek		
	| atOnAnnualDate
	;

atInSpec
	: atInMonth
	| atInSeason
	| atInYear
	;
	
atOnDaysOfWeek
	: atDow+
	;
	
atOnDate
	: AtON atFullDate+
	;

atOnAnnualDate
	: atMonth atDay?
	;
			
atFullDate
	: atMonth atDay AtCOMMA atYear
	;		

atInYear
	: AtINT
	;
atYear
	: AtINT
	;

atInMonth
	: AtMONTH
	;
atMonth
	: AtMONTH
	;

atDow
	: AtDOW
	;

atHoliday
	: AtCHRISTMAS
	;

atDay
	: AtINT
	;

atInSeason
	: AtSEASON
	;
atSeason
	: AtSEASON
	;
	
