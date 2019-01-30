/**
 * 
 */
package metricsengine.metrics.concrete;

import metricsengine.metrics.AMetric;
import metricsengine.metrics.MetricDescription;
import metricsengine.values.IValue;
import repositorydatasource.model.Repository;

/**
 * @author migue
 *
 */
public class MetricAverageDaysToCloseAnIssue extends AMetric {

	public MetricAverageDaysToCloseAnIssue(MetricDescription description, IValue valueMinDefault,
			IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		// TODO Auto-generated method stub
		return null;
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
