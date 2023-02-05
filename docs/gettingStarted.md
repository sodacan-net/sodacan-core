# Getting Started
This guide will show you how to get a Sodacan installation up and running. The major components are as follows:

- Java 11+
- Apache Kafka 3.3+
- Sodacan binary distribution

Note: Kafka is more than a messaging system. It is also the "source-of-truth" for the operation of Sodacan. Almost all configuration as well as all operation data is stored in many different *topics* in Kafka. By way of explanation, one could, in theory, delete Sodacan and re-install and all data and configuration would be preserved as long as the Kafka *log files* are not disturbed.

For this reason, in production, it is advisable to configure Kafka for a replication factor of at least three. Replicas should be on different hardware. If possible, these machines should be connected to different power sources: UPS, Solar, etc. Likewise, the network hardware should be as reliable as possible. Second priority is connectivity to the Internet, depending on nerds of the configuration.

For demonstration purposes, a single replica of Kafka, Zookeeper (part of Kafka), and one or two Sodacan Agents, and the Sodacan web server can all run on a single machine.
Another approach for demonstration purposes is to use a Docker approach which eliminates a lot of the work involved in getting Sodacan working.

### Docker Containers

This is a single-node setup, sufficient for demonstration.
Uses:

- Docker
- DockerCompose

Containers:

- Zookeeper
- Kafka
- Sodacan Web Server
- Sodacan Agent(s)
- Sodacan Command line tool

You will probably need to make adjustments to the `docker-compose.yaml` configuration file but once that's done, you should be ready to go.
To start the docker containers:

```
    docker-compose up
    
```
That's it.

The command line tool, described elsewhere, will need to be invoked a bit differently:

```
   docker run --rm --name soda:latest -- <normal soda commands go here>
   
```

### Directory Structure
After Sodacan is installed in a non-container environment, it should have a directory structure similar to the following:

```
      sodacan/
         |
         +---- bin/
         +---- config/
         +---- lib/
         +---- docs/
         +---- html/
         |       |
         |       +----css/
         |       +----js/
```

### Linux Installation

### Windows installation

## Initial Configuration
Using the command line tool, you can start up the Sodacan message bus (broker).