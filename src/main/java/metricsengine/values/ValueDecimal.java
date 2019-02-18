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
	private Double value;
	
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
	public Double getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value The value to set.
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see metricsengine.IValue#getString()
	 */
	@Override
	public String valueToString() {
		return value.toString();
	}

}
