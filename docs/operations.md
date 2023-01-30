# Operations Guide
### Deployment Modes
When its time to roll out a new or updated module, you might want to do a final test on the live system without affecting the live system. To do this, the soda command line tool can be used to initiate a "copy" of the current (default) mode to a separate mode probably named something like "test-new-light-control". But we'll use "test3" in the examples below.

The copy operation is comprehensive. In particular, topics use the same name as before with the mode name appended. Modules are also renamed with the mode appended to the module name. 

To aid in development and testing, a mode can also have a manually operated clock rather than a real-time clock. A new mode starts life with a frozen clock set at a time that is unrealistic. Therefore, any module that reacts to time events will not activate.

For normal operation, the clock for a mode is normally set to "live". That is, the time base used to determine when an `AT` statement is triggered by the passage of time of the actual wall-clock.  This can be done with the soda command-line tool:

```
  soda mode clock test3 live

```
However, for testing or debugging, a live clock is not very helpful. Let's say it's July and you want to test a module that does something on new year's day. You don't want to wait until January to test it and changing the system clock is not a good idea. 

In the command-line tool, you can manually control the clock for a given mode. In the following, we set the clock to midnight on new year's day in our testing mode.

```
  soda mode create test3
  soda module deploy test3 myNewModule
  soda clock test3 1-jan-2024 00:00

```
In this manual mode, the clock is only advanced when you set the clock to a different time.

### Sodacan Configuration
Each Sodacan agent needs a configuration file which, at a minimum determines how to connect to the message bus.

The Web Server also needs a configuration which, in addition to the above, specifies which port to listen on for the RESTful API.

### Kafka Configuration
Port usage

Broker ID should be non-zero for distributed

Producer linger setting should be set to 0ms. This application is more about responsiveness than throughput.
