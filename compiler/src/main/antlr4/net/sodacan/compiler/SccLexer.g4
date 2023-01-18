lexer grammar SccLexer;
//options { caseInsensitive=true; }

MODULE
	: 'MODULE'
	;

TOPIC
	: 'TOPIC'
	;
		
PUBLISH
	: 'PUBLISH'
	;
		
SUBSCRIBE
	: 'SUBSCRIBE'
	;

PRIVATE
	: 'PRIVATE'
	;
		
TIMER
	: 'TIMER'
	;
	
AT
	: 'AT' -> pushMode(AtMode)
	;

ON
	: 'ON'
	;
	
THEN
	: 'THEN'
	;

TRUE
	: 'TRUE'
	;
	
FALSE
	: 'FALSE'
	;

NOT
	: 'NOT'
	;

AS
	: 'AS'
	;


COMMA
	: ','
	;

DECR
	: '--'
	;
	
MINUS
	: '-'
	;
	
INCR
	: '++'
	;
	
PLUS
	: '+'
	;

MUL
	: '*'
	;
	
DIV
	: '/'
	;

AND
	: 'AND'
	;

OR
	: 'OR'
	;

LE
	: '<='
	;

GE
	: '>='
	;

LT
	: '<'
	;

GT
	: '>'
	;

EQ
	: '=='
	;

NE
	: '!='
	;
	
DOT
	: '.'
	;
LPAREN
	: '('
	;
	
RPAREN
	: ')'
	;
	
LBRACE
	: '{'
	;
	
RBRACE
	: '}'
	;
	
LBRACKET
	: '['
	;

RBRACKET
	: ']'
	;

INT
	: [0-9]+
	;
		
ID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

STRING
	: '"' ~["\\\r\n]* '"'
	;

COMMENT
	: '//' ~[\r\n]* '\r'? '\n' -> skip
	;

EOL
	: '\n' 
	;
	
WS
	: [ \t\r]+ -> skip
	;

// AT statement has different syntax up until end of line
mode AtMode;

AtMONTH
	: 'January'
	| 'February'
	| 'March'
	| 'April'
	| 'May'
	| 'June'
	| 'July'
	| 'August'
	| 'September'
	| 'October'
	| 'November'
	| 'December'
	;

AtAMPM
	: 'am'
	| 'pm'
	;

AtDOW
	: 'Monday'
	| 'Tuesday'
	| 'Wednesday'
	| 'Thursday'
	| 'Friday'
	| 'Saturday'
	| 'Sunday'
	| 'weekends'
	| 'weekdays'
	;

AtSUNRISE
	: 'sunrise'
	;
AtSUNSET
	: 'sunset'
	;

AtSEASON
	: 'winter'
	| 'spring'
	| 'summer'
	| 'fall'
	| 'autumn'
	;

AtFROM
	: 'FROM'
	;

AtTHROUGH
	: 'THROUGH'
	;	

AtHOUR
	: 'hours'
	| 'hour'
	;
	
AtMINUTE
	: 'minutes'
	| 'minute'
	;
AtBEFORE
	: 'before'
	;
AtAFTER
	: 'after'
	;
AtCHRISTMAS
	: 'christmas'
	| 'xmas'
	;

AtMIDNIGHT
	: 'midnight'
	;

AtNOON
	: 'noon'
	;

AtON
	: 'ON'
	| 'IN'
	;

AtCOMMA
	: ','
	;
		

AtCOLON
	: ':'
	;
	
AtINT
	: [0-9]+
	;
	
AtCOMMENT
	: '//' ~[\r\n]* '\r'? '\n' -> skip
	;

AtEOL
	: '\n' -> popMode
	;
	
AtWS
	: [ \t\r]+ -> skip
	;
