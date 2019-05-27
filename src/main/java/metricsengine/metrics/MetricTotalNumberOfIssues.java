package metricsengine.metrics;

import datamodel.Repository;
import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;

/**
 * Compute the total number of issues of a repository.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricTotalNumberOfIssues extends AMetric {

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
			"I1",
			"Total number of issues",
			"Jacek Ratzinger",
			"2007",
			"Process Orientation",
			"How many issues have been created in the repository?",
			"TNI = Total number of issues",
			"Repository",
			"TNI >= 0. Better small values",
			MetricDescription.EnumTypeOfScale.ABSOLUTE,
			"TNI : Count");
	
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueInteger(6);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueInteger(44);
	
	/**
	 * Constructor that initializes the metric with default values.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricTotalNumberOfIssues() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricTotalNumberOfIssues(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		Integer tni = repository.getRepositoryInternalMetrics().getTotalNumberOfIssues();
		return tni != null &&
				tni >= 0;
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		return new ValueInteger(repository.getRepositoryInternalMetrics().getTotalNumberOfIssues());
	}
}
