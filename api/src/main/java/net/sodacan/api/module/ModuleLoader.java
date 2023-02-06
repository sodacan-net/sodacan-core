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
package net.sodacan.api.module;

import java.io.IOException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.SodacanException;
import net.sodacan.api.kafka.SodacanProducer;
import net.sodacan.api.kafka.TopicAdmin;
import net.sodacan.api.topic.Initialize;
import net.sodacan.api.utility.ModuleMethods;
import net.sodacan.compiler.ModuleCompiler;
import net.sodacan.mode.Mode;
import net.sodacan.module.statement.SodacanModule;
/**
 * <p>Load a module into Sodacan.</p>
 * <ul>
 * <li>All of this is Mode-sensitive. Therefore, -m option is important even though a default is used if not provided.</li>
 * <li>Starting with a string containing the module source:</li>
 * <li>Compile the module</li>
 * <li>If compile errors, exception</li>
 * <li>Extract module name from compiled module</li>
 * <li>Add the module name to the Modules topic (it may already be there), if so, add it again.</li>
 * <li>The "value" of the Modules entry is the current (real) time.</li>
 * <li>Create a topic containing the module name for administrative message delivery for that module (as a whole, not per instance).</li>
 * <li>Create a topic containing the module name for message (variable) publishing for that module</li>
 * <li>Create a topic containing the module name for hold State and offsets.</li>
 * <li>Either of the three functions above will fail if the module already exists, that's normal and not considered an error.</li>
 * <li>Send the source code to the module administrative topic. 
 * (The compiled Module structure is not retained at this point.)</li>
 * </ul>
 * <p>Once loaded, the module will take effect as soon as the agent responsible for that module can process the
 * new or updated source code.</p>
 * @author John Churin
 *
 */
public class ModuleLoader {
	private final static Logger logger = LoggerFactory.getLogger(ModuleLoader.class);

	public static SodacanModule compile(String rawString) {
        // Fire up the compiler
		ModuleCompiler compiler = new ModuleCompiler();
		SodacanModule module = compiler.compile(rawString, null);
		return module;
	}
	/**
	 * <p>Create three topics associated with a module: </p>
	 * <ul>
	 * <li>A topic to hold the state of the variables for a module.
	 * This topic is compacted aggressively.</li>
	 * <li>A topic to hold messages (containing variables) published by the module. T
	 * his topic will be retained for a long time since it represents
	 * a history of events published by the module.</li>
	 * <li>A topic that holds administrative messages to the topic, primarily module source code updates.</li>
	 * </ul>
	 * </p>
	 * @param module
	 */
	public static void createModuleTopics( SodacanModule module ) {
		TopicAdmin topicAdmin = new TopicAdmin();
		Mode mode = Mode.getInstance();
		String stateTopic = ModuleMethods.getModuleStateTopicName(mode, module);
		boolean result = topicAdmin.createTopic(stateTopic, true);
		if (!result) {
			logger.info("Topic " + stateTopic + " already exists");
		}
		String publishTopic = ModuleMethods.getModulePublishTopicName(mode, module);
		result = topicAdmin.createTopic(publishTopic, false);
		if (!result) {
			logger.info("Topic " + publishTopic + " already exists");
		}
		String adminTopic = ModuleMethods.getModuleAdminTopicName(mode, module);
		result = topicAdmin.createTopic(adminTopic, false);
		if (!result) {
			logger.info("Topic " + adminTopic + " already exists");
		}
	}
	
	public static void loadModule( String rawString  ) {
		SodacanProducer producer = new SodacanProducer();
		try {
			SodacanModule module = compile(rawString);
			System.out.println( "Errors: " + module.getErrors());
			if(module.getErrors().size() > 0) {
				throw new SodacanException("Compile Errors, aborting");
			}
			String moduleName = module.getName();
			producer.put(Initialize.MODULES, ModuleMethods.getModuleKeyName(moduleName, null),Instant.now().toString());
			createModuleTopics( module);
		} catch (Exception e) {
			throw new SodacanException("Error loading module", e);
		} finally {
			producer.close();
		}
	}

}
