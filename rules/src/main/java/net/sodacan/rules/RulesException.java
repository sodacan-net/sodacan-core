package net.sodacan.rules;

public class RulesException extends Exception {
	private static final long serialVersionUID = 1L;
	public RulesException(String msg) {
		super(msg);
	}
	public RulesException(String msg, Exception cause) {
		super(msg,cause);
	}
}
