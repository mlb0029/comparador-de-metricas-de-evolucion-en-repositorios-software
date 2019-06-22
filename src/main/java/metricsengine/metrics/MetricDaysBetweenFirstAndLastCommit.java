package metricsengine.metrics;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import datamodel.Repository;
import metricsengine.MetricTemplate;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import metricsengine.values.ValueInteger;

/**
 * Computes the days between the first and the last commit.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricDaysBetweenFirstAndLastCommit extends MetricTemplate {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -1644028814570031865L;

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
			"TC2",
			"Days between the first and the last commit",
			"",
			"",
			"Time constraints",
			"How many days have passed between the first and last commit?",
			"DBFLC = Max(CD) - Min(CD) (in days). DBFLC = Days between the first and the last commit, CD = Vector with de commits dates",
			"CD: Repository",
			"DBFLC >= 0, better large values.",
			MetricDescription.EnumTypeOfScale.ABSOLUTE,
			"CD: Set of times");
	
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueInteger(81);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueInteger(198);
	
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
	public MetricDaysBetweenFirstAndLastCommit() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, EVALUATION_FUNCTION);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricDaysBetweenFirstAndLastCommit(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault, EVALUATION_FUNCTION);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		Collection<Date> commitDates = repository.getRepositoryInternalMetrics().getCommitDates();
		Integer totalNumberOfCommits = repository.getRepositoryInternalMetrics().getTotalNumberOfCommits();
		
		if(totalNumberOfCommits != null &&
				commitDates != null &&
				commitDates.size() == totalNumberOfCommits && 
				totalNumberOfCommits > 1) {
			for (Date date : commitDates) {
				if (date == null) return false;
			}
		}else {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		List<Date> commitDates = repository.getRepositoryInternalMetrics().getCommitDates().stream().sorted(Date::compareTo).collect(Collectors.toList());
		long firstDate = commitDates.get(0).getTime();
		long lastDate = commitDates.get(commitDates.size() - 1).getTime();
		int result = (int) ((lastDate - firstDate) / (1000 * 60 * 60 * 24));
		return new ValueInteger(result);
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
