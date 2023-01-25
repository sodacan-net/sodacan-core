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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.sodacan.module.value.Value;
import net.sodacan.module.value.ValueDeserializer;
import net.sodacan.module.value.ValueSerializer;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variables.ModuleVariables;
/**
 * We use aliases. This verifies that one record can be found either way.
 * @author john
 *
 */
public class TestVariableSerialization {

	static final BigDecimal NUMBER1 = new BigDecimal("123.4");
	static final String STRING1 = "A String";
	
	@Test
	public void testCounts() {
		ModuleVariables mvs = new ModuleVariables();
		VariableDef vd1 = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(NUMBER1)).build();
		mvs.addVariable(vd1);
		assert(1==mvs.nameCount());
		assert(1==mvs.uniqueVariableCount());
		VariableDef vd2 = VariableDef.newVariableDefBuilder().name("y").alias("z").initialValue(new Value(STRING1)).build();
		mvs.addVariable(vd2);
		assert(3==mvs.nameCount());
		assert(2==mvs.uniqueVariableCount());		
	}
	
	static class ValueWrapper {
		@JsonProperty("value")
		@JsonSerialize(using = ValueSerializer.class)
		@JsonDeserialize(using = ValueDeserializer.class)
		Value value;
		public ValueWrapper() {
			
		}
		public ValueWrapper( String str) {
			this.value = new Value(str);
		}
	}
	
	@Test
	public void testSerializeTrivialList() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		List<ValueWrapper> list1 = Arrays.asList(new ValueWrapper("a"),new ValueWrapper("b"),new ValueWrapper("c"));
		String json1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list1);
		System.out.println(json1);
		List<ValueWrapper> list2 = mapper.readValue(json1, new TypeReference<List<ValueWrapper>>() { });

//		for (ValueWrapper item : list2) {
//			System.out.println(item.value);
//		}
		assert(true);
	}
	@Test
	public void testSerialize() throws IOException {
		ModuleVariables mvs = new ModuleVariables();
		VariableDef vd1 = VariableDef.newVariableDefBuilder().name("x").initialValue(new Value(NUMBER1)).build();
		mvs.addVariable(vd1);
		VariableDef vd2 = VariableDef.newVariableDefBuilder().name("y").alias("z").initialValue(new Value(STRING1)).build();
		mvs.addVariable(vd2);
		// What does it look like as json?
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mvs.getAllVariables());
		System.out.println(json);
		// That was fun. Now, can we reconstruct the variables?
//		List<ModuleVariable> vl2 = Arrays.asList(mapper.readValue(json, ModuleVariable[].class));
		List<ModuleVariable> vl2 = mapper.readValue(json, new TypeReference<List<ModuleVariable>>() { });
//		List<ModuleVariable> vl2 = mapper.readValue(json, List.class);
//		ModuleVariable[] vl2 = mapper.readValue(json, ModuleVariable[].class);
		for (ModuleVariable mv : vl2) {
			System.out.println(mv);
		}
		ModuleVariables mvs2 = new ModuleVariables();
		mvs2.addAllVariables(vl2);
		System.out.println(mvs2);
		
	}
}
