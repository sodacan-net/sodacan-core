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
package net.sodacan.module.expression.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sodacan.SodacanException;
import net.sodacan.module.expression.Expression;
import net.sodacan.module.expression.datetime.criteria.AnnualRangeCriteria;
import net.sodacan.module.expression.datetime.criteria.Criteria;
import net.sodacan.module.expression.datetime.criteria.DateCriteria;
import net.sodacan.module.expression.datetime.criteria.EndingCriteria;
import net.sodacan.module.expression.datetime.criteria.MonthCriteria;
import net.sodacan.module.expression.datetime.criteria.MonthDayCriteria;
import net.sodacan.module.expression.datetime.criteria.MonthDayYearCriteria;
import net.sodacan.module.expression.datetime.criteria.StartingCriteria;
import net.sodacan.module.expression.datetime.criteria.WeekdayCriteria;
import net.sodacan.module.expression.datetime.criteria.WeekendCriteria;
import net.sodacan.module.expression.datetime.criteria.YearCriteria;
import net.sodacan.module.value.Value;
import net.sodacan.module.variables.Variables;

/**
 * Combining date criteria in one expression provides "and" and "or" combinations.
 * For example,  ON weekends DURING summer
 * This means what it looks like, weekends during summer.
 * However, ON monday tuesday DURING winter
 * means only a monday or a tuesday, and only during the winter.
 * More: ON July 1
 * Date Range: FROM July 1 THROUGH August 17
 *
 * @author John Churin
 *
 */
public class DateExpression extends Expression {

	private List<DateCriteria> criteria = new ArrayList<>();

	private DateExpression(DateExpressionBuilder builder) {
		this.criteria = builder.criteria;
	}

