package metricsengine.metrics;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import repositorydatasource.model.Repository;

/**
 * Computes the metric Peak Change.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricPeakChange extends AMetric {

	/**
	 * Constructor of a metric that establishes the description and the default values.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
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
		return  repository != null &&
				repository.getTotalNumberOfCommits() != null &&
				repository.getCommitDates() != null &&
				repository.getCommitDates().size() == repository.getTotalNumberOfCommits().intValue();
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		Collection<Date> commitDates = repository.getCommitDates();
		Stream<Date> commitDatesStream = commitDates.stream();
		Integer totalNumberOfCommits = repository.getTotalNumberOfCommits();
		//TODO
		return null;
	}
}
