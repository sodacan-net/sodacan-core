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

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import net.sodacan.config.Config;
import net.sodacan.module.expression.Expression;
import net.sodacan.module.operator.AddOperator;
import net.sodacan.module.operator.DivideOperator;
import net.sodacan.module.operator.EqualsOperator;
import net.sodacan.module.operator.GreaterThanOperator;
import net.sodacan.module.operator.LessOrEqualToOperator;
import net.sodacan.module.operator.LessThanOperator;
import net.sodacan.module.operator.MultiplyOperator;
import net.sodacan.module.operator.SubtractOperator;
import net.sodacan.module.terminal.LiteralExpression;
import net.sodacan.module.terminal.VariableRefExpression;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.Variables;

public class TestSimpleExpressions extends TestConfig {
	static final String STRING1 = "a String";
	static final String STRING2 = "a";
	static final String STRINGb = "b";
	static final BigDecimal NUMBER1 = new BigDecimal("123.4");
	static final BigDecimal NUMBER2 = new BigDecimal("100.0");
	static final BigDecimal NUMBER2a = new BigDecimal("100");
	static final BigDecimal ANSWER1 = new BigDecimal("223.4");
	static final BigDecimal ANSWER2 = new BigDecimal("23.4");
	static final BigDecimal ANSWER3 = new BigDecimal("12340.00");
	static final BigDecimal ANSWER3a = new BigDecimal("12340.0");
	static final BigDecimal ANSWER4 = new BigDecimal("1.234");
	static final Value ANSWER5 = new Value(ANSWER1);
	@Test
	public void testStringLiteral() {
		Config config = setupConfig();
		Expression ex = new LiteralExpression(STRING1);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex.execute(variables, now);
		assert(STRING1.equals(result.getValue()));
	}

	@Test
	public void testTwoStringsForEquality() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(STRING2);
		Expression ex2 = new LiteralExpression(STRING2);
		Expression ex3 = new EqualsOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(result.getBoolean());
	}

	@Test
	public void testTwoStringsForInequality() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(STRING1);
		Expression ex2 = new LiteralExpression(STRING2);
		Expression ex3 = new EqualsOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(!result.getBoolean());
	}

	@Test
	public void testNumberLiteral() {
		Config config = setupConfig();
		Expression ex = new LiteralExpression(NUMBER1);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex.execute(variables, now);
		assert(NUMBER1.equals(result.getNumber()));
	}

	@Test
	public void testAddTwoNumbers() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new AddOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(ANSWER1.equals(result.getNumber()));
	}
	
	@Test
	public void testSubtractTwoNumbers() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new SubtractOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(ANSWER2.equals(result.getNumber()));
	}

	/**
	 * Using BigDecimal.equals is NOT forgiving with numbers of different scales.
	 * Be sure to use Bigdecimal.CompareTo in code
	 */
	@Test
	public void testMultiplyTwoNumbers() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new MultiplyOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(ANSWER3.equals(result.getNumber()));
	}

	/**
	 * Using BigDecimal.equals is NOT forgiving with numbers of different scales.
	 * Be sure to use Bigdecimal.CompareTo in code as we do here.
	 */
	@Test
	public void testMultiplyTwoNumbersDifferentScale() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2a);
		Expression ex3 = new MultiplyOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(0==ANSWER3.compareTo(result.getNumber()));
	}
	
	/**
	 * Notice that compareTo is forgiving with numbers of different scales.
	 */
	@Test
	public void testMultiplyTwoNumbersWithDifferentScale() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new MultiplyOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(0==ANSWER3a.compareTo(result.getNumber()));
	}

	@Test
	public void testDivideTwoNumbers() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new DivideOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex3.execute(variables, now);
		assert(0==ANSWER4.compareTo(result.getNumber()));
	}

	/**
	 * 123.4*100.0 = 223.0 is true
	 */
	@Test
	public void testCompareTwoAddedNumbers() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new AddOperator(ex1,ex2);
		Expression ex4 = new LiteralExpression(ANSWER1);
		Expression ex5 = new EqualsOperator(ex3,ex4);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex5.execute(variables, now);
		assert(result.getBoolean());
	}

	/**
	 * 123.4 > 100.0 is true
	 * 
	 */
	@Test
	public void testCompareTwoNumbersGT() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex5 = new GreaterThanOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex5.execute(variables, now);
		assert(result.getBoolean());
	}

	/**
	 * 123.4 < 100.0 is false
	 * 
	 */
	@Test
	public void testCompareTwoNumbersLT() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex5 = new LessThanOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex5.execute(variables, now);
		assert(!result.getBoolean());
	}

	/**
	 * 123.4 <= 100.0 is false
	 * 
	 */
	@Test
	public void testCompareTwoNumbersLE() {
		Config config = setupConfig();
		Expression ex1 = new LiteralExpression(NUMBER1);
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex5 = new LessOrEqualToOperator(ex1,ex2);
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		Value result = ex5.execute(variables, now);
		assert(!result.getBoolean());
	}

	/**
	 * x  with initial value 123.4
	 * x + 100.0 
	 * result is 223.4
	 */
	@Test
	public void testAddTwoNumbersOneIsAVariable() {
		Config config = setupConfig();
		Variables variables = new Variables();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 20, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
//		Expression ex1 = new LiteralExpression(NUMBER1);
		VariableDef variableDef = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(NUMBER1)).build();
		variables.addVariable(variableDef);
		Expression ex1 = new VariableRefExpression("x");
		Expression ex2 = new LiteralExpression(NUMBER2);
		Expression ex3 = new AddOperator(ex1,ex2);
		Value result = ex3.execute(variables, now);
		assert(0==ANSWER1.compareTo(result.getNumber()));
	}
	
	
}
