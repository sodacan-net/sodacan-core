package net.sodacan.api.kafka.admin;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TopicAdmin extends Admin {
    private static Logger logger = LoggerFactory.getLogger(TopicAdmin.class);
    private static final int PARTITIONS = 1;
	private static final short REPLICAS = 3;
	private static final int NUMBER_OF_TRIES = 5;
	private static final int WAIT_SECONDS = 5;
	 
	 public TopicAdmin() {
		 super();
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
	 public String describeTopic( String topic ) throws Exception {
		 // Describe each of those topics
		 DescribeTopicsResult dtr = getAdminClient().describeTopics(Arrays.asList(topic));
		 KafkaFuture<Map<String, TopicDescription>> rslt = dtr.all();
		 StringBuffer sb = new StringBuffer();
		 for (Entry<String,TopicDescription> entry: rslt.get(5, TimeUnit.SECONDS).entrySet()) {
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
			 for (ConfigEntry ce: entry.getValue().entries()) {
				 if (!ce.isDefault()) {
					 sb.append("   ");
					 sb.append(ce.name());
					 sb.append(": ");
					 sb.append(ce.value());
					 sb.append('\n');
				 }
			 }
		 }
		 return sb.toString();
	 }

	 public List<String> listTopicsForSuffix(String suffix) {
		 String dashSuffix = "-" + suffix;
		 try {
			// List the topics available
			 List<String> topicNames = new LinkedList<String>();
			 ListTopicsResult ltr = getAdminClient().listTopics();
			 for (String name : ltr.names().get()) {
				 if (name.endsWith(dashSuffix)) {
					 topicNames.add(name);
				 }
			 }
			return topicNames;
		} catch (Exception e) {
			throw new RuntimeException("Unable to list topics with suffix " + suffix, e);
		}
	 }
	 /**
	  * This method will wait for the topics to be created before returning. It can also be used to
	  * verify that topics exist. In any case, this method may wait for several seconds before returning.
	  * THIS METHOD IS OBSOLETE
	  * @param topics list of topic names to wait for
	  * @return true if the topics exist, otherwise false
	  */
	 public boolean waitForTopics(List<String> topics ) {
		 for (int x = 0; x < NUMBER_OF_TRIES; x++) {
			 List<String> topicsFound = listTopics();
			 int numberFound = 0;
			 for (String topic : topics) {
				 if (topicsFound.contains(topic)) {
					 numberFound++;
				 }
			 }
			 if (topics.size()==numberFound) {
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
	  * Create one or more topics. This method will request that the topics be created and will 
	  * wait for the completion up to WAIT_SECONDS at with point it will throw an exception if unsuccessful.
	  * The number of partitions (1) and number of replicas (3) are FIXED for the moment.
	  * In the case of events, the number of partitions should always be 1 (per suffix) since our rule engine
	  * must be able to reason over all states and events (for a given suffix).
	  * @param topics list of topic names to be created
	  */
	 public void createTopics(List<String> topics ) {
		 List<NewTopic> newTopics = new ArrayList<NewTopic>();
		 for (String topic : topics) {
			 newTopics.add(new NewTopic(topic, PARTITIONS, REPLICAS));
		 }
		 CreateTopicsResult ctr = getAdminClient().createTopics(newTopics);
		 try {
			 KafkaFuture<Void> f = ctr.all();
			 f.get(WAIT_SECONDS, TimeUnit.SECONDS);
			if (f.isDone()) {
				return;
			}
			throw new RuntimeException("Create topic(s) timed out");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new RuntimeException("Create topic(s) failed", e);
		}
	 }
	 
	 /**
	  * Create state, event, and control topics with the specified suffix. This method will not return until the topics have been created.
	  * @param suffix
	  */
	 public void createTopic( String topic) {
		 List<String> topics = new ArrayList<String>();
		 topics.add(topic);
		 createTopics(topics);
	 }
	 
	 public boolean deleteTopics( List<String> topics ) {
		 DeleteTopicsResult dtr = getAdminClient().deleteTopics(topics);
		 try {
			 KafkaFuture<Void> f = dtr.all();
			 f.get(WAIT_SECONDS, TimeUnit.SECONDS);
			if (f.isDone()) {
				return true;
			}
			logger.warn("Delete topic(s) timed out");
			return false;
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.warn("Delete topic(s) {} failed {}",topics, e.getMessage());
			return false;
		}
	 }
	 
}
