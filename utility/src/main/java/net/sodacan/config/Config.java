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
package net.sodacan.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import net.sodacan.SodacanException;

public class Config {
	private final static Logger logger = LoggerFactory.getLogger(Config.class);

	private static Config instance = null;

	private WebServer webServer;
	private Rules rules;
	private Location location;
	private Kafka kafka;
	private List<ConfigMode> modes = new LinkedList<>();
	
	private Config() {
		
	}
	/**
	 * True if config file has already been read and config is initialized.
	 * @return True if config file has already been read and config is initialized.
	 */
	public static boolean isInitialized() {
		return (instance!=null);
	}
	
	public static Config getInstance() {
		if (instance==null) {
			instance = new Config();
		}
		if (instance == null) {
			throw new RuntimeException("Config not initialized");
		}
		return instance;
	}
	
	public static Config init(String filename) throws SodacanException {
		try {
			logger.debug("Opening config file: {}", filename);
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			instance = mapper.readValue(new File(filename), Config.class);
			return instance;
		} catch (Exception e) {
			throw new SodacanException("Error initializing configuration", e);
		}
	}

	public WebServer getWebServer() {
		return webServer;
	}

	public void setWebServer(WebServer webServer) {
		this.webServer = webServer;
	}

	public Rules getRules() {
		return rules;
	}

	public void setRules(Rules rules) {
		this.rules = rules;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Kafka getKafka() {
		return kafka;
	}

	public void setKafka(Kafka kafka) {
		this.kafka = kafka;
	}

	public List<ConfigMode> getModes() {
		return modes;
	}

	public void setModes(List<ConfigMode> modes) {
		this.modes = modes;
	}
	
}
