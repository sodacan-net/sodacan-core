package net.sodacan.grammer;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

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
      LanguageCustomVisitor visitor = new LanguageCustomVisitor();
      try {
    	  Value result = visitor.visit(tree);
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
      String files[] = args.length==0? new String[]{ "test." + EXTENSION } : args;
        System.out.println("Dirbase: " + DIRBASE);
        for (String file : files){
            System.out.println("Processing: " + file);
            CharStream in = CharStreams.fromFileName(DIRBASE + file);
            LanguageLexer lexer = new LanguageLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LanguageParser parser = new LanguageParser(tokens);
            ProgContext tree = parser.prog();
            LanguageCustomVisitor visitor = new LanguageCustomVisitor();
            try {
          	  Value result = visitor.visit(tree);
          	  System.out.println("Properties: " + visitor.getProperties());
            } catch (Throwable e) {
          	  System.out.println(file + " error: "  + e.getMessage());
            }
            // Visit tree and print result
            System.out.println("------");
        }
    }
}
