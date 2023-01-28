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

import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.LoggerProvider;
import net.sodacan.mode.spi.ModeProvider;

public class LoggerService extends ModeService {
	protected List<LoggerProvider> providers = new ArrayList<>();

	public LoggerService(Mode mode) {
		super(mode, LoggerProvider.class);
	}

	@Override
	public void loadProviders( Set<String> types) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(types)) {
				providers.add((LoggerProvider) provider);
			}
		}
	}

	@Override
	protected List<LoggerProvider> getProviders() {
		return (List<LoggerProvider>) providers; 
	}
	
	/**
	 * Send a log message to all providers
	 * @param msg
	 */
	public void log(String msg) {
		for (LoggerProvider provider : getProviders()) {
			provider.log(getMode().getName(), msg);
		}
	}
}
