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
package net.sodacan.plugin.memory;

import java.util.Set;

import com.google.auto.service.AutoService;
import net.sodacan.mode.spi.StateStoreProvider;
import net.sodacan.mode.spi.VariablePayload;

/**
* This simple state store plugin is not fussy about mode, it accepts all as long as it's found on the classpath.
 * 
 */

@AutoService(StateStoreProvider.class)
public class StateStore extends Plugin implements StateStoreProvider {
	
	private int count = 0;
	
	@Override
	public void save(VariablePayload payload) {
		System.out.println("Seq: " + count++ + ", Mode: " + getMode() + ", Variable to save: " + payload.getContent());

	}

}
