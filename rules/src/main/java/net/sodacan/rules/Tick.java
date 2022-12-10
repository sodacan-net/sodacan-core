package net.sodacan.rules;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

public class Tick extends Event {
	private ZonedDateTime now;
	private ZonedDateTime sunrise;
	private ZonedDateTime sunset;
	
	public Tick(ZonedDateTime now) {
		super(null, null);
		this.now = now;
	}
	
	@Override
	public String getName() {
		return toString();
	}
	
	public ZonedDateTime getNow() {
		return now;
	}

	public ZonedDateTime getSunrise() {
		return sunrise;
	}

	public void setSunrise(ZonedDateTime sunrise) {
		this.sunrise = sunrise;
	}

	public ZonedDateTime getSunset() {
		return sunset;
	}

	public void setSunset(ZonedDateTime sunset) {
		this.sunset = sunset;
	}

	public int getYear() {
		return now.get(ChronoField.YEAR);
	}
	public int getMonth() {
		return now.get(ChronoField.MONTH_OF_YEAR);
	}
	public int getDay() {
		return now.get(ChronoField.DAY_OF_MONTH);
	}
	public int getHour() {
		return now.get(ChronoField.HOUR_OF_DAY);
	}
	public int getMinute() {
		return now.get(ChronoField.MINUTE_OF_HOUR);
	}

	public boolean isDaytime() {
		if (now.isAfter(getSunrise()) && now.isBefore(getSunset())) {
			return true;
		}
		return false;
	}
	public boolean isNighttime() {
		return !isDaytime();
	}
	@Override
	public String toString() {
		return "Tick";
	}

	/**
	 * Ensure that a tick event is a singleton
	 */
	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Tick);
	}
	
}
