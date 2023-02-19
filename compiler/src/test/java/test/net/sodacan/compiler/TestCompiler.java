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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import net.sodacan.compiler.ModuleCompiler;
import net.sodacan.config.Config;
import net.sodacan.config.Location;
import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.statement.SodacanModule;
import net.sodacan.module.value.Value;
import net.sodacan.module.variables.CompositeVariables;
import net.sodacan.module.variables.SystemVariables;
import net.sodacan.module.variables.Variables;

public class TestCompiler {
    private static final String EXTENSION = ".scc";
    private static final String DIRBASE = "src/test/resources/";

    /**
     * Setup a mock configuration. Note: it really is a singleton (a side effect)
     * but we're in a unit test so it doesn't matter. the really good news is 
     * that we're not reading this from a file: That would be outside the bounds of
     * this particular unit test.
     * @return The configuration structure lightly populated.
     */
	public void setupTestConfig() {
		Config config = Config.getInstance();
		config.setLocation(new Location());
		config.getLocation().setLatitude(42.557982);
		config.getLocation().setLongitude(-123.393342);
		config.getLocation().setTimezone("America/Los_Angeles");
	}
	/**
	 * A close approximation to what a real runtime "cycle" setup might look like.
	 * @param module The module to be setup
	 * @return
	 */
	public Variables setupVariables( SodacanModule module) {
        // 
        Config config = Config.getInstance();
		ZonedDateTime now = ZonedDateTime.of(2023, 1, 21, 16, 30, 0, 0, ZoneId.of(config.getLocation().getTimezone()));
		SystemVariables sysvars = new SystemVariables(now,config);
        Variables mvs = module.createVariablesMap();
		CompositeVariables variables = new CompositeVariables(sysvars,mvs);
		return variables;
	}
	
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
	public void testAll() throws IOException {
		// I don't think we need properties any longer. Need to look into.
		Properties properties = new Properties();
		properties.setProperty("property1", "value of property 1");
        // Setup a fake runtime environment (this would normally not be here, but for testing)
        setupTestConfig();
        // Fire up the compiler
		ModuleCompiler compiler = new ModuleCompiler();
        List<String> filenameList = getSccFileList(DIRBASE);
        Collections.sort(filenameList);
		for (String filename : filenameList) {
			Path p = Path.of(DIRBASE+filename);
	        SodacanModule module = compiler.compile(p,properties);
	        System.out.println( "Errors: " + module.getErrors());
	        assert(0==module.getErrors().size());
	        Variables variables = setupVariables(module);
	        // We need to create a test message
	        ModuleMessage mmb = ModuleMessage.newModuleMessageBuilder().name("mode").value(new Value("on")).build();
	        boolean rslt = module.processEvent(variables, mmb);
		}
	}

}
