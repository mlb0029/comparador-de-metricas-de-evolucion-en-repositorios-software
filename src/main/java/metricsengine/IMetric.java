package metricsengine;

import datamodel.Repository;
import metricsengine.values.IValue;

/**
 * Defines the interface of a metric.
 * 
 * @author MALB
 *
 */
public interface IMetric {
	
	IValue getValueMaxDefault();

	IValue getValueMinDefault();

	/**
	 * Calculate the metric for a repository passed by parameter and add it to the set passed also by parameter.
	 * 
	 * @param repository Entity to be measured
	 * @param metricsResults Collector where to store the result.
	 * @return The calculated value or ValueUncalculated if imposible to calculate.
	 */
	IValue calculate(Repository repository,MetricsResults metricsResults);
}
