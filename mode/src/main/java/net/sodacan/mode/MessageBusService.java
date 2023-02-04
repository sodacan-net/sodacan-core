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
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.mode.spi.MessageBusProvider;
import net.sodacan.mode.spi.ModeProvider;
/**
 * @author John Churin
 *
 */
public class MessageBusService extends ModeService {
	private final static Logger logger = LoggerFactory.getLogger(MessageBusService.class);

	public MessageBusService(Mode mode) {
		super(mode, MessageBusProvider.class);
	}
	protected List<MessageBusProvider> providers = new ArrayList<>();

	@Override
	public void loadProviders(Set<String> types) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(types)) {
				providers.add((MessageBusProvider) provider);
				provider.setMode(getMode().getName());
				logger.info("Mode: " + getMode().getName() + " Types: " + types + " Provider: " + provider.getClass().getName());
			}
		}
	}

	@Override
	protected List<MessageBusProvider> getProviders() {
		return providers;
	}

}
