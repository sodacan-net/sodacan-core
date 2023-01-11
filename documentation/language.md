
# Sodacan Control (SCC)
A declarative language for controlling things. 
The goal of this language is to be concise and to the extent possible declarative. 
## General lexical rules
The SCC language is relatively simple. In fact, it is designed to be used by non-programmers. While tabs and whitespace improve human readability, they are ignored in the language. Should more complicated logic be needed, other languages such as Java or C++ can be used.  
Very few reserved words are needed for any particular statement. Each statement will be described below.
SCC is line oriented with one statement per line.
### Case Insensitive
In most examples, key words are capitalized. This is simply a convention to aid readability. Statements, identifiers and other reserved words match regardless of case.
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

A boolean variable is similar to an enumeration with the possible values limited to `true` and `false`.

```	
	PUBLIC available {false,true}
```

Date and time literals are more complicated and are covered in a separate section, below.

A string literal contains any alphanumeric characters. A variable containing a string must 
be initialized with a string literal, even if the string literal is empty:
```
	PUBLIC myName "John"
```
Some variables have no value whatsoever. Such a variable is still quite useful. For example, if we declare a variable named
`bedtime`, the variable can be **activated**. In other words, "it's bedtime". This causes that variable to be published as a message so that other modules
can subscribe to it. Other than triggering an action, the message does not carry any other useful information.
```
	MODULE topics
		TOPIC myhouse.bedtime
```
A simple button can be declared the same way:
```
	MODULE livingRoomButton
		PUBLIC active
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
Notice that the enumeration constraint is specified on the subscription above even though it is your neighbor that defines the state variable. This restatement of the constraint is used to help ensure that the module is correct during compilation even if the neighbors system is not, yet, operational.

Two or more modules within the same domain, such as a lamp and the button that controls it can (and should) dispense with domain names completely.
In the following two modules, a message *from* button1 is subscribed to by the `lamp1` module. 

```
	MODULE lamp1
		SUBSCRIBE button1.press	// This subscribes to button1.press
		PUBLIC state
		ON button1.press
		  WHEN state==off
		  THEN state=on
		ON button1.press
		  WHEN state==on
		  THEN state=off

	MODULE button1
		PUBLIC press
		PRIVATE pin2
		ON pin2==true
		  THEN activate(press)	// This publishes button1.press
		...
		
```
### Asynchronous
In a request-response architecture, one module needing some bit of data typically issues a request and waits for a response. In SCC, this process is reversed by using a [publish-subscribe](https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern) approach.

In the previous example, notice that button1 makes no mention of lamp1. In other words, button1 only **published** information about the button press (the activate function call). But it doesn't put a destination on the message.  The name of the message is `ourDomain.button1.press`. The `lamp1` module then **subscribes** to the that message and thus responds to that message in the `ON` statement(s). The interesting part of this approach is that either of these modules could be written and tested before the other even exists. Furthermore, there is no technical constraint as to the mechanism used to deliver the message from the sending module to the receiving module. For example, the two modules could be on completely different computers. 

Any number of modules (including none) can subscribe to the same message and react how it sees fit. The following is a simple bedtime button setup. Any lamp participating in the bedtime event initiated by the bedtime button does something, usually to turn itself off although some could turn it on as well.

### Topics


```
	MODULE bedtime
		PUBLIC activate
		PRIVATE pin2
		ON pin2==true
		  THEN activate(activate)	// This publishes button1.press
		...
		
	MODULE lamp1
		SUBSCRIBE bedtime.activate	// This subscribes to button1.press
		PUBLIC state
		ON bedtime.activate
		  THEN state=off			// Turn off this light

		
```
Now, the name of `bedtime` module is a little restrictive. It assumes that there is only one source of the bedtime message which is in the bedtime(button) module. If we had a phone app that could initiate bedtime, we would want it to generate the same message. So, to make this setup more flexible we ignore the lamp1 module name and name a neutral `topic` which will carry the message. This is what the button module looks like now:

```
	MODULE bedtimeButton
		PUBLIC activate AS bedtime.activate
		PRIVATE pin2
		ON pin2==true
		  THEN activate(activate)	// This publishes button1.press
		...	
```
Notice that we changed the name of the module to be more descriptive ie `bedtimeButton` and modified 
the declaration of the activate variable to use the alias `AS bedtime.activate`.

It is important to point out a restriction in this setup: when a public variable uses the `AS` clause, 
it could, in theory, spoof another module. Or, it could name a topic that has not been defined. 
To prevent this from happening, a simple check is made that prevents an alias from naming an 
existing module other than itself. In the above example, there should be no module named 'bedtime' and there isn't 
but how can we be sure that `bedtime` is reserved so that a future module doesn't use that name?

We reserve the name (`bedtime` in this case) using a **TOPIC** definition. A topic is a channel for messages to
be exchanged. Each `PUBLIC` variable defines a topic and each `SUBSCRIBE` statement subscribes to a topic.
This new topic, `bedtime`, is defined explicitly in a `TOPIC` declaration. In most respects, it is the same as a `PUBLIC`
declaration. The main difference is that the name of a topic does not include the name of the module. By convention,
a module that defines one or more topics should not contain any other type of statement.

```
	MODULE topics
		TOPIC bedtime.activate
		TOPIC 
```

When a variable is published or subscribed to, SCC can validate that the name is contained in either a module name or a topic name.

