# Sodacan Internals
This document describes how Sodacan source code is maintained and provides a closer look at the various system components.
The reader should be familiar with:

- Java 11+
- Apache Maven
- Apache Kafka and publish-subscribe in general
- <a href="architecture.md">Sodacan Architecture</a>
- The <a href="moduleReference.md">Sodacan Module Reference</a>

### Clone Repository

```
    git clone https://github.com/sodacan-net/sodacan-core.git
```

### Build from source
Maven will take care of all dependent libraries during the build.

```
    cd sodacan-core
    mvn clean install

```

### Create the bin Directory
After a clean install of the top-level project, the Windows and Linux scripts that run executables should be generated. For the CLI, the resulting files are `soda` and `soda.bat`.

```
    mvn -pl cli appassembler:assemble

```

### Configuration File
The configuration file contains connection information for Kafka and listener configuration for the web server.

### Running the command line tool (soda)

```
    cli/bin/soda
  or  
    cli\bin\soda.bat
```

### Initializing Kafka
Sodacan needs to create several topics to get started. This is done using the initialize command. This is not a harmful command: It can be repeated should things change. If it has already created a particular topic, then it just sjips that step.

```
	cli/bin/soda initialize
```

You can view the topics with:

```
	cli/bin/soda topic list
```

