/**
 * 
 */
package metricsengine.metrics.concrete;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * Computes the metric: Change Activity Range Per Month.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 */
public class MetricChangeActivityRangePerMonth extends AMetric {

	/**
	 * Constructor of a metric that establishes the description and the default values.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricChangeActivityRangePerMonth(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		return  repository != null &&
				repository.getTotalNumberOfCommits() != null &&
				repository.getLifeSpanMonths() != null;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		Integer lifeSpanMonths = (repository.getLifeSpanMonths() == 0?repository.getLifeSpanMonths():1);
		double result = repository.getTotalNumberOfCommits() / lifeSpanMonths;
		return new ValueDecimal(result);
	}

}
