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
package net.sodacan.module.expression.datetime.criteria;

import java.time.ZonedDateTime;

public class MonthDayYearCriteria extends DateCriteria {

	protected int month;
	protected int day;
	protected int year;
	
	public MonthDayYearCriteria(int month, int day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}

	@Override
	public boolean isMatch(ZonedDateTime date) {
		int month=date.getMonthValue();
		int day=date.getDayOfMonth();
		int year=date.getYear();
		return (this.month==month && this.day==day && year==year);
	}

	@Override
	public String toString() {
		return "M/D/Y " + month + '/' + day + '/' + year;
	}

}
