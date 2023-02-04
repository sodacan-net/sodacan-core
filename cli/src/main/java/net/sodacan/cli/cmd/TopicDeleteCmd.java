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

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;

import net.sodacan.SodacanException;
import net.sodacan.api.kafka.TopicAdmin;
import net.sodacan.cli.Action;

public class TopicDeleteCmd implements Action {

	@Override
	public void execute(CommandLine commandLine, int index) {
		try {
			if (commandLine.getArgList().size() <= index) throw new SodacanException("missing topic name"); 
			String topicName = commandLine.getArgs()[index];
			if (!commandLine.hasOption('f')) {
				String response = null;
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				System.out.printf("Are you sure that you want to delete topic %s [y/n]: ",topicName);
				response = bufferedReader.readLine();
				if (response==null || !response.equalsIgnoreCase("y")) {
					System.out.println("Canceled delete");
					return;
				}
			}
			TopicAdmin topicAdmin = new TopicAdmin();
			topicAdmin.deleteTopic(topicName);
			System.out.println("Topic Deleted: " + commandLine.getArgs()[index]);
		} catch (Exception e) {
			throw new SodacanException("Problem deleting topic", e);
		}
	}

}
