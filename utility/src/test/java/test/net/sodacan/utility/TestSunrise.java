package test.net.sodacan.utility;

import static org.junit.Assert.*;

import org.junit.Test;
import org.shredzone.commons.suncalc.SunTimes;

public class TestSunrise {

	@Test
	public void test() {
		SunTimes ss = SunTimes.compute()
		        .on(2023, 1, 6)             // May 1st, 2020, starting midnight
		        .latitude(42.557982)
		        .longitude(-123.393342)
		        .timezone("America/Los_Angeles")
		        .execute();
		System.out.println("Sunrise: " + ss.getRise());
		System.out.println("Sunset:  " + ss.getSet());
	}

}
