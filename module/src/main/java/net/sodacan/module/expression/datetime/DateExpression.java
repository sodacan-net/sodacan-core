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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sodacan.SodacanException;
import net.sodacan.module.expression.Expression;
import net.sodacan.module.expression.datetime.TimeExpression.TimeExpressionBuilder;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDefs;

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

	List<DateCriteria> criteria = new ArrayList<>();

	private DateExpression(DateExpressionBuilder builder) {
		this.criteria = builder.criteria;
	}

	@Override
	public Value execute(VariableDefs variables) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Create a new, empty, builder for a DateExpression
	 */
	public static DateExpressionBuilder newDateExpressionBuilder() {
		return new DateExpressionBuilder();
	}

	/**
	 * Builder class for DateExpressions
	 *
	 */
	public static class DateExpressionBuilder {
		List<DateCriteria> criteria = new ArrayList<>();

		protected DateExpressionBuilder() {
			
		}
		
		public DateExpressionBuilder date(LocalDate time) {
//			this.localDate = time;
			return this;
		}
		
		public DateExpressionBuilder weekend() {
			criteria.add(new WeekendCriteria());
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
