package metricsengine;

import repositorydatasource.model.Repository;

/**
 * Defines the interface of a metric.
 * 
 * @author MALB
 *
 */
public interface IMetric {
	Measure calculate(Repository repository);
}
