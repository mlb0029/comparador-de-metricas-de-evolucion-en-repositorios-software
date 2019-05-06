package gui.common;

import java.util.HashMap;
import java.util.Map;

import metricsengine.MetricConfiguration;
import metricsengine.MetricProfile;
import metricsengine.MetricsResults;
import metricsengine.metrics.MetricAverageDaysBetweenCommits;
import metricsengine.metrics.MetricAverageDaysToCloseAnIssue;
import metricsengine.metrics.MetricChangeActivityRange;
import metricsengine.metrics.MetricCommitsPerIssue;
import metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit;
import metricsengine.metrics.MetricPeakChange;
import metricsengine.metrics.MetricPercentageClosedIssues;
import metricsengine.metrics.MetricTotalNumberOfIssues;
import model.Repository;
import model.RepositoryCalculatedMetrics;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricsService {

	private Map<String, MetricProfile> metricsProfileMap;
	
	private static MetricsService metricsService;
	
	private MetricsService() {
		metricsProfileMap = new HashMap<String, MetricProfile>();
		initializeDefaultMetricProfile();
	}

	/**
	 * Gets the single instance of metricsService.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the single instance of metricsService.
	 */
	public static MetricsService getMetricsService() {
		if (metricsService == null) metricsService = new MetricsService();
		return metricsService;
	}
	
	 /**
     * Initializes the default metric profile.
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     */
    private void initializeDefaultMetricProfile() {
    	MetricProfile defaultMetricProfile = new MetricProfile("DEFAULT");
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricTotalNumberOfIssues()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricCommitsPerIssue()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPercentageClosedIssues()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysToCloseAnIssue()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysBetweenCommits()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricDaysBetweenFirstAndLastCommit()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricChangeActivityRange()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPeakChange()));
    	metricsProfileMap.put(defaultMetricProfile.getName(), defaultMetricProfile);
    }
    
    public MetricProfile getMetricProfileByName(String name) {
    	return metricsProfileMap.get(name);
    }
    
    /**
     * Calcu.
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     * @param repository
     * @param metricProfile
     */
    public void calculateMetrics(Repository repository, MetricProfile metricProfile) {
    	MetricsResults metricsResults = new MetricsResults();
		for (MetricConfiguration metricConfiguration : metricProfile.getMetricConfigurationCollection()) {
			metricConfiguration.calculate(repository, metricsResults);
		}
    	repository.getCalculatedMetricsCollection().add(new RepositoryCalculatedMetrics(metricProfile, metricsResults));
    }
}
