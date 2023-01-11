package net.sodacan.parseTest;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SccErrorListener extends BaseErrorListener {

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		List<String> stack = ((SccParser)recognizer).getRuleInvocationStack();
		Collections.reverse(stack);
//		if (offendingSymbol instanceof CommonToken) {
//			CommonToken t = (CommonToken)offendingSymbol;
//			
//			System.out.println("**" + t);
////			String src = t.getInputStream().;
//
//		}
		System.err.print("\nRule stack: "+stack);
		System.err.println();
		System.err.print(recognizer.getInputStream().getSourceName());
		System.err.print(" line "+line+":"+charPositionInLine + ": " + msg);
		super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
	}

}
