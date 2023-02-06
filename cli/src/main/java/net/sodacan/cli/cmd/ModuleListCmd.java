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
package net.sodacan.cli.cmd;

import java.util.List;

import org.apache.commons.cli.CommandLine;

import net.sodacan.api.topic.Initialize;
import net.sodacan.api.topic.ModuleConsumer;
import net.sodacan.cli.Action;

public class ModuleListCmd implements Action {

	@Override
	public void execute(CommandLine commandLine, int index) {
		ModuleConsumer mc = new ModuleConsumer(Initialize.MODULES);
		mc.consume(false);
		List<String> names = mc.getListOfModuleNames();
		names.sort(String::compareTo);
		names.forEach((n) -> System.out.println(n));
	}

}
