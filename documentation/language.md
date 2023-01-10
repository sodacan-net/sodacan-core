
# Sodacan Control (SCC)
A declarative language for controlling things. 
The goal of this language is to be concise and and to the extent possible declarative. 
## General lexical rules
The language hierarchy is relatively shallow. While tabs and whitespace improve human readability, they are ignored in the language.
### Line oriented
SCC is line oriented. One statement per line.
### Case Insensitive
In most examples, key words are capitalized. This is simply a convention to aid readability.
### Reserved Words
The following are reserved and recognized at the beginning of a line. Elsewhere, they are allowed as identifiers:

 | Word        | Description |
 | ----------- | ----------- |
 | MODULE      | Defines a module       |
 | AT          | Activates when the specified time occurs        |
 | PUBLIC | Declare a variable that will be published |
 | PRIVATE | Declare a variable that is not published |
 | ON | Activates when a variable changes |
 | SUBSCRIBE | Declares an interest in messages from outside the module|
 
### Comments
Comments begin with // and continue to the end of line


## Modules
The controller scans for one or more `module` files with an extension of `.scc`. The name of the module must match the file name (not including the `.scc`). For example, if the module file is named my

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