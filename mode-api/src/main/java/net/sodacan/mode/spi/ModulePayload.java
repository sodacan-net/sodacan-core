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
/**
 * This class only carries meta-data about a module, not the module contents, which will show up later in the
 * inbound module/variable queue.
 * @author John Churin
 *
 */
public class ModulePayload {
	private String modeName;
	private String moduleName;
	private String instanceName;
	/**
	 * This constructor is needed for serialization
	 */
	protected ModulePayload() { }

	/**
	 * Build an immutable ModulePayload for transmission between MessageBus plugin(s) and Runtime.
	 * @param modeName
	 * @param moduleName
	 * @param instanceName
	 */
	public ModulePayload( String modeName, String moduleName, String instanceName) {
		this.modeName = modeName;
		this.moduleName = moduleName;
		this.instanceName = instanceName;
	}
	
	public String getModeName() {
		return modeName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public String getInstanceName() {
		return instanceName;
	}

}
