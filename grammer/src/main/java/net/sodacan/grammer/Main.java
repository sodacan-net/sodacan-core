package net.sodacan.grammer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.text.StringSubstitutor;

import net.sodacan.grammer.LanguageParser.ProgContext;

public class Main {

    private static final String EXTENSION = "unit";
    private static final String DIRBASE = "src/test/resources/";

    public static boolean test(String expression) {
      CharStream in = CharStreams.fromString(expression);
      LanguageLexer lexer = new LanguageLexer(in);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      LanguageParser parser = new LanguageParser(tokens);
      ProgContext tree = parser.prog();
      UnitVisitor visitor = new UnitVisitor();
      try {
    	  visitor.visit(tree);
      } catch (Throwable e) {
    	  System.out.println(expression + " error: "  + e.getMessage());
      }
      // Visit tree and print result
      System.out.println("------");
//      return result.;
      return true;
    }
    public static void main(String[] args) throws IOException {
    	// Load properties
    	Properties properties = new Properties(); 
    	properties.load(new FileInputStream( DIRBASE + "unit.properties"));
    	String files[] = args.length==0? new String[]{ "test." + EXTENSION } : args;
        System.out.println("Dirbase: " + DIRBASE);
        for (String file : files){
            System.out.println("Processing: " + file);
            // Read in the file
            String template = Files.readString(Path.of(DIRBASE + file));
            // Apply any property references now (lexical substitution)
        	String source = StringSubstitutor.replace(template,properties);
        	// Lexical analyzer creates a list of tokens
            LanguageLexer lexer = new LanguageLexer(CharStreams.fromString(source));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // Create a parser and do the parse
            LanguageParser parser = new LanguageParser(tokens);
            ProgContext tree = parser.prog();
            BindVisitor bindVisitor = null;
        	UnitVisitor unitVisitor = null;
            // Now extract the units we found from the parse tree
            try {
            	unitVisitor = new UnitVisitor();
          	  	unitVisitor.visit(tree);
          	  	unitVisitor.dereferenceLikes();
          	  	// If the parse worked, we have a valid collection of unit objects
          	  	// At this point we should know declared identifiers used in all units.
          	  	// In the bind visitor, we visit to verify that all identifiers used in
          	  	// when statements reference valid declared identifiers
          	  	bindVisitor = new BindVisitor(unitVisitor.getUnits());
          	  	bindVisitor.visit(tree);
          	  	// For debugging, print out the units
                for (Unit unit : unitVisitor.getUnitList()) {
                	System.out.println(unit);
                }
            } catch (Throwable e) {
          	  	System.out.println(file + " error: "  + e.getMessage());
            }
            // Print the entire parse tree
            System.out.println(tree.toStringTree(parser));
            
            System.out.println("Execute an event");
            ExecuteVisitor ex = new ExecuteVisitor(unitVisitor.getUnitList());
            ex.processEvent(new Event("lamp1","on"));
            // Visit tree and print result
            System.out.println("\n------");
            ex.processEvent(new Event("lamp1","next"));
            System.out.println("\n------");
            ex.processEvent(new Event("lamp6","on"));
        }
    }
}
