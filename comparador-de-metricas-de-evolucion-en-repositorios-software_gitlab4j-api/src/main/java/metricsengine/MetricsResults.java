package metricsengine;

import java.util.Collection;
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
public class MetricsResults {
	
	/**
	 * Collection of measures.
	 */
	private Set<Measure> measures;

	/**
	 * Date of the collect.
	 */
	private Date date;
	
	/**
	 * Constructor.
	 */
	public MetricsResults() {
		this.date = new Date();
		measures = new HashSet<Measure>();
	}
	/**
	 * Gets the collection of measures.
	 * 
	 * @return The collection of measures.
	 */
	public Collection<Measure> getMeasures() {
		return measures;
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
	 * Adds a measure to the collection.
	 * 
	 * @param measure Measure of a metric.
	 */
	public void addMeasure(Measure measure) {
		measures.add(measure);
	}
	
}
