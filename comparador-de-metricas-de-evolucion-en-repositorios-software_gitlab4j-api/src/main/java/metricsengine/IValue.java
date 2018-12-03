package metricsengine;

/**
 * Defines the interface of a metric value.
 *  
 * @author MALB
 * @since 03/12/2018
 *
 */
public interface IValue {
	
	/**
	 * Return a string representing the value.
	 * 
	 * @return A value parsed to string.
	 */
	String getString();	
}
