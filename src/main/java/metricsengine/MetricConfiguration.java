package metricsengine;

import metricsengine.values.IValue;
import model.Repository;

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
	 * Constructor.
	 * 
	 * @param metric Metric to configure.
	 * @param valueMin Minimum value.
	 * @param valueMax Maximum value.
	 */
	public MetricConfiguration(AMetric metric, IValue valueMin, IValue valueMax) {
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
	public MetricConfiguration(AMetric metric) {
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
	public AMetric getMetric() {
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
	public IValue calculate(Repository repository, MetricConfiguration metricConfig, MetricsResults metricsResults) {
		return this.metric.calculate(repository, metricConfig, metricsResults);
	}
	
	/**
	 * 
	 * Execute the calculate method of IMetric taking the same instance 
	 * as an argument to the MetricConfiguration parameter.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repository  Entity to be measured
	 * @param metricsResults Collector where to store the result
	 * @return The calculated value.
	 * @throws MetricsEngineException 
	 * @see {@link IMetric#calculate(Repository, MetricConfiguration, MetricsResults)}
	 */
	public IValue calculate(Repository repository, MetricsResults metricsResults) {
		return this.metric.calculate(repository, this, metricsResults);
	}
}
