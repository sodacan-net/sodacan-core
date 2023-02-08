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
package net.sodacan.api.topic;
/**
 * Consume the Modes topic looking only for one mode.
 * @author John Churin
 *
 */
public class ModeConsumer extends ReductionConsumer<String,String> {
	private String modeName;
	public ModeConsumer(String modeName) {
		super(Initialize.MODES);
		this.modeName = modeName;
	}
	/**
	 * Add a filter to the process = we're only interested in one mode
	 */
	@Override
	protected void processRecord(String name, String value) {
		if (name.equals(modeName)) {
			super.processRecord(name, value);
		}
	}
	
}
