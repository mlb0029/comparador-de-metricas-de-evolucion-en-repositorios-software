package metricsengine;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Collect the measures of the metrics.
 * 
 * @author MALB
 * @since 03/12/2018
 *
 */
public class MetricResults {
	
	/**
	 * Date of the collect.
	 */
	private Date date;
	
	/**
	 * Collection of measures.
	 */
	private Set<Measure> measures;
	
	/**
	 * Constructor.
	 */
	public MetricResults() {
		this.date = new Date();
		measures = new HashSet<Measure>();
	}
	/**
	 * Gets the day of the collect.
	 * 
	 * @return The date of the collect.
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Gets the collection of measures.
	 * 
	 * @return The collection of measures.
	 */
	public Set<Measure> getMeasures() {
		return measures;
	}
	
	/**
	 * Adds a measure to the collection.
	 * 
	 * @param measure Measure of a metric.
	 */
	public void addMeasure(Measure measure) {
		measures.add(measure);
	}
	
}
