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

import java.time.Clock;
import java.time.Instant;
import java.util.Queue;

/**
 * >p>Our job is to provide clock ticks, one per minute without skipping or duplicating any individual ticks. We run in our own
 * thread and deliver clock ticks to the specified queue. The clock can be a real clock or a synthetic clock used for testing.
 * In any case, the supplied clock will not affect our job of producing one tick per minute.</p>
 * 
 * <p>This class will deliver ticks only as the raw clock prescribes. The tick is never allowed to overtake the raw clock. However,
 * ticks may occur very quickly if the tick time falls behind the clock for any reason.</p>
 * <p>A side note about the cycle function: The message stream also contains a timestamp which, along with the Tick queue is used to merge events into a module.
 * This is helpful for testing or startup when the arrival messages could get ahead of the clock thereby causing 
 * passage-of-time statements to occur out of order.</p>
 * <p>This Ticker does not advance the raw clock, which must be provided by an external time source.</p>
 * <p>Note: This Ticker is not used for Sodacan Timers - which have a much smaller time resolution than
 * Ticker (nominally one minute).</p>
 * 
 * @author John Churin
 *
 */
public class Ticker implements Runnable {

	public static final long TICK_SECONDS = 60;
	private Instant tickTime;	// The only way to "see" this variable is through the queue that we populate.
	private Clock clock;
	private Queue<Instant> queue;

	/**
	 * <p>Construct a new Ticker and start it in a thread. The start time ensures that 
	 * the ticker starts in a stable state. When the raw time is set to a time ahead of the start time
	 * then a tick will be sent to the queue.</p>
	 * @param queue The queue to populate with tick events.
	 * @param clock The clock to use to get the "current" time (now)
	 * @return A freshly minted and started Ticker
	 */
	public static Ticker createAndStartTicker(Queue<Instant> queue, Clock clock) {
		Ticker ticker = new Ticker(queue);
		ticker.clock = clock;
		ticker.tickTime = clock.instant();
		new Thread(ticker,"Ticker").start();
		return ticker;
	}

	/**
	 * Construct a new Ticker specifying the queue that it will feed as
	 * time passes.
	 */
	protected Ticker( Queue<Instant> queue) {
		this.queue = queue;
	}
	
	/**
	 * <p>The main ticker loop. Ticks are sent (queued) up until the current time, then it sleeps for a while.</p>
	 */
	@Override
	public void run() {
		while (true) {
			while (tickTime.isBefore(clock.instant())) {
				tickTime = tickTime.plusSeconds(TICK_SECONDS);
				queue.add(tickTime);
			}
			try {
//				Thread.sleep(TICK_SECONDS/2);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
