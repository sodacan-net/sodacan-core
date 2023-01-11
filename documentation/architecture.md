# Architecture
Components of this system communicate using publish/subscribe semantics. You should be familiar with this design pattern before reading further.

### Messages
In SodaCan, `PUBLIC` variables are essentially messages waiting to be sent. And, `SUBSCRIBE` variables are messages waiting to be received. Messages are exchanged through what is called a **topic** which is defined in more detail below. Simply put, a topic groups together messages of a specific format and names that format, the topic name.

All messages contain a `timestamp` which implies a temporal sequence for messages. Messages also contain a `key` and a `payload`.

### Message Bus
Abstractly, a message bus exits to exchange messages. Ignoring security, anyone can produce a message and anyone can consume messages. In SodaCan, the message bus is an implementation detail handled in the background. The modules that make up a system are unaware of the bus itself. Like a post office handles the logistics of getting a newspaper from its source (producer) to its destination(s) (consumer(s)). In a message bus architecture, the producer of a message as no control over who consumes that message. And, in general, the consumer has no control over who how or when the messages it receives is produced. This is the essence of decoupling in a microservice architecture.

### Message Producer
A `MODULE` that contains one or more `PUBLIC` variables is a message producer. Each `PUBLIC` variable is sent onto the message bus.
### Message Consumer

### Topic
In SodaCan, all topics, and therefore, all messages must be formally defined.
A topic defines a schema, or format, of messages for a specific purpose. 
A topic definition can originate from `MODULE PUBLIC` declarations or explicitly as a `TOPIC` declaration.
Once defined, a topic usually lasts forever, or until manually deleted.

### In-Transit messages
When a message is produced, it has a life of its own, neither belonging to the producer nor to any of its potential consumers. 
There is no sure-fire way for SodaCan to know when a message has been reliably consumed. For example, a module might not even be deployed yet that
could, in the future, consume messages of a particular topic. If resources were infinite, there is no reason SodaCan would need to clean up any messages.
The messages within a topic can come and go. Indeed, most topics define the lifetime of messages contained within that topic.

Consider, for example, a module that reports on the average number of uses of a certain button per month. In a traditional system, the data could be a challenge to create. But in SodaCan, the data already exists in the topic that was used to get the button press notification to the lamp that is controlled by that button press. So, the new module simply subscribes to that topic and it will get all of the messages from the past and then, into the future.

Now, SodaCan has several ways to deal with old messages. We can set an expiration date for a particular topic: Messages older than a certain number of days, weeks, months, or years will be automatically deleted. Or, once a topic exceeds a certain size, older messages can be deleted. Finally, we can just let the messages accumulate forever. Consider that most messages in a SodaCan application are quite small. Our button activation message will be about 20 bytes long. If we press that button 100 times per day for a year, that would add up to less than one megabytes. Therefore, it's probably not worth cleaning up this type of message. Of course the messages from a security camera are much larger and so the topic should be cleaned up either based on size (a very safe option) or the age of messages.

