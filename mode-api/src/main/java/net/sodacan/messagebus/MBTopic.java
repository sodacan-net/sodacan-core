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

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * General topic interface that can be backed by memory queue or Kafka
 * @author John Churin
 *
 */
public interface MBTopic extends Closeable {
	public String getTopicName();
//	public long getNextOffset();
	public MBRecord poll(Duration timeout);
	public Map<String, MBRecord> snapshot();
	public void close() throws IOException;
}
