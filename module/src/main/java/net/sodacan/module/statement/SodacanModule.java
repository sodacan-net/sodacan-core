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

/**
 * Top-level module. Essentially an AST, produced from source code, from the compiler perspective. We walk this tree to execute a module at runtime.
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
	List<PublishStatement> publishStatements = new ArrayList<>();
	List<SubscribeStatement> subscribeStatements = new ArrayList<>();
	List<AtStatement> atStatements = new ArrayList<>();
	List<OnStatement> onStatements = new ArrayList<>();
	List<AndStatement> whenStatements = new ArrayList<>();
	List<ErrorComponent> errors = new ArrayList<>();
	
	public SodacanModule() {
		
	}
	
	public void addError(ErrorComponent error) {
		errors.add(error);
	}
	/**
	 * Add statements to the module
	 * @param statement
	 */
	public void addStatement(Statement statement) {
		if (statement instanceof PublishStatement) {
			publishStatements.add((PublishStatement)statement);
		}
		if (statement instanceof SubscribeStatement) {
			subscribeStatements.add((SubscribeStatement)statement);
		}
		if (statement instanceof AtStatement) {
			atStatements.add((AtStatement)statement);
		}
		if (statement instanceof OnStatement) {
			onStatements.add((OnStatement)statement);
		}
		if (statement instanceof AndStatement) {
			whenStatements.add((AndStatement)statement);
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

	
	public List<PublishStatement> getPublishStatements() {
		return publishStatements;
	}

	public List<SubscribeStatement> getSubscribeStatements() {
		return subscribeStatements;
	}

	public List<AtStatement> getAtStatements() {
		return atStatements;
	}

	public List<OnStatement> getOnStatements() {
		return onStatements;
	}

	public List<AndStatement> getWhenStatements() {
		return whenStatements;
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
