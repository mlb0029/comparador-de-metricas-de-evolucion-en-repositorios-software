package metricsengine;

import java.util.Collection;
import java.util.HashMap;

/**
 * It allows to use several configurations for the metrics.
 * 
 * @author MALB
 * @since 03/12/2018
 *
 */
public class MetricProfile {
	
	/**
	 * Name of the profile.
	 */
	private String name;
	
	/**
	 * All configurations.
	 */
	private HashMap<AMetric, MetricConfiguration> metricConfigurations;
	
	/**
	 * Constructor.
	 * 
	 * @param name Name of the profile.
	 */
	public MetricProfile(String name) {
		this.name = name;
		this.metricConfigurations = new HashMap<AMetric, MetricConfiguration>();
	}

	/**
	 * Gets the name of the profile.
	 * 
	 * @return The name of the profile.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the profile.
	 * 
	 * @param name The new name of the profile.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the configured metrics.
	 * 
	 * @return The configured metrics.
	 */
	public Collection<MetricConfiguration> getMetricConfigurationCollection(){
		return this.metricConfigurations.values();
		
	}

	/**
	 * It allows adding a configuration to a metric, specifying the metric and the minimum and maximum values.
	 * 
	 * @param metric Metric to configure.
	 * @param valueMin Minimum value.
	 * @param valueMax Maximum value.
	 */
	public void addMetricConfiguration(AMetric metric, IValue valueMin, IValue valueMax) {
		this.metricConfigurations.put(metric, new MetricConfiguration(metric, valueMin, valueMax));
	}

	/**
	 * Adds a metric configuration.
	 * 
	 * @param metricConfiguration Metric configuration.
	 */
	public void addMetricConfiguration(MetricConfiguration metricConfiguration) {
		this.metricConfigurations.put(metricConfiguration.getMetric(), metricConfiguration);
	}

	/**
	 * Removes a metric configuration.
	 * 
	 * @param metricConfiguration Metric configuration.
	 */
	public void removeMetricConfiguration(MetricConfiguration metricConfiguration) {
		this.metricConfigurations.remove(metricConfiguration.getMetric());
	}
	
}
