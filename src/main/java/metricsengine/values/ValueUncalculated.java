package metricsengine.values;

/**
 * Value that obtains a measure of a metric that could not be calculated.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class ValueUncalculated implements IValue {

	public static final String VALUE = "NC";
	
	/* (non-Javadoc)
	 * @see metricsengine.values.IValue#valueToString()
	 */
	@Override
	public String getValueString() {
		return VALUE;
	}

}
