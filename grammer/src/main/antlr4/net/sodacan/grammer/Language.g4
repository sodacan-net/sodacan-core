grammar Language;

prog:	units  EOF ;

units
	: unit*
	;
	
unit
	: UNIT ID (LIKE ID)? declaration* statement*
	;

declaration
	: DEFINE ID (constraints)			# DefineStatement
	; 

constraints
	: enumeration					# enumerationConstraint
	| numericRange					# rangeContstraint
	|								# nullContstraint
	;
	
enumeration
	: '{' ID (',' ID)* '}'
	;

numericRange
	: '{' REAL '-' REAL '}'
	;

statement
	: WHEN whenExpression (THEN thenExpression)?	# WhenStatement
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
	: simple
	| expression (';' expression)*
	;
	
simple
	: ID '.' ID
	;
	
expression
    : atom												# AtomExpr
    | ID		          								# VariableExpr
    | ID LPAREN parameterList? RPAREN				# FunctionExpr
	| (LPAREN expression RPAREN)  		 				# ParenExpr
	| expression op=(MUL|DIV|MOD) expression			# MulDivExpr
    | expression op=(ADD|SUB) expression		        # AddSubExpr
    | expression EQUALS expression						# EqualsExpr
    | ID ASSIGN expression							# AssignExpr
	;

parameterList
	: expression (',' expression)*
	;
		
atom
	: INT			# IntegerLiteral
	| STRING		# StringLiteral
	| TRUE  		# TrueKeyword
	| FALSE 		# FalseKeyword
	;

COMMENT : '//' ~[\r\n]* '\r'? '\n' -> skip ;
DEFINE: 'DEFINE';
PROP: 'PROPERTY';
UNIT: 'UNIT';
LIKE: 'LIKE';
NOT: 'NOT';
TRUE: 'true';
FALSE: 'false';
AND: 'AND';
OR: 'OR';
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
REAL	: [0-9]+ ('.' [0-9]+) ;
ID      : [a-zA-Z][a-zA-Z0-9]* ;
EOL	    : ';';
STRING:  '"' ~["\\\r\n]* '"';
NEWLINE : [\n\t\r ]+ -> skip;
