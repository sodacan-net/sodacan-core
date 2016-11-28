# SodaCan
This is a proof-of-concept for plant automation using Java 8+, Apache Kafka and JBoss Drools. It is probably overkill for simple Home Automation such as to control lighting only, but it does provide a lan-based alternative to those systems that would otherwise have to depend on a commercial cloud service. Persistence, fault-tolerance, commumication security, and communication is provided by the SodaCan environment. 

My test environment is a so-called "farm" that has two residences, a 2300 sq ft shop building, a small amphitheter, septic system, a well and water storage system, and other features spread out across three and one half acres that will benefit from a coordinated control system. In particular, the entire plan is, at this point, powered by a standalone solar photovoltaic system. The PV system, especially in winter, requires some careful management of electric usage to avoid having to use a fossil-fueled backup generator. As a simple example, water should only be pumped from the well to the water storage tank on sunny days. 

If you are looking for something that has all of the fault-tolerance of a cloud-based service (Amazon, Google, etc) in an in-house package, this may eventually be a solution. 

## Device Controllers
Each "device" should be connected to a local microcontroller capable of running C, Java or Python. BeagleBone Black or Wireless BeagleBone Green would be appropriate. Also, pykafka supports client communication from Python to the Kafka broker(s).

With a large device controller such as BeagleBone, it is reasonable for it to control many devices. For example, one physical area could have a single controller manage all devices in that area as long as the bandwidth of the controller is not overburdened. This approach suggests that a device controller should be soft-configured. That is, generic code loaded onto each controller and the behavior intended for that controller downloaded from the central servers. As descussed shortly, the configuration can be distributed to the controller via message. Each controller has a dedicated "topic" which contains device parameter change requests. These parameters can include both dynamic changes such as requesting that a digital output pin be changed as well as configuration changes such as that a particular GPIO pin's mode be set to output. Thus, the controller is simply responding to a stream of requests (and generating state changes and events as a result). This allows changes to be rolled out under control of rules which can even do so at a specific future time. 

## Rules
The SodaCan contains rules that manage events, device state, alerts, heartbeat, temporal reasoning, etc. A key concept is that SodaCan rules should be able to reason over all available facts which mostly boils down to all parameters of all devices in the system. In modern computing, there's really no reason to have to pick and choose which parameters need to be sent to which logic engine.

Device parameters are represented as facts in the rule engine. Rules should not change device parameters directly but rather should send a device parameter change request to the device controller to which the device is connected. In response, the device controller will make the parameter change locally and then send an updated parameter to the SodaCan.

Device Events originate in certain device controllers, typically buttons or similar triggers. An event doesn't usually persist very long in the rule engine. For example, a button press used to toggle another device on or off would have little meaning once the target device state change has occured. For this reason, events are stored in a separate Kafka topic from parameters. An event persists in a topic for a configurable amount of time, say one week at which point it's space is simply reclaimed. Events are not replayed should the SodaCan fail and be restarted.

Device Parameters are handled differently. Logically, the last setting of each unique parameter will remain in the topic. If a SodaCan should fail, when it starts again, it simply rewinds to the beginning of the topic and loads all parameters into working memory. Behind the scenes, the "log compaction process" which retains the most recent parameter state runs only periodically so it is possible that during a reload, multiple versions of a single parameter might be processed. This is not a problem for the SodaCan since the end state is the same in any case.

As a minor contradiction to the "all facts known to one SodaCan", it is a rather simple configuration change to have two or more different sets of rules operating on different servers. This can even be done in a live system. The name of the "group" that contains SodaCan is SodaCanGroup. In another process, it can be changed to some other name, such as "MyRulesGroup" and when that process runs, it will see the same facts as the SodaCanGroup but can make completely different decisions based on those facts. This feature is distict from having multiple instances of consumers with the same group name.

## High-level Dataflow
Dataflow from various perspectives and operational conditions are described here.

#### Device Controller, steady-state
During normal operation, a device controller maintains a copy of all parameters associated with all devices connected to that microcontroller. This is the "source of truth" for these parameters and no other components should normally change the parameter values. However, when an external component, primary SodaCan, desires to change a parameter, it will send a parameter change message to the microcontroller which it can use as notification of a request to change a paramter value. In any case, when the microcontroller changes the value of a parameter (or the parameter is created), it must send a message containing the new value. This notifies anoyone interested in that parameter value.

When appropriate, a microcontroller can send an event rather than changing it's state. The difference between a button-press (event) and say, a temperature reading (a parameter change) is that events are short-lived whereas a parameter change is permanent, and saved, until changed.

