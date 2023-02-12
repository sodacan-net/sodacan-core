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

import net.sodacan.SodacanException;
import net.sodacan.config.ConfigMode;
import net.sodacan.messagebus.MB;
import net.sodacan.mode.spi.MessageBusProvider;
import net.sodacan.mode.spi.ModeProvider;
/**
 * Provide access to one or more message bus providers.
 * @author John Churin
 *
 */
public class MessageBusService extends ModeService {
	private final static Logger logger = LoggerFactory.getLogger(MessageBusService.class);

	public MessageBusService(ConfigMode configMode) {
		super(configMode, MessageBusProvider.class);
		String pluginType = configMode.getMessageBus().get("pluginType");
		loadProviders(pluginType);
	}
	protected List<MessageBusProvider> providers = new ArrayList<>();

//	@Override
	protected void loadProviders(String pluginType) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(pluginType)) {
				providers.add((MessageBusProvider) provider);
				provider.setMode(getModeName());
				logger.debug("Mode: " + getModeName() + " PluginType: " + pluginType + " Provider: " + provider.getClass().getName());
			}
		}
		if (providers.size()==0) {
			throw new SodacanException("No providers found for type: " + pluginType + " for mode " + getModeName());
		}
	}

	@Override
	protected List<MessageBusProvider> getProviders() {
		return providers;
	}
	
	/**
	 * Get a message bus for this Mode
	 * @return MessageBus (MB) 
	 */
	public MB getMB() {
		if (providers.size()>0) {
			return providers.get(0).getMB(getConfigMode().getMessageBus());
		}
		return null;
	}
	
	/**
	 * Close this service by closing the providers it is using
	 */
	public void close() {
		logger.debug("Closing MessageBus service: " + getConfigMode());
		for (MessageBusProvider provider : getProviders()) {
			provider.close();
		}
		super.close();
	}
}
