# SodaCan Command Line Interface


```
soda <cmd> [args]

Commands:
  soda broker startup --config
  soda broker shutdown --config
  soda module compile --deploy   	Compile one or more modules
  soda module deploy			Deploy modules
  soda module test			Test modules
  soda create instance <module> <key>
  soda list modules <pattern>
  soda list instances <module> <pattern>
  soda create topic <params>     create a new topic
  soda list topics <pattern>
  soda normalize  normalize markdown in a sample (parse & draft)
  soda publish <topic> <key> <payload>
  soda subscribe	<topic>			Wait for messages to arrive

Options:
  --version      Show version number                                   [boolean]
  --verbose, -v                                                 [default: false]
  --help         Show help                                             [boolean]
```