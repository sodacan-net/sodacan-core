# SodaCan

*Note: The code is not complete.*

This project is a proof-of-concept for home and plant automation using a message-driven architecture.

My test environment is a five acre property in southern Oregon. It includes a residence, shop, water pumps, irrigation, Christmas lighting, an outdoor movie theater, security, indoor and outdoor lighting.

Real-time high-frequency control of industrial devices is beyond the scope of this project. For example, robotics, printers (2d or 3d), and similar devices would tax the network and would likely behave poorly if response was delayed. However, meta-control, such as submitting job specifications to an otherwise dedicated device controller using this technology would be a good fit.

If you are looking for something that has all of the scale, reliability, and fault-tolerance of a cloud-based service (Amazon, Google, etc) in an in-house package, this may be a good solution. 

## How It Works
SodaCan is comprised of several key components. Arguably the most crucial is a message bus. The underlying technology uses Apache Kafka. Connected to this message bus are `adapters` and `modules`. Adapters connect messages from the outside world into the message bus and from the message bus to the outside world. Modules provide the decision making capability. SodaCan is distributed; Each adaptor and module is a separate program and they don't need to be on a single server. In fact, for many devices, an adapter for that device might run directly on that device. 

A compiler converts the module language into structures needed by the SodaCan runtime system. String decoupling is enforced by the SodaCan architecture. For example, on module is not allowed to "see" into another module. In fact, a module cannot even call into another module. Doing so would require the called module to exist prior to deploying the module calling it. This decoupling is accomplished using a message-passing approach. That is, and data coming into or going out of a module is done via asynchronous message. While a module will usually have some state variable, persisting that state is transparent to the module developer. The developer never has to "go get" data. Messages deliver data to modules proactively.

From a black-box perspective, modules simply send and receive messages. A few other services are provided by the infrastructure and can be enhanced or overridden with plugins: Module persistence (Adapters should be stateless). The variables defined in a module will be saved and restored automatically. A clock source is also provided by the infrastructure: Modules have a built-in capability to react to the passage of time. This make it very easy to implement time-based control logic (for example: *at sunset, turn light on*).

## More Details
[Language](https://github.com/johnchurin/Sodacan/blob/master/documentation/language.md)

[Architecture](https://github.com/johnchurin/Sodacan/blob/master/documentation/architecture.md)

