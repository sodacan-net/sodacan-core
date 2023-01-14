package net.sodacan.compiler;

import java.io.File;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import net.sodacan.grammer.SccLexer;

public class Main {
 
    private static final String EXTENSION = "scc";
    private static final String DIRBASE = "src/test/resources/";

    public static void main(String[] args) throws IOException {
        String files[] = args.length==0? new String[]{ "test." + EXTENSION } : args;
        System.out.println("Directory: " + DIRBASE);
        for (String file : files){
        	File f = new File(file);
            CharStream in = CharStreams.fromFileName(DIRBASE + file);
            SccLexer lexer = new SccLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SccParser parser = new SccParser(tokens);
            parser.addParseListener(new SccListener(parser,f.getName()));
            parser.removeErrorListeners();
            parser.addErrorListener(new SccErrorListener());
            System.out.println("*****Start parse: " + file);
            SccParser.StartContext tree = parser.start();
            System.out.println("*****Finish parse: " + file + " with " + parser.getNumberOfSyntaxErrors() + " error(s)");
            System.out.println(tree.toStringTree(parser));
//            System.out.println("*****Visit: " + file);
//            AtVisitor visitor = new AtVisitor();
//            visitor.visit(tree);
//            System.out.println("*****Visit Finish: " + file);

        }
    }
}
