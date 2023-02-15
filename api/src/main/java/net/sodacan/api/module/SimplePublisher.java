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
package net.sodacan.api.module;

import net.sodacan.messagebus.MB;
import net.sodacan.mode.Mode;
import net.sodacan.module.ModuleMethods;
import net.sodacan.module.value.Value;

/**
 * <p>This publisher does not require that the module it belongs to exists or not.
 * Two scenarios where this method of publishing is useful:</p>
 * <ul>
 * <li>You are using this API to publish from an external source. In this case, the module will probably never exist.</li>
 * <li>During module development, you may want to send an event (variable) to a module under development 
 * without having to worry about the other module(s) that will eventually be publishing the event.</li>
 * </ul>
 * @author John Churin
 *
 */
public class SimplePublisher {
	private Mode mode;
	
	public SimplePublisher( Mode mode ) {
		this.mode = mode;
	}

	/**
	 * <p>Send off the message. The topic receiving the event must already exist. In other words, at least one module
	 * has to subscribe to the topic before you can send to it. Also, the variable name should match one of the module's subscriber statements.
	 * While not required, the value without a matching variable name will not get processed until a subscription for that module and that variable does exist.</p>
	 * <p>Fine Point: When a module consumes events from a topic, it consumes all messages from a topic (module name). If a variable does 
	 * not match a subscribed variable, the message is skipped, forever. The only way to replay 
	 * skipped variables is to add a subscription to the skipped variable and then replay the entire set of variables from that module.</p>
	 * 
	 * @param fullModuleName
	 * @param variable
	 * @param value
	 */
	public void publish(String fullModuleName, String variable, String value) {
		String topicName = ModuleMethods.getModulePublishTopicName(mode.getModeName(), fullModuleName);
		MB mb = mode.getMB();
		Value v = new Value(value);
		mb.produce(topicName,
				ModuleMethods.getVariableKeyName(variable), 
				v.serialize());
	}
}
