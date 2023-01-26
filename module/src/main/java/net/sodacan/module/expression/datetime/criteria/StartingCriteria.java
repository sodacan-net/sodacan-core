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

/**
 * The current date has to be beyond the date held here.
 * @author John Churin
 *
 */
public class StartingCriteria extends MonthDayYearCriteria {

	public StartingCriteria(int month, int day, int year) {
		super(month, day, year);
	}

	@Override
	public boolean isMatch(ZonedDateTime date) {
		ZonedDateTime datec = ZonedDateTime.of(year, month, month, day, 0, 0, 0, date.getZone());
		return date.isAfter(datec);
	}
	@Override
	public String toString() {
		return "Starting " + month + '/' + day + '/' + year;
	}

}
