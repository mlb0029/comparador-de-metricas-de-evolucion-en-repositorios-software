package metricsengine.metrics;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

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
	 * Constructor that initializes the metric with default values defined by the programmer.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricCommitsPerIssue() {
		super(new MetricDescription(
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
					"TNI,TNC:Count"), 
				new ValueDecimal(0.5), 
				new ValueDecimal(1.0));
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
		return repository.getTotalNumberOfIssues() != null && repository.getTotalNumberOfCommits() != null;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = repository.getTotalNumberOfIssues() / repository.getTotalNumberOfCommits();
		return new ValueDecimal(result);
	}
}
