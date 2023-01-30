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

import org.junit.Test;

import net.sodacan.mode.Mode;
import net.sodacan.mode.service.LoggerService;
import net.sodacan.mode.service.StateStoreService;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variables.ModuleVariables;

public class TestPlugins {

	@Test
	public void testMemoryLogger() {
		// Do this only on time per mode. This example is small. Usually, messageBus, clock, and stateStore also also
		// setup at this time.
		/* Mode mode = */ Mode.newModeBuilder().name("Mode1").logger("memory").stateStore("memory").build();
		
		// This would normally be called when a thread is recently started or restarted. For example, 
		// in a filter before processing a REST api call.
		Mode.setModeInThread("Mode1");

		// This is used when we want to find a mode anytime during a thread.
		LoggerService ls = Mode.getInstance().getLoggerService();
		
		for (int x = 0; x < 5; x++) {
			ls.log("Hello: "+ x);
		}
		// Create some variables
		ModuleVariables mvs = new ModuleVariables();
		VariableDef vd1 = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(123)).build();
		ModuleVariable v1 = (ModuleVariable)mvs.addVariable(vd1);
		v1.setChangedInCycle(true);
		VariableDef vd2 = VariableDef.newVariableDefBuilder().name("y").alias("z").initialValue(new Value(456)).build();
		ModuleVariable v2 = (ModuleVariable)mvs.addVariable(vd2);
		v2.setChangedInCycle(true);
		
		StateStoreService ss = Mode.getInstance().getStateStoreService();
//		ss.save(mvs);
		Mode.clearModeInThread();
	}

	@Test
	public void testMultipleMemoryLoggers() {
		{
		// Do this only on time per mode. This example is small. Usually, messageBus, clock, and stateStore also also
		// setup at this time.
		/* Mode mode = */ Mode.newModeBuilder().name("Mode1").logger("memory").stateStore("memory").build();
		/* Mode mode = */ Mode.newModeBuilder().name("Mode2").logger("memory").stateStore("memory").build();
		
		// This would normally be called when a thread is recently started or restarted. For example, 
		// in a filter before processing a REST api call.
		Mode.setModeInThread("Mode1");

		// This is used when we want to find a mode anytime during a thread.
		LoggerService ls = Mode.getInstance().getLoggerService();
		
		for (int x = 0; x < 5; x++) {
			ls.log("Hello: "+ x);
		}
		StateStoreService ss = Mode.getInstance().getStateStoreService();
//		ss.save("A Little Nothing");
		Mode.clearModeInThread();
		}
		{
		// This would normally be called when a thread is recently started or restarted. For example, 
		// in a filter before processing a REST api call.
		Mode.setModeInThread("Mode2");

		// This is used when we want to find a mode anytime during a thread.
		LoggerService ls = Mode.getInstance().getLoggerService();
		
		for (int x = 0; x < 3; x++) {
			ls.log("Hello2: "+ x);
		}
		
		StateStoreService ss = Mode.getInstance().getStateStoreService();
//		ss.save("A Little Nothing2");
		Mode.clearModeInThread();
		}
	}

}
