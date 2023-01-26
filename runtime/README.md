# SodaCan Runtime
This runtime deals with execution of compiled modules. The runtime is given a module structure and executes it. It is not thread safe because it doesn't need to be: messages (in and out) and time events are serialized. An agent can run more than one module, each module is in a separate thread.

The runtime has no direct storage or communication capability. All IO is handled by registered adapters to/from messaging. 

One consumed message is passed to the "cycle" method and a collection of zero or more message structures are returned at the completion of a cycle, ready for publication. The SodaCan runtime also provides a clock and timer functions which use pseudo messages to communicate with the cycle execution.

The runtime clock can either be the real system clock or, for testing purposes, a "manual" clock can be activated. Clocks, as with messages, are partitioned by "mode" which means an agent may be operating both kinds of clocks at the same.

### Compiled Code
The runtime compiles source code received via message. Updated module source code may arrive at anytime and the code is interleaved with other messages. The compiled module is the basis for the runtime execution. (See <a href="../module/README.md">Module</a> along with the optional variables structure containing the list of variables.

### Plugable IO
The runtime does no input or output. Everything that it needs to function is passed in (or out, typically by listeners registered in the runtime). For example, the current state of a module's variables can be accessed for saving state. The runtime will make a callout to a registered pluging that can decide when and how to persist state for the module.

### Temporal Serialization
Under normal operation, the runtime introduces a clock "tick" into the modules it maintains. This allows time-based events to occur. These ticks are interleaved with data messages based on the timestamp of the message and the time of a clock tick. 

The date/time criteria in a module is based on one or more points in time. This is important. If a module says turn the lights on at noon, then there is an assumption that at some point, a "tick" will represent noon. If not, then as far as the module is concerned, noon never happened. The runtime system ensures that only exactly one per minute is delivered to the module. Even if the system falls behind (busy, down, etc), it will still "catch up" as soon as possible.

Whenever the runtime detects that it is catching up, it still sends *all* of the missing ticks and at the same time, holds any outbound messages. Once time is caught up, the messages are released. This is not much different from normal operation: during one cycle, the runtime only makes a note of PUBLISH variables that have changed. Even if the module makes several changes to a PUBLISH variable, only the final state of that variable is published. So, the same thing happens during a catch-up: all of the ticks, and messages, during the catch-up are processed by the module and then the final value of PUBLISH variables is sent.

Note: The ticks are not actually stored as messages. The runtime handles the interleaving of ticks and messages.

### Message processing model
The runtime "pulls" messages. That is, when the runtime is ready to receive a message, it asks (via callout). The runtime has no ability to buffer messages. There is no method to pass in a message for processing.
