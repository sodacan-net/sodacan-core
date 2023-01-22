package test.net.sodacan.api.kafka.admin;



import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import net.sodacan.api.kafka.admin.TopicAdmin;


public class TestTopicAdmin {
	private final static Logger logger = LogManager.getLogger();
	List<String> brokers = new ArrayList<>();

	
//	@Test
	public void noLongerATest() throws InterruptedException, ExecutionException, TimeoutException {

		TopicAdmin topicAdmin = new TopicAdmin();
		
		Properties props = new Properties();
		props.put("java.net.preferIPv4Stack", "true");
		props.put("bootstrap.servers", "soda6.eden:9092");
		 AdminClient adminClient = AdminClient.create(props);
		 
		 // Get information about the brokers that are running
		 DescribeClusterResult dcr = adminClient.describeCluster();
		 KafkaFuture<Collection<Node>> nodes = dcr.nodes();
		 List<Integer> brokers = new LinkedList<Integer>();
		 for (Node node : nodes.get()) {
			 System.out.println("Node: " + node);
			 brokers.add(node.id());
		 }
		 
		 // List the topics available
		 List<String> topicNames = new LinkedList<String>();
		 ListTopicsResult ltr = adminClient.listTopics();
		 for (String name : ltr.names().get()) {
			 System.out.println("Topic: " + name);
			 topicNames.add(name);
		 }
		 
		 // Describe each of those topics
		 DescribeTopicsResult dtr = adminClient.describeTopics(ltr.names().get());
		 KafkaFuture<Map<String, TopicDescription>> rslt = dtr.allTopicNames();
		 for (Entry<String,TopicDescription> entry: rslt.get(5, TimeUnit.SECONDS).entrySet()) {
			 System.out.println(entry.getKey() + entry.getValue());
		 }

		 // Show configuration settings for each topic
		 for (String topicName : topicNames) {
			 System.out.println(topicName + ":");
			 List<ConfigResource> cr = new LinkedList<ConfigResource>();
			 cr.add(new ConfigResource(ConfigResource.Type.TOPIC, topicName));
			 DescribeConfigsResult dfigr = adminClient.describeConfigs(cr);
			 for (Entry<ConfigResource, Config> entry : dfigr.all().get().entrySet()) {
				 for (ConfigEntry ce: entry.getValue().entries()) {
					 if (!ce.isDefault()) {
						 System.out.println("   " + ce.name() + ": " + ce.value());
					 }
				 }
			 }
		 }
	}

}
