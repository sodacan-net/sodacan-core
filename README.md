# SodaCan
This is a first cut at plant automation using Java 8+, Apache Kafka and JBoss Drools. This is probably overkill for Home Automation but it does provide an alternative to those systems that depend on a commercial cloud service. If you are looking for something that has all of fault-tolerance of a cloud-based service (Amazon, Google, etc) in an in-house package, this may be a solution. 

Each "device" should be a microcontroller capable of running Java. BeagleBone Black or Green would be appropriate.
The central control, what I'm calling the SodaCan, is a Drools rule engine that manages events and device state. Persistence, fault-tolerance, and communication is provided by Apache Kafka. 

At least three physical servers should provide sufficient redundancy that will provide availability and reliability. 

Kafka provides all state persistence as well as reliable, and fast, message delivery. Leader election is provided by Zookeeper which also manages distributed configuration.


