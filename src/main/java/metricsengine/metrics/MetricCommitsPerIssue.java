package metricsengine.metrics;

import datamodel.Repository;
import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;

/**
 * Computes the commits per issue.
 * <p>
 * Total number of issues / Total number of commits.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricCommitsPerIssue extends AMetric {
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -3512786510470075695L;

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
			"I2",
			"Commits per issue",
			"",
			"",
			"Process Orientation",
			"How many commits per issues?",
			"CI = TNI/TNC. CI = Commits per issue.TNI = Total number of issues. TNC = Total number of commits",
			"TNI, TNC:Repository",
			"CI >= 0. Better small values",
			MetricDescription.EnumTypeOfScale.RATIO,
			"TNI,TNC:Count");
	
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueDecimal(0.5);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueDecimal(1.0);
	
	/**
	 * Constructor that initializes the metric with default values.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricCommitsPerIssue() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricCommitsPerIssue(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		Integer tni = repository.getRepositoryInternalMetrics().getTotalNumberOfIssues();
		Integer tnc = repository.getRepositoryInternalMetrics().getTotalNumberOfCommits();
		return tni != null && 
				tni >= 0 &&
				tnc != null &&
				tnc > 0;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = (double) repository.getRepositoryInternalMetrics().getTotalNumberOfIssues() / repository.getRepositoryInternalMetrics().getTotalNumberOfCommits();
		return new ValueDecimal(result);
	}
}
