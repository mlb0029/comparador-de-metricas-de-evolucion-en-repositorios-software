package metricsengine.metrics;

import java.util.Collection;

import datamodel.Repository;
import metricsengine.MetricTemplate;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;

/**
 * Computes the average of days to close an issue.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricAverageDaysToCloseAnIssue extends MetricTemplate {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 664935243481317998L;

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
			"TI1",
			"Average of days to close an issue",
			"",
			"",
			"Time constraints",
			"How long does it take to close an issue?",
			"ADCI = SUM(DCI) / NCI. ADCI = Average of days to close an issue. NCI = Number of closed issues. DCI = Vector with the days it took to close each issue.",
			"DCI, NCI:Repository",
			"ADCI >= 0. Better small values",
			MetricDescription.EnumTypeOfScale.RATIO,
			"NCI:Count, DCI: Set of Counts");
		
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueDecimal(2.2);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueDecimal(19.41);
	
	public static final EvaluationFunction EVALUATION_FUNCTION = 
			(measuredValue, minValue, maxValue) -> {
				try {
					Double value, min, max;
					value = MetricTemplate.formatTwoDecimals(((ValueDecimal) measuredValue).getValue());
					min = MetricTemplate.formatTwoDecimals(((ValueDecimal) minValue).getValue());
					max = MetricTemplate.formatTwoDecimals(((ValueDecimal) maxValue).getValue());
					if (value > min && value < max) return EvaluationResult.GOOD;
					else if (value == min || value == max) return EvaluationResult.WARNING;
					else return EvaluationResult.BAD;
				} catch (Exception e){
					return EvaluationResult.BAD;
				}
			};
			
	/**
	 * Constructor that initializes the metric with default values.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricAverageDaysToCloseAnIssue() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, EVALUATION_FUNCTION);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricAverageDaysToCloseAnIssue(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault, EVALUATION_FUNCTION);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		Collection<Integer> daysToCloseEachIssue = repository.getRepositoryInternalMetrics().getDaysToCloseEachIssue();
		Integer numberOfClosedIssues = repository.getRepositoryInternalMetrics().getNumberOfClosedIssues();
		
		if (daysToCloseEachIssue == null ||
				numberOfClosedIssues == null ||
				numberOfClosedIssues != daysToCloseEachIssue.size() ||
				numberOfClosedIssues <= 0
				) {
			return false;
		} else {
			for (Integer numDays : daysToCloseEachIssue) {
				if (numDays == null || numDays < 0) return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = repository.getRepositoryInternalMetrics().getDaysToCloseEachIssue().stream().mapToInt(i -> i).average().orElseThrow();
		return new ValueDecimal(result);
	}

	@Override
	public EvaluationResult evaluate(IValue measuredValue) {
		return getEvaluationFunction().evaluate(measuredValue, getValueMinDefault(), getValueMaxDefault());
	}

	@Override
	public EvaluationFunction getEvaluationFunction() {
		return EVALUATION_FUNCTION;
	}
}