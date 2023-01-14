package net.sodacan.webserver;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.core.UriBuilder;
import net.sodacan.SodacanException;
import net.sodacan.config.Config;

public class Main {

	public static void main(String[] args) {
    	// Setup the REST Api server
	    HttpServer server = null;
	      try {
	      	if (args.length<1) {
	    		throw new SodacanException("Missing command line argument containing configuration file name");
	    	}
	    	Config config = Config.init(args[0]);

	    	URI baseUri = UriBuilder.fromUri(config.getApi().getUrl()).build();
	        final ResourceConfig resourceConfig = new ResourceConfig();
	        resourceConfig.packages("net.sodacan.api.resource");

	        // create and start a new instance of grizzly http server
	        // exposing the Jersey application at BASE_URI
	        server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
	     // Static content handler at http://localhost:8080/static/...
	        StaticHttpHandler staticHandler = new StaticHttpHandler("html/");
	        staticHandler.setFileCacheEnabled(false);
	        server.getServerConfiguration().addHttpHandler( staticHandler, "/");
//	        Map<HttpHandler,String[]> handlers = server.getServerConfiguration().getHttpHandlers();
//	        server.getServerConfiguration().
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
