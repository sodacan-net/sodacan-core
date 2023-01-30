# Adapters
In general, adapter code is attached to a Sodacan `MODULE`. The adapter code has complete read access to the variables in the module. Custom subscription code can be safely single-threaded: Sodacan will call the custom code synchronously, one message at a time. A simple adapter module follows:

```
	MODULE lamp3
		SUBSCRIBE livingRoom.lamp AS lamp
		EXTERNAL someFunction
		ON lamp
			THEN someFunction
```

A slightly more complicated adapter module involves instances: If the message contains an instance, then the instance is also available to the adapter code through callback to Sodacan.

```
	MODULE lampAdapter[location]
		SUBSCRIBE lamp[location] AS lamp
		EXTERNAL someFunction
		ON lamp
			THEN someFunction[location]
```

For publishing messages, the custom code can't know if the `MODULE` it is attached to is in the middle of a cycle or not. The stimulus for sending a message is only known to the adapter code, not Sodacan. So, when an event is raised by adapter code, a message is published asynchronously: The custom code does this by calling back to Sodacan (actually the agent running that module) and asks it to publish a message to the topic and variable declared by the `PUBLISH` variable. 

Internally, normal publish actions are taken at the end of a cycle. However, for adapter code, the publication occurs immediately. If the adapter module limits its behavior to just calling adapter functions, this difference should be invisible.

In some cases, especially when an existing driver is involved, Sodacan adapter code can be a simple "wrapper" adapter. That is, a small bit of adapter code that simply passes messages along to the *real* adapter code using, say, a TCP socket.

