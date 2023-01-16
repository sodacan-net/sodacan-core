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

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import net.sodacan.module.statement.ErrorComponent;
import net.sodacan.module.statement.SodacanModule;

public class SccErrorListener extends BaseErrorListener {
	SodacanModule module;
	public SccErrorListener(SodacanModule module) {
		this.module = module;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		List<String> stack = ((SccParser)recognizer).getRuleInvocationStack();
		Collections.reverse(stack);
		String symbol = null;
		if (offendingSymbol instanceof CommonToken) {
			CommonToken t = (CommonToken)offendingSymbol;
			symbol = t.getText();
//			System.out.println("**" + t);
//			String src = t.getInputStream().;

		}
		System.err.print("\nRule stack: "+stack);
		System.err.println();
		System.err.print(recognizer.getInputStream().getSourceName());
		System.err.print(" line "+line+":"+charPositionInLine + ": " + msg);
		module.addError(new ErrorComponent(line,charPositionInLine, stack, msg, symbol, e));
		super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
	}

}
