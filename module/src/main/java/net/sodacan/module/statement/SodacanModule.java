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
package net.sodacan.module.statement;

import java.util.ArrayList;
import java.util.List;

import net.sodacan.module.variable.VariableDef;
import net.sodacan.module.variable.VariableDefs;

/**
 * Top-level Sodacan module. Essentially an AST, produced from source code, or from Java builders, or from the Sodacan API. 
 * The Sodacan runtime will walk one of three module sub-trees, depending on the circumstance: Due to the passage of time: atStatements. 
 * Due to an incomming message: OnStatements. And, after either case: IfStatements.
 * @author John Churin
 *
 */
public class SodacanModule {
	String name;
	String instanceName;
	String source;
	String originalFileName;
	
	// Note: statements within each group are processed in the order listed. in other respects, the declarative nature of SodaCan 
	// means the order of statements is unimportant.
	VariableDefs variableDefs = new VariableDefs();
	List<ErrorComponent> errors = new ArrayList<>();
	List<AtStatement> atStatements = new ArrayList<>();
	List<OnStatement> onStatements = new ArrayList<>();
	List<IfStatement> ifStatements = new ArrayList<>();

	/**
	 * A Sodacan module is constructed very early in the process, however, once complete, 
	 * it is entirely immutable, or effectively immutable, by the Sodacan runtime. 
	 * If there are no errors in the list of errors for this module, then the module is considered executable.
	 * A modules is always serializable into a Json string.
	 */
	public SodacanModule() {
		
	}

	/**
	 * Without errors, the module is considered executable.
	 * @return
	 */
	public boolean isExecutable() {
		return (errors.size()==0);
	}

	/**
	 * Add an error such as from parsing the module language.
	 * @param error
	 */
	public void addError(ErrorComponent error) {
		errors.add(error);
	}

	public boolean addVariableDef(VariableDef variable) {
		return variableDefs.addVariableDef(variable);
	}

	public VariableDef findVariableDef( String name) {
		return variableDefs.find(name);
	}
	
	protected VariableDefs getVariableDefs() {
		return variableDefs;
	}

	public void setVariableDefs(VariableDefs variableDefs) {
		this.variableDefs = variableDefs;
	}
	/**
	 * Add statements to the module
	 * @param statement
	 */
	public void addStatement(Statement statement) {
		if (statement instanceof AtStatement) {
			atStatements.add((AtStatement)statement);
		}
		if (statement instanceof OnStatement) {
			onStatements.add((OnStatement)statement);
		}
		if (statement instanceof IfStatement) {
			ifStatements.add((IfStatement)statement);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}


	public List<AtStatement> getAtStatements() {
		return atStatements;
	}

	public List<OnStatement> getOnStatements() {
		return onStatements;
	}

	public List<IfStatement> getIfStatements() {
		return ifStatements;
	}

	public List<ErrorComponent> getErrors() {
		return errors;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SodacanModule) {
			SodacanModule other = (SodacanModule) obj;
			if (getName()==null) return false;
			return (getName().contentEquals(other.getName()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		if (this.instanceName!=null) {
			sb.append('[');
			sb.append(instanceName);
			sb.append(']');
		}
		return sb.toString();
	}
	
	
}
