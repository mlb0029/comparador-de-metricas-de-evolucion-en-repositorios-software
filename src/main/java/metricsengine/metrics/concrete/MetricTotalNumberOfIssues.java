package metricsengine.metrics.concrete;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;
import repositorydatasource.model.Repository;

public class MetricTotalNumberOfIssues extends AMetric {

	/**
	 * Constructor of a metric that establishes the description and the default values.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricTotalNumberOfIssues(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		return repository != null && repository.getTotalNumberOfIssues() != null;
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		return new ValueInteger(repository.getTotalNumberOfIssues());
	}
}
