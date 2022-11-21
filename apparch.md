Device Event Stream eg button push, video frame, level reading
Should we subscribe to events as well?
Also, make subscriptions a kafka msg. ie dynamic.
State update - process events, update state. process filters events its interested in.
State propogation - to subscribers
State persistence
If two or more states are needed for a single event, then wire state subscriptions to the event rule
