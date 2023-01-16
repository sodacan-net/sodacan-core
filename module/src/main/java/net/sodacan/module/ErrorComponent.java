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
package net.sodacan.module;

import java.util.List;
/**
 * Represent one module error in the module structure.
 * @author John Churin
 *
 */
public class ErrorComponent {
	private int line;
	private int charPositionInLine;
	private List<String> stack;
	private String msg;
	private String offendingSymbol;
	private Exception e;
	
	public ErrorComponent(int line,int charPositionInLine,List<String> stack,String msg,String offendingSymbol, Exception e) {
		this.line = line;
		this.charPositionInLine = charPositionInLine;
		this.stack = stack;
		this.msg = msg;
		this.offendingSymbol = offendingSymbol;
		this.e = e;
	}

	public int getLine() {
		return line;
	}

	public int getCharPositionInLine() {
		return charPositionInLine;
	}

	public List<String> getStack() {
		return stack;
	}

	public String getMsg() {
		return msg;
	}

	public String getOffendingSymbol() {
		return offendingSymbol;
	}

	public Exception getE() {
		return e;
	}

	@Override
	public String toString() {
		return "Line " + line + ":" + charPositionInLine + " " + msg;
	}
	
}
