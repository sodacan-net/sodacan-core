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
import net.sodacan.api.topic.TopicConsumer;
import net.sodacan.cli.Action;
import net.sodacan.cli.CmdBase;
import net.sodacan.cli.CommandContext;

public class TopicWatchCmd extends CmdBase implements Action {

	public TopicWatchCmd( CommandContext cc) {
		super( cc );
	}

	@Override
	public void execute(CommandLine commandLine, int index) {
		if (commandLine.getArgList().size() <= index) throw new SodacanException("missing topic name"); 
		String topicName = commandLine.getArgs()[index];
		TopicConsumer tc = new TopicConsumer(topicName);
		tc.consume(System.out, true);
	}

}
