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
package net.sodacan.mode.spi;

import java.beans.PropertyChangeEvent;


/**
 * Communicates a property change back from a plugin.
 * @author John Churin
 *
 */
public class PluginEvent extends PropertyChangeEvent {
	private static final long serialVersionUID = 1L;
	String mode;
	Plugin plugin;
		
	public PluginEvent(Plugin plugin, String propertyName, Object oldValue, Object newValue) {
		super(plugin, propertyName, oldValue, newValue);
		this.mode = plugin.getMode();
		this.plugin = plugin;
	}
	public String getMode() {
		return mode;
	}
	public Plugin getPlugin() {
		return plugin;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PluginEvent) {
			PluginEvent other = (PluginEvent)obj;
			if (getMode().equals(other.getMode())
			&& getPlugin()==other.getPlugin()
			&& getPropertyName().equals(other.getPropertyName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getPropertyName().hashCode();
	}

	
}
