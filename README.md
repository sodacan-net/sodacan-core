# SodaCan
This is a proof-of-concept for plant automation using Java 8+, Apache Kafka and JBoss Drools. It is probably overkill for Home Automation but it does provide a lan-based alternative to those systems that depend on a commercial cloud service. If you are looking for something that has all of the fault-tolerance of a cloud-based service (Amazon, Google, etc) in an in-house package, this may eventually be a solution. 

## Device Controllers
Each "device" should be a microcontroller capable of running Java. BeagleBone Black or Wireless BeagleBone Green would be appropriate.

## Servers
The central servers control device behavior. The SodaCan, base on JBoss Drools rule engine, contains rules that manages events, device state, alerts, heartbeat, delays, etc. Persistence, fault-tolerance, commumication security, and communication is provided by Apache Kafka. 

At least three physical servers should provide sufficient redundancy. While my servers are in a single rack, they could be more distributed to improve reliability. 

The logical organization of servers is: Three Zookeeper servers, Three Kafka Brokers, and three SodaCans. Since all three servers can be expected to be up most of the time, it is best that each of these three major components runs on eack of the physical servers. Of course in the case of a failure, the service running on the failed box will shift to one of the remaining boxes.

Using the 3x3 configuration described above, one could in theory run these on nine separate boxes. But that would leave 6 boxes idle most of the time. In any case, Docker containers are used to represent each of these nine logical boxes so that they can be deployed over three servers, or on AWS or similar service if desired. Should a phyical box go down, the Docker container can simply be run on a different box.  

## Peristence
Kafka provides all state persistence for SodaCan as well as fast, reliable message delivery. Leader election is provided by Zookeeper which also manages distributed configuration information. There's really no need for a reliable disk configuration due to efficient replication and failover. As a general philosophy, if a server should fail for any reason, it should simply be wiped clean and rebuilt. There should be no need for backups or for any downtime. This philosophy also applies to the disk files used by Kafka: If a server goes down, the kafka files can be wiped. When Kafka restarts, they will be 

## Cabling
I'm running normal Cat-6 cable to each device controller (BeagleBone). In the case of lighting,  I run traditional RS-485 from a server to any number of DMX-based lighting devices, most of which are custom-made.

## Indistrial Standards
This approach is much less compact than a protocol such as MODBUS. Nevertheless, it is relatively compact and provides built-in security, error detection, failover, etc. This project makes no attempt to comply with the SCADA standard. There are products available that support protocol conversion between various standards. That function is beyond the scope of this project.

## Load Balancing
The primary objective is to provide high-performance reliability. While Kafka provides partitioning which can provide distribution of processing across many systems, scalability (tens-of-thousands of nodes) is not a goal of this project. In Kafka terms, each "topic" has only one partition. A typical system might have hundreds of devices. For a system with tens of tousands of devices, it is likely that rules would not be able to reason over all facts and that a tiered system would be needed. Such could be done with SodaCan as the intermediate tier, but that's not something I'm working on at this point.
