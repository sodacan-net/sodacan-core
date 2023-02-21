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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.config.ConfigMode;

/**
 * <p>
 * One Base Mode per mode specified in Conf file.
 * This class seeks the plugins (ServiceProviders) that provide IO to/from Sodacan.
 * </p>
 * <code>app -> Mode -> BaseMode -> ServiceProvider -> Provider interface -> Provider impl</code>
 * @author John Churin
 *
 */
public class BaseMode {
	private final static Logger logger = LoggerFactory.getLogger(BaseMode.class);
	
	private MessageBusService messageBusService;
	private ClockService clockService;
	private LoggerService loggerService;
	private TickSourceService tickSourceService;
	
	/**
	 * A Base mode is constructed from a ConfigMode which usually comes from
	 * the config file but can also be build in memory.
	 * @param configMode
	 */
	public BaseMode(ConfigMode configMode) {
		if (configMode.getMessageBus().size()!=0) {
			messageBusService = new MessageBusService(configMode);
		}
		if (configMode.getClock().size()!=0) {
			clockService = new ClockService(configMode);
		}
		if (configMode.getLogger().size()!=0) {
			loggerService = new LoggerService(configMode);
		}
		if (configMode.getTickSource().size()!=0) {
			tickSourceService = new TickSourceService(configMode);
		}
	}

	public MessageBusService getMessageBusService() {
		return messageBusService;
	}
		
	public TickSourceService getTickSourceService() {
		return tickSourceService;
	}

	public ClockService getClockService() {
		return clockService;
	}

	public LoggerService getLoggerService() {
		return loggerService;
	}
	
	/**
	 * Close the base mode and tell our plugins we're going away.
	 */
	public void close() {
		if (messageBusService!=null) {
			messageBusService.close();
		}
		if (clockService!=null) {
			clockService.close();
		}
		if (loggerService!=null) {
			loggerService.close();
		}
		if (tickSourceService!=null) {
			tickSourceService.close();
		}
	}
}
