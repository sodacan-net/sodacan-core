package net.sodacan.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestConfig {
	String configFileName1 = "src/test/resources/test1.yaml";
	String configFileName2 = "src/test/resources/test2.yaml";
	@Test
	public void testConfigFilePresent() {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
    	Config config = Config.init(configFileName1);
	}

	@Test
	public void testWebServer() {
    	Config config = Config.init(configFileName1);
    	assert(config.getWebServer().getListen().contentEquals("http://0.0.0.0:4011/api"));
	}

	@Test
	public void testLocation() {
    	Config config = Config.init(configFileName1);
    	assert(config.getLocation().getLatitude()==42.5583482);
    	assert(config.getLocation().getAddress().contentEquals("123 Elm Street"));
    	assert(config.getLocation().getName().contentEquals("123 Elm Street"));
    	assert(config.getLocation().getTimezone().equals("America/Los_Angeles"));
	}

	@Test
	public void testAgent() {
    	Config config = Config.init(configFileName1);
    	assert(config.getAgent()!=null);
	}
}
