# Architecture
Components of this system communicate using publish/subscribe semantics. You should be familiar with this design pattern before reading further.

### Messages
In SodaCan, `PUBLIC` variables are essentially messages waiting to be sent. And, `SUBSCRIBE` variables are messages waiting to be received. Messages are exchanged through what is called a **topic** which is defined in more detail below. Simply put, a topic groups together messages of a specific format and names that format, the topic name.

All messages contain a `timestamp` which implies a temporal sequence for messages. The producer is also identified in a message. Messages also contain a `key` and a value or `payload`, both of which are optional.

### Message Bus
Abstractly, a message bus exits to exchange messages. Ignoring security, anyone can produce a message and anyone can consume messages. In SodaCan, the message bus is an implementation detail handled in the background. The modules that make up a system are unaware of the bus itself. Like a post office handles the logistics of getting a newspaper from its source (producer) to its destination(s) (consumer(s)). In a message bus architecture, the producer of a message as no control over who consumes that message. And, in general, the consumer has no control over who how or when the messages it receives is produced. This is the essence of decoupling in a microservice architecture.

```mermaid
flowchart BT;
    A[Module A]-->B[Message Bus];
    C-->B;
    D-->B;
    E-->B;


### Message Producer
A `MODULE` that contains one or more `PUBLIC` statements is a message producer. Each `PUBLIC` variable is sent onto the message bus.
### Message Consumer
A `MODULE` that contains one or more `SUBSCRIBE` statements is a message consumer. 
### Topic
In SodaCan, all topics, and therefore, all messages must be formally defined.
A topic defines a schema, or format, of messages for a specific purpose. 
Once defined, a topic usually lasts forever, or until manually deleted.
A `MODULE` that contains `TOPIC` statements defines independent topics.

### In-Transit messages
When a message is produced, it takes on a life of its own, neither belonging to the producer nor to any of its potential consumers. 
There is no sure-fire way for SodaCan to know when a message has been completely consumed. For example, a module that *might* consume a particular type of message 
may not exist yet. If resources were infinite, there is no reason SodaCan would need to recover space used by any messages.
The messages within a topic can come and go. Indeed, most topics define the lifetime of messages contained within that topic.

Consider, for example, a module that reports on the average number of uses of a certain button per month. In a traditional system, the data could be a challenge to create. But in SodaCan, the data already exists in the topic that was used to get the button press notification to the lamp that is controlled by that button press. So, the new module simply subscribes to that same topic and it will get all of the messages from the past and into the future.

Now, SodaCan has several ways to deal with old messages in a topic. It can set an expiration date for a particular topic: Messages older than a certain number of days, weeks, months, or years will be automatically deleted. Or, once a topic exceeds a certain size, older messages can be deleted. Finally, we can just let the messages accumulate forever. Consider that most messages in a SodaCan application are quite small. Our button activation message will be about 20 bytes long. If we press that button 100 times per day for a year, that would add up to less than one megabytes. Therefore, it's probably not worth cleaning up this type of message. On the other hand, messages from a security camera are much larger and so the topic should be cleaned up either based on size (a very safe option) or the age of messages.

### Module behavior
A module waits quietly for either the passage of time or a message to arrive. If two or more messages arrive at the same time, one is chosen to go first. At that point, the list of `AT` (in the case of the passage of time) or `ON` (the arrival of a message) statements is considered, one at a time, in the order which they are declared, until one *matches*. The `THEN` statement of the corresponding `ON` or `AT` is executed. The passage of time may not trigger any `ON` statements. That's normal. However, 
for messages, if no matching `ON` statement is found, then an error is thrown. Why? When a `module` subscribes to a particular topic, it declares its intent to deal with that message. If that doesn't happen, there's a problem: Either the `SUBSCRIBE` is wrong or the `ON`s are wrong or missing. 
