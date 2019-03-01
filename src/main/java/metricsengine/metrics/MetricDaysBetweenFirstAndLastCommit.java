package metricsengine.metrics;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;
import repositorydatasource.model.Repository;

/**
 * Computes the days between the first and the last commit.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricDaysBetweenFirstAndLastCommit extends AMetric {

	/**
	 * Constructor that initializes the metric with default values defined by the programmer.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricDaysBetweenFirstAndLastCommit() {
		super(new MetricDescription(
					"TC2",
					"Days between the first and the last commit",
					"",
					"",
					"Process Orientation",
					"How many days have passed between the first and last commit?",
					"DBFLC = Max(CD) - Min(CD) (in days). DBFLC = Days between the first and the last commit, CD = Vector with de commits dates",
					"CD: Repository",
					"ADBC >= 0, better large values.",
					MetricDescription.EnumTypeOfScale.ABSOLUTE,
					"CD: Set of times"), 
				new ValueInteger(81), 
				new ValueInteger(198));
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricDaysBetweenFirstAndLastCommit(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		Collection<Date> commitDates = repository.getCommitDates();
		Integer totalNumberOfCommits = repository.getTotalNumberOfCommits();
		return  commitDates != null &&
				totalNumberOfCommits != null &&
				totalNumberOfCommits.intValue() >= 2 &&
				commitDates.size() == totalNumberOfCommits.intValue();
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		Stream<Date> commitDates = repository.getCommitDates().stream();
		long firstDate = commitDates.min(Date::compareTo).get().getTime();
		long lastDate = commitDates.max(Date::compareTo).get().getTime();
		int result = (int) ((lastDate - firstDate) / (1000 * 60 * 60 * 24));
		return new ValueInteger(result);
	}
}
