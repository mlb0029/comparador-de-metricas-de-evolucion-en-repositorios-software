package metricsengine.metrics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;


public class MetricAverageDaysBetweenCommits extends AMetric {
	/**
	 * Constructor that initializes the metric with default values defined by the programmer.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricAverageDaysBetweenCommits() {
		super(new MetricDescription(
					"TC1",
					"Average of days between commits",
					"",
					"",
					"Process Orientation",
					"How much time is there between one commit and the next?",
					"ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits.",
					"CD, TNC: Repository",
					"ADBC >= 0, better small values.",
					MetricDescription.EnumTypeOfScale.RATIO,
					"TNC:Count, CD: Set of times"), 
				new ValueDecimal(1.0), 
				new ValueDecimal(4.3));
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricAverageDaysBetweenCommits(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		ArrayList<Date> commitDates = new ArrayList<Date>(repository.getCommitDates());
		Integer totalNumberOfCommits = repository.getTotalNumberOfCommits();
		return 	totalNumberOfCommits != null &&
				commitDates != null &&
				totalNumberOfCommits.intValue() >= 2 &&
				commitDates.size() == totalNumberOfCommits.intValue();
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		List<Date> commitDates = repository.getCommitDates().stream().sorted().collect(Collectors.toList());
		List<Integer> daysBetweenCommits = new ArrayList<Integer>();
		for (int i = 1; i < commitDates.size(); i++) {
			long fecha1 = commitDates.get(i-1).getTime();
			long fecha2 = commitDates.get(i).getTime();
			daysBetweenCommits.add((int) ((fecha2 - fecha1) / (1000 * 60 * 60 * 24)));
		}
		return new ValueDecimal(daysBetweenCommits.stream().mapToInt(i -> i.intValue()).average().orElseThrow());
	}

}
