# SodaCan

*Note: The code is not complete.*

![build workflow](https://github.com/johnchurin/sodacan/actions/workflows/maven.yml/badge.svg?branch=master)

This project is for home and plant automation using a message-driven architecture. You might think of it as post-IOT but it's more like they work together. IIOT? Internet and Intranet Of Things?

## Motivation
I live on a five acre property in southern Oregon. It includes a residence, shop, electric gate, water pumps, irrigation, Christmas lighting, an outdoor movie theater, security, indoor and outdoor lighting, and more. I have a bunch of controllers running these components and I'm adding more all the time. However, most of these components run in their own little world. What I need is a way to tie them all together. If I want to turn on a light in the house when the a lawn sprinkler is running, I should be able to do so with little or no coding. If I want my electric gate to open at 3pm on Thursdays, but only during the spring, that shouldn't require complex coding or rules. Whatever happens at bedtime should not be limited to lighting. If I want to close the gate, if it's still open, then that should be easy to configure. I have a flapole which is lit from sundown to sunrise, except during Christmas season when the flagpole becomes a huge Christmas tree: Flagpole light off, Christmas lights on... until Midnight.

So, the essence of Sodacan is tying disparate device controllers together using modern technology.

## Reliability
Are you  concerned about trusting the operation of your various devices to a cloud-based solution? If someone rings the doorbell and your Internet connection is down what happens? (I'm rural, so it happens from time to time). Sure, Alexa is nice when it's available.  In Sodacan, an AWS notification can easily initiate an action to the Sodacan servers: (eg "Alexa, turn on the living room light"). But if the Internet is not available, you still have the browser-based dashboard, served locally, available.

## Hardware
While Sodacan will run on beefy servers the design target for Sodacan is a small cluster of Raspberry Pi 4 boards: The more boards, the more fault tolerance and the more throughput. (In Apache Kafka terms:  Replication and Partitioning). And the hardware configuration is where the name Sodacan originates: The server hardware should fit into a soda can. (And, it is my beverage of choice). WiFi and/or Ethernet connectivity from servers to each other and servers to device controllers running Sodacan "agents" typically close to or on the device. 

## Limitations
Real-time high-frequency control of industrial devices is beyond the scope of this project. For example, robotics, printers (2d or 3d), and similar devices would tax the network and would likely behave poorly if response was delayed. However, meta-control, such as sending control instructions to an otherwise dedicated device controller using this technology would be a good fit. 

Best practice: Sodacan sends instructions to a simple Sodacan agent installed inside the gate control box and hard wired to the existing gate controller. The gate controller knows how to control open/close speed, limit detection, etc. It just needs to know when to open or close: from Sodacan.

## Summary
If you are looking for something that has all of the scale, reliability, and fault-tolerance of a cloud-based service (Amazon, Google, etc) in an intranet environment, this may be a good solution for you. 

## How It Works
SodaCan is comprised of several key components. Arguably the most crucial is a message bus. The underlying technology uses Apache Kafka. Connected to this message bus are agents that run SodaCan `modules`. Some modules, called `adapters`, connect messages from the outside world into the message bus and others connect from the message bus to the outside world. Modules provide the decision making capability of SodaCan. SodaCan is distributed; Each module can be in a separate program and modules do not need to be on a single server. In fact, for many devices, an adapter module for that device might run directly on the microcontroller where that device is connected. 

A SodaCan module compiler converts the SodaCan module language into structures needed by the SodaCan runtime system. Strict decoupling is enforced by the SodaCan architecture. For example, one module is not allowed to "see" into another module. In fact, a module cannot even call into another module. Doing so would require the called module to exist prior to deploying the module calling it. This decoupling is accomplished using a message-passing approach. That is, and data coming into or going out of a module is done via asynchronous messages. While a module will usually have some state variables, persisting that state is transparent to the module developer. The developer never has to "go get" data. Messages deliver data to modules proactively in order to ensure minimum processing time. 

From a black-box perspective, modules simply send and receive messages. A few other services are provided by the infrastructure and can be enhanced or overridden with plugins: Module persistence means that the variables defined in a module will be saved and restored automatically. A clock source is also provided by the infrastructure: Modules have a built-in capability to react to the passage of time. This make it very easy to implement time-based control logic (for example: *at sunset: turn light on*).

## Security
[tbd]

## Logging
[tbd]

## More Details

<a href="documentation/architecture.md">SodaCan Architecture</a><br/>
<a href="documentation/languageGuide.md">SodaCan Module Language Guide</a><br/>
<a href="documentation/languageReference.md">SodaCan Module Language Reference</a><br/>
<a href="documentation/developer.md">SodaCan Module Developer's Guide</a><br/>

<a href="documentation/gettingStarted.md">Getting Started with SodaCan</a><br/>
<a href="documentation/operations.md">SodaCan Operations</a><br/>
<a href="documentation/internals.md">For SodaCan Source code developers</a><br/>

## Project Documentation

<a href="agent/README.md">SodaCan Agent</a><br/>
<a href="api/README.md">SodaCan API</a><br/>
<a href="compiler/README.md">SodaCan Compiler</a><br/>
<a href="message/README.md">SodaCan Message Structure</a><br/>
<a href="module/README.md">SodaCan Module Structure</a><br/>
<a href="runtime/README.md">SodaCan Module Runtime</a><br/>
<a href="sodacan/README.md">Top-level SodaCan Project</a><br/>
<a href="utility/README.md">Utility functions</a><br/>
<a href="webserver/README.md">SodaCan RESTful API and web page server</a><br/>
