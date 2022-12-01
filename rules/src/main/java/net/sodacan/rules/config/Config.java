package net.sodacan.rules.config;

import java.io.File;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import net.sodacan.rules.RulesException;

public class Config {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Config.class);

	private static Config instance = null;

	private Api api;
	private Rules rules;
	private String timezone;
	private Location location;
	
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
	
	public static Config init(String filename) throws RulesException {
		try {
			logger.info("Opening config file: {}", filename);
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			instance = mapper.readValue(new File(filename), Config.class);
			return instance;
		} catch (Exception e) {
			throw new RulesException("Error initializing configuration", e);
		}
		
	}

	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	public Rules getRules() {
		return rules;
	}

	public void setRules(Rules rules) {
		this.rules = rules;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
