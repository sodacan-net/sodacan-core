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


public class AnnualRangeCriteria extends DateCriteria {
	protected int fromMonth;
	protected int fromDay;
	protected int throughMonth; 
	protected int throughDay;
	
	public AnnualRangeCriteria( int fromMonth, int fromDay, int throughMonth, int throughDay ) {
		this.fromMonth = fromMonth;
		this.fromDay = fromDay;
		this.throughMonth = throughMonth;
		this.throughDay = throughDay;
	}

	@Override
	public boolean isMatch(ZonedDateTime date) {
		ZonedDateTime dateFrom = ZonedDateTime.of(date.getYear(), fromMonth, fromDay, 0, 0, 0, 0, date.getZone());
		ZonedDateTime dateThrough = ZonedDateTime.of(date.getYear(), throughMonth, throughDay, 23, 59, 59, 0, date.getZone());
		return (date.isAfter(dateFrom) && date.isBefore(dateThrough));
	}

	@Override
	public String toString() {
		return "Range: " + fromMonth + "/" + fromDay + " through " + throughMonth + "/" + throughDay;
	}


}
