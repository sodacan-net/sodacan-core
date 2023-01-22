# Message Structure
This project is all about the message as both in-flight and at-rest data structure. The objects in this project are also used as the basis for serialization and deserialization of messages into json, XML, and similar formats.

This also provides the interface definitions for a message bus implementation (eg Kafka).

The message structure object are POJOs with little to no external dependencies. Messages are serialized to/from  Json.
