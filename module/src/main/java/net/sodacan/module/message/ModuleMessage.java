/*
 * Copyright 2023 John M Churin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sodacan.module.message;

import java.util.ArrayList;
import java.util.List;

import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Constraint;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.VariableDef.VariableDefBuilder;
import net.sodacan.module.variable.VariableDef.VariableType;

/**
 * Most Sodacan messages are in this format. This structure may be serialized into Json format for transmission. In any case, it's a flat 
 * structure to facilitate transmission.
 * This format may not be the wire format used by the underlying message bus. But it is how we pass messages into and out of modules.
 * A Sodacan message is, in essence, a wrapper around a variable. When a publish variable is changed, it is sent out as a message.
 * When a message is received due to a subscription, the value carried in the message replaces that variable of the module.
 * In summary: variable from module A to this message format, transmission, reception, and finally back to variable in module B
 * @author John Churin
 */
public class ModuleMessage {
	private String msgid;		// Offset in Kafka parlance
	private String timestamp;	// in UTC ISO format eg 1970-01-01T00:00:00Z
	private String producer;	// Sometimes is the same as the topic
	private String topic;
	private String namespace;	// or domain
	private String name;		// Variable name
	private String instance;	// Or null if none
	private String value;		// The value

	private ModuleMessage(ModuleMessageBuilder builder) {
		this.msgid = builder.msgid;			// Offset in Kafka parlance
		this.timestamp = builder.timestamp;	// in UTC ISO format eg 1970-01-01T00:00:00Z
		this.producer = builder.producer;	// Sometimes is the same as the topic
		this.topic = builder.topic;
		this.namespace = builder.namespace;	// or domain
		this.name = builder.name;			// Variable name
		this.instance = builder.instance;	// Or null if none
		this.value = builder.value;			// The value

	}

	public String getMsgid() {
		return msgid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getProducer() {
		return producer;
	}
	public String getTopic() {
		return topic;
	}
	public String getNamespace() {
		return namespace;
	}
	public String getName() {
		return name;
	}
	public String getInstance() {
		return instance;
	}
	public String getValue() {
		return value;
	}
	
	public static ModuleMessageBuilder newModuleMessageBuilder() {
		return new ModuleMessageBuilder();
	}
	public static class ModuleMessageBuilder {

			private String msgid;		// Offset in Kafka parlance
			private String timestamp;	// in UTC ISO format eg 1970-01-01T00:00:00Z
			private String producer;	// Sometimes is the same as the topic
			private String topic;
			private String namespace;	// or domain
			private String name;		// Variable name
			private String instance;	// Or null if none
			private String value;		// The value

			protected ModuleMessageBuilder() {
				
			}
			
			public ModuleMessageBuilder msgid(String msgid) {
				this.msgid = msgid;
				return this;
			}
			public ModuleMessageBuilder timestamp(String timestamp) {
				this.timestamp = timestamp;
				return this;
			}
			
			public ModuleMessageBuilder producer(String producer) {
				this.producer = producer;
				return this;
			}

			public ModuleMessageBuilder topic(String topic) {
				this.topic = topic;
				return this;
			}

			public ModuleMessageBuilder namespace(String namespace) {
				this.namespace = namespace;
				return this;
			}

			public ModuleMessageBuilder name(String name) {
				this.name = name;
				return this;
			}

			public ModuleMessageBuilder instance(String instance) {
				this.instance = instance;
				return this;
			}

			public ModuleMessageBuilder value(Value value) {
				this.value = value.serialize();
				return this;
			}

			/**
			 * Validate and create a new message
			 * @return
			 */
			public ModuleMessage build() {
				return new ModuleMessage(this);
			}

		}
	
}
