package metricsengine.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import datamodel.Repository;
import metricsengine.MetricTemplate;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;

/**
 * Computes the average of days between commits.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricAverageDaysBetweenCommits extends MetricTemplate {
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -1039405944018960452L;

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
			"TC1",
			"Average of days between commits",
			"",
			"",
			"Time constraints",
			"How much time is there between one commit and the next?",
			"ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits.",
			"CD, TNC: Repository",
			"ADBC >= 0, better small values.",
			MetricDescription.EnumTypeOfScale.RATIO,
			"TNC:Count, CD: Set of times");
		
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueDecimal(1.0);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueDecimal(4.3);
	
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
	public MetricAverageDaysBetweenCommits() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, EVALUATION_FUNCTION);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricAverageDaysBetweenCommits(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
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
		long date1;
		long date2;
		int daysBetweenCommits;
		List<Date> commitDates = repository.getRepositoryInternalMetrics().getCommitDates().stream().sorted().collect(Collectors.toList());
		List<Integer> lstDaysBetweenCommits = new ArrayList<Integer>();
		for (int i = 1; i < commitDates.size(); i++) {
			date1 = commitDates.get(i-1).getTime();
			date2 = commitDates.get(i).getTime();
			daysBetweenCommits = (int) ((date2 - date1) / (1000 * 60 * 60 * 24));
			lstDaysBetweenCommits.add(daysBetweenCommits);
		}
		return new ValueDecimal(lstDaysBetweenCommits.stream().mapToInt(i -> i.intValue()).average().orElseThrow());
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
