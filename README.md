# SodaCan
This is a proof-of-concept for plant automation using Java 8+, Apache Kafka and JBoss Drools. It is probably overkill for Home Automation but it does provide a lan-based alternative to those systems that depend on a commercial cloud service. If you are looking for something that has all of the fault-tolerance of a cloud-based service (Amazon, Google, etc) in an in-house package, this may eventually be a solution. 

## Device Controllers
Each "device" should be a microcontroller capable of running Java. BeagleBone Black or Wireless BeagleBone Green would be appropriate.

## Servers
The central servers control device behavior. SodaCan rules use a JBoss Drools rule engine that manages events and device state. Persistence, fault-tolerance, and communication is provided by Apache Kafka. 

At least three physical servers should provide sufficient redundancy. While my servers are in a single rack, they could be more distributed to improve reliability and availability. To provide additional modularity, each of the major components can run in a Docker container. This allows a mix of more logical servers on fewer physical servers and allows replacement of a server (logical or phyisical) with less trouble.  

## Peristence
Kafka provides all state persistence as well as fast, reliable message delivery. Leader election is provided by Zookeeper which also manages distributed configuration. There's really no need for a reliable disk configuration due to efficient replication and failover. As a general philosophy, if a server should fail for any reason, it should simply be wiped clean and rebuilt. There should be no need for backups or for any downtime.

## Cabling
I'm running normal Cat-6 cable to each device controller (BeagleBone). In the case of lighting,  I run traditional RS-485 from a server to any number of DMX-based lighting devices, most of which are custom-made.

## Leader Election
Kafka handles leader election among the brokers. However, for the SodaCan itself (the rule engine) there should only be one instance running at a time since rules should reason over the entire domain of facts available to it. Nevertheless, it is desirable to have more than one SodaCan running at the same time should the current leader fail. There are two approaches: 1. The SodaCan is completely idle and essentially only synchronizes when it becomes leader. 2. A non-leader SodaCan can listen to the same messages as the leader without taking any actions based on those messages in order to stay as synchronized as possible. 

Once a given SodaCan becomes the leader, it remains the leader until it is killed or it dies. For this reason, the only thing that a leading SodaCan can do it it loses leadership is to exit.

## Indistrial Standards
This approach is much less compact than a protocol such as MODBUS. Nevertheless, it is relatively compact and provides built-in security, error detection, failover, etc. This project makes no attempt to comply with the SCADA standard. There are products available that support conversion between various standards. That function is beyond the scope of this project.
