grammar Language;

prog:	units  EOF ;

units
	: unit*
	;
	
unit
	: UNIT ID (LIKE ID)? declaration* statement*
	;

declaration
	: EVENT events								# EventStatement
	| STATE states								# StateStatement
	; 

events
	: event (',' event)*
	;

event
	: ID
	;

states
	: state (',' state)*
	;

state
	: ID
	;	

statement
	: WHEN whenExpression (THEN thenExpression)*		# WhenStatement
	;

whenExpression
	: whenIdentifier							  # whenId
    | (LPAREN whenExpression RPAREN)              # ParenWhen
    | NOT whenExpression						  # NotWhen
	| whenExpression (op=(AND|OR)) whenExpression # AndOrWhen
	;

whenIdentifier
	: ID '.' ID ('.' ID)?
	;

thenExpression
	: thenIdentifier
	;
	
thenIdentifier
	: ID '.' ID
	;

expression
    : ID		                                # VariableExpr
    | atom										# AtomExpr
    | (LPAREN thenExpression RPAREN)                # ParenExpr
  	| thenExpression op=(MUL|DIV|MOD) thenExpression    # MulDivExpr
    | thenExpression op=(ADD|SUB) thenExpression        # AddSubExpr
    | thenExpression EQUALS thenExpression              # EqualsExpr
    | thenExpression ASSIGN thenExpression              # AssignExpr
    ;

atom
	: INT			# IntegerLiteral
	| STRING		# StringLiteral
	| TRUE  		# TrueKeyword
	| FALSE 		# FalseKeyword
	;

COMMENT : '//' ~[\r\n]* '\r'? '\n' -> skip ;
PROP: 'PROPERTY';
UNIT: 'UNIT';
LIKE: 'LIKE';
NOT: 'NOT';
TRUE: 'true';
FALSE: 'false';
AND: 'AND';
OR: 'OR';
EVENT: 'EVENT';
STATE: 'STATE';
WHEN: 'WHEN';
THEN: 'THEN';
ADD: '+';
SUB: '-';
MUL: '*';
MOD: '%';
DIV: '/';
EQUALS: '==';
ASSIGN: '=';
LPAREN: '(';
RPAREN: ')';
INT     : [0-9]+ ;
ID      : [a-zA-Z][a-zA-Z0-9]* ;
EOL	    : ';';
STRING:  '"' ~["\\\r\n]* '"';
NEWLINE : [\n\t\r ]+ -> skip;
