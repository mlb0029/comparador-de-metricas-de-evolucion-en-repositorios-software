package metricsengine;

import metricsengine.IMetric;
import repositorydatasource.model.Repository;

/**
 * Wrapper for a metric that allows you to use other minimum and maximum values, 
 * instead of the default values.
 *  
 * @author MALB
 * @since 03/12/2018
 *
 */
public class MetricConfiguration implements IMetric {
	
	/**
	 * Metric.
	 */
	private AMetric metric;
	
	/**
	 * Minimum value.
	 */
	private IValue valueMin;
	
	/**
	 * Maximum value.
	 */
	private IValue valueMax;
	
	/**
	 * Indicates whether these values ​​are used or the default values ​​of the metric.
	 */
	private Boolean active;

	
	/**
	 * Constructor.
	 * 
	 * @param metric Metric to configure.
	 * @param valueMin Minimum value.
	 * @param valueMax Maximum value.
	 */
	public MetricConfiguration(AMetric metric, IValue valueMin, IValue valueMax) {
		this.metric = metric;
		this.valueMin = valueMin;
		this.valueMax = valueMax;
		this.active = false;
	}
	
	/**
	 * Gets the metric.
	 * 
	 * @return The metric.
	 */
	public AMetric getMetric() {
		return metric;
	}

	/**
	 * Gets the minimum value.
	 * @return The minimum value.
	 */
	public IValue getValueMin() {
		return valueMin;
	}

	/**
	 * Gets the maximum value.
	 * @return The maximum value.
	 */
	public IValue getValueMax() {
		return valueMax;
	}

	/**
	 * Returns if the configuration is enabled.
	 * 
	 * @return True if these values ​​are used instead of the default values ​​of the metric.
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Enables or disables the configuration.
	 * 
	 * @param active True to activate, false to disable.
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public Measure calculate(Repository repository) {
		return this.metric.calculate(repository);
	}
}
