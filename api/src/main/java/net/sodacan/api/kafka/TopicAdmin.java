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
package net.sodacan.api.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.SodacanException;

public class TopicAdmin extends Admin {
	private final static Logger logger = LoggerFactory.getLogger(TopicAdmin.class);
	private static final int PARTITIONS = 1;
	private static final short REPLICAS = 1;
	private static final int NUMBER_OF_TRIES = 5;
	private static final int WAIT_SECONDS = 5;
	private static TopicAdmin instance = null;
	private TopicAdmin() {
		super();
	}
	/**
	 * TopicAdmin is a singleton
	 * @return An instance of TopicAdmin
	 */
	public static TopicAdmin getInstance() {
		if (instance==null) {
			instance = new TopicAdmin();
		}
		return instance;
	}
	
	public List<String> listTopics() {
		try {
			// List the topics available
			List<String> topicNames = new LinkedList<String>();
			ListTopicsResult ltr = getAdminClient().listTopics();
			for (String name : ltr.names().get()) {
				topicNames.add(name);
			}
			return topicNames;
		} catch (Exception e) {
			throw new RuntimeException("Unable to list topics", e);
		}
	}

	public String describeTopic(String topic) throws Exception {
		// Describe each of those topics
		DescribeTopicsResult dtr = getAdminClient().describeTopics(Arrays.asList(topic));
		KafkaFuture<Map<String, TopicDescription>> rslt = dtr.all();
		StringBuffer sb = new StringBuffer();
		for (Entry<String, TopicDescription> entry : rslt.get(5, TimeUnit.SECONDS).entrySet()) {
			sb.append(entry.getKey());
			sb.append('=');
			sb.append(entry.getValue());
			sb.append('\n');
		}

		// Show configuration settings for each topic
		List<ConfigResource> cr = new LinkedList<ConfigResource>();
		cr.add(new ConfigResource(ConfigResource.Type.TOPIC, topic));
		DescribeConfigsResult dfigr = getAdminClient().describeConfigs(cr);
		for (Entry<ConfigResource, Config> entry : dfigr.all().get().entrySet()) {
			for (ConfigEntry ce : entry.getValue().entries()) {
				if (!ce.isDefault()) {
					sb.append("   ");
					sb.append(ce.name());
					sb.append(": ");
					sb.append(ce.value());
					sb.append('\n');
				}
			}
			for (ConfigEntry ce : entry.getValue().entries()) {
				if (ce.isDefault()) {
					sb.append(" D ");
					sb.append(ce.name());
					sb.append(": ");
					sb.append(ce.value());
					sb.append('\n');
				}
			}
		}
		return sb.toString();
	}

	/**
	 * This method will wait for the topics to be created before returning. It can
	 * also be used to verify that topics exist. In any case, this method may wait
	 * for several seconds before returning. THIS METHOD IS OBSOLETE
	 * 
	 * @param topics list of topic names to wait for
	 * @return true if the topics exist, otherwise false
	 */
	public boolean waitForTopics(List<String> topics) {
		for (int x = 0; x < NUMBER_OF_TRIES; x++) {
			List<String> topicsFound = listTopics();
			int numberFound = 0;
			for (String topic : topics) {
				if (topicsFound.contains(topic)) {
					numberFound++;
				}
			}
			if (topics.size() == numberFound) {
				return true;
			}
			logger.debug("Waiting for topics to be created, attmept {}", x);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				return false;
			}
		}
		logger.debug("Giving up waiting for topics to be created");
		return false;
	}

	/**
	 * <p>Create one or more topics. This method will request that the topics be
	 * created and will wait for the completion up to WAIT_SECONDS at with point it
	 * will throw an exception if unsuccessful. The number of partitions (1) and
	 * number of replicas (3) are FIXED for the moment. In the case of events, the
	 * number of partitions should always be 1 (per suffix) since our rule engine
	 * must be able to reason over all states and events (for a given suffix).</p>
	 * <p>A compacted topic means that only the most recent version of each key is needed.
	 * An uncompacted topic will retain records forever.</p>
	 * 
	 * @param topics list of topic names to be created
	 * @param compacted If true, a compacted topic is created (old records deleted)
	 */
	public boolean createTopics(List<String> topics, boolean compacted) {
		Map<String,String> configs = new HashMap<>();
		if (compacted) {
			configs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT);
		} else {
			configs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
			configs.put(TopicConfig.RETENTION_BYTES_CONFIG, "-1");
			configs.put(TopicConfig.RETENTION_MS_CONFIG, "-1");
		}
		List<NewTopic> newTopics = new ArrayList<NewTopic>();
		for (String topic : topics) {
			newTopics.add(new NewTopic(topic, PARTITIONS, REPLICAS).configs(configs));
		}
		CreateTopicsResult ctr = getAdminClient().createTopics(newTopics);
		try {
			KafkaFuture<Void> f = ctr.all();
			f.get(WAIT_SECONDS, TimeUnit.SECONDS);
			if (f.isDone()) {
				return !f.isCompletedExceptionally();
			}
			throw new RuntimeException("Create topic(s) timed out");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.debug("Create topic(s) " + topics + " failed");
			return false;
		}
	}

	/**
	 * Create state, event, and control topics with the specified suffix. This
	 * method will not return until the topics have been created.
	 * 
	 * @param suffix
	 */
	public boolean  createTopic(String topic, boolean compacted) {
		List<String> topics = new ArrayList<String>();
		topics.add(topic);
		return createTopics(topics, compacted);
	}

	public boolean deleteTopics(List<String> topics) {
		DeleteTopicsResult dtr = getAdminClient().deleteTopics(topics);
		try {
			KafkaFuture<Void> f = dtr.all();
			f.get(WAIT_SECONDS, TimeUnit.SECONDS);
			if (f.isDone()) {
				return true;
			}
			throw new SodacanException("Delete topic(s) timed out");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new SodacanException("Delete topic(s) " + topics + " failed", e );
		}
	}

	public void deleteTopic(String topic) {
		List<String> topics = new ArrayList<String>();
		topics.add(topic);
		deleteTopics(topics);
	}

}
