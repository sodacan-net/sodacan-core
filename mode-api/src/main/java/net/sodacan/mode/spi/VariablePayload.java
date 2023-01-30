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
package net.sodacan.mode.spi;

import java.time.Instant;

/**
 * <p>This class is used to transmit a variable metadata to and from plugins. The same format can be used for publish-subscribe (MessageBus) and
 * for save/restore state (State Store) plugins.
 * <p>Metadata needed to store a variable includes:
 * <ul>
 * <li>MsgId - A sequential number assigned by the plugin. (Offset in Kafka terms)</li>
 * <li>Timestamp - Supplied from Sodacan, don't use wall clock time which could be different</li>
 * <li>Mode - already available to the plugin but should be stored if a plugin handles multiple modes.</li>
 * <li>Producer - Name of the module producing the variable.</li>
 * <li>Topic - This is either the Module name or a standalone topic.</li>
 * <li>Variable name - unique within a module</li>
 * <li>Instance key - Included when the module has instances</li>
 * </ul>
 * The payload for a variable, in JSON format, consists of the definition of the variable and the serialized content (value) of the variable.</P>
 * @author John Churin
 *
 */
public class VariablePayload {
	private long msgId;
	private Instant timestamp;
	private String mode;
	private String producer;
	private String topic;
	private String variableName;
	private String instanceKey;
	private String content;

	private VariablePayload( VariablePayloadBuilder vpb) {
		this.msgId = vpb.msgId;
		this.timestamp = vpb.timestamp;
		this.mode = vpb.mode;
		this.producer = vpb.producer;
		this.topic = vpb.topic;
		this.variableName = vpb.variableName;
		this.instanceKey = vpb.instanceKey;
		this.content = vpb.content;
	}
	
	public long getMsgId() {
		return msgId;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public String getMode() {
		return mode;
	}

	public String getProducer() {
		return producer;
	}

	public String getTopic() {
		return topic;
	}

	public String getVariableName() {
		return variableName;
	}

	public String getInstanceKey() {
		return instanceKey;
	}

	public String getContent() {
		return content;
	}

	public static VariablePayloadBuilder newVariablePayloadBuilder() {
		return new VariablePayloadBuilder();
	}
	
	public static class VariablePayloadBuilder {
		private long msgId;
		private Instant timestamp;
		private String mode;
		private String producer;
		private String topic;
		private String variableName;
		private String instanceKey;
		private String content;

		protected VariablePayloadBuilder() {
			
		}
		
		public VariablePayloadBuilder msgId(long msgId) {
			this.msgId = msgId;
			return this;
		}
		
		public VariablePayloadBuilder timestamp(Instant timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		public VariablePayloadBuilder timestamp(String timestamp) {
			this.timestamp = Instant.parse(timestamp);
			return this;
		}

		public VariablePayloadBuilder mode(String mode) {
			this.mode = mode;
			return this;
		}
		public VariablePayloadBuilder producer(String producer) {
			this.producer = producer;
			return this;
		}
		public VariablePayloadBuilder topic(String topic) {
			this.topic = topic;
			return this;
		}
		public VariablePayloadBuilder variableName(String variableName) {
			this.variableName = variableName;
			return this;
		}
		public VariablePayloadBuilder instanceKey(String instanceKey) {
			this.instanceKey = instanceKey;
			return this;
		}
		public VariablePayloadBuilder content(String content) {
			this.content = content;
			return this;
		}
		
		public VariablePayload build() {
			return new VariablePayload(this);
		}
	}
}
