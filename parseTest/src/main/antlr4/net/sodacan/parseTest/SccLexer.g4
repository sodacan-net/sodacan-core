lexer grammar SccLexer;
options { caseInsensitive=true; }

MODULE
	: 'MODULE' -> pushMode(ModuleMode)
	;

TOPIC
	: 'TOPIC' -> pushMode(VarMode)
	;
		
PUBLIC
	: 'PUBLIC' -> pushMode(VarMode)
	;
		
PRIVATE
	: 'PRIVATE' -> pushMode(VarMode)
	;
		
TIMER
	: 'TIMER' -> pushMode(VarMode)
	;
	
SUBSCRIBE
	: 'SUBSCRIBE' -> pushMode(SubscribeMode)
	;

AT
	: 'AT' -> pushMode(AtMode)
	;

ON
	: 'ON' -> pushMode(OnMode)
	;
	
WITH
	: 'WITH' -> pushMode(WithMode)
	;
	
THEN
	: 'THEN' -> pushMode(ThenMode)
	;

SEND
	: 'SEND' -> pushMode(SendMode)
	;

INT
	: [0-9]+
	;
	
ID
	: [a-z][a-z0-9]* 
	;
	
COMMENT : '//' ~[\r\n]* '\r'? '\n' -> skip ;


WS
	: [ \t\r\n]+ -> skip
	;
	
mode VarMode;

VarCOMMA
	: ','
	;
	
VarLBRACE
	: '{'
	;
	
VarRBRACE
	: '}'
	;
	
VarTRUE
	: 'TRUE'
	;
	
VarFALSE
	: 'FALSE'
	;

VarAS
	: 'AS'
	;

VarEVENT
	: 'EVENT'
	;
	
VarINT
	: [0-9]+
	;
		
VarID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

VarCOMMENT
	: '//' ~[\n]* -> skip
	;

VarEOL
	: [\n] -> popMode
	;
	
VarWS
	: [ \t\r]+ -> skip
	;


mode ModuleMode;

ModID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

ModCOMMENT
	: '//' ~[\n]* -> skip
	;

ModEOL
	: [\n] -> popMode
	;
	
ModWS
	: [ \t\r]+ -> skip
	;

mode SubscribeMode;
SubDOT
	: '.'
	;

SubID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

SubCOMMENT
	: '//' ~[\n]* -> skip
	;

SubEOL
	: [\n] -> popMode
	;
	
SubWS
	: [ \t\r]+ -> skip
	;
	
mode AtMode;

MONTH
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
AMPM
	: 'am'
	| 'pm'
	;
DOW
	: 'monday'
	| 'tuesday'
	| 'wednesday'
	| 'thursday'
	| 'friday'
	| 'saturday'
	| 'sunday'
	| 'weekends'
	| 'weekdays'
	;

SUNRISE
	: 'sunrise'
	;
SUNSET
	: 'sunset'
	;

SEASON
	: 'winter'
	| 'spring'
	| 'summer'
	| 'fall'
	| 'autumn'
	;

FROM
	: 'FROM'
	;

THROUGH
	: 'THROUGH'
	;	

HOUR
	: 'hours'
	| 'hour'
	;
	
MINUTE
	: 'minutes'
	| 'minute'
	;
BEFORE
	: 'before'
	;
AFTER
	: 'after'
	;
CHRISTMAS
	: 'christmas'
	| 'xmas'
	;

MIDNIGHT
	: 'midnight'
	;

NOON
	: 'noon'
	;

ATON
	: 'ON'
	| 'IN'
	;

COMMA
	: ','
	;
		

SEMICOLON
	: ';' // -> popMode
	;

COLON
	: ':'
	;
	
AtINT
	: [0-9]+
	;
	
AtCOMMENT
	: '//' ~[\n]* -> skip
	;

AtEOL
	: [\n] -> popMode
	;
	
AtWS
	: [ \t\r]+ -> skip
	;

mode OnMode;
OnDOT
	: '.'
	;

OnID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

OnCOMMENT
	: '//' ~[\n]* -> skip
	;

OnEOL
	: [\n] -> popMode
	;
	
OnWS
	: [ \t\r]+ -> skip
	;

mode WithMode;
WithLPAREN
	: '('
	;
	
WithRPAREN
	: ')'
	;
WithAND
	:'AND'
	;
WithOR
	:'OR'
	;
WithNOT
	: 'NOT' 
	;
WithDOT
	: '.'
	;

WithID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

WithCOMMENT
	: '//' ~[\n]* -> skip
	;

WithEOL
	: [\n] -> popMode
	;
	
WithWS
	: [ \t\r]+ -> skip
	;
mode ThenMode;
ThenLPAREN
	: '('
	;
	
ThenRPAREN
	: ')'
	;
ThenAND
	:'AND'
	;
ThenOR
	:'OR'
	;
ThenNOT
	: 'NOT' 
	;
ThenDOT
	: '.'
	;

ThenID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

ThenCOMMENT
	: '//' ~[\n]* -> skip
	;

ThenEOL
	: [\n] -> popMode
	;
	
ThenWS
	: [ \t\r]+ -> skip
	;
mode SendMode;
SendDOT
	: '.'
	;

SendID
	: [a-z][a-z0-9]* 
//	: [a-z][a-zA-Z0-9]* 
	;

SendCOMMENT
	: '//' ~[\n]* -> skip
	;

SendEOL
	: [\n] -> popMode
	;
	
SendWS
	: [ \t\r]+ -> skip
	;
	