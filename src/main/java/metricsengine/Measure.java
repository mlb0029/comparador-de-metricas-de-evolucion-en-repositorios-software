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
	private MetricConfiguration metricConfiguration;
	
	/**
	 * Value of the measurement.
	 */
	private IValue measuredValue;
	
	/**
	 * Date of measurement.
	 */
	private Date dateOfMeasurement;
	
	/**
	 * Constructor.
	 * 
	 * @param metricConfiguration Metric that has been measured.
	 * @param value Value of the measurement.
	 */
	public Measure(MetricConfiguration metricConfiguration, IValue value) {
		this.metricConfiguration = metricConfiguration;
		this.measuredValue = value;
		this.dateOfMeasurement = new Date();
	}
	
	/**
	 * The metric that has been measured.
	 * 
	 * @return The metric that has been measured.
	 */
	public MetricConfiguration getMetricConfiguration() {
		return metricConfiguration;
	}

	/**
	 * Gets the value of the measurement.
	 * 
	 * @return The value of the measurement.
	 */
	public IValue getMeasuredValue() {
		return measuredValue;
	}
	
	/**
	 * Gets the date of measurement
	 * 
	 * @return The date of measurement.
	 */
	public Date getDateOfMeasurement() {
		return dateOfMeasurement;
	}
}
