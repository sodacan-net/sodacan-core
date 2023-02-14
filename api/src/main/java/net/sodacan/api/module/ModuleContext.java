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

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.SodacanException;
import net.sodacan.api.topic.Initialize;
import net.sodacan.compiler.ModuleCompiler;
import net.sodacan.messagebus.MB;
import net.sodacan.messagebus.MBRecord;
import net.sodacan.messagebus.MBTopic;
import net.sodacan.mode.Mode;
import net.sodacan.module.ModuleMethods;
import net.sodacan.module.statement.SodacanModule;
/**
 * <p>Mode-specific functions for saving and restoring modules to/from the MessageBus.
 * An instance of this class is good for at least one cycle for a single module.
 * This class is normally paired with an instance of the VariableLoader class.</p>
 * @author John Churin
 *
 */
public class ModuleContext {
	private final static Logger logger = LoggerFactory.getLogger(ModuleContext.class);

	private Mode mode;
	private String rawSource;
	private ModuleCompiler compiler;
	protected SodacanModule module;
	protected String modeName;
	protected String moduleName;
	protected String instanceName;
	protected MB mb;

	public ModuleContext(Mode mode) {
		this.mode = mode;
		this.modeName = mode.getModeName();
		// We'll need a message bus
		mb = mode.getMB();
        // Fire up the compiler
		compiler = new ModuleCompiler();
	}

	// Compile the module
	public void compile() {
		this.module = compiler.compile(rawSource, null);
		System.out.println( "Errors: " + module.getErrors());
		if(module.getErrors().size() > 0) {
			throw new SodacanException("Compile Errors, aborting");
		}
		moduleName = module.getName();
		instanceName = module.getInstanceName();
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
	protected void createModuleTopics( ) {
		
		String stateTopic = ModuleMethods.getModuleStateTopicName(mode.getModeName(), module.getName(), module.getInstanceName());
		boolean result = mb.createTopic(stateTopic, true);
		if (!result) {
			logger.info("Topic " + stateTopic + " already exists");
		}
		String publishTopic = ModuleMethods.getModulePublishTopicName(mode.getModeName(), module.getName(), module.getInstanceName());
		result = mb.createTopic(publishTopic, false);
		if (!result) {
			logger.info("Topic " + publishTopic + " already exists");
		}
		String adminTopic = ModuleMethods.getModuleAdminTopicName(mode.getModeName(), module.getName(), module.getInstanceName());
		result = mb.createTopic(adminTopic, false);
		if (!result) {
			logger.info("Topic " + adminTopic + " already exists");
		}
	}
	
	protected void pushSourceToAdminTopic() {
		String topicName = ModuleMethods.getModuleAdminTopicName(mode.getModeName(), module.getName(), module.getInstanceName());
		mb.produce(topicName,"scc", rawSource);
		logger.info("Module source pushed to " + topicName);
	}

	/**
	 * We fetch a module by name from the topic holding the module source( <code>admin-&#60;mode&#62;-&#60;modulename&#62;</code>)
	 *  and compile it.
	 * @param fullModuleName with instance name if present
	 */
	public void fetchModule( String fullModuleName ) {
		String topicName = ModuleMethods.getModuleAdminTopicName(mode.getModeName(), fullModuleName);
		MBTopic topic;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Get snapshot of topic: " + topicName);
			}
			topic = mb.openTopic(topicName, 0);
			Map<String,MBRecord> mbrs = topic.snapshot();
			MBRecord record = mbrs.get(fullModuleName);
			if (record==null) {
				throw new SodacanException("Module " + fullModuleName + " not found");
			}
			// Load up the raw source
			rawSource = record.getValue();
			// Compile so we have a module in memory
			// Normally, this compile shouldn't fail since it 
			// couldn't have been uploaded with compile errors.
			compile();
		} finally {
			// The topic is already closed after the call to snapshot
		}
	}
	
	/**
	 * <p>Add or update a module from rawSource, usually derived from the file system.</p>
	 * <ul>
	 * <li>All of this is done in the context of a Mode.</li>
	 * <li>Starting with a string containing the module source:</li>
	 * <li>Compile the module</li>
	 * <li>If compile errors, raise an exception</li>
	 * <li>Extract module name and instance name, if any, from the compiled module (done by compile() method.</li>
	 * <li>Add the module name plus instance as the key to the "Modules" topic (it may already be there), if so, add it again.</li>
	 * <li>The "value" of the Modules entry is the current (real) time.</li>
	 * <li>Create a topic containing the module name for administrative message delivery for that module (as a whole, not per instance).</li>
	 * <li>Create a topic containing the module name for message (variable) publishing for that module</li>
	 * <li>Create a topic containing the module name for holding our variable State and offsets.</li>
	 * <li>Either of the three functions above will fail if the module already exists, that's normal and not considered an error.</li>
	 * <li>Send the source code to the module administrative topic. key is "scc" for Sodacan source code.
	 * (The compiled Module structure is not retained at this point.)</li>
	 * </ul>
	 * <p>Once loaded, the module will take effect as soon as the agent responsible for that module can process the
	 * new or updated source code.</p>
	 * @param rawSource
	 */
	public void loadRawModule( String rawSource  ) {
		this.rawSource = rawSource;
		try {
			compile();
			mb.produce(Initialize.MODULES, ModuleMethods.getModuleKeyName(modeName, moduleName, instanceName),Instant.now().toString());
			createModuleTopics();
			pushSourceToAdminTopic();
//			fetchModuleVariables();
		} catch (Exception e) {
			throw new SodacanException("Error loading module", e);
		} finally {
//			producer.close();
		}
	}

}
