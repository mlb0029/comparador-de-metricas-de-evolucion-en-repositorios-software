package metricsengine.values;

/**
 * Integer value.
 * 
 * @author MALB
 *
 */
public class ValueInteger implements IValue {

	/**
	 * Integer value.
	 */
	private int value;
	
	/**
	 * Initialize the value.
	 * 
	 * @param value The value to set.
	 */
	public ValueInteger(int value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 * 
	 * @return The value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value The value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see metricsengine.IValue#getString()
	 */
	@Override
	public String valueToString() {
		return String.valueOf(value);
	}
}
