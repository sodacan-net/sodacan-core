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
package net.sodacan.module.variable;

import net.sodacan.module.message.ModuleMessage;
import net.sodacan.module.value.Value;
/**
 * A subscribe variable is just like any normal module variable except that it has additional attributes available
 * that access the underlying message that populates the variable.
 * It also makes the variable read only.
 * 
 * @author John Churin
 *
 */
public class SubscribeVariable extends ModuleVariable {

	private ModuleMessage message; // Only when from a message

	public SubscribeVariable(VariableDef variableDef, Value value) {
		super(variableDef, value);
		// TODO Auto-generated constructor stub
	}

	public ModuleMessage getMessage() {
		return message;
	}

	public void setMessage(ModuleMessage message) {
		this.message = message;
	}

	@Override
	public void setValue(Value value) {
		// TODO Auto-generated method stub
		super.setValue(value);
	}

	@Override
	public Value getAttribute(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String attributeName) {
		// TODO Auto-generated method stub

	}

}
