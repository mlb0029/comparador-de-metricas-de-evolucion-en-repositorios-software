package metricsengine;

import metricsengine.IMetric;

/**
 * Partially implements the IMetric interface.
 * <p> 
 * The calculate () function will be implemented in the subclasses.
 * 
 * @author MALB
 * @since 03/12/2018
 *
 */
public abstract class AMetric implements IMetric {
	
	/**
	 * Minimum value by default.
	 */
	private IValue valueMinDefault;
	
	/**
	 * Maximum value by default.
	 */
	private IValue valueMaxDefault;

	/**
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public AMetric(IValue valueMinDefault, IValue valueMaxDefault) {
		this.valueMinDefault = valueMinDefault;
		this.valueMaxDefault = valueMaxDefault;
	}

	/**
	 * Gets the minimum value by default.
	 * 
	 * @return The minimum value by default.
	 */
	public IValue getValueMinDefault() {
		return valueMinDefault;
	}

	/**
	 * Gets the maximum value by default.
	 * 
	 * @return The maximum value by default.
	 */
	public IValue getValueMaxDefault() {
		return valueMaxDefault;
	}
}
