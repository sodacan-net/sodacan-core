# SodaCan Runtime
This runtime deals with execution of compiled modules. The runtime is given a module structure and executes it. This aspect of the runtime is *not* thread safe because it doesn't need to be: messages (in and out) and time events are serialized. An agent can run more than one module, each module is in a separate thread.

The runtime has no direct storage or communication capability. All IO is handled by registered plugins. 

One consumed message is passed to the "cycle" method and a collection of zero or more message structures are published at the completion of a cycle, ready for publication. The SodaCan runtime also provides a clock and timer functions which use pseudo messages to communicate with the cycle execution.

The runtime clock can either be the real system clock or, for testing purposes, a "manual" clock can be activated. Clocks, as with messages, are partitioned by "mode" which means an agent may be operating both kinds of clocks at the same. And maybe several different copies of the clock at the same time. For this reason, there is no single master clock in Sodacan. Each instance of a module has it's own clock.

If your application has the ability to send or receive Kafka, MQTT, or REST calls, then it may not require any connection whatsoever to a Sodacan agent or runtime whatsoever. For example, a button tied to an ESP8266 microcontroller can easily send button events, after debounce, through the Sodacan REST API. Simple. reliable. Using the well understood HTTP protocol. Complete isolation from Sodacan.

If the state of a module was changed in any way, then the state-store method is called to store a snapshot of the module's variables to a separate Kafka topic that, should the need arise, can be quickly replayed to recover the state of the module. This special topic is aggressively compacted so that, in many cases, only the most recent one or two snapshots are available. This keeps a restart fast.
### Compiled Code
The runtime compiles source code received via message. Updated module source code may arrive at any time and the code-carrying message is interleaved with other messages. The compiled module is the basis for the runtime execution. (See <a href="../module/README.md">Module</a> along with the optional variables structure containing the list of variables.

### Plugable IO
The runtime does no input or output. Everything that it needs to function is passed in (or out, typically by listeners registered in the runtime). For example, the current state of a module's variables can be accessed for saving state. The runtime will make a callout to a registered pluging that can decide when and how to persist state for the module.

### Mode and Plugins
Sodacan maintains a mode during processing of messages. Mode is a very strong partitioning in Sodacan, and most that partitioning is enforced in this `runtime` component. In Java terms, the Mode class sits between `sodacan-core` code and the service providers that accompany the core code.

The Mode project goes into detail about how modes work in Sodacan.

### Temporal Serialization
Under normal operation, the runtime introduces a clock "tick" into the modules it maintains. This allows time-based events to occur. These ticks are interleaved with data messages based on the timestamp of the message and the time of a clock tick. 

The date/time criteria in a module is based on one or more points in time. This is important. If a module says turn the lights on at noon, then there is an assumption that at some point, a "tick" will represent noon. If not, then as far as the module is concerned, noon never happened. The runtime system ensures that only exactly one per minute is delivered to the module. Even if the system falls behind (busy, down, etc), it will still "catch up" as soon as possible.

Whenever the runtime detects that it is catching up, it still sends *all* of the missing ticks and at the same time, holds any outbound messages. Once time is caught up, the messages are released. This is not much different from normal operation: during one cycle, the runtime only makes a note of PUBLISH variables that have changed. Even if the module makes several changes to a PUBLISH variable, only the final state of that variable is published. So, the same thing happens during a catch-up: all of the ticks, and messages, during the catch-up are processed by the module and then the final value of PUBLISH variables is sent.

Note: The ticks are not actually stored as messages. The runtime handles the interleaving of ticks and messages.

### Message processing model - Cycle
The runtime "pulls" messages. That is, when the runtime is ready to receive a message, it asks (via callout). The runtime has no ability to buffer messages. There is no method to pass a message from service provider to the sodacan-runtime for processing.

The service provider can block for IO if it wants to. For example, it may be sometime before a message arrives to a Sodacan inbound service. During the wait, the module remains active, processing time-based actions as needed. And, in the case where more than one message source is provided for a module, the runtime will coordinate the calls so that even different service providers can block on IO. And the runtime will serialize the messages so that the module sees them one-at-a-time.

A note about Kafka: The Kafka consumer client is poll-based. This works nicely with the Sodacan runtime: When the runtime requests a message, the plugin initiates a Kafka poll request... with a timeout. If the poll times out, return to the runtime and it will call right back. If one or more messages are delivered by Kafka, the plugin will need to hold onto them in a queue and return one at a time to the runtime. Once the queue is empty, the plugin can return to polling. This interaction between Sodacan and the plugin is intended to be fast so that the Kafka consumer doesn't time out causing a rebalance.

As described above, when a module has out-of-date messages to process, the sending of outbound messages is delayed until the messages have caught up. So, should messages arrive too fast, the runtime will automatically go into catch-up mode which should help reduce the possibility of causing a system overload.

Another important point about how the runtime processes messages: A message is not allowed to get ahead of the "clock". This situation only occurs when testing, but it is useful. For example, if the clock is manually adjusted, only those messages that are older then the current time are processed.
In effect, slowing down the clock results in the message flow slowing down. Of course if a test moves the clock ahead by a large amount, there could be a large number messages ready to process quickly.

As noted elsewhere, the clock always advances through time, usually one minute at a time, even if that means running the many processing minutes within a wall-clock minute. The clock is not allowed to just skip ahead. This ensures that any time-based events are not lost, even during testing.
The clock does not have to actually wait a minute between time checks (ticks) when it's catching up.

