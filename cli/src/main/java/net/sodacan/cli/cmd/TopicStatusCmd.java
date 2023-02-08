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

import org.apache.commons.cli.CommandLine;

import net.sodacan.SodacanException;
import net.sodacan.api.kafka.TopicAdmin;
import net.sodacan.cli.Action;
import net.sodacan.cli.CmdBase;
import net.sodacan.cli.CommandContext;

public class TopicStatusCmd extends CmdBase implements Action {

	public TopicStatusCmd( CommandContext cc) {
		super( cc );
	}

	@Override
	public void execute(CommandLine commandLine, int index) {
		String topicName = null;
		try {
			if (commandLine.getArgList().size() <= index) throw new SodacanException("missing topic name"); 
			topicName = commandLine.getArgs()[index];
			TopicAdmin topicAdmin = TopicAdmin.getInstance();
			System.out.println(topicAdmin.describeTopic(topicName));
		} catch (Exception e) {
			throw new SodacanException("Problem getting for topic: " + topicName, e);
		}
	}
}
