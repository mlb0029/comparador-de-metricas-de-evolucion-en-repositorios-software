package metricsengine;

import java.util.Date;

/**
 * Measure computed of a metric.
 * 
 * @author MALB
 * @since 03/12/2018
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
	 * Gets the value of the measurement.
	 * 
	 * @return The value of the measurement.
	 */
	public IValue getValue() {
		return value;
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
	 * Gets the date of measurement
	 * 
	 * @return The date of measurement.
	 */
	public Date getDate() {
		return date;
	}
}
