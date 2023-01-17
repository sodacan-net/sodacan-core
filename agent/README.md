# SodaCan Agent
The SodaCan agent provides a framework around the execution of one or more  `modules`. It also is where custom or plugin code can be added to a module for specific kinds of interfaces.

An agent contains timer and clock capabilities used by modules. And, finally, it is an agent which subscribes and responds to inbound messages from the message bus. And it publishes messages at the conclusion of a "message cycle" through the runtime.

The actual processing of a message is done but the <a href="../runtime/README.md">Runtime</a> component.

