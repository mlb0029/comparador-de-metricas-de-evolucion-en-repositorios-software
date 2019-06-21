package metricsengine;

import datamodel.Repository;
import metricsengine.values.IValue;
import metricsengine.values.ValueUncalculated;

/**
 * Partially implements the IMetric interface.
 * 
 * @author MALB
 *
 */
public abstract class AMetric implements IMetric {
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 8459616601304750512L;

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
	public IValue calculate(Repository repository, MetricConfiguration metricConfig, MetricsResults metricsResults) {
		IValue value;
		if (repository == null || metricConfig == null ||  metricsResults == null) throw new IllegalArgumentException("All parameters must be not null");
		value = (check(repository))?run(repository):new ValueUncalculated();
		metricsResults.addMeasure(new Measure(metricConfig, value));
		return value;
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
