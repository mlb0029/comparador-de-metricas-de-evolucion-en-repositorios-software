/**
 * 
 */
package metricsengine.metrics.concrete;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
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
	 * Constructor of a metric that establishes the description and the default values.
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
		if (repository != null) {
			Set<Date> commitDates = repository.getCommitDates();
			Integer totalNumberOfCommits = repository.getTotalNumberOfCommits();
			return  commitDates != null &&
					totalNumberOfCommits != null &&
					totalNumberOfCommits.intValue() >= 2 &&
					commitDates.size() == totalNumberOfCommits.intValue();
		}
		return false;
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
