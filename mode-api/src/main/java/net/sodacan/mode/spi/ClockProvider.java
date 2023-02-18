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

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import net.sodacan.messagebus.MBRecord;

public interface ClockProvider extends ModeProvider {
	public void setClock(int year, int month, int day, int hour, int minute, int second);
	public long getTimestamp();
	public void advanceClock( Duration duration);
	public  Future<?> follow(BlockingQueue<MBRecord> queue);
}
