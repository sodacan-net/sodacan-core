# Mode
Mode has several purposes
- Partition the application data and processing threads
- Provide a separation between behavior and various input-output (IO) needs
- Mode is home to the Service Provider Interface (SPI) for Time and IO services

### Mode and Plugins
Sodacan maintains a mode during processing of messages. Mode is a very strong partitioning in Sodacan, and most that partitioning is enforced in this `runtime` component. In Java terms, the Mode class sits between `sodacan-core` code and the service providers that accompany the core code.

As an example of the flow involving mode and a service, start with the arrival of  REST API request that needs something from a service provider plugin. In this case, the name of the mode accompanying the request is in a cookie. The REST API call is intercepted in a filter so that the behavior is the same for all requests.

In the filter, a call is made to the mode object with the name of the mode. 

```
    Mode.setupThreadMode(String modeName);

```

The mode object finds the mode, adds a references to the matching Mode object in thread-local storage. A thread only has one mode so a simple

```
    Mode mode = Mode.getInstance();

```
  
in downstream code gets the correct mode.

The mode object, when it was created, discovers and keeps a pointer to the services needed by that mode. For example, a test `mode` might write a log entry to the console whereas a production `mode` would write the log entry to a database or monitoring system.

In general, service requests usually don't pass through the mode object. Rather, the service will provide a factory (a proxy) to the runtime which in turn uses the factory directly. The Sodacan runtime and other components must keep track of the mode. Why? The runtime handles any number of modes at the same time, each potentially configured with different service providers.

A single service provider can easily find itself dealing with requests for multiple modes at the same time. Sodacan architecture requires that modes don't talk to each other nor do they share I-O. But this is a grey area: Sodacan doesn't care how a service is implemented. Consider a database plugin. It could connect to a single DB server and simply partition the data by include mode in the primary key(s). But the spirit remains the same. Modes are separate from each other.

Sodacan includes the mode (or it's name) in every call to the service and that the service should partition its behavior accordingly.

### Mode in the Code
Mode is a major operational partitioning mechanism in the Sodacan runtime. All IO is partitioned by mode.
For example, in a test mode, IO might be to a flat file while in production, data might be in a database.
Sodacan core can handle any number of modes simultaneously.

A mode instance will seek to establish services needed for that mode. 
Mode is not passed as an argument in most cases. Rather, mode is stored in and accessed from thread local storage.

On activation of the thread, such as when a REST api occurs, call the static method getInstance with the mode name.
In this case, mode is typically in a session or cookie. the latter is preferred because session setting might also
be stored by mode.

```
    Mode mode = Mode.getInstance();

```

One thread can only be in one mode at a time. However, and number of threads can be in the same mode. Service providers
should be aware of this and be prepared to handle thread synchronization if applicable. In the case of calls from modules, 
the service provider can be certain of the single thread. Remember that calls of this sort are by module and, if applicable, 
module instance.

Persisting modes is a bit tricky because of Sodacan rules about no cross-mode interaction. 
So, we leave Mode persistence to the individual services. On startup, we poll each of the
services asking for which mode(s) they apply to, if any. This allows the service to use its own method for persisting it's state.
This process reconstitutes the Mode list.
