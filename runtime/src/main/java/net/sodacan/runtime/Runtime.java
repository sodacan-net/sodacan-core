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
package net.sodacan.runtime;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.sodacan.module.statement.SodacanModule;

public class Runtime {
	Set<SodacanModule> modules = new HashSet<>();
	@JsonIgnore
	Instant timestamp;	// Set and used by Runtime
	/**
	 * The Timestamp of a module originates in a clock plugin and is set by the Runtime system. 
	 * It represents the time "now" as far as the current processing cycle of the module is concerned.
	 * This property is also accessible through system variables, which are also not persisted.
	 * It is a transient attribute, not persisted.
	 * @return
	 */
	@JsonIgnore
	public Instant getTimestamp() {
		return timestamp;
	}

	@JsonIgnore
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

}
