# SodaCan
This project is a proof-of-concept for plant automation using Java 8+, Apache Kafka and JBoss Drools. It is probably overkill for simple Home Automation such as to control lighting only, but it does provide a lan-based alternative to those systems that would otherwise have to depend on a commercial cloud service. Persistence, fault-tolerance, commumication security, and communication is provided by the SodaCan environment. 

My test environment is a so-called "farm" that has two residences, a 2300 sq ft shop building, a small amphitheter, septic system, a well and water storage system, and other features spread out across three and one half acres that will benefit from a coordinated control system. In particular, the entire plan is, at this point, powered by a standalone solar photovoltaic system. The PV system, especially in winter, requires some careful power management to avoid having to use a fossil-fueled backup generator or to connect to the grid. As a simple example, water should only be pumped from the well to the water storage tank on sunny days. 

Realtime high-frequency control of industrial devices is beyond the scope of this project. For example, robotics, printers (2d or 3d), and similar devices would tax the network and would likely behave pooly if response was delayed. However, meta-control, such as submitting job specifications to an otherwise dedicated device controller using this technology would be a good fit.

If you are looking for something that has all of the fault-tolerance of a cloud-based service (Amazon, Google, etc) in an in-house package, this may eventually be a solution. 

## Device Controllers
Each "device" should be connected to a local microcontroller capable of running C, Java or Python. BeagleBone Black or Wireless BeagleBone Green would be appropriate. Also, pykafka supports client communication from Python to the Kafka broker(s).

## Device Controller Configuration
With a large device controller such as BeagleBone, it is reasonable for it to control many devices. For example, one physical area could have a single controller manage all devices in that area as long as the bandwidth of the controller is not overburdened. This approach suggests that a device controller should be soft-configured. That is, generic code loaded onto each controller and the behavior intended for that controller downloaded from the central servers. 

As descussed shortly, the configuration can be distributed to the controller via message. Each controller has a dedicated "topic" which contains device parameter change requests. These parameters can include both dynamic changes such as requesting that a digital output pin be changed as well as configuration changes such as that a particular GPIO pin's mode be set to output. Thus, the controller is simply responding to a stream of requests (and generating state changes and events as a result). This allows changes to be rolled out under control of rules which can even do so at a specific future time. 

When a controller starts up, it seeks to the beginning of it's "topic" to get configuration information and any state change requests from the SodaCan. The message system does not distinguish between old catch-up states and the most recent state change. While the final state will be the correct state, during startup of a controller, there could be undiserable flicker in outputs being controlled if the states were processed as received. Therefore, the controller may need to maintain a temporary map containing the final state of each parameter and then process only the final states during startup. This situation is likely to be minor since the timing between each of these startup state changes, if any, will be on the order of a few milliseconds.

In this approach, the only thing that is unique to each device controller is its host name. 

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

When a server fails for any reason other than power failue to the box, the server is essentially taken off-line. Technically, it means that the Docker container is paused or perhaps exited. In the case of power failure, docker will automatically restart the container when the box boots up.

The primary decision to be made after a failure: Should the server be rebuilt or restarted. Because of the nature of SodaCan, 
an existing Docker container can always be destroyed and a new container created from the existing image. So, the only question is if the file system used by that container be left as-is or should it start clean. This is the choice needed to be made by a system administrator. To help make this choice, the administrator should be made aware of the state of other replicas of the data. And further, the decision to clean the last remaining in-sync replica should require double confirmation. (A replica that is currently in the process of syncing from another replica is not considered in-sync.) 

## Cabling
I'm running normal Cat-6 LAN cable to each device controller (BeagleBone). In the case of lighting,  I run traditional RS-485 from a server to any number of DMX-based lighting fixtures. Some of the lighting fixtures are custom-made.

## Industrial Standards
The approach used by SodaCan is much less compact than a protocol such as MODBUS. Nevertheless, it is relatively compact and provides built-in security, error detection, failover, etc. This project makes no attempt to comply with the SCADA standard. 

There are products available that support protocol conversion between various standards. That function is beyond the scope of this project.

## Load Balancing
While Kafka provides partitioning which can provide distribution of processing across many nodes, scalability (tens-of-thousands of nodes) is not a goal of this project. In Kafka terms, each SodaCan "topic" has only one partition. A typical system might have hundreds of devices. For a system with tens of tousands of devices, it is likely that rules would not be able to reason over all facts and that a tiered system would be needed. Such could be done with SodaCan as the intermediate tier, but that's not something I'm working on at this point.

## Authentication
Determining the identity of a user is required for most functions in SodaCan.
#### Persistence
As with other aspects of SodaCan, user account information is stored in Kafka. 
#### Realm and Username
Username is qualified by realm. Therefore, a username in one realm does not conflict with the same username in another domain. In many cases, only a single Realm is sufficient. A separate realm is used for testing in any case.

Username is not unique without realm to qualify it. For example, a user may be known by Joe123 in the facebook realm and joe456 in the Sodacan realm. In any case, Realm is prepended to the username when looking up permissions for the user and whenever else a username is looked up.

#### Password
The hashed password, password salt, the hash algorithm, and the time when the password was last changed are stored with user information. A password history is also stored for the user.
## Authorization
A user must be authorized to access various aspects of the SodaCan application. For example, one user might be able to only view various parameters while another may be able to change certain parameters. Therefore, each user must have a specified list of "permissions" which correspond to the permissions associated with various functions. Permissions are fine-grained. For example, a user might be able to read a specific parameter, such as water-level, but not to the pump turn-on time. In general, permissions go to the instance-level. That is, a user is not usually authorized to access all pump-controllers but rather specific pump controllers. Therefore, permissions are usually stated in the form "well-pump-controller" or sometimes "well-A-pump-controller"
#### User Role
As a convenience, a user may be given a "role" which is a name for a group of one or more permissions. The actual permissions granted to a user in a certain role is determined at login. This allows the definition of a role to change over time without having to modify all of a user's permissions.
#### Persistence
As with other aspects of SodaCan, user authorization is stored in Kafka. Unlike authentication, authorization is stored together for all users regardless of the realm used to authentication the identity of the user. 
#### Permission Check
A user can also be granted specific permissions though this is less frequent.

When a user logs in, some information about the user is copied into the session object for that user. This includes all of the permissions for that user, both explicit and implied (via role).

