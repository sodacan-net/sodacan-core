Device Event Stream eg button push, video frame, level reading

Should we subscribe to events as well?

Also, make subscriptions a kafka msg. ie dynamic.

State update - process events, update state. process filters events its interested in.

State propogation - to  

State persistence

##Wiring
If one or more states are needed for a single event, then wire state subscriptions to the event sinc/rule. Think of this as a slow version of Drools - all data is transmitted to the front door of the rule, persistentely so that the rule only needs to respond to the data it has whenever state changes or an event is presented.

This results in a lot of message traffic but on the upside it is all very debuggable and modular (microservice).

A framework will make this easy. Each rule declares what state and events it needs and a callback gets the info. The callback specifies if the state is new or a replay due to restart. Use REGEX for topic to allow groups. Each input port is a topic.

If the output (state) of a rule is needed by another rule, that rule sends a subscription message to the source rule which then creates a topic, if needed, and then sends updates. The source rule does not need to even know about the subscription.

When a rule starts up, it must load up its subscribers list in addition to its state data. (They are essentially the same thing).

Bootstrap: The controller activates the rule. everyhting else is done by the rule. Very distributable. Background code does the replay to restore state. It creates subscriptions (ports) and topics (wiring) as needed. Huge dependency problem? Does lazy messaging solve this? My preference is to do the wiring in code rather than separate config file. This allows the code, if needed, to react to changes in the configuration! 

Example 1: Date input (system state). Computed state output: sunrise event , sunset event, day state, night state. Also: Wakeup (system) event.

Notice that rules can be removed from memory at any time because their state is already saved and can be recovered easily.

Bottome line, everything needed to make a decision is brought together via messages. The processor just needs to react to the state or event as presented.

Pseudo-code: 
- Declare inputs (Subscriptions to topics)
- Declare outputs (topics)
- onEvent
- onStateChange
- onStateLoad (replay/restore) default is to call setXXX method
- onWakeup (before any state loaded)
- onHibernate
- onChange (rule changed)
- getState
- setState (and publish to declared topics)

Local state is simply set by the rule and never published.
If done as a bean, we can literally use get/set methods and let 
Setup as a java bean so set/get can be automatic

It should be easy to create a new rule that responds to an existing event stream or state.

Compensation: If a rule (and topic) is deleted, replace (updated). 

Register a class. Introspector.getBeanInfo returns bean properties so that they will be sent/received.
Use annotations to "name" the properties.
