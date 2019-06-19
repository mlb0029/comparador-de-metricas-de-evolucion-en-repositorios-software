package metricsengine.values;

/**
 * Decimal value.
 * 
 * @author MALB
 *
 */
public class ValueDecimal extends NumericValue {

	/**
	 * Double value.
	 */
	private double value;
	
	/**
	 * Initialize the value.
	 * 
	 * @param value The value to set.
	 */
	public ValueDecimal(Double value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 * 
	 * @return The value.
	 */
	public double getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see metricsengine.IValue#getString()
	 */
	@Override
	public String valueToString() {
		return String.valueOf(value);
	}

	@Override
	public int intValue() {
		return (int) value;
	}

	@Override
	public long longValue() {
		return (long) value;
	}

	@Override
	public double doubleValue() {
		return value;
	}

}
