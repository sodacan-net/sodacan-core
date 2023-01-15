package net.sodacan.webserver;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.core.UriBuilder;
import net.sodacan.SodacanException;
import net.sodacan.config.Config;

public class Main {
	private final static Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		{	// Setup logger
//			logger = LogManager.getRootLogger();
		}
		// Setup the REST Api server
	    HttpServer server = null;
	      try {
	    	  
	      	if (args.length<1) {
	    		throw new SodacanException("Missing command line argument containing configuration file name");
	    	}
	    	Config config = Config.init(args[0]);
	    	// Configure API
	    	URI apiUri = UriBuilder.fromUri(config.getWebServer().getListen()).path("api").build();
	    	URI staticUri = UriBuilder.fromUri(config.getWebServer().getListen()).path("/").build();
	        final ResourceConfig resourceConfig = new ResourceConfig();
	        resourceConfig.packages("net.sodacan.webserver.resource");

	        // create and start a new instance of grizzly http server
	        // exposing the Jersey application at BASE_URI
	        server = GrizzlyHttpServerFactory.createHttpServer(apiUri, resourceConfig);
	        // Add a static http handler
	        StaticHttpHandler staticHandler = new StaticHttpHandler("html/");	// Served from this directory
	        staticHandler.setFileCacheEnabled(false);
	        server.getServerConfiguration().addHttpHandler( staticHandler, staticUri.getPath());	// At this url
//	        final TCPNIOTransport transport = server.getListener("grizzly").getTransport();
//	        transport.setSelectorRunnersCount(3);
//	        transport.setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setCorePoolSize(3).setMaxPoolSize(6));
	    	// For Jetty, See: Jersey Issue: 4739
	        // Start the API server
	    	server.start();
	    	logger.info("RESTAPI listening at " + apiUri);
	    	logger.info("Static content from  " + staticUri);

	      } catch (Throwable e) {
	      	e.printStackTrace();
//	    	throw new RuntimeException("Error starting server", e);
		  } finally {
//			server.shutdownNow();
		  }
	    	

	}

}
