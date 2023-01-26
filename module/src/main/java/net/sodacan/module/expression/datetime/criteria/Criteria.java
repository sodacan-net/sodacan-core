package net.sodacan.module.expression.datetime.criteria;
import java.time.ZonedDateTime;

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

public abstract class Criteria {

	public Criteria() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Return true of the date passed in meets the this criteria.
	 * For example, the Weekend subclass returns true of the
	 * passed-in date is a weekend day
	 * @param date
	 * @return
	 */
	public abstract boolean isMatch( ZonedDateTime date);

}
