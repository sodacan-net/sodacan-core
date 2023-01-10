
3 Control
A declarative language for controlling things such as lights and pumps from stimulus such as buttons, switches, and other sensors.


```// Comment
MODULE test
	SUBSCRIBE ns.module.mode {on,off} as <simpleName>	// ns assumed to be us if not specified
	PUBLIC state {off,on}
	PRIVATE count 0
	PRIVATE price 0.95
	ON price
	  WHEN 
	ON mode.on
	ON mode.off
	  THEN state.off
	AT 10 minutes before sunset
	  THEN blow.nose

```