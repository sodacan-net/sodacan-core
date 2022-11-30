package net.sodacan.api.resource;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import net.sodacan.rules.Event;
import net.sodacan.rules.EventSource;

@Path("rule")
public class RuleApi {
	Logger logger = LogManager.getLogger(RuleApi.class);
	   
	   public RuleApi() {
	   }
	 /**
	 * Return the server time as a simple String, UTC format and timezone.
	 * @return
	 */
	@Path("time")
	@GET
	@Produces("text/plain")
	public String serverTime() {
		return Instant.now().toString();
	}	
	/**
	 * Return the server time as a simple String, UTC format and timezone.
	 * @return
	 */
	@Path("echo/{msg}")
	@GET
	public String echo(@PathParam("msg") String msg) {
		logger.debug("rule/echo/{msg} = " + msg );
		return msg;
	}	

	@Path("state/{name}/{value}")
	@GET
	public String state(@PathParam("name") String name, @PathParam("value") String value ) {
		EventSource.getInstance().addState(name, value);
		return "ok";
	}	

	@Path("event/{name}/{value}")
	@GET
	public String event2(@PathParam("name") String name, @PathParam("value") String value ) {
		EventSource.getInstance().addEvent(new Event(name, value));
		return "ok";
	}	

	@Path("level/{name}/{value}")
	@GET
	public String event2(@PathParam("name") String name, @PathParam("value") Double value ) {
		EventSource.getInstance().addEvent(new Event(name, value));
		return "ok";
	}	

	@Path("event/{name}")
	@GET
	public String event(@PathParam("name") String name ) {
		EventSource.getInstance().addEvent(name);
		return "ok";
	}	


}
