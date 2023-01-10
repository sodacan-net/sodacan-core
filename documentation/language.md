
# Sodacan Control (SCC)
A declarative language for controlling things. 
The goal of this language is to be concise and and to the extent possible declarative. 
## General lexical rules
The language hierarchy is relatively shallow. While tabs and whitespace improve human readability, they are ignored in the language.
### Line oriented
SCC is line oriented. One statement per line.
### Case Insensitive
In most examples, key words are capitalized. This is simply a convention to aid readability. Statements and other reserved word match regardless of case.
``` THEN
	then
	Then
``` 
All mean the same thing. Likewise, identifiers are case insensitive
```LOCAL myIdentifer
   ON myIdentifier
   ON MYIDENTIFIER
   ON myidentifier
```
Are equivalent.
Quotes strings are, of course, retain the case as specified in the string.
### Literal values
Enumerations are the most commonly used variables. For example:
```	PUBLIC state {off,on}
```
describes a variable named 'state' and it has two possible values, `on` and `off`.
The braces are what alert the declaration of an enumeration variable.

An decimal variable is declared in either of these form:
```	PUBLIC count 0
```	PUBLIC price 0.00
```
with the `0` or `0.00` being the initial value of the variable.


### Reserved Words
The following are reserved and recognized at the beginning of a line. Elsewhere, they are allowed as identifiers:

 | Word        | Description |
 | ----------- | ----------- |
 | MODULE      | Defines a module       |
 | AT          | Activates when the specified time occurs        |
 | PUBLIC | Declare a variable that will be published as its value changes.|
 | PRIVATE | Declare a variable that is only visible within a module |
 | ON | Activates when a variable (PUBLIC or PRIVATE) changes |
 | SUBSCRIBE | Declares an interest in messages from outside the module|
 
### Comments
Comments begin with // and continue to the end of line
```// A Comment
	AT noon // Another comment
	// AT midnight // This line of code is "commented out"
```

## Modules
The controller scans the directory or directories specified in the configuration for one or more `module` files with an extension of `.scc`. The name of the module must match the file name (not including the `.scc`). For example, if the module file is named `myModule.scc` then the module should have the name
```module myModule
```
Unlike most other areas of the code, module names are **case sensitive**.

```// Comment
MODULE test
	SUBSCRIBE ns.module.mode {on,off} as <simpleName>	// ns assumed to be us if not specified
	PUBLIC state {off,on}
	PRIVATE count 0
	PRIVATE price 0.95
	ON price
	  WHEN 
	ON mode.on
	ON mode.off
	  THEN state.off
	AT 10 minutes before sunset
	  THEN blow.nose

```