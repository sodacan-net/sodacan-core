# SodaCan
First cut at plant automation using Java 8+, Apache Kafka and Drools.
Each "device" should be a microcontroller capable of running Java. BeagleBone Black or Green would be appropriate.
The central control, what I'm calling the SodaCan, is a Drools rule engine that manages events and device state. Persistence, fault-tolerance, and persistence is proviced by Apache Kafka. The central systems can be distributed as needed. Three replicas of everything should provide sufficient availability and reliability.


