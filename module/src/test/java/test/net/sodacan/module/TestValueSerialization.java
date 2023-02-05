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
package test.net.sodacan.module;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.sodacan.module.value.Value;

public class TestValueSerialization {
	private static final String STRING1 = "A string";
	private static final String NUMBERSTRING2 = "654.30";
	private static final BigDecimal NUMBER2 = new BigDecimal(NUMBERSTRING2);

	@Test
	public void testStringValue() {
		Value v1 = new Value(STRING1);
		String v1str = v1.serialize();
		Value v2 = Value.deserialize(v1str);
		assert(v2.getString().equals(STRING1));
	}

	@Test
	public void testNumberValue() {
		Value v1 = new Value(NUMBER2);
		String v1str = v1.serialize();
		Value v2 = Value.deserialize(v1str);
		// We use the strict form of comparison
		assert(v2.getNumber().equals(NUMBER2));
	}

	@Test
	public void testBooleanValue() {
		Value v1 = new Value(true);
		String v1str = v1.serialize();
		Value v2 = Value.deserialize(v1str);
		assert(v2.getBoolean()==v1.getBoolean());
	}

	@Test
	public void testDateTimeValue() {
		ZonedDateTime now = ZonedDateTime.now();
		Value v1 = new Value(now);
		String v1str = v1.serialize();
		Value v2 = Value.deserialize(v1str);
		assert(v2.getDateTime().equals(now));
	}

	@Test
	public void testIdentifierValue() {
		Value v1 = new Value("ida",true);
		String v1str = v1.serialize();
		Value v2 = Value.deserialize(v1str);
		assert(v2.getIdentifier().equals(v1.getIdentifier()));
	}

	@Test
	public void testArrayValue() {
		List<Value> list = Arrays.asList(new Value("a"), new Value("b"), new Value("c"));
		Value v1 = new Value(list);
		String v1str = v1.serialize();
		Value v2 = Value.deserialize(v1str);
		assert(v2.getArray().equals(v1.getArray()));
		
	}
}
