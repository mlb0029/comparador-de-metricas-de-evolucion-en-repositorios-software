package exceptions;

/**
 * It is thrown when a metric can not be calculated because it does not have enough information.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class UncalculableMetricException extends Exception {

	/**
	 * Serial.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public UncalculableMetricException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message the detail message. The detail message is saved forlater retrieval by the getMessage() method.
	 */
	public UncalculableMetricException(String message) {
		super(message);
	}
}
