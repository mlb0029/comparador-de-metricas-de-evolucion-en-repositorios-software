package metricsengine.metrics.concreteMetrics;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;
import repositorydatasource.model.Repository;

public class MetricTotalNumberOfIssues extends AMetric {

	/**
	 * Constructor that sets the default values passed by parameter.
	 * 
	 * @param
	 * @param valueMinDefault
	 * @param valueMaxDefault
	 */
	public MetricTotalNumberOfIssues(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		if (repository != null && repository.getTotalNumberOfIssues() != null) return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		return new ValueInteger(repository.getTotalNumberOfIssues());
	}
}
