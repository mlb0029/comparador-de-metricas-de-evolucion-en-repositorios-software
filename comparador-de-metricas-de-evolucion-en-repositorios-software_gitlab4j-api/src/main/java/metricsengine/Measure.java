package metricsengine;

import java.util.Date;

import metricsengine.metrics.MetricConfiguration;
import metricsengine.values.IValue;

/**
 * Measure computed of a metric.
 * 
 * @author MALB
 *
 */
public class Measure {
	
	/**
	 * Metric that has been measured.
	 */
	private MetricConfiguration metric;
	
	/**
	 * Value of the measurement.
	 */
	private IValue value;
	
	/**
	 * Date of measurement.
	 */
	private Date date;
	
	/**
	 * Constructor.
	 * 
	 * @param metric Metric that has been measured.
	 * @param value Value of the measurement.
	 */
	public Measure(MetricConfiguration metric, IValue value) {
		this.metric = metric;
		this.value = value;
		this.date = new Date();
	}
	
	/**
	 * The metric that has been measured.
	 * 
	 * @return The metric that has been measured.
	 */
	public MetricConfiguration getMetric() {
		return metric;
	}

	/**
	 * Gets the value of the measurement.
	 * 
	 * @return The value of the measurement.
	 */
	public IValue getValue() {
		return value;
	}
	
	/**
	 * Gets the date of measurement
	 * 
	 * @return The date of measurement.
	 */
	public Date getDate() {
		return date;
	}
}
