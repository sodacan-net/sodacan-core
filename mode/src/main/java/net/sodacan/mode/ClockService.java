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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.SodacanException;
import net.sodacan.mode.spi.ClockProvider;
import net.sodacan.mode.spi.ModeProvider;
/**
 * Instances of this service provide clock services. Sodacan will 
 * @author John Churin
 *
 */
public class ClockService extends ModeService {
	private final static Logger logger = LoggerFactory.getLogger(ClockService.class);

	public ClockService(Mode mode) {
		super(mode, ClockProvider.class);
	}

	protected List<ClockProvider> providers = new ArrayList<>();

	@Override
	public void loadProviders(Set<String> types) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(types)) {
				providers.add((ClockProvider) provider);
				provider.setMode(getMode().getName());
				logger.info("Mode: " + getMode().getName() + " Types: " + types + " Provider: " + provider.getClass().getName());
			}
		}
	}

	@Override
	protected List<ClockProvider> getProviders() {
		return providers;
	}

	/**
	 * In this case, we can't provide more than one clock supplier to a module so
	 * the first one wins.
	 * @return A supplier of instances
	 */
	public Supplier<Instant> getSupplier() {
		if (providers.size()>0) {
			return providers.get(0).getSupplier();
		}
		return null;
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
}
