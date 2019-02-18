package metricsengine.metrics.concrete;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * Computes the percentage of closed issues.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricPercentageClosedIssues extends AMetric {

	/**
	 * Constructor of a metric that establishes the description and the default values.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
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
		double result = (repository.getNumberOfClosedIssues() / repository.getTotalNumberOfIssues()) * 100;
		return new ValueDecimal(result);
	}

}
