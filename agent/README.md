# Sodacan Agent
The Sodacan agent provides a framework around the execution of one or more `modules`. 

The Sodacan agent is mostly a thin wrapper around the Sodacan runtime. It manages the top-level list of modules handled by that agent and creates the thread used by each module/runtime.

As with other aspects of Sodacan, modules are organized by *mode* in an agent. So, it is possible that one agent is hosting the same module in two different modes. One being completely independent from the other, including, possibly, different versions of the module in each mode.

## Module-Topic
A Sodacan topic is a channel for publish-subscribe messages between modules. However, module name is not sufficient to "name" a topic. In Sodacan, a topic is identified by Mode name, and Module name, and instance name, if any. This kind of topic is referred to as a module-topic. The distinction between module-topic and module is important but in most of the documentation, we use module and topic interchangeably.

## Module-Topic Partitioning
Module-topics are not and cannot be partitioned because a `module` is a sequential processor. Partitioning would split the stream of events to multiple parallel modules which would break the sequential processing model per module.

## Module-Master-Topic
With an exception described below, all agents subscribe to all module-topics. But, how does an agent learn of the list of module-topics?

All agents subscribe to the Module-Master-Topic to get a list of all known modules in Sodacan. The agent then subscribes to all of the topics listed in the Module-Master-Topic. It also continues to poll the module-master-topic for changes and adjusts its subscription list accordingly. When this list changes, a shuffle occurs. No modules will be running during the shuffle. However, this shuffle usually happens quickly. Interestingly, with one exception, after the shuffle, a module may be running on a different agent. This entire process is transparent to the module.

## Mode-Master-Topic
We left a little out so far. Where does mode comes from, since it is critical to the identity of a module. There is another topic called the Mode-Master-Topic which contains a list of all known Modes. This list is also monitored by the agent in case a new mode is added. 

## Subscription vs Assignment
Even though an agent subscribes to *all* topics, it may only receive messages for *some* of the topics. For example, if two agents are running, then each agent will receive half of the topics. If ten agents are running, they each get one tenth of the topics. This distribution of modules is dynamic.

## Dynamic Topic Assignment
If an agent should become unavailable for any reason, Sodacan will shuffle the topics among the remaining agents. One machine can run any number of agents and there can be any number of machines running agents in a cluster. So, adding more servers provides fault tolerance and adds throughput for larger configurations. 

## The Exception - Named Agents
If a module is associated with a specific device-controller, then it must run on that controller. The module names the agent that it needs to run on and the agent specifies its name so that it *only* activates those modules on this agent. Additionally, those modules that have an affinity for a specific named-agent are excluded from the collection of module-topics that can be shuffled among available un-named agents.

If a named-agent is not running, then messages subscribed to by those modules running on that agent will be delivered when the agent resumes.

