package metricsengine;

/**
 * Defines the interface of a metric.
 * 
 * @author MALB
 *
 */
public interface IMetric {
	/**
	 * Compute the metric.
	 * 
	 * @return The result of computing the metric.
	 */
	IValue calculate();
}
