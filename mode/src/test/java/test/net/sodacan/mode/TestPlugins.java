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

import static org.junit.Assert.*;

import org.junit.Test;

import net.sodacan.mode.Mode;

public class TestPlugins {

	@Test
	public void testMemoryLogger() {
		Mode mode = Mode.newModeBuilder().name("Mode1").logger("anylogger").build();
		Mode.setupThreadMode("Mode1");
		Mode.getInstance().getLoggerService().log("Hello1");
	}

}
