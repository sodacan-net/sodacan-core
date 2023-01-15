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
package net.sodacan.webserver.resource;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import net.sodacan.SodacanException;
import net.sodacan.config.Config;

@Path("")
public class RestApi {
	private final static Logger logger = LogManager.getLogger();
	private static ObjectMapper objectMapper = new ObjectMapper();;
	
	public RestApi() {
	}
	@Path("time")
	@GET
	public String getTime() {
		logger.info("The time is nye");
		return "It's time";
	}
//	/**
//	 * Specify the current time.
//	 * Used only in test mode, see configuration file
//	 */
//	@Path("tick/{time}")
//	@GET
//	public String setTime(@PathParam("time") String timeString ) {
//		ZonedDateTime time = ZonedDateTime.parse(timeString);
//		Config config = Config.getInstance();
//		String mode = config.getRules().getMode();
//		if (!"test".equals(mode)) {
//			throw new SodacanException("Time can only be set in test mode");
//		}
//		EventSource.getInstance().addEvent(new Tick(time));
//		return "ok";
//	}	
//
//	@Path("state/{name}/{value}")
//	@POST
//	public String state(@PathParam("name") String name, @PathParam("value") String value ) {
//		EventSource.getInstance().addState(name, value);
//		return "ok";
//	}	
//
//	@Path("event/{name}/{value}")
//	@POST
//	public String event2(@PathParam("name") String name, @PathParam("value") String value ) {
//		EventSource.getInstance().addEvent(new Event(name, value));
//		return "ok";
//	}	
//
//	@Path("level/{name}/{value}")
//	@GET
//	public String event2(@PathParam("name") String name, @PathParam("value") Double value ) {
//		EventSource.getInstance().addEvent(new Event(name, value));
//		return "ok";
//	}	
//
//	@Path("event/{name}")
//	@POST
//	public String event(@PathParam("name") String name ) {
//		EventSource.getInstance().addEvent(name);
//		return "ok";
//	}
//
//	@Path("states")
//	@GET
//	public String getStates( ) {
//		List<State> states = EventSource.getInstance().getAllStates();
//		try {
//			String json = objectMapper.writeValueAsString(states);
//			return json;
//		} catch (JsonProcessingException e) {
//			throw new SodacanException("Error creating JSON String",e);
//		}
//	}
//	@Path("countdowns")
//	@GET
//	public String getCountdowns( ) {
//		List<Countdown> countdowns = EventSource.getInstance().getAllCountdowns();
//		try {
//			String json = objectMapper.writeValueAsString(countdowns);
//			return json;
//		} catch (JsonProcessingException e) {
//			throw new SodacanException("Error creating JSON String",e);
//		}
//	}
}
