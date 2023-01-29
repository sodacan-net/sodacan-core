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
package net.sodacan.mode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ClockProvider;
import net.sodacan.mode.spi.ModeProvider;
/**
 * Instances of this service provide clock services. Sodacan will 
 * @author John Churin
 *
 */
public class ClockService extends ModeService {
	private final static Logger logger = LogManager.getLogger();

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
				logger.info("Mode: {}, Types: {}, Provider: {}",getMode().getName(),types, provider.getClass().getName());
			}
		}
	}

	@Override
	protected List<ClockProvider> getProviders() {
		return providers;
	}

}
