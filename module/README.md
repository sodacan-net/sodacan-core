# Model Structure
The POJOs in this project comprise the abstract syntax tree (AST) of a Sodacan module. This structure, created by the compiler, is then used by the runtime system to "execute" a module.

It also has the classes that represent variables, including a class that holds all variables for a module/instance. However, the module does not actually hold the variables. They, along with the appropriate clock are held in an associated collection in the runtime. This is necessary because the module structure can change from one cycle to the next.
 
Most objects in this project have no external dependencies. They can be serialized to JSON for convenient storage (persistence).
