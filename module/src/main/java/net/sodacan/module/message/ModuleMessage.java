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

import net.sodacan.module.value.Value;
import net.sodacan.module.variable.Variable;

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

}
