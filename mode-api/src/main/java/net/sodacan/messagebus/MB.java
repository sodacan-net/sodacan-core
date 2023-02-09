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
package net.sodacan.messagebus;

import java.util.List;
/**
 * Sodacan-specific interface to the "Message Bus"
 * @author John Churin
 */
public interface MB {
//	/**
//	 * MB is intended to be a singleton so this message supports that.
//	 * @return and instance of MB
//	 */
//	public MB getInstance();
	
	/**
	 * Return a list of known topics
	 * @return List of topic known to message bus
	 */
	public List<String> listTopics();

	/**
	 * Prvide detail about a topic
	 * @param topicName
	 * @return String containing topic details
	 */
	public String describeTopic( String topicName );

	/**
	 * Add a topic to the message bus
	 * @param topicName
	 * @return true if topic created, false if it already exists
	 */
	public boolean createTopic(String topicName, boolean compacted);

	/**
	 * Delete a topic from message bus
	 * @param topicName
	 */
	public void deleteTopic(String topicName);
	/**
	 * Return the requested topic, ready for consumption. 
	 * @param nextOffset The first offset to be read will be this. zero is common except in cases where offset is stored elsewhere.
	 * @return A topic
	 */
	public MBTopic openTopic(String topicName, long nextOffset);

	/**
	 * Produce a new record on the specified topic
	 * @param topicName
	 * @param key
	 * @param value
	 */
	public void produce( String topicName, String key, String value);
	
}
