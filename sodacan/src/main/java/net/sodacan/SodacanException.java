package net.sodacan;

public class SodacanException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SodacanException(String message, Throwable cause) {
		super(message, cause);
	}

	public SodacanException(String message) {
		super(message);
	}

}
