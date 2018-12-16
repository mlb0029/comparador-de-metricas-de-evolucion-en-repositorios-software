package metricsengine;

import metricsengine.IMetric;

/**
 * Partially implements the IMetric interface.
 * <p> 
 * The calculate () function will be implemented in the subclasses.
 * 
 * @author MALB
 *
 */
public abstract class AMetric implements IMetric {
	
	/**
	 * The description of the metric.
	 */
	private MetricDescription description;
	
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
	public AMetric(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		this.description = description;
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
	
	/**
	 * Gets the name of the metric.
	 * 
	 * @return The name of the metric.
	 */
	public String getName() {
		return description.getName();
	}
	
	/**
	 * Gets the description of the metric.
	 * 
	 * @return The description of the metric. 
	 */
	public MetricDescription getDescription() {
		return description;
	}
}
