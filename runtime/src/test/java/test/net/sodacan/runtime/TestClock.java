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
package test.net.sodacan.runtime;

import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import net.sodacan.runtime.ManualClock;
import net.sodacan.runtime.Ticker;

public class TestClock {
	/**
	 * For this test, we use a blocking queue which would not be appropriate for the runtime,
	 * which must coordinate Ticks with incoming message. In that case, a ConcurrentLinkedQueue is
	 * appropriate.
	 * @throws InterruptedException
	 */
	@Test
	public void test() throws InterruptedException {
		BlockingQueue<Instant> queue = new LinkedBlockingQueue<Instant>();
		ManualClock clock = new ManualClock(2023, 1, 31, 10, 36, 0);
		Instant t1 = new ManualClock(2023, 1, 31, 10, 37, 0).instant();
		Instant t2 = new ManualClock(2023, 1, 31, 10, 38, 0).instant();
		Instant t3 = new ManualClock(2023, 1, 31, 10, 39, 0).instant();
		Ticker.createAndStartTicker(queue,clock);
		clock.plusOneMinute();
		clock.plusOneMinute();
		clock.plusOneMinute();
		Instant r1 = queue.take();
		assert(r1.equals(t1));
		Instant r2 = queue.take();
		assert(r2.equals(t2));
		Instant r3 = queue.take();
		assert(r3.equals(t3));
	}

}
