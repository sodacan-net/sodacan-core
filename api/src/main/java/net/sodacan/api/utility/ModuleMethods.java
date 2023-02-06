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
package net.sodacan.api.utility;

import net.sodacan.mode.Mode;
import net.sodacan.module.statement.SodacanModule;

public class ModuleMethods {

	/**
	 * Return a consistent way of naming the state store topic for this module
	 * @return The topicName for this combination of mode and module
	 */
	public static String getModuleStateTopicName(Mode mode, SodacanModule module) {
		StringBuffer sb = new StringBuffer();
		sb.append("state-");
		sb.append(mode.getName());
		sb.append('-');
		sb.append(module.getName());
		if (module.getInstanceName()!=null) {
			sb.append('-');
			sb.append(module.getInstanceName());
		}
		return sb.toString();
	}
	
	/**
	 * Return a consistent way of naming the publish topic for the supplied module
	 * @return The topicName for this combination of mode and module
	 */
	public static String getModulePublishTopicName(Mode mode, SodacanModule module) {
		StringBuffer sb = new StringBuffer();
		sb.append("pub-");
		sb.append(mode.getName());
		sb.append('-');
		sb.append(module.getName());
		if (module.getInstanceName()!=null) {
			sb.append('-');
			sb.append(module.getInstanceName());
		}
		return sb.toString();
	}

	
	/**
	 * Return a consistent way of naming the admin topic for the supplied module.
	 * This topic only exists for the module as a while, not the individual instances.
	 * @return The topicName for this combination of mode and module
	 */
	public static String getModuleAdminTopicName(Mode mode, SodacanModule module) {
		StringBuffer sb = new StringBuffer();
		sb.append("admin-");
		sb.append(mode.getName());
		sb.append('-');
		sb.append(module.getName());
		return sb.toString();
	}

	/**
	 * Return a consistent way of naming the mode topic for the supplied mode
	 * @return The topicName for this mode
	 */
	public static String getModTopicName(Mode mode) {
		StringBuffer sb = new StringBuffer();
		sb.append("mode-");
		sb.append(mode.getName());
		return sb.toString();
	}

	public static String getModuleKeyName( String moduleName, String instanceKey) {
		StringBuffer sb = new StringBuffer();
		sb.append(moduleName);
		if (instanceKey!=null) {
			sb.append('[');
			sb.append(instanceKey);
			sb.append(']');
		}
		return sb.toString();
	}
}
