
# Sodacan Control (SCC)
A declarative language for controlling things. 
The goal of this language is to be concise and and to the extent possible declarative. 
## General lexical rules
The language hierarchy is relatively shallow. While tabs and whitespace improve human readability, they are ignored in the language.
### Line oriented
SCC is line oriented. One statement per line.
### Case Insensitive
In most examples, key words are capitalized. This is simply a convention to aid readability.
### Reserved
The following are globally reserved words:

 | Word        | Description |
 | ----------- | ----------- |
 | MODULE      | Title       |
 | AT          | Text        |

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