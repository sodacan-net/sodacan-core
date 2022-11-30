package net.sodacan.rules;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.core.UriBuilder;
import net.sodacan.rules.config.Config;

/**
 * Start the SodaCan rule engine
 */
public class Main {
	static Logger logger = LogManager.getLogger(Main.class);
    public static void main( String[] args ) throws RulesException
    {
    	if (args.length<1) {
    		throw new RulesException("Missing command line argument containing configuration file name");
    	}
    	Config config = Config.init(args[0]);

    	// Force rule engine to start up
    	EventSource.getInstance();

    	// Setup the REST Api server
	    HttpServer server = null;
	      try {
	    	URI baseUri = UriBuilder.fromUri(config.getApi().getUrl()).build();
	        final ResourceConfig resourceConfig = new ResourceConfig();
	        resourceConfig.packages("net.sodacan.api.resource");

	        // create and start a new instance of grizzly http server
	        // exposing the Jersey application at BASE_URI
	        server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
//	        final TCPNIOTransport transport = server.getListener("grizzly").getTransport();
//	        transport.setSelectorRunnersCount(3);
//	        transport.setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setCorePoolSize(3).setMaxPoolSize(6));
	    	// For Jetty, See: Jersey Issue: 4739
	        // Start the API server
	    	server.start();
	      } catch (Throwable e) {
	      	e.printStackTrace();
//	    	throw new RuntimeException("Error starting server", e);
		  } finally {
//			server.shutdownNow();
		  }
	    	

    }
}
