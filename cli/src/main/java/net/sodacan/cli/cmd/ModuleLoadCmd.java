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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;

import net.sodacan.SodacanException;
import net.sodacan.api.module.ModuleLoader;
import net.sodacan.cli.Action;
import net.sodacan.compiler.ModuleCompiler;
/**
 * <p>Load a module into Sodacan.</p>
 * <ul>
 * <li>Read in the file, if not found, error</li>
 * <li>Ask the ModuleLoader in the api to load the module.</li>
 * </ul>
 * <p>Once loaded, the module will take effect as soon as the agent responsible for that module can process the
 * new or updated source code.</p>
 * @author John Churin
 *
 */
public class ModuleLoadCmd implements Action {

	@Override
	public void execute(CommandLine commandLine, int index) {
		if (commandLine.getArgList().size() <= index) throw new SodacanException("missing topic name"); 
		String moduleName = commandLine.getArgs()[index];
		Path path = Path.of(moduleName);
		String rawSource;
		try {
			rawSource = Files.readString(path);
		} catch (IOException e) {
			throw new SodacanException("Error opening module file " + path.toString(), e);
		}
		ModuleLoader.loadModule( rawSource );
	}

}