	@Override
	public Value execute(Variables variables) {
		Value v = variables.findValue("system.clock#now");
		return new Value(isMatch(v.getDateTime()));
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Date Criteria: ");
		boolean first = true;
		for (Criteria criterium : criteria) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(criterium);
		}
		if (first) {
			sb.append("Any");
		}
		sb.append('\n');
		return sb.toString();
	}

	/**
	 * You give me a datetime and I'll tell you if it's included in the set.
	 * If there is no criteria, then all dates match.
	 * @param now
	 * @return true if it matched at least one criteria
	 */
	public boolean isMatch( ZonedDateTime now) {
		if (criteria.size()==0) {
			return true;
		}
		for (Criteria criterium : criteria) {
			if (criterium.isMatch(now)) return true;
		}
		return false;
	}

	/**
	 * Create a new, empty, builder for a DateExpression
	 */
	public static DateExpressionBuilder newDateExpressionBuilder() {
		return new DateExpressionBuilder();
	}

	protected static Map<String,List<DayOfWeek>> buildDowMap() {
		Map<String,List<DayOfWeek>> dows = new HashMap<>();
		dows.put("Monday", Arrays.asList(DayOfWeek.MONDAY));
		dows.put("Tuesday", Arrays.asList(DayOfWeek.TUESDAY));
		dows.put("Wednesday", Arrays.asList(DayOfWeek.WEDNESDAY));
		dows.put("Thursday", Arrays.asList(DayOfWeek.THURSDAY));
		dows.put("Friday", Arrays.asList(DayOfWeek.FRIDAY));
		dows.put("Saturday", Arrays.asList(DayOfWeek.SATURDAY));
		dows.put("Sunday", Arrays.asList(DayOfWeek.SUNDAY));
		dows.put("weekends", Arrays.asList(DayOfWeek.SATURDAY,DayOfWeek.SUNDAY));
		dows.put("weekdays", Arrays.asList(DayOfWeek.MONDAY,DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY,DayOfWeek.THURSDAY,DayOfWeek.FRIDAY));
		return dows;
	}
	protected static Map<String,Integer> buildMonthMap() {
		Map<String,Integer> month = new HashMap<>();
		month.put("January", 1);
		month.put("February", 2);
		month.put("March", 3);
		month.put("April", 4);
		month.put("May", 5);
		month.put("June", 6);
		month.put("July", 7);
		month.put("August", 8);
		month.put("September", 9);
		month.put("October", 10);
		month.put("November", 11);
		month.put("December", 12);
		return month;
	}
	protected static Map<String,Integer> buildLastDayOfMonthMap() {
		Map<String,Integer> lastDayOfMonth = new HashMap<>();
		lastDayOfMonth.put("January", 31);
		lastDayOfMonth.put("February", 28);
		lastDayOfMonth.put("March", 31);
		lastDayOfMonth.put("April", 30);
		lastDayOfMonth.put("May", 31);
		lastDayOfMonth.put("June", 30);
		lastDayOfMonth.put("July", 31);
		lastDayOfMonth.put("August", 31);
		lastDayOfMonth.put("September", 30);
		lastDayOfMonth.put("October", 31);
		lastDayOfMonth.put("November", 30);
		lastDayOfMonth.put("December", 31);
		return lastDayOfMonth;
	}
	/**
	 * Builder class for DateExpressions
	 *
	 */
	public static class DateExpressionBuilder {
		private List<DateCriteria> criteria = new ArrayList<>();

		protected static Map<String,List<DayOfWeek>> dows = buildDowMap();
		protected static Map<String,Integer> months = buildMonthMap();
		protected static Map<String,Integer> lastDayOfMonth = buildLastDayOfMonthMap();
		
		protected DateExpressionBuilder() {
		}
		
		public DateExpressionBuilder date(LocalDate time) {
			return this;
		}

		public DateExpressionBuilder month(int month) {
			if (month<1 || month>12) {
				throw new SodacanException("Invalid month number: " + month);
			}
			criteria.add(new MonthCriteria(month));
			return this;
		}
		public DateExpressionBuilder month(String month) {
			return month(months.get(month));
		}
		public DateExpressionBuilder year(int y) {
			criteria.add(new YearCriteria(y));
			return this;
		}
		public DateExpressionBuilder january() {
			return month(1);
		}
		public DateExpressionBuilder february() {
			return month(2);
		}
		public DateExpressionBuilder march() {
			return month(3);
		}
		public DateExpressionBuilder april() {
			return month(4);
		}
		public DateExpressionBuilder may() {
			return month(5);
		}
		public DateExpressionBuilder june() {
			return month(6);
		}
		public DateExpressionBuilder july() {
			return month(7);
		}
		public DateExpressionBuilder august() {
			return month(8);
		}
		public DateExpressionBuilder september() {
			return month(9);
		}
		public DateExpressionBuilder october() {
			return month(10);
		}
		public DateExpressionBuilder november() {
			return month(11);
		}
		public DateExpressionBuilder december() {
			return month(12);
		}

		public DateExpressionBuilder spring() {
			return season("spring");
		}
		public DateExpressionBuilder summer() {
			return season("summer");
		}
		public DateExpressionBuilder fall() {
			return season("fall");
		}
		public DateExpressionBuilder autumn() {
			return season("autumn");
		}
		public DateExpressionBuilder winter() {
			return season("winter");
		}
		public DateExpressionBuilder season(String season) {
			if ("spring".equalsIgnoreCase(season)) {
				return this.april().may().june();
			}
			if ("summer".equalsIgnoreCase(season)) {
				return this.july().august().september();
			}
			if ("fall".equalsIgnoreCase(season) || "autum".equalsIgnoreCase(season)) {
				return this.october().november().december();
			}
			if ("winter".equalsIgnoreCase(season)) {
				return this.january().february().march();
			}
			return this;
		}
		public DateExpressionBuilder day(String day) {
			List<DayOfWeek> dowlist = dows.get(day);
			if (dowlist==null) {
				throw new SodacanException("Day of week not recognized: " + day);
			}
			for (DayOfWeek dow : dowlist) {
				criteria.add(new DayOfWeekCriteria(dow.getValue()));
			}
			return this;
		}
		
		public DateExpressionBuilder monthDay(int month, int day) {
			criteria.add(new MonthDayCriteria(month, day));			
			return this;
		}
		
		public DateExpressionBuilder monthDay(String month, String day) {
			return monthDay(months.get(month),Integer.parseInt(day));
		}

		public DateExpressionBuilder monthDayYear(int month, int day, int year) {
			criteria.add(new MonthDayYearCriteria(month, day, year));
			return this;
		}
		
		public DateExpressionBuilder monthDayYear(String month, String day, String year) {
			return monthDayYear(months.get(month),Integer.parseInt(day), Integer.parseInt(year));
		}
		
		public DateExpressionBuilder starting(int month, int day, int year) {
			criteria.add(new StartingCriteria(month,day,year));
			return this;
		}
		
		public DateExpressionBuilder ending(int month, int day, int year) {
			criteria.add(new EndingCriteria(month,day,year));
			return this;
		}
		
		public DateExpressionBuilder starting(String month, String day, String year) {
			return starting(months.get(month),Integer.parseInt(day), Integer.parseInt(year));
		}
		
		public DateExpressionBuilder ending(String month, String day, String year) {
			return ending(months.get(month),Integer.parseInt(day), Integer.parseInt(year));
		}

		public DateExpressionBuilder annualRange(int fromMonth, int fromDay, int throughMonth, int throughDay) {
			criteria.add(new AnnualRangeCriteria( fromMonth, fromDay, throughMonth, throughDay));
			return this;
		}
		/**
		 * Specify an annual date range. June 1 through September 15
		 * @param fromMonth
		 * @param fromDay If null, then the first of the month
		 * @param throughMonth
		 * @param throughDay If null, then the last day of the month
		 * @return
		 */
		public DateExpressionBuilder annualRange(String fromMonth, String fromDay, String throughMonth, String throughDay) {
			int fromDayInt;
			if (fromDay!=null) {
				fromDayInt = Integer.parseInt(fromDay);
			} else {
				fromDayInt = 1;
			}
			int throughDayInt;
			if (throughDay!=null) {
				throughDayInt = Integer.parseInt(throughDay);
			} else {
				throughDayInt = lastDayOfMonth.get(throughMonth);
			}
			return annualRange(months.get(fromMonth),fromDayInt, months.get(throughMonth),throughDayInt);
		}
		
		public DateExpressionBuilder monday() {
			criteria.add(new DayOfWeekCriteria(1));
			return this;
		}
		
		public DateExpressionBuilder tuesday() {
			criteria.add(new DayOfWeekCriteria(2));
			return this;
		}
		
		public DateExpressionBuilder wednesday() {
			criteria.add(new DayOfWeekCriteria(3));
			return this;
		}
		
		public DateExpressionBuilder thursday() {
			criteria.add(new DayOfWeekCriteria(4));
			return this;
		}
		
		public DateExpressionBuilder friday() {
			criteria.add(new DayOfWeekCriteria(5));
			return this;
		}
		
		public DateExpressionBuilder saturday() {
			criteria.add(new DayOfWeekCriteria(6));
			return this;
		}
		
		public DateExpressionBuilder sunday() {
			criteria.add(new DayOfWeekCriteria(7));
			return this;
		}
		
		public DateExpressionBuilder weekend() {
			criteria.add(new WeekendCriteria());
			return this;
		}
		
		public DateExpressionBuilder weekday() {
			criteria.add(new WeekdayCriteria());
			return this;
		}

		/**
		 * Add a specific date from y,m,d components
		 * @return
		 */
		public DateExpressionBuilder date(int year, int month, int day ) {
//			this.localDate = LocalDate.of(year,month,day);
			return this;
		}
		public DateExpressionBuilder date(int year, String month, int day) {
			
			return this;
		}
		public DateExpression build() {
			return new DateExpression(this);
		}

	}


}
