package net.sodacan.rules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	public static Sun get(ZonedDateTime now) {
		try {
			int y = now.getYear();
			int m = now.getMonthValue();
			int d = now.getDayOfMonth();
			String ds = String.format("%04d-%02d-%02d", y,m,d);
			Map<String, String> parameters = new HashMap<>();
			parameters.put("formatted", "0");
			parameters.put("lat", "42.5583482");
			parameters.put("lng", "-123.3968023");
			parameters.put("date",ds);
			String p = ParameterStringBuilder.getParamsString(parameters);
			URL url = new URL(site + p);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);

			int status = con.getResponseCode();
			if (status != 200) {
				throw new RuntimeException("Bad status returned from " + url);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			// Now parse the json contents into an object
			String json = content.toString();
			SunriseSunset ss = mapper.readValue(json, SunriseSunset.class);
			ss.results.setQueryTime(now);
		    return ss.getResults();
		} catch (Exception e) {
			throw new RuntimeException("Error getting sunrise sunset times", e);
		}
	}
}
