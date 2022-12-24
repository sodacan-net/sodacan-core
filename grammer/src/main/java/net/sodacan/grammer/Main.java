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
//    	test("UNIT lamp1 \ntrue\n");
//    	test("UNIT lamp1\n 1\n");
//    	test("UNIT lamp1\n 5*8\n");
//    	test("UNIT lamp1\n a = 1\n 2+a\n");
//    	test("UNIT lamp1\n UNIT lamp2 \n");
//    	test("x=7+50*6/3;x+1;");
//    	test("x;");
//    	test("7+50*6/3;");
//      test("7+50*(6/3);");
//      test("(7+50)*6/3;");
//      test("114==(7+50)*6/3;");
//      test("true;");
//      test("false;");
//      test("true==false;");
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
            // Now extract the units we found from the parse tree
            try {
            	UnitVisitor unitVisitor = new UnitVisitor();
          	  	unitVisitor.visit(tree);
          	  	unitVisitor.dereferenceLikes();
          	  	// If the parse worked, we have a valid collection of unit objects
          	  	// At this point we should know declared identifiers used in all units.
          	  	// In the bind visitor, we visit to verify that all identifiers used in
          	  	// when statements reference valid declared identifiers
          	  	BindVisitor bindVisitor = new BindVisitor(unitVisitor.getUnits());
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
            // Visit tree and print result
            System.out.println("------");
        }
    }
}
