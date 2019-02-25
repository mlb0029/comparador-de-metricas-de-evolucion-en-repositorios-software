package metricsengine.metrics;

import java.util.Collection;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
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
	 * Constructor that initializes the metric with default values defined by the programmer.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricAverageDaysToCloseAnIssue() {
		super(new MetricDescription(
					"TI1",
					"Average of days to close an issue",
					"",
					"",
					"Process Orientation",
					"How long does it take to close an issue?",
					"ADCI = SUM(DCI) / NCI. ADCI = Average of days to close an issue. NCI = Number of closed issues. DCI = Vector with the days it took to close each issue.",
					"DCI, NCI:Repository",
					"ADCI >= 0. Better small values",
					MetricDescription.EnumTypeOfScale.RATIO,
					"NCI:Count, DCI: Set of times"), 
				new ValueDecimal(2.2), 
				new ValueDecimal(19.41));
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
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
		Collection<Integer> daysToCloseEachIssue = repository.getDaysToCloseEachIssue();
		Integer numberOfClosedIssues = repository.getNumberOfClosedIssues();
		return  daysToCloseEachIssue != null && 
				numberOfClosedIssues != null && 
				daysToCloseEachIssue.size() == numberOfClosedIssues.intValue();

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
