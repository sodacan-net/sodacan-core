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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sodacan.cli.cmd.AgentListCmd;
import net.sodacan.cli.cmd.AgentStatusCmd;
import net.sodacan.cli.cmd.BrokerListCmd;
import net.sodacan.cli.cmd.BrokerStatusCmd;
import net.sodacan.cli.cmd.InitializeCmd;
import net.sodacan.cli.cmd.ModeCreateCmd;
import net.sodacan.cli.cmd.ModeListCmd;
import net.sodacan.cli.cmd.TopicDeleteCmd;
import net.sodacan.cli.cmd.TopicListCmd;
import net.sodacan.cli.cmd.TopicPrintCmd;
import net.sodacan.cli.cmd.TopicStatusCmd;
import net.sodacan.cli.cmd.TopicWatchCmd;
import net.sodacan.config.Config;

public class Main {
	private final static Logger logger = LoggerFactory.getLogger(Main.class);
	private Command command;
	private Options options;
	private CommandLineParser parser;
	
	public Main() {
		logger.trace("Setup Command Dispatch");
		// Setup command structure
		command = new SubCommand()
				.action("agent", "list", new AgentListCmd(), "List known agents")
				.action("agent", "status", new AgentStatusCmd(),"[<pattern>] Show status of matching agents")
				.action("broker", "list", new BrokerListCmd(), "List known brokers")
				.action("broker", "status", new BrokerStatusCmd(), "Show status of broker(s)")
				.action("initialize", new InitializeCmd(), "Initialize topics")
				.action("mode", "list", new ModeListCmd(), "List known modes")
				.action("mode", "create", new ModeCreateCmd(),"<mode> Create a new mode")
				.action("topic", "list", new TopicListCmd(), "List known topics")
				.action("topic", "delete", new TopicDeleteCmd(), "<topic> Delete a topic")
				.action("topic", "print", new TopicPrintCmd(), "<topic> print contents of a topic")
				.action("topic", "status", new TopicStatusCmd(), "<topic> status of a topic")
				.action("topic", "watch", new TopicWatchCmd(), "<topic> watch contents of a topic")
				.action("help",  null, "Show help in interactive mode")
				;

		// create Options object
		logger.trace("Setup Options");
		options = new Options();
		// add t option
		options.addOption("c", "config", true, "Config file, default config/config.yaml");
		options.addOption("d", "debug", false, "show debug output");
		options.addOption("f", "force", false, "Don't ask for confirmation before critical action");
		options.addOption("h", "help", false, "This help");
		options.addOption("i", "interactive", false, "Interactive mode");
		options.addOption("l", "limit", true, "Limit output to <lines>, detault 1000");
		options.addOption("m", true, "Specify sticky mode, default is default");
		options.addOption("v", "verbose", false, "Be verbose");
		options.addOption("s", "start", true, "Start output at <line>, detault 1");
		parser = new DefaultParser(true);
	}
	public void interactiveMode() {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				System.out.print("soda: ");
				String response = bufferedReader.readLine();
				String args[] = response.split(" ");
				if (args.length==0 || args[0].isEmpty()) continue;
				if ("quit".equals(args[0])) {
					break;
				}
				if ("help".equals(args[0])) {
					showHelp();
				} else {
					parse(args);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Throwable t = e.getCause();
				while (t!=null) {
					System.out.println("  " + t.getMessage());
					t = t.getCause();
				}
			}
		}
	}
	
	/**
	 * Setup configuration file
	 * @param fileName
	 */
	public void setupConfig(String fileName) {
		logger.debug("Working Directory = " + System.getProperty("user.dir"));
    	Config.init(fileName);
	}

	public void parse(String[] args) {
		try {
			logger.trace("Parse Options");
			CommandLine cmd = parser.parse(options, args);
			// These options take action immediately
			if (cmd.hasOption('h')) {
				HelpFormatter formatter = new HelpFormatter();
			      formatter.printHelp("soda [options] [command]", options);
			      System.out.println("\nCommands:");
			      command.printHelp("");
			      return;
			}
			// Config file setup
			if (cmd.hasOption('c')) {
				setupConfig(cmd.getOptionValue("c"));
			} else {
				setupConfig( "config/config.yaml");
			}
			if (cmd.hasOption('i')) {
				interactiveMode();
				return;
			}
			// The rest depend on command(s)
			logger.trace("Dispatch");
			command.dispatch(cmd,0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Throwable t = e.getCause();
			while (t!=null) {
				System.out.println("  " + t.getMessage());
				t = t.getCause();
			}
		}
	}
	
	public void showHelp() {
		HelpFormatter formatter = new HelpFormatter();
	      formatter.printHelp("soda [options] [command]", options);
	      System.out.println("\nCommands:");
	      command.printHelp("");

	}

	public static void main(String[] args) throws ParseException {
		Main main = new Main();
		if (args==null || args.length==0) {
			main.showHelp();
		} else {
			main.parse(args);
		}
	}
}
