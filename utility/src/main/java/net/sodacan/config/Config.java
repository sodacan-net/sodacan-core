package net.sodacan.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import net.sodacan.SodacanException;

public class Config {
	private final static Logger logger = LogManager.getLogger();

	private static Config instance = null;

	private WebServer webServer;
	private Rules rules;
	private Location location;
	private Agent agent;
	
	private Config() {
		
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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
}
