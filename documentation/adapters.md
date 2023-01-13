# Adapters
In general, adapter code is attached to a sodaCan `MODULE`. The adapter code has complete read access to the variables in the module. Custom subscription code can be safely single-threaded: SodaCan will call the custom code synchronously, one message at a time. 

For publishing messages, the custom code can't know if the MODULE is in the middle of a cycle or now. The stimulus for sending a message is only known to the adapter code, not SodaCan. So, when an event is raised by adapter code, a message is published asynchronously: The custom code does this by calling back to SodaCan (actually the agent running that module) and asks it to publish a message to the topic and variable declared by the `PUBLISH` variable. Internally, normal publish actions are taken at the end of a cycle. However, for adapter code, the publication occurs immediately.

A convenient way to write an adapter in order to keep the custom SodaCan code as small as possible is to use a "wrapper" adapter. That is, a small bit of adapter code that simply passes messages along to the real module using a TCP socket.
