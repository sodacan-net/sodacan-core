package net.sodacan.api.resource;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import net.sodacan.rules.Event;
import net.sodacan.rules.EventSource;
import net.sodacan.rules.RulesException;
import net.sodacan.rules.Tick;
import net.sodacan.rules.config.Config;

@Path("rule")
public class RuleApi {
	Logger logger = LogManager.getLogger(RuleApi.class);
	   
	public RuleApi() {
	}

	/**
	 * Specify the current time.
	 * Used only in test mode, see configuration file
	 */
	@Path("tick/{time}")
	@GET
	public String setTime(@PathParam("time") String timeString ) {
		ZonedDateTime time = ZonedDateTime.parse(timeString);
		Config config = Config.getInstance();
		String mode = config.getRules().getMode();
		if (!"test".equals(mode)) {
			throw new RulesException("Time can only be set in test mode");
		}
		EventSource.getInstance().addEvent(new Tick(time));
		return "ok";
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
