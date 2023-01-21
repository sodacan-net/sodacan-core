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
package net.sodacan.utility;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.shredzone.commons.suncalc.SunTimes;

import net.sodacan.config.Config;

public class SunriseSunset {
	private static Config config = Config.getInstance();
	private static final SunriseSunset instance = new SunriseSunset();
	private LRUCache<Integer,SunTimes> cache; 

	private SunriseSunset() {
		this.cache = new LRUCache<Integer,SunTimes>(5);
	}
	
	public static SunriseSunset getInstance() {
		return instance;
	}
	/**
	 * Get the sunTime instance that matches the date in time
	 * Use a cache to avoid recompute.
	 * @param time
	 * @return
	 */
	protected SunTimes getSuntimes(ZonedDateTime time) {
		Integer dayNumber = time.getDayOfYear()+time.getYear()*366;
		SunTimes ss = cache.get(dayNumber);
		if (ss!=null) return ss;
		SunTimes nss;
		nss = SunTimes.compute()
		    .on(time.getYear(),time.getMonthValue(),time.getDayOfMonth())
		    .latitude(config.getLocation().getLatitude())
		    .longitude(config.getLocation().getLongitude())
		    .execute();
		cache.add(dayNumber,nss);
		return nss;
	}
	public ZonedDateTime getSunrise(ZonedDateTime time) {
		SunTimes ss = getSuntimes(time);
		return ss.getRise();
	}
	
	public ZonedDateTime getSunset(ZonedDateTime time) {
		SunTimes ss = getSuntimes(time);
		return ss.getSet();
	}
}
