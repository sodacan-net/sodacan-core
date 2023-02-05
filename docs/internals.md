# Sodacan Internals
This document describes how Sodacan source code is maintained and provides a closer look at the various system components.
The reader should be familiar with:

- Java 11+
- Apache Maven
- Apache Kafka and publish-subscribe in general. See <a href="gettingStarted.md">Getting Started</a> for instructions on how to install
Kafka. (It will be a standard install).
- <a href="architecture.md">Sodacan Architecture</a>
- The <a href="moduleReference.md">Sodacan Module Reference</a>

### Clone Repository
Clone the Sodacan repository. To do this, you should be in a folder where you keep git working trees. This folder 
should *not* have a `sodacan-core` folder.

Once downloaded, you can `cd` to the `sodacan-core` parent project. This will have the top-level pom for Sodacan.

```
    git clone https://github.com/sodacan-net/sodacan-core.git
    cd sodacan-core
    
```
 
### Build from source
Maven will take care of all dependent libraries during the build.

```
    mvn clean install

```

### The bin Directory
After a clean install of the top-level project, the Windows and Linux scripts that run executables will have been generated. For the CLI, the resulting files are `soda` and `soda.bat` in the bin/ folder of the cli project.

### Configuration File
The configuration file contains connection information for Kafka and listener configuration for the web server. Various other configuration details.
It is located in the config/ folder of the cli project.

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

