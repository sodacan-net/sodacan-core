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
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import net.sodacan.compiler.ModuleCompiler;
import net.sodacan.module.statement.SodacanModule;

public class TestCompiler {
    private static final String EXTENSION = ".scc";
    private static final String DIRBASE = "src/test/resources/";

    public List<String> getSccFileList(String dir) throws IOException {
    	Path ePath = Path.of(EXTENSION);
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
	public void testAll() throws IOException {
        ModuleCompiler compiler = new ModuleCompiler();
        List<String> filenameList = getSccFileList(DIRBASE);
        Collections.sort(filenameList);
		for (String filename : filenameList) {
			Path p = Path.of(DIRBASE+filename);
	        SodacanModule module = compiler.compile(p,new Properties());
	        System.out.println( module.getErrors());
	        assert(0==module.getErrors().size());
		}
	}

}
