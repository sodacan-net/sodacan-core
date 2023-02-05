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
package test.net.sodacan.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodacan.SodacanException;
import net.sodacan.module.value.Value;
import net.sodacan.module.variable.ModuleVariable;
import net.sodacan.module.variable.Variable;
import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.VariableDef.VariableType;

public class TestModuleSerialize {
    private static final String EXTENSION = ".scc";
    private static final String DIRBASE = "src/test/resources/";

	
	public List<String> getSccFileList(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
              .filter(file -> !Files.isDirectory(file))
              .filter(file -> file.toString().endsWith(EXTENSION))
              .map(Path::getFileName)
              .map(Path::toString)
              .collect(Collectors.toList());
        }
    } 

	@Test
	public void testSerializeModule() throws IOException {
        List<String> filenameList = getSccFileList(DIRBASE);
        Collections.sort(filenameList);
		for (String filename : filenameList) {
			Path path = Path.of(DIRBASE+filename);
			String rawSource;
			try {
				rawSource = Files.readString(path);
			} catch (IOException e) {
				throw new SodacanException("Error opening module file " + path.toString(), e);
			}
			String[] lines = rawSource.split("\n");
			List<Value> values = new ArrayList<>(lines.length);
			for (String line : lines) {
				values.add(new Value(line));
			}
			Value v1 = new Value(values);
			VariableDef vd = VariableDef.newVariableDefBuilder()
					.name("scc")
					.type(VariableType.SourceVariable)
					.build();
			Variable var1 = new ModuleVariable(vd,v1);
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			String json1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(var1);
			System.out.println(json1);
			Variable var2 = mapper.readValue(json1, ModuleVariable.class);
			System.out.println(var1);
			assert(var1.equals(var2));
			assert(var1.getValue().equals(var2.getValue()));
		}
	}

}
