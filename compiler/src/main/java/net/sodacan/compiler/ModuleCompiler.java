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
package net.sodacan.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.text.StringSubstitutor;

import net.sodacan.SodacanException;
import net.sodacan.module.ModuleComponent;
import net.sodacan.module.SodacanModule;

/**
 * Compile a module.
 *
 */
public class ModuleCompiler {
	/**
	 * Construct a module compiler
	 */
	public ModuleCompiler( ) {
	}
	
	public SodacanModule compile( Path path, Properties properties) {
		String rawSource;
		String fileName = path.getFileName().toString();
		try {
			rawSource = Files.readString(path);
		} catch (IOException e) {
			throw new SodacanException("Error opening module file " + path.toString(), e);
		}
		SodacanModule module = new SodacanModule();
    	if (properties==null) {
    		module.setSource(rawSource);
    	} else {
    		module.setSource(StringSubstitutor.replace(rawSource,properties));
    	}
		module.setOriginalFileName(fileName);
		module.setSource(rawSource);
		compile(module);
		return module;
	}
	
	/**
	 * Compile a module from raw source. If properties is non-null, do a substitution
	 * prior to compilation.
	 * This compiler is unaware of any other modules and so cannot check for duplicate module names.
	 * This and other checks are made when the module is about to be deployed. See SodaCan API.
	 * @param rawSource
	 * @param properties
	 * @return SodacanModule which contains all of the compiled results including a copy of the
	 * raw source and the pre-parsed source (after string substitution).
	 */
	public SodacanModule compile(String rawSource, Properties properties) {
		SodacanModule module = new SodacanModule();
		String source;
    	if (properties==null) {
    		source = rawSource;
    	} else {
    		source = StringSubstitutor.replace(rawSource,properties);
    	}
        // Hang onto the source in the resulting modules
        module.setSource(source);
        compile(module);
        return module;
	}
	
	protected void compile( SodacanModule module ) {
        SccLexer lexer = new SccLexer(CharStreams.fromString(module.getSource()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SccParser parser = new SccParser(tokens);
        parser.addParseListener(new SccListener(parser,module));
        parser.removeErrorListeners();
        parser.addErrorListener(new SccErrorListener(module));
//        System.out.println("*****Start parse: " + file);
        SccParser.StartContext tree = parser.start();
        System.out.println("*****Finish parse: with " + parser.getNumberOfSyntaxErrors() + " error(s)");
        if (0==parser.getNumberOfSyntaxErrors()) {
        	SccModuleVisitor smc = new SccModuleVisitor(module);
        	// This should return the SodacanModule object as above
        	ModuleComponent c = smc.visit(tree);
        }
//        System.out.println(tree.toStringTree(parser));
//        System.out.println("*****Visit: " + file);
//        AtVisitor visitor = new AtVisitor();
//        visitor.visit(tree);
//        System.out.println("*****Visit Finish: " + file);
		
	}
}
