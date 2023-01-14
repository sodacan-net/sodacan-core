package net.sodacan.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Top-level module. Essentially an AST, produced from source code, from the compiler perspective. We walk this tree to execute a module at runtime.
 * @author John Churin
 *
 */
public class Module {
	String name;
	String instanceName;
	// Note: statements within each group are processed in the order listed. in other respects, the declarative nature of SodaCan 
	// means the order of statements is unimportant.
	List<PublishStatement> publishStatements = new ArrayList<>();
	List<SubscribeStatement> subscribeStatements = new ArrayList<>();
	List<AtStatement> atStatements = new ArrayList<>();
	List<OnStatement> onStatements = new ArrayList<>();
	List<WhenStatement> whenStatements = new ArrayList<>();

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
		if (statement instanceof WhenStatement) {
			whenStatements.add((WhenStatement)statement);
		}
	}
	
}
