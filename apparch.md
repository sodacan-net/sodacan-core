Device Event Stream eg button push, video frame, level reading

Should we subscribe to events as well?

Also, make subscriptions a kafka msg. ie dynamic.

State update - process events, update state. process filters events its interested in.

State propogation - to  

State persistence

##Wiring
If one or more states are needed for a single event, then wire state subscriptions to the event sinc/rule. Think of this as a slow version of Drools - all data is transmitted to the front door of the rule, persistentely so that the rule only needs to respond to the data it has whenever state changes or an event is presented.

This results in a lot of message traffic but on the upside it is all very debuggable and modular (microservice).

Bottome line, everything needed to make a decision is brought together via messages. The processor just needs to react to the state or event as presented.
