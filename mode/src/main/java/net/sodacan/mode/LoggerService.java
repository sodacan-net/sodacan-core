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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.config.ConfigMode;
import net.sodacan.mode.spi.LoggerProvider;
import net.sodacan.mode.spi.MessageBusProvider;
import net.sodacan.mode.spi.ModeProvider;

public class LoggerService extends ModeService {
	private final static Logger logger = LoggerFactory.getLogger(LoggerService.class);

	protected List<LoggerProvider> providers = new ArrayList<>();

	public LoggerService(ConfigMode configMode) {
		super(configMode, LoggerProvider.class);
		String pluginType = configMode.getLogger().get("pluginType");
		loadProviders(pluginType);
	}

	public void loadProviders( String pluginType) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(pluginType)) {
				providers.add((LoggerProvider) provider);
				provider.setMode(getModeName());
				logger.info("Mode: " + getModeName() + " PluginType: " + pluginType + " Provider: " + provider.getClass().getName());
			}
		}
	}

	@Override
	protected List<LoggerProvider> getProviders() {
		return (List<LoggerProvider>) providers; 
	}
	
	public void log(String msg) {
		for (LoggerProvider loggerProvider : getProviders()) {
			loggerProvider.log(msg);
		}
	}

	/**
	 * Close this service by closing the providers it is using
	 */
	public void close() {
		logger.debug("Closing Logger service: " + getConfigMode());
		for (LoggerProvider provider : getProviders()) {
			provider.close();
		}
		super.close();
	}

}
