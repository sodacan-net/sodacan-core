# SodaCan
This is a proof-of-concept for plant automation using Java 8+, Apache Kafka and JBoss Drools. It is probably overkill for Home Automation but it does provide a lan-based alternative to those systems that depend on a commercial cloud service. Persistence, fault-tolerance, commumication security, and communication is provided by the SodaCan environment. 

If you are looking for something that has all of the fault-tolerance of a cloud-based service (Amazon, Google, etc) in an in-house package, this may eventually be a solution. 

## Device Controllers
Each "device" should be connected to a local microcontroller capable of running Java or Python. BeagleBone Black or Wireless BeagleBone Green would be appropriate. pykafka supports client communication to the Kafka broker(s). 

## Rules
The SodaCan contains rules that manage events, device state, alerts, heartbeat, temporal reasoning, etc. A key concept is that SodaCan rules should be able to reason over all available facts which mostly boils down to all parameters of all devices in the system. In modern computing, there's really no reason to have to pick and choose which parameters need to be sent to which logic engine.

Device parameters are represented as facts in the rule engine. Rules should not change device parameters directly but rather should send a device parameter change request to the controller to which the device is connected. In response, the device will make the parameter change locally and then send an updated parameter to the SodaCan.

Device Events originate in certain devices, typically buttons or similar triggers. An event doesn't usually persist very long in the rule engine. For example, a button press used to toggle another device on or off would have little meaning once the target device state change has occured. For this reason, events are stored in a separate Kafka topic from parameters. An event persists in a topic for a configurable amount of time, say one week when it's space is simply reclaimed. Events are not replayed should the SodaCan fail and be restarted.

Device Parameters are handled differently. Logically, the last setting of each unique parameter will remain in the topic. If a SodaCan should fail, when it starts again, it simply rewinds to the beginning of the topic and loads all parameters into working memory. Behind the scenes, the "log compaction process" which retains the most recent parameter state runs only periodically so it is possible that during a load, multiple versions of a single parameter might be processed (in order). This is not a problem for the SodaCan.

As a minor contradiction to the "all facts known to one SodaCan", it is a rather simple configuration change to have two or more different sets of rules operating on different servers. This can even be done in a live system. The name of the "group" that contains SodaCan is SodaCanGroup. In another process, it can be changed to some other name, such as "MyRulesGroup" and when that process runs, it will see the same facts as the SodaCanGroup but can make completely different decisions based on those facts. This feature is distict from having multiple instances of the same group name such as 3 replicas of the SodaCanGroup. 

## High-level Dataflow
Dataflow from various perspectives and operational conditions are described here.
#### Device Controller, steady-state
During normal operation, a device controller maintains a copy of all parameters associated with all devices connected to that microcontroller. This is the "source of truth" for these parameters and no other components should normally change the parameter values. However, when an external component, primary SodaCan, desires to change a parameter, it will send a parameter change message which the microcontroller can use as notification of a request to change a paramter value. In any case, when the microcontroller changers the value of a parameter (or the parameter is added), it must send a message containing the new value.

When appropriate, a microcontroller can send an event rather than changing it's state. The difference between a button-press (event) and say, a temperature reading (a parameter change).

Each microcontroller should also send a "heartbeat" event periordically so that the rules can react to a device being off-line.

#### SodaCan, steady-state
SodaCan reacts to parameter changes by inserting a new fact or modifying an existing fact in working memory. Thus, working memory contains, at least, the corrent value of all parameters in the system. Rules will then react as appropriate, or not at all, to parameter changes. Events are processed differently: Most events only spend a brief time in working memory.The tend to "age out" within seconds. Should a rule desire to change a parameter value, such as when a button event causes the state of a light to toggle from on-to-off, the SodaCan does not change it's value but rather sends out a parameter change request, which the device is likely to honor by making the parameter change and sending the updated parameter back to SodaCan.

## Servers
In general, server describes a logical concept. Indeed, a server in this environment may be nothing more than a [Docker](https://www.docker.com/) container. I'll use the term "box" to refer to a physical server.

The logical organization of servers is: Three zookeeper servers, three Message Broker servers, and three SodaCan servers. These logical servers can be implemented on three physical boxes to provide sufficient redundancy. All three physical boxes can be expected to be up most of the time so it is best that each of the three major components runs on each of the boxes. Just make sure that the three instances of any one server function are not all allocated to a single box. In the case of a failure, the services running on the failed box will shift to processes running on the remaining boxes.

Using the 3x3 configuration described above, one could run these on nine separate boxes. But that would leave 6 boxes idle most of the time. More boxes might be justified for additional capacity, but that is a separate discussion. In any case, Docker containers are used to represent each of these nine logical servers so that they can be deployed over three physical boxes, or on AWS or similar service if desired. Should a phyical box go down, the Docker container can simply be run on a different box. Replication in this style also facilitates rolling upgrades in order to eliminate the need for downtime.

## Peristence
Kafka provides all state persistence for SodaCan as well as fast, reliable message delivery. There is really no need for a reliable disk configuration (eg RAID) due to efficient replication and failover at the software level. As a general philosophy, if a server should fail for any reason, it should simply be wiped clean and rebuilt. There should be no need for backups or for any downtime. This philosophy also applies to the disk files used by Kafka: If a server goes down, any persistent files can be wiped. When a server restarts, data will be restored from replicas on other servers.

## Cabling
I'm running normal Cat-6 LAN cable to each device controller (BeagleBone). In the case of lighting,  I run traditional RS-485 from a server to any number of DMX-based lighting fixtures. The lighting fixtures are custom-made.

## Industrial Standards
The approach used by SodaCan is much less compact than a protocol such as MODBUS. Nevertheless, it is relatively compact and provides built-in security, error detection, failover, etc. This project makes no attempt to comply with the SCADA standard. 

There are products available that support protocol conversion between various standards. That function is beyond the scope of this project.

## Load Balancing
While Kafka provides partitioning which can provide distribution of processing across many systems, scalability (tens-of-thousands of nodes) is not a goal of this project. In Kafka terms, each "topic" has only one partition. A typical system might have hundreds of devices. For a system with tens of tousands of devices, it is likely that rules would not be able to reason over all facts and that a tiered system would be needed. Such could be done with SodaCan as the intermediate tier, but that's not something I'm working on at this point.

## Device Controller Configuration
Some device mirocontrollers can be setup in such a way that, with the exception of its unique host name, is completely configured from a central site using Kafka. To make this efficient, the following approach is used: Each device has its own topic. This topic is only used to send messages from SodaCan to the controller. Anything sent in the other direction (controller to server) is done via the event or state topic. When a controller starts up, it seeks to the beginning of it's "topic" to get configuration information and any state change requests from the SodaCan.
