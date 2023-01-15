package net.sodacan.api.kafka.admin;

import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

public class Admin {
	private AdminClient adminClient;
	private final static Logger logger = LogManager.getLogger();
	public Admin() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "soda6.eden:9092");
		adminClient= AdminClient.create(props);
	}
	public AdminClient getAdminClient() {
		return adminClient;
	}
	
}
