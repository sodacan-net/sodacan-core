# Sodacan Agent
The Sodacan agent provides a framework around the execution of one or more  `modules`. 

The Sodacan agent is mostly a thin wrapper around the Sodacan runtime. It manages the top-level list of modules handled by that agent and creates the thread used by each module.

As with other aspects of Sodacan, modules are organized by *mode* in an agent. So, it is possible that one agent is hosting the same module in tw different modes. One being completely independent from the other, including, possibly, different versions of the module in each mode.