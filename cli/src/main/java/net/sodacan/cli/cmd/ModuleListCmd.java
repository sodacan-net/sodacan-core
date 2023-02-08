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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import net.sodacan.api.topic.Initialize;
import net.sodacan.api.topic.ReductionConsumer;
import net.sodacan.cli.Action;
import net.sodacan.cli.CmdBase;
import net.sodacan.cli.CommandContext;

public class ModuleListCmd extends CmdBase implements Action, PropertyChangeListener {

	public ModuleListCmd( CommandContext cc) {
		super( cc );
	}

	@Override
	public void execute(CommandLine commandLine, int index) {
		needReductionConsumer(Initialize.MODULES, this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println(event.getPropertyName() + " = " + event.getNewValue());
	}

}
