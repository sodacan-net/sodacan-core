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

import java.time.ZonedDateTime;

import net.sodacan.module.expression.datetime.criteria.DateCriteria;

public class DayOfWeekCriteria extends DateCriteria {
	int dayOfWeek;

	public DayOfWeekCriteria(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public boolean isMatch(ZonedDateTime date) {
		int dow = date.getDayOfWeek().getValue();
		return (dow==dayOfWeek);
	}

	@Override
	public String toString() {
		return "DOW=" + dayOfWeek;
	}

}
