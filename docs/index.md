# Welcome to Sodacan
Sodacan is for home and building automation. One might describe it as "post-IOT": A little less cloud. A little more local infrastructure.

## History
I live on a five acre property in southern Oregon. It includes a residence, shop, electric gate, water pumps, irrigation, Christmas lighting, an outdoor movie theater, security, indoor and outdoor lighting, and more. I have a bunch of controllers running these components and I'm adding more all the time. However, most of these components run in their own little world. What I need is a way to tie them all together. If I want to turn on a light in the house when the a lawn sprinkler is running, I should be able to do so with little or no coding. If I want my electric gate to open at 3pm on Thursdays, but only during the spring, that shouldn't require complex coding or rules. Whatever happens at bedtime should not be limited to lighting. If I want to close the gate, if it's still open, then that should be easy to configure. I have a flapole which is lit from sundown to sunrise, except during Christmas season when the flagpole becomes a huge Christmas tree: Flagpole light off, Christmas lights on... at night.

So, the essence of Sodacan is connecting disparate device controllers together using modern and inexpensive technology.

## Reliability
Are you  concerned about trusting the operation of your various devices to a cloud-based solution? If someone rings the doorbell and your Internet connection is down what happens? (I'm rural, so it happens from time to time). Sure, Alexa is nice when it's available.  In Sodacan, an AWS notification can easily initiate an action to the local Sodacan servers: (eg "Alexa, turn on the living room light" signals Sodacan to do so). But if the Internet is not available, you still have the control logic running locally. And, you have a Sodacan web server running locally for browser-based access to the super control panel.

## Hardware
While Sodacan will run on beefy servers, the design target for Sodacan is a small cluster of Raspberry Pi 4 boards: The more boards, the more fault tolerance and the more throughput. (In Apache Kafka terms:  Replication and Partitioning). The hardware configuration is where the name Sodacan originates: The server hardware should be the size of a soda can. (And, a real one holds my beverage of choice). WiFi and/or Ethernet connectivity between servers and also from servers to device controllers running Sodacan "agents" typically close to or on the device. 

## Limitations
Real-time high-frequency control of industrial devices is beyond the scope of this project. For example, robotics, printers (2d or 3d), and similar devices would tax the network and would likely behave poorly if response was delayed. However, meta-control, such as sending control instructions to an otherwise dedicated device controller using this technology would be a good fit. 

Typical events should make it through Sodacan in less that 10 ms. For example, latency from a light-switch to the light it controls should not have a noticeable delay. Scheduled events and timers are specified down to whole minutes. For example, Lights off at a specific time means at hh:mm and not hh:mm:ss. Timers can be scheduled down to seconds. For example: unlock the cat door for 5 seconds.

Best practice: Sodacan sends instructions to a simple Sodacan agent installed inside the gate control box. The control agent is in turn hard wired to the existing gate controller. The gate controller knows how to control open/close speed, limit switch detection, obstructions, etc, which Sodacan takes no part in. The gate controller just needs to know *when* to open or close, ie from Sodacan.

## Summary
If you are looking for something that has all of the scale, reliability, and fault-tolerance of a cloud-based service (Amazon, Google, etc) in a local environment, this may be a good solution for you.

## More Details

<a href="architecture.md">Sodacan Architecture</a><br/>
<a href="languageGuide.md">Sodacan Module Language Guide</a><br/>
<a href="languageReference.md">Sodacan Module Language Reference</a><br/>
<a href="developer.md">Sodacan Module Developer's Guide</a><br/>

<a href="gettingStarted.md">Getting Started with Sodacan</a><br/>
<a href="operations.md">Sodacan Operations</a><br/>
<a href="internals.md">For Sodacan Source code developers</a><br/>

## Project Documentation

Source code and detailed documentation can be found on <a href="https://github.com/sodacan-net/sodacan-core">GitHub</a>.
