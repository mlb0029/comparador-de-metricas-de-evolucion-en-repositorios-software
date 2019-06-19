package metricsengine;

import datamodel.Repository;
import metricsengine.values.IValue;

/**
 * Wrapper for a metric that allows you to use other minimum and maximum values, 
 * instead of the default values.
 *  
 * @author MALB
 *
 */
public class MetricConfiguration implements IMetric {
	
	/**
	 * Metric.
	 */
	private IMetric metric;
	
	/**
	 * Minimum value.
	 */
	private IValue valueMin;
	
	/**
	 * Maximum value.
	 */
	private IValue valueMax;
	
	/**
	 * Constructor.
	 * 
	 * @param metric Metric to configure.
	 * @param valueMin Minimum value.
	 * @param valueMax Maximum value.
	 */
	public MetricConfiguration(IMetric metric, IValue valueMin, IValue valueMax) {
		if (metric == null)
			throw new IllegalArgumentException("There can be no metric configuration without specifying a metric");
		if (valueMin == null || valueMax == null)
			throw new IllegalArgumentException("There can be no metric configuration without configuration values");
		this.metric = metric;
		this.valueMin = valueMin;
		this.valueMax = valueMax;
	}
	
	/**
	 * Constructor that sets the metric with its default values.
	 * 
	 * @param metric Metric to configure.
	 */
	public MetricConfiguration(IMetric metric) {
		if (metric == null)
			throw new IllegalArgumentException("There can be no metric configuration without specifying a metric");
		this.metric = metric;
		this.valueMin = metric.getValueMinDefault();
		this.valueMax = metric.getValueMaxDefault();
	}
	
	/**
	 * Gets the metric.
	 * 
	 * @return The metric.
	 */
	public IMetric getMetric() {
		return metric;
	}

	/**
	 * Gets the minimum value.
	 * 
	 * @return The minimum value.
	 */
	public IValue getValueMin() {
		return valueMin;
	}

	/**
	 * Gets the maximum value.
	 * 
	 * @return The maximum value.
	 */
	public IValue getValueMax() {
		return valueMax;
	}

	/* (non-Javadoc)
	 * @see metricsengine.IMetric#calculate(repositorydatasource.model.Repository, metricsengine.MetricConfiguration, metricsengine.MetricsResults)
	 */
	@Override
	public IValue calculate(Repository repository, MetricsResults metricsResults) {
		return this.metric.calculate(repository, metricsResults);
	}

	@Override
	public IValue getValueMaxDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValue getValueMinDefault() {
		// TODO Auto-generated method stub
		return null;
	}
}
