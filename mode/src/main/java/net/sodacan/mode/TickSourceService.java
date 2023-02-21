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
import net.sodacan.mode.spi.ModeProvider;
import net.sodacan.mode.spi.TickSourceProvider;
/**
 * <p>TickSource could be called Variable Store because the collection of variables for a module
 * comprise the totality of state for that module.</p>
 * <p>Save Variable State to all providers. Even though we're passed the entire set of variables for a module, our job is to pick
 * through the variables and save only those that have changed. We leave behind the class structure of variables and serialize to
 * json which in turn is what we pass to the interested plugin(s) for storage. This approach limits the scope of the plugin to IO 
 * rather than having to deal with a lot of Sodacan internals.</p>
 * <p>When restoring state, we'll collect all variables from all plugins interested. If a plugin just wants to lurk, that's fine. 
 * It should then return zero variables when asked to return stored variables.</p>
 * 
 * @author John Churin
 *
 */
public class TickSourceService extends ModeService {
	private final static Logger logger = LoggerFactory.getLogger(TickSourceService.class);
	protected List<TickSourceProvider> providers = new ArrayList<>();

	public TickSourceService(ConfigMode configMode) {
		super(configMode, TickSourceProvider.class);
		String pluginType = configMode.getTickSource().get("pluginType");
		loadProviders(pluginType);
	}

	public void loadProviders(String pluginType) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(pluginType)) {
				providers.add((TickSourceProvider) provider);
				provider.setMode(getModeName());
				logger.debug("Mode: " + getModeName() + " PluginType: " + pluginType + " Provider: " + provider.getClass().getName());
			}
		}
		if (providers.size()==0) {
			throw new SodacanException("No TickSource providers found for type: " + pluginType + " for mode " + getModeName());
		}
	}

	@Override
	protected List<TickSourceProvider> getProviders() {
		for (TickSourceProvider p : providers) {
			p.initConfig(configMode.getTickSource());
		}
		return providers;
	}
	

	/**
	 * Close this service by closing the providers it is using
	 */
	public void close() {
		logger.debug("Closing TickSource service: " + getConfigMode());
		for (TickSourceProvider provider : getProviders()) {
			provider.close();
		}
		super.close();
	}

}
