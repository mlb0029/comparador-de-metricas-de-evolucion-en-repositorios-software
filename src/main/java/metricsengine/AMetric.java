package metricsengine;

import exceptions.UncalculableMetricException;
import metricsengine.IMetric;
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
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public AMetric(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		setDescription(description);
		setValueMinDefault(valueMinDefault);
		setValueMaxDefault(valueMaxDefault);
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
	
	/**
	 * Sets the description of the metric.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param description the description to set
	 */
	private void setDescription(MetricDescription description) {
		if(description == null)
			throw new IllegalArgumentException("'description' can't be null");
		this.description = description;
	}

	/**
	 * Sets the minimum value by default.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param valueMinDefault the valueMinDefault to set
	 */
	private void setValueMinDefault(IValue valueMinDefault) {
		if(valueMinDefault == null)
			throw new IllegalArgumentException("'valueMinDefault' can't be null");
		this.valueMinDefault = valueMinDefault;
	}

	/**
	 * Sets the maximum value by default.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param valueMaxDefault the valueMaxDefault to set
	 */
	private void setValueMaxDefault(IValue valueMaxDefault) {
		if(valueMaxDefault == null)
			throw new IllegalArgumentException("'valueMaxDefault' can't be null");
		this.valueMaxDefault = valueMaxDefault;
	}

	/* (non-Javadoc)
	 * @see metricsengine.IMetric#calculate(repositorydatasource.model.Repository, metricsengine.MetricsResults)
	 */
	@Override
	public IValue calculate(Repository repository, MetricConfiguration metricConfig, MetricsResults metricsResults) throws UncalculableMetricException {
		if (repository == null || metricConfig == null ||  metricsResults == null)
			throw new UncalculableMetricException("Impossible to calculate with any of the null arguments");
		if (check(repository)) {
			IValue value = run(repository);
			metricsResults.addMeasure(new Measure(metricConfig, value));
			return value;
		}else {
			throw new UncalculableMetricException(
					"Can not calculate the metric '" + getName() + 
					"' for the repository '" + repository.getName() + "'");
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
