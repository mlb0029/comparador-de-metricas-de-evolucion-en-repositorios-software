package metricsengine.values;

/**
 * Defines the interface of a metric value.
 *  
 * @author MALB
 *
 */
public interface IValue {
	
	public static final ValueComparator VALUE_COMPARATOR = new ValueComparator();
	
	/**
	 * Return a string representing the value.
	 * 
	 * @return A value parsed to string.
	 */
	String valueToString();	
}
