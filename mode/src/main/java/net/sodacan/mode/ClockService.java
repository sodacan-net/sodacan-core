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
package net.sodacan.mode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.SodacanException;
import net.sodacan.config.ConfigMode;
import net.sodacan.mode.spi.ClockProvider;
import net.sodacan.mode.spi.ModeProvider;
/**
 * Instances of this service provide clock services.
 * @author John Churin
 *
 */
public class ClockService extends ModeService {
	private final static Logger logger = LoggerFactory.getLogger(ClockService.class);

	public ClockService(ConfigMode configMode) {
		super(configMode, ClockProvider.class);
		String pluginType = configMode.getClock().get("pluginType");
		loadProviders(pluginType);
	}

	protected List<ClockProvider> providers = new ArrayList<>();

	public void loadProviders(String pluginType) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(pluginType)) {
				providers.add((ClockProvider) provider);
				provider.setMode(getModeName());
				logger.info("Mode: " + getModeName() + " PluginType: " + pluginType + " Provider: " + provider.getClass().getName());
			}
		}
	}

	@Override
	protected List<ClockProvider> getProviders() {
		return providers;
	}

	/**
	 * Close this service by closing the providers it is using
	 */
	public void close() {
		logger.debug("Closing Clock service: " + getConfigMode());
		for (ClockProvider provider : getProviders()) {
			provider.close();
		}
		super.close();
	}
	
	/**
	 * Set the clock (this only works for manual clocks. A real clock will ignore this request.
	 */
	public void setClock(int year, int month, int day, int hour, int minute, int second) {
		if (providers.size()>0) {
			providers.get(0).setClock(year, month, day, hour, minute, second);
		}
	}

	public long getTimestamp() {
		if (providers.size()>0) {
			return providers.get(0).getTimestamp();
		}
		throw new SodacanException("No clock provider, cannot return timestamp");
	}
	
	public void advanceClock( Duration duration) {
		if (providers.size()>0) {
			providers.get(0).advanceClock( duration);
		}
		throw new SodacanException("No clock provider, cannot advance the clock");
	}
}