Each device controller should also send a "heartbeat" event periordically so that the rules can react to a device being off-line.

#### SodaCan, steady-state
SodaCan reacts to parameter changes by inserting a new fact or modifying an existing fact in working memory. Thus, working memory contains, at least, the corrent value of all parameters in the system. Rules will then react as appropriate, or not at all, to parameter changes. Events are processed differently: Most events only spend a brief time in working memory.The tend to "age out" within seconds. Should a rule desire to change a parameter value, such as when a button event causes the state of a light to toggle from on-to-off, the SodaCan does not change it's value but rather sends out a parameter change request, which the device is likely to honor by making the parameter change and sending the updated parameter back to SodaCan.

## Servers
In general, server describes a logical concept. Indeed, a server in this environment may be nothing more than a [Docker](https://www.docker.com/) container. I'll use the term "box" to refer to a physical (bare metal) server.

The logical organization of servers is: Three zookeeper servers, three Message Broker servers, and three SodaCan servers. These logical servers can be implemented on three physical boxes to provide sufficient redundancy. All three physical boxes can be expected to be up most of the time so it is best that each of the three major components runs on each of the boxes. Just make sure that the three instances of any one server function are not all allocated to a single box. In the case of a failure, the services running on the failed box will shift to processes running on the remaining boxes.

Using the 3x3 configuration described above, one could run these server on nine separate boxes. But that would leave 6 boxes idle most of the time. More boxes might be justified for additional capacity, but that is a separate discussion. In any case, Docker containers are used to represent each of these nine logical servers so that they can be deployed over three physical boxes, or on AWS or a similar service if desired. Should a phyical box go down, the Docker container can simply be run on a different box. Replication in this style also facilitates rolling upgrades thus eliminating the need for downtime.

## Peristence
Kafka provides all state persistence for SodaCan as well as fast, reliable message delivery. There is really no need for a reliable disk configuration (eg RAID) due to efficient replication and failover at the software level. As a general philosophy, if a server should fail for any reason, it should simply be wiped clean and rebuilt. There should be no need for backups or for any downtime. This philosophy also applies to the disk files used by Kafka: If a server goes down, any persistent files can be wiped. When a server restarts, data will be restored from replicas on other servers.

#### Power Failure
An exception to the idea of rebuilding a server should an exception be detected is a power failure. In this case, the data maintained in queues can be safely used when the server is restarted. If one server loses power and then recovers, it may need to catch up. This is done automatically in the background at that server will handle any client work unto it is in sync with the other. If all servers lose power, then the first servers to be restored will be elected leader(s) and handle network traffic.

#### Controlling Server Recovery
Determining what should be done when a server is started can be complex. As suggested, it is sometimes wise to simple start fresh when a server starts up whereas sometimes it is required to continue where it left off. The following is a reasonably conservative aprroach which has a disadvantage that it manual intervention. This approach may not be desired in the case of home automation which may have no person available to intervene in the case of a failure. However, it does allow time for operation to continue perhaps with one or more servers off-line until intervention can be provided. 
When a server fails for any reason other than power failue, the server 

## Cabling
I'm running normal Cat-6 LAN cable to each device controller (BeagleBone). In the case of lighting,  I run traditional RS-485 from a server to any number of DMX-based lighting fixtures. The lighting fixtures are custom-made.

## Industrial Standards
The approach used by SodaCan is much less compact than a protocol such as MODBUS. Nevertheless, it is relatively compact and provides built-in security, error detection, failover, etc. This project makes no attempt to comply with the SCADA standard. 

There are products available that support protocol conversion between various standards. That function is beyond the scope of this project.

## Load Balancing
While Kafka provides partitioning which can provide distribution of processing across many systems, scalability (tens-of-thousands of nodes) is not a goal of this project. In Kafka terms, each "topic" has only one partition. A typical system might have hundreds of devices. For a system with tens of tousands of devices, it is likely that rules would not be able to reason over all facts and that a tiered system would be needed. Such could be done with SodaCan as the intermediate tier, but that's not something I'm working on at this point.

## Device Controller Configuration
Some device mirocontrollers can be setup in such a way that, with the exception of its unique host name, is completely configured from a central site using Kafka. To make this efficient, the following approach is used: Each device has its own topic. This topic is only used to send messages from SodaCan to the controller. Anything sent in the other direction (controller to server) is done via the event or state topic. When a controller starts up, it seeks to the beginning of it's "topic" to get configuration information and any state change requests from the SodaCan.
