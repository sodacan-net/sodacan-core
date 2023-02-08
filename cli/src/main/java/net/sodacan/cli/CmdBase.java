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
package net.sodacan.cli;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import net.sodacan.SodacanException;
import net.sodacan.api.topic.Initialize;
import net.sodacan.api.topic.ModeConsumer;
import net.sodacan.api.topic.ReductionConsumer;
import net.sodacan.mode.Mode;

/**
 * Provides suport methods for many commands.
 * @author John Churin
 *
 */
public abstract class CmdBase {
	private CommandLine commandLine;
	private List<String> remainingArguments;
	// name to use if we get an error
	private String commandName;
	protected CommandContext cc;
	
	protected CmdBase( CommandContext cc ) {
		this.cc = cc;
	}
	
	/**
	 * Save common arguments and recreate command line commands
	 * @param commandLine From the parse
	 * @param index Index of first leftover after command parse
	 */
	protected void init( CommandLine commandLine, int index) {
		this.commandLine = commandLine;
		commandName = String.join(" ", commandLine.getArgList());
		remainingArguments = new LinkedList<>();
		// Load up the rest of the arguments on the line to a separate list
		for (int x = index; x < commandLine.getArgList().size(); x++) {
			remainingArguments.add(commandLine.getArgList().get(x));
		}
	}
	/**
	 * Many commands need a mode to be specified, set it up here.
	 * @return
	 */
	protected Mode needMode() {
		String modeName;
		// The -m option specifies mode, but we allow a default mode, too
		if (commandLine.hasOption("m")) {
			modeName = commandLine.getOptionValue("m");
		} else {
			modeName = Initialize.DEFAULT_MODE;
		}
		// Get the latest rendition of the mode
		ModeConsumer rc = new ModeConsumer(modeName);
		rc.snapshot();
		// Get the value
		String json = rc.get(modeName);
		if (json==null) {
			throw new SodacanException(commandName  + " Mode " + modeName + " not found");
		}
		// Turn the json into a node object
		Mode mode = Mode.createModeFromJson(json);
		// And initialize it (load plugins)
		mode.initialize();
		return mode;
	}

	/**
	 * If a command needs a file to be specified, we get it here.
	 * @param offset specifies the relative position of the filename, zero is the most common.
	 * @return Path containing the fully qualified file name.
	 */
	protected Path needPath(int offset) {
		File file = new File(needArg(offset,"path name"));
		return file.toPath();
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	protected String needArg(int index, String argName) {
		if (remainingArguments==null) {
			throw new SodacanException(" CmdBase not initialized");
		}
		if (index>=remainingArguments.size()) {
			throw new SodacanException(commandName + " missing argment" + argName);
		}
		return remainingArguments.get(index);
	}

	protected String needFileContents( Path path ) {
		try {
			return  Files.readString(path);
		} catch (IOException e) {
			throw new SodacanException(commandName + " Error opening file " + path.toString(), e);
		}
	}
	
	protected ReductionConsumer<String,String> needReductionConsumer(String topicName, PropertyChangeListener listener ) {
		ReductionConsumer<String, String> tc = new ReductionConsumer<String, String>(topicName);
		tc.addPropertyChangeListener(listener);
		// If -f, follow the activity
		if (commandLine.hasOption("f")) {
			if (commandLine.hasOption("all")) {
				tc.followAll();
			} else {
				tc.follow();
			}
			cc.addFollowable(tc);
			
		} else {
			if (commandLine.hasOption("all")) {
				System.out.println("Note: -all has no effect here" );
			}
			tc.snapshot();
		}
		return tc;
	}
}
