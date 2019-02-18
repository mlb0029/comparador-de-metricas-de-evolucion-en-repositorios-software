package metricsengine.metrics;

import metricsengine.Measure;
import metricsengine.MetricsResults;
import metricsengine.metrics.IMetric;
import metricsengine.values.IValue;
import repositorydatasource.model.Repository;

/**
 * Partially implements the IMetric interface.
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
	 * Constructor of a metric that establishes the description and the default values.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public AMetric(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		this.description = description;
		this.valueMinDefault = valueMinDefault;
		this.valueMaxDefault = valueMaxDefault;
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
	
	/* (non-Javadoc)
	 * @see metricsengine.IMetric#calculate(repositorydatasource.model.Repository, metricsengine.MetricsResults)
	 */
	@Override
	public IValue calculate(Repository repository, MetricConfiguration metricConfig, MetricsResults metricsResults) {
		if (check(repository)) {
			IValue value = run(repository);
			Measure measure = new Measure(metricConfig, value);
			metricsResults.addMeasure(measure);
			return value;
		}else {
			throw new RuntimeException();// TODO
		}
	}
	
	/**
	 * Check if it is possible to calculate the metric.
	 * 
	 * @param repository Repository from which to obtain the metric
	 * @return True if it is possible to calculate the metric, false otherwise.
	 */
	protected abstract Boolean check(Repository repository);
	
	/**
	 * Calculate the metric.
	 * 
	 * @param repository Repository from which to obtain the metric
	 * @return The calculated value
	 */
	protected abstract IValue run(Repository repository);
}
