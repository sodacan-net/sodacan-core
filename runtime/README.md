# SodaCan Runtime
This project deals with execution of compiled modules. The runtime is given a module structure and executes it is not thread safe for of course and agent can run more than one module in separate threads.

This project has no direct storage or communication capability. One consumed message is passed to the "cycle" method and a collection of zero or more message structures are returned at the completion of a cycle, ready for publication. The SodaCan runtime also provides a clock and timer functions which use pseudo messages to communicate with the cycle execution.
