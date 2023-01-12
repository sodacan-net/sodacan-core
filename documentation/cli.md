# SodaCan Command Line Interface


```
soda <cmd> [args]

Commands:
  soda broker startup --config
  soda broker shutdown --config
  soda module compile --deploy   	Compile one or more modules
  soda module deploy	<module> -m <mode>				Deploy module(s)
  soda module test					Test modules
  soda create instance <module> <key>
  soda copy mode from <mode> to <mode>	The destination mode cannot exist
  soda delete mode <mode>            The named mode is completely removed from the system	
  soda list modules <pattern>
  soda list instances <module> <pattern>
  soda create topic <params>     	Create a new topic
  soda list topics <pattern>		
  soda normalize  normalize markdown in a sample (parse & draft)
  soda publish <topic> <key> <payload>
  soda subscribe	<topicPattern>		Wait for messages to arrive, ctrl-c to stop
  soda rewind <topic> <time>        Replay messages from a specific time
  soda delete topic <topic>
  soda delete messages <topic> before/after <timestamp> Delete messages either before or after the specified time

Options:
  --mode <mode>  Specify which mode this action affects                 [string]
  --version      Show version number                                   [boolean]
  --verbose, -v                                                 [default: false]
  --help         Show help                                             [boolean]
```

### Mode
When mode is specified on a command-line, it means that the action, such as deploying a module, will be separate from the actions in other modes.
This is often used for testing. For a new or updated module, for example, you may want to test it in the real system yet keep the
current configuration as is.

- make a copy of the default mode to a new mode name
- deploy one or more new modules with *-m <mode>* option
- Using the cli, you can send and receive messages to this new mode by including option:
