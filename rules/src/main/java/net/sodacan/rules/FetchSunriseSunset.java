package net.sodacan.rules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.rules.config.Config;
/*
 * 2019-10-06T14:34:35.332Z
 * It is NOT dark.
 * 2019-10-06T14:44:35.328Z
 * {"sunrise":"2019-10-06T14:16:56+00:00","sunset":"2019-10-07T01:46:07+00:00"...
 * Sunrise local time 7:15
 * 17:54 UTC (now) 10:54 = -7 hrs
 */
public class FetchSunriseSunset {
	static final ObjectMapper mapper = new ObjectMapper();
	public static String site = "http://api.sunrise-sunset.org/json?";
	private static LocalDate ssDate;
	private static ZonedDateTime sunrise;
	private static ZonedDateTime sunset;
	public static void get(Tick tick, ZoneId zoneId) {
		// If we need sunrise/sunset for a different date, get it now.
		try {
			if (ssDate==null || !tick.getNow().toLocalDate().equals(ssDate)) {
				Config config = Config.getInstance();
				ssDate = tick.getNow().toLocalDate();
				int y = tick.getNow().getYear();
				int m = tick.getNow().getMonthValue();
				int d = tick.getNow().getDayOfMonth();
				String ds = String.format("%04d-%02d-%02d", y,m,d);
				Map<String, String> parameters = new HashMap<>();
				parameters.put("formatted", "0");
				parameters.put("lat", Double.toString(config.getLocation().getLatitude()));
				parameters.put("lng", Double.toString(config.getLocation().getLongitude()));
				parameters.put("date",ds);
				String p = ParameterStringBuilder.getParamsString(parameters);
				URL url = new URL(site + p);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
	
				con.setConnectTimeout(5000);
				con.setReadTimeout(5000);
	
				int status = con.getResponseCode();
				if (status != 200) {
					throw new RulesException("Bad status returned from " + url);
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	//			String inputLine;
	//			StringBuilder content = new StringBuilder();
	//			while ((inputLine = in.readLine()) != null) {
	//			    content.append(inputLine);
	//			}
	//			// Now parse the json contents into an object
	//			String json = content.toString();
	//			SunriseSunset ss = mapper.readValue(json, SunriseSunset.class);
				JsonNode top  = mapper.readTree(in);
				in.close();
				String sunriseString = top.get("results").get("sunrise").asText();
				String sunsetString = top.get("results").get("sunset").asText();
				sunrise = ZonedDateTime.ofInstant(Instant.parse(sunriseString), zoneId);
				sunset = ZonedDateTime.ofInstant(Instant.parse(sunsetString), zoneId);
			}
		tick.setSunrise( sunrise );
		tick.setSunset( sunset );
		} catch (Exception e) {
//			tick.setSunrise(sunrise);
//			throw new RulesException("Error getting sunrise sunset times", e);
		}
	}
}
