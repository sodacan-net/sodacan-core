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
package net.sodacan.messagebus;

import java.time.Instant;
/**
 * An implementation of message bus record that represents a clock tick.
 * @author John Churin
 *
 */
public class MBTick implements MBRecord {
	long tick;
	
	public MBTick( long timestamp) {
		tick = timestamp;
	}
	@Override
	public String getTopic() {
		return "clock";
	}

	@Override
	public long getTimestamp() {
		return tick;
	}

	@Override
	public long getOffset() {
		return 0;
	}

	@Override
	public String getKey() {
		return "tick";
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public boolean isEOF() {
		return false;
	}
	@Override
	public String toString() {
		return "Tick: " + Instant.ofEpochMilli(tick);
	}

}
