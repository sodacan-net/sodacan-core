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
