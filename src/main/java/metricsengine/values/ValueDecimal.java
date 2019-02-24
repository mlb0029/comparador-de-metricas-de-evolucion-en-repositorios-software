package metricsengine.values;

/**
 * Decimal value.
 * 
 * @author MALB
 *
 */
public class ValueDecimal implements IValue {

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

}
