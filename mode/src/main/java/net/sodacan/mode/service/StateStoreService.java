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
package net.sodacan.mode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sodacan.mode.Mode;
import net.sodacan.mode.spi.ModeProvider;
import net.sodacan.mode.spi.StateStoreProvider;

public class StateStoreService extends ModeService {
	protected List<StateStoreProvider> providers = new ArrayList<>();

	public StateStoreService(Mode mode) {
		super(mode, StateStoreProvider.class);
	}

	@Override
	public void loadProviders(Set<String> types) {
		for (ModeProvider provider : getLoader()) {
			if (provider.isMatch(types)) {
				providers.add((StateStoreProvider) provider);
			}
		}
	}

	@Override
	protected List<StateStoreProvider> getProviders() {
		return providers;
	}
	
}
