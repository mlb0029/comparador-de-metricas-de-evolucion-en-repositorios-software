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
	private Integer value;
	
	/**
	 * Initialize the value.
	 * 
	 * @param value The value to set.
	 */
	public ValueInteger(Integer value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 * 
	 * @return The value.
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value The value to set.
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see metricsengine.IValue#getString()
	 */
	@Override
	public String getString() {
		return value.toString();
	}

}
