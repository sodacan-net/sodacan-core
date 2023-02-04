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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/**
 * Service Providers in this plugin respond to "memory" plugin type
 * @author John Churin
 *
 */
public abstract class Plugin implements ModeProvider {
	protected String mode;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

	@Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

	public void firePropertyChange​(String propertyName, Object oldValue, Object newValue) {
		this.pcs.firePropertyChange(new PluginEvent(this, propertyName, null, newValue));
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}
	
}
