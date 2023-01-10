
# Sodacan Control (SCC)
A declarative language for controlling things. 
The goal of this language is to be concise and and to the extent possible declarative. 
## General lexical rules
The language hierarchy is relatively shallow. While tabs and whitespace improve human readability, they are ignored in the language.
### Line oriented
SCC is line oriented. One statement per line.
### Case Insensitive
In most examples, key words are capitalized. This is simply a convention to aid readability. Statements and other reserved word match regardless of case.
``` 
	THEN
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

Quoted strings, of course, retain the case as specified in the string. A string literal
```
	"Any string in a storm"
```
### Literal values and variables
Enumerations are the most commonly used literals used to constrain a variable. For example:
```	
	PUBLIC state {off,on}
```
describes a variable named 'state' and it has two possible values, `on` and `off`.
The braces are what identify the declaration of a variable as having having enumerated values.

An decimal variable is declared in either of these forms:
```	
PUBLIC count 0
PUBLIC price 0.00
```
with the `0` or `0.0` being the initial value of the variable.

Date and time literals are more complicated and are covered in a separate section, below.

A string literal contains any alphanumeric characters. A variable containing a string must 
be initialized with a string literal, even if the string literal is empty:
```
	PUBLIC myName "John"
```

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

### Modules
A module is, in essence, a way of specifying an intermediate name space. The next section covers the details of naming and scope.

Unlike most other areas of the code, module names are **case sensitive**.

The controller scans the directory or directories specified in the configuration for one or more `module` files with an extension of `.scc`. The name of the module must match the file name (not including the `.scc`). For example, if the module file is named `myModule.scc` then the module should have the name.
```
module myModule
```
### Naming
Every item in a module has a name. For example, the `test` module has a variable named `level`. 
```
MODULE test
	PUBLIC level 0.0
	...
```
Surrounding all of this is a domain name that names the system containing the modules. This name is used as a namespace name, not a specific bit of hardware or software. However, it can be used to name a messaging broker which does usually run on a single computer or a cluster of computer. 

 | Scope        | Description |
 | ----------- | ----------- |
 | domain      | A server or cluster of servers       |
 | module      | A module       |
  | variable name      | An individual variable within a module       |
 
The domain scope is intended to be globally unique. However, in a small setup, this requirement can be relaxed so that the domain is something simple like `MyHouse`. 
Such a simple name would be inappropriate if, for example, two houses (properties) could communicate with each other (for example, property owner A knows if property owner B's garage door is open or not and if so to delay turning on his lawn sprinklers).

The module scope means any variable contained in that module is implicitly named by the module. So, a variable such as `state` within the `lamp1` module which is part of the `net.sodacan.scc` domain has the full, globally unique, name of `net.sodacan.scc.lamp1.state`.

Within the `lamp1` module, the name `state` is sufficient. And, within the `net.sodan.scc` domain, `lamp1.state` is sufficient. In fact, the domain should only be used sparingly. The system will add and remove the outer name scopes as needed. Of course if within a module you subscribe to a variable outside of your local domain, the the full name must be specified.
```
	SUBSCRIBE my.neighor.garageDoor.state {on,off}
```
Notice that the enumeration constraint is specified on the subscription above even though it is your neighbor that defines the state variable. This restatement is used to help ensure that the module is correct during compilation.
