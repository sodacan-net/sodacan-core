package net.sodacan.rules;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Sun {
	private String sunrise;
	private String sunset;
	private String solar_noon;
	private String day_length;
	private String civil_twilight_begin;
	private String civil_twilight_end;
	private String nautical_twilight_begin;
	private String nautical_twilight_end;
	private String astronomical_twilight_begin;
	private String astronomical_twilight_end;
	private ZonedDateTime queryTime;
	public Sun() {
		
	}
	@JsonIgnore
	public String toString() {
		return "Sunrise: ";// + getSunriseDT().withZoneSameInstant(ZoneId.systemDefault()).toLocalTime() + " sunset: " + getSunsetDT().withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
	}
	@JsonIgnore
	public ZonedDateTime getQueryTime() {
		return queryTime;
	}
	@JsonIgnore
	public void setQueryTime(ZonedDateTime queryTime) {
		this.queryTime = queryTime;
	}
	public String getSunrise() {
		return sunrise;
	}
	public ZonedDateTime getSunriseDT(ZoneId zoneId) {
		return ZonedDateTime.ofInstant(Instant.parse(sunrise), zoneId);
	}
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}
	public String getSunset() {
		return sunset;
	}
	public ZonedDateTime getSunsetDT(ZoneId zoneId) {
		return ZonedDateTime.ofInstant(Instant.parse(sunset), zoneId);
	}
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}
	public String getSolar_noon() {
		return solar_noon;
	}
	public void setSolar_noon(String solar_noon) {
		this.solar_noon = solar_noon;
	}
	public String getDay_length() {
		return day_length;
	}
	public String getDay_lengthHM() {
		int s = Integer.parseInt(day_length);
		int h = s/(60*60);
		int m = s%(60*60)/60;
		return "Sunlight today is " + h + " hrs " + m + " mins";
	}
	public void setDay_length(String day_length) {
		this.day_length = day_length;
	}
	public String getCivil_twilight_begin() {
		return civil_twilight_begin;
	}
	public void setCivil_twilight_begin(String civil_twilight_begin) {
		this.civil_twilight_begin = civil_twilight_begin;
	}
	public String getCivil_twilight_end() {
		return civil_twilight_end;
	}
	public void setCivil_twilight_end(String civil_twilight_end) {
		this.civil_twilight_end = civil_twilight_end;
	}
	public String getNautical_twilight_begin() {
		return nautical_twilight_begin;
	}
	public void setNautical_twilight_begin(String nautical_twilight_begin) {
		this.nautical_twilight_begin = nautical_twilight_begin;
	}
	public String getNautical_twilight_end() {
		return nautical_twilight_end;
	}
	public void setNautical_twilight_end(String nautical_twilight_end) {
		this.nautical_twilight_end = nautical_twilight_end;
	}
	public String getAstronomical_twilight_begin() {
		return astronomical_twilight_begin;
	}
	public void setAstronomical_twilight_begin(String astronomical_twilight_begin) {
		this.astronomical_twilight_begin = astronomical_twilight_begin;
	}
	public String getAstronomical_twilight_end() {
		return astronomical_twilight_end;
	}
	public void setAstronomical_twilight_end(String astronomical_twilight_end) {
		this.astronomical_twilight_end = astronomical_twilight_end;
	}
	
}
