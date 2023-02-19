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
package net.sodacan.module.expression;

/**
 * A terminal node in the AST. All name segments of an identifier are here as is the instance designator, if applicable.
 * The last segment is the variable name.
 * 
 * domain.topic.module.variable[instance]
 * 
 * @author John Churin
 */
public class VariableRef {
	String domainName;
	String topicName;
	String variableName;
	Expression instanceExpression;
	
	public VariableRef(String domainName, String topicName, String variableName, Expression instanceExpression) {
		super();
		this.domainName = domainName;
		this.topicName = topicName;
		this.variableName = variableName;
		this.instanceExpression = instanceExpression;
	}
}
