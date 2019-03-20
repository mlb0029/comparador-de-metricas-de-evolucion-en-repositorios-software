package metricsengine.metrics;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * Computes the metric Peak Change.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricPeakChange extends AMetric {

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
			"C1 - Peak Change Count",
			"Number of changes in the peak monthnormalized on the overall number of changes",
			"Jacek Ratzinger",
			"2007",
			"Time constraints",
			"What is the proportion of work done in the month with the greatest number of changes?",
			"PCC = NCPM / TNC. PCC = Peak Change Count, NCPM = Number of commits in peak month, TNC = Total number of commits",
			"NCPM, TNC: Repository",
			"0 <= PCC <= 1, better medium values",
			MetricDescription.EnumTypeOfScale.RATIO,
			"NCPM:Count, TNC:Count");
		
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueDecimal(30.0);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueDecimal(40.0);
	
	/**
	 * Constructor that initializes the metric with default values.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricPeakChange() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricPeakChange(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		if (repository == null) return false;
		Collection<Date> commitDates = repository.getCommitDates();
		Integer totalNumberOfCommits = repository.getTotalNumberOfCommits();
		
		if(totalNumberOfCommits != null &&
				commitDates != null &&
				commitDates.size() == totalNumberOfCommits && 
				totalNumberOfCommits > 0) {
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
		Calendar calendar = Calendar.getInstance();
		
		Map<String, Integer> commitsPerMonth = new HashMap<String, Integer>();
		String yearMonth;
		Integer commitsInMonth;
		
		Integer totalNumberOfCommits = repository.getTotalNumberOfCommits();
		Integer commitsInPeakMonth;
		
		Double peakChange;
		
		for (Date date : repository.getCommitDates()) {
			calendar.setTime(date);
			yearMonth = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH);
			commitsInMonth=commitsPerMonth.get(yearMonth);
			if (commitsInMonth == null) 
				commitsPerMonth.put(yearMonth, 1);
			else
				commitsPerMonth.put(yearMonth, commitsInMonth + 1);
		}
		
		commitsInPeakMonth = commitsPerMonth.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
		
		peakChange = (double) commitsInPeakMonth / totalNumberOfCommits; 
		return new ValueDecimal(peakChange);
	}
}