package metricsengine.metrics;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * Computes the metric: Change Activity Range Per Month.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 */
public class MetricChangeActivityRange extends AMetric {
	
	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
					"TC3 - Change Activity Range",
					"Number of changes relative to the number of months in the period",
					"Jacek Ratzinger",
					"2007",
					"Time constraints",
					"How many changes per month?",
					"CAR = TNC / NM. CAR = Number of changes relative to the number of months in the period, TNC = Total number of commits, NM = Number of months",
					"TNC, NM: Repository",
					"CAR > 0, better medium values",
					MetricDescription.EnumTypeOfScale.RATIO,
					"TNC:Count, NM: Count");
		
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueDecimal(6.0);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueDecimal(26.4);
	
	/**
	 * Constructor that initializes the metric with default values.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricChangeActivityRange() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricChangeActivityRange(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		if (repository == null) return false;
		return  repository != null &&
				repository.getInternalMetrics().getTotalNumberOfCommits() != null &&
				repository.getInternalMetrics().getTotalNumberOfCommits() >= 0 &&
				repository.getInternalMetrics().getLifeSpanMonths() != null &&
				repository.getInternalMetrics().getLifeSpanMonths() > 0;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = (double) repository.getInternalMetrics().getTotalNumberOfCommits() / repository.getInternalMetrics().getLifeSpanMonths();
		return new ValueDecimal(result);
	}
}
