package metricsengine;

import java.util.Collection;
import java.util.HashMap;

import metricsengine.metrics.*;
import metricsengine.values.IValue;

/**
 * It allows to use several configurations for the metrics.
 * 
 * @author MALB
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
	 * Gets the default metric profile defined by the programmer.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return The default metric profile.
	 */
	public static MetricProfile getDefaultMetricProfile() {
		MetricProfile defaultMetricProfile = new MetricProfile("Default");
		
		MetricTotalNumberOfIssues i1 = new MetricTotalNumberOfIssues();
		defaultMetricProfile.addMetricConfiguration(i1, i1.getValueMinDefault(), i1.getValueMaxDefault());		
		MetricCommitsPerIssue i2 = new MetricCommitsPerIssue();
		defaultMetricProfile.addMetricConfiguration(i2, i2.getValueMinDefault(), i2.getValueMaxDefault());	
		MetricPercentageClosedIssues i3 = new MetricPercentageClosedIssues();
		defaultMetricProfile.addMetricConfiguration(i3, i3.getValueMinDefault(), i3.getValueMaxDefault());	
		MetricAverageDaysToCloseAnIssue ti1 = new MetricAverageDaysToCloseAnIssue();
		defaultMetricProfile.addMetricConfiguration(ti1, ti1.getValueMinDefault(), ti1.getValueMaxDefault());	
		MetricAverageDaysBetweenCommits tc1 = new MetricAverageDaysBetweenCommits();
		defaultMetricProfile.addMetricConfiguration(tc1, tc1.getValueMinDefault(), tc1.getValueMaxDefault());	
		MetricDaysBetweenFirstAndLastCommit tc2 = new MetricDaysBetweenFirstAndLastCommit();
		defaultMetricProfile.addMetricConfiguration(tc2, tc2.getValueMinDefault(), tc2.getValueMaxDefault());	
		MetricChangeActivityRange tc3 = new MetricChangeActivityRange();
		defaultMetricProfile.addMetricConfiguration(tc3, tc3.getValueMinDefault(), tc3.getValueMaxDefault());	
		MetricPeakChange c1 = new MetricPeakChange();
		defaultMetricProfile.addMetricConfiguration(c1, c1.getValueMinDefault(), c1.getValueMaxDefault());	
		
		return defaultMetricProfile;
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
