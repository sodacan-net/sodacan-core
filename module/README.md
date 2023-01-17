# Model Structure
The POJOs in this project comprise the abstract syntax tree (AST) of a SodaCan module. This structure, created by the compiler, is then used by the runtime system to "execute" a module.

It also has the classes that represent variables, including a class that holds all variables for a module/instance.
 
Most objects in this project have no external dependencies. They can be serialized to JSON for convenient storage (persistence).
