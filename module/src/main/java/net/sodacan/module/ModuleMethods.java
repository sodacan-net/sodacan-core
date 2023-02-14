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
package net.sodacan.module;

public class ModuleMethods {
	/**
	 * A full module name contains module name and instance. Parse them to a string array.
	 * @param fullModuleName
	 * @return A one or two entry String array holind the module name and, if present, the instance name.
	 */
	public static String[] parseFullModuleName( String fullModuleName) {
		int lbracket = fullModuleName.indexOf('[');
		if (lbracket<0) {
			return new String[] {fullModuleName};
		} else {
			String moduleName = fullModuleName.substring(0,lbracket);
			String instanceName = fullModuleName.substring(lbracket+1,fullModuleName.length()-1);
			return new String[] {moduleName, instanceName};
		}
	}

	public static String addTopicSuffix(StringBuffer sb, String modeName, String moduleName, String instanceName) {
		sb.append(modeName);
		sb.append('-');
		sb.append(moduleName);
		if (instanceName!=null) {
			sb.append('-');
			sb.append(instanceName);
		}
		return sb.toString();
	}
	public static String addTopicSuffix(StringBuffer sb, String modeName, String fullModuleName) {
		String fullName[] = parseFullModuleName(fullModuleName);
		String instanceName;
		if (fullName.length>1) {
			instanceName = fullName[1];
		} else {
			instanceName = null;
		}
		return addTopicSuffix( sb, modeName, fullName[0], instanceName);
	}
	
	/**
	 * Return a consistent way of naming the state store topic for this module
	 * @return The topicName for this combination of mode and module
	 */
	public static String getModuleStateTopicName(String modeName, String moduleName, String instanceName) {
		StringBuffer sb = new StringBuffer();
		sb.append("state-");
		return addTopicSuffix( sb, modeName,moduleName, instanceName);
	}
		
	/**
	 * Return a consistent way of naming the publish topic for the supplied module
	 * @return The topicName for this combination of mode and module
	 */
	public static String getModulePublishTopicName(String modeName, String moduleName, String instanceName) {
		StringBuffer sb = new StringBuffer();
		sb.append("pub-");
		return addTopicSuffix( sb, modeName,moduleName, instanceName);
	}

	public static String getModuleAdminTopicName(String modeName, String moduleName, String instanceName) {
		StringBuffer sb = new StringBuffer();
		sb.append("admin-");
		return addTopicSuffix( sb, modeName,moduleName, instanceName);
	}

	/**
	 * Return a consistent way of naming the admin topic for the supplied module.
	 * This topic only exists for the module as a while, not the individual instances.
	 * @return The topicName for this combination of mode and module
	 */
	public static String getModuleAdminTopicName(String modeName, String fullModuleName) {
		StringBuffer sb = new StringBuffer();
		sb.append("admin-");
		return addTopicSuffix( sb, modeName,fullModuleName);
	}
	
	/**
	 * Return a consistent way of naming the mode topic for the supplied mode
	 * @return The topicName for this mode
	 */
	public static String getModTopicName(String modeName) {
		StringBuffer sb = new StringBuffer();
		sb.append("mode-");
		sb.append(modeName);
		return sb.toString();
	}
	
	/**
	 * The Modules topic contains all modules known to Sodacan, qualified by Mode and an optional instanceKey.
	 * @param modeName
	 * @param moduleName
	 * @param instanceKey
	 * @return
	 */
	public static String getModuleKeyName( String modeName, String moduleName, String instanceKey) {
		StringBuffer sb = new StringBuffer();
		sb.append(modeName);
		sb.append('-');
		sb.append(moduleName);
		if (instanceKey!=null) {
			sb.append('[');
			sb.append(instanceKey);
			sb.append(']');
		}
		return sb.toString();
	}
	
	/**
	 * Given a variable name and an variable (not module) instance name (or null if not an array),
	 * construct a key to be used in the module state topic.
	 * @param variableName
	 * @param variableInstance
	 * @return String suitable for use in the key of a variable.
	 */
	public static String getVariableKeyName(String variableName) {
		return "v-" + variableName;
	}

	/**
	 * After reading and processing a subscription, we keep our (new) offset in the state topic.
	 * The key is distinguished from variables with a "t-" prefix
	 * @param eventTopic
	 * @return
	 */
	public static String getEventKeyName( String eventTopic) {
		return "t-" + eventTopic;
	}
	
}
