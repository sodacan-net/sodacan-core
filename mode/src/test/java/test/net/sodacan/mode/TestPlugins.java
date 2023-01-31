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
package test.net.sodacan.mode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import net.sodacan.mode.LoggerService;
import net.sodacan.mode.Mode;
import net.sodacan.mode.StateStoreService;
import net.sodacan.mode.spi.PluginEvent;
import net.sodacan.mode.spi.VariablePayload;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variables.ModuleVariables;

public class TestPlugins implements PropertyChangeListener{
	private Map<String,PluginEvent> changeEvents = new HashMap<>();

	public PluginEvent findPluginEventByModeAndProperty(String mode, String propertyName) {
		return changeEvents.get(mode+propertyName);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event instanceof PluginEvent) {
			PluginEvent pe = (PluginEvent)event;
			this.changeEvents.put(pe.getMode() + pe.getPropertyName(), pe);
		} else {
			throw new RuntimeException("Something other a plugin event arrived");
		}
	}
	
	@Test
	public void testMemoryLogger() {
		// Do this only one time per mode. This example is small. Usually, messageBus, clock, and stateStore also also
		// setup at this time.VariablePayload
		// We want to ensure that the message makes it to the plugin so we'll ask the plugin(s) to
		// send us a property change event.
		/* Mode mode = */ Mode.newModeBuilder().name("Mode1").logger("memory").listener(this).build();
		
		// This would normally be called when a thread is recently started or restarted. For example, 
		// in a filter before processing a REST api call.
		Mode.setModeInThread("Mode1");

		// This is used when we want to find a mode anytime during a thread.
		// We also return the logger service on the same line
		LoggerService ls = Mode.getInstance().getLoggerService();

		// Tell the logger service to do something
		for (int x = 0; x < 5; x++) {
			ls.log("Hello: "+ x);
		}
		PluginEvent pe = findPluginEventByModeAndProperty("Mode1","msg");
		assert("Hello: 4".equals(pe.getNewValue()));
		
		// Before leaving the thread, remove the Mode variable
		Mode.clearModeInThread();
	}

	@Test
	public void testMultipleMemoryLoggers() {
		// Do this only one time per mode. This example is small. Usually, messageBus, clock, and stateStore also also
		// setup at this time.
		// We also register ourself so we hear back from plugins on property changes
		// Mostly for testing
		/* Mode mode = */ Mode.newModeBuilder().name("Mode1").logger("memory").listener(this).build();
		/* Mode mode = */ Mode.newModeBuilder().name("Mode2").logger("memory").listener(this).build();
		
		// This would normally be called when a thread is recently started or restarted. For example, 
		// in a filter before processing a REST api call.
		Mode.setModeInThread("Mode1");
		try {
			// This is used when we want to get the current mode any time during a thread.
			LoggerService ls1 = Mode.getInstance().getLoggerService();
	
			for (int x = 0; x < 5; x++) {
				ls1.log("Hello: "+ x);
			}
			Mode.clearModeInThread();
			// This would normally be called when a thread is recently started or restarted. For example, 
			// in a filter before processing a REST api call.
			Mode.setModeInThread("Mode2");
			LoggerService ls2 = Mode.getInstance().getLoggerService();
	
			for (int x = 0; x < 3; x++) {
				ls2.log("Hello2: "+ x);
			}
			
			PluginEvent pe1 = findPluginEventByModeAndProperty("Mode1","msg");
			assert("Hello: 4".equals(pe1.getNewValue()));
			PluginEvent pe2 = findPluginEventByModeAndProperty("Mode2","msg");
			assert("Hello2: 2".equals(pe2.getNewValue()));
			
		} finally {
			Mode.clearModeInThread();
		}
	}
	
	@Test
	public void testSaveState() {
		try {
			// Create some variables
			ModuleVariables mvs = new ModuleVariables();
			VariableDef vd1 = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(123)).build();
			ModuleVariable v1 = (ModuleVariable)mvs.addVariable(vd1);
			v1.setChangedInCycle(true);
			VariableDef vd2 = VariableDef.newVariableDefBuilder().name("y").alias("z").initialValue(new Value(456)).build();
			ModuleVariable v2 = (ModuleVariable)mvs.addVariable(vd2);
			v2.setChangedInCycle(true);
			/* Mode mode = */ Mode.newModeBuilder().name("Mode1").stateStore("memory").listener(this).build();
	
			// This would normally be called when a thread is recently started or restarted. For example, 
			// in a filter before processing a REST api call.
			Mode.setModeInThread("Mode1");
			SodacanModule sm = new SodacanModule();
			sm.setName("DummyModule");
			StateStoreService ss = Mode.getInstance().getStateStoreService();
			// We don't have a module! Do we need one?
			ss.save(sm,mvs);
			PluginEvent pe1 = findPluginEventByModeAndProperty("Mode1","variable");
			// Normally invisible to runtime but we use this for testing
			VariablePayload vp1 = (VariablePayload) pe1.getNewValue();
			assert(vp1.getVariableName().equals("y"));
		} finally {
			Mode.clearModeInThread();
		}
	}



}
