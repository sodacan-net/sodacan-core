# Model Structure
The POJOs in this project comprise the abstract syntax tree of a SodaCan module. This structure, created by the compiler, is then used by the runtime system to "execute" a module.

Most objects in this project have no external dependencies. They can be serialized to JSON for convenient storage (persistence).
