package net.sodacan.api.kafka.admin;

import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.LoggerFactory;

public class Admin {
	private AdminClient adminClient;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Admin.class);
	public Admin() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "soda6.eden:9092");
		adminClient= AdminClient.create(props);
	}
	public AdminClient getAdminClient() {
		return adminClient;
	}
	
}
