package metricsengine.metrics.concreteMetrics;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import repositorydatasource.model.Repository;

/**
 * @author migue
 *
 */
public class MetricPercentageClosedIssues extends AMetric {

	public MetricPercentageClosedIssues(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		return repository != null && repository.getTotalNumberOfIssues() != null && repository.getNumberOfClosedIssues() != null;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		// TODO Auto-generated method stub
		return null;
	}

}
