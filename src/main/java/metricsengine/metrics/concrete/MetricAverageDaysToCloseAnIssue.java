package metricsengine.metrics.concrete;

import java.util.List;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;

import repositorydatasource.model.Repository;

/**
 * Computes the average of days to close an issue.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricAverageDaysToCloseAnIssue extends AMetric {

	/**
	 * Constructor of a metric that establishes the description and the default values.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricAverageDaysToCloseAnIssue(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		if (repository != null) {
			List<Integer> daysToCloseEachIssue = repository.getDaysToCloseEachIssue();
			Integer numberOfClosedIssues = repository.getNumberOfClosedIssues();
			return  daysToCloseEachIssue != null && 
					numberOfClosedIssues != null && 
					numberOfClosedIssues.intValue() > 0 &&
					daysToCloseEachIssue.size() == numberOfClosedIssues.intValue();
		}
		return false;

	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = repository.getDaysToCloseEachIssue().stream().mapToInt(i -> i).average().orElseThrow();
		return new ValueDecimal(result);
	}

}
