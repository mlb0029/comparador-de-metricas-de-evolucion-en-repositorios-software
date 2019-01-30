package metricsengine.metrics.concrete;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * @author migue
 *
 */
public class MetricCommitsPerIssue extends AMetric {
	
	public MetricCommitsPerIssue(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		if (repository != null && repository.getTotalNumberOfIssues() != null && repository.getTotalNumberOfCommits() != null) return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = repository.getTotalNumberOfIssues() / repository.getTotalNumberOfCommits();
		return new ValueDecimal(result);
	}

}
