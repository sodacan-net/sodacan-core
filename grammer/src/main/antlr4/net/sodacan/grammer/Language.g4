grammar Language;

prog:	props units  EOF ;

props
	: prop*							# PropertyList
	;

prop
	: PROP ID expression			# PropertyDeclaration
	;
	
units
	: unit*
	;
	
unit
	: UNIT ID (LIKE ID)? statements?
	;

statements
	: statement+							# StatementList
	;
	
statement
	: EVENT events						# EventStatement
	| STATE states						# StateStatement
	| ID ASSIGN expression EOL				# AssignStatement
	| expression EOL						# ExpressionStatement
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

expression
    : ID		                                # VariableExpr
    | atom										# AtomExpr
    | (LPAREN expression RPAREN)                # ParenExpr
  	| expression op=(MUL|DIV|MOD) expression    # MulDivExpr
    | expression op=(ADD|SUB) expression        # AddSubExpr
    | expression EQUALS expression              # EqualsExpr
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
TRUE: 'true';
FALSE: 'false';
EVENT: 'event';
STATE: 'state';
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
