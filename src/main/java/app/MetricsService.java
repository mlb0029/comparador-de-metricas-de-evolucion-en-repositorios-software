package app;

import java.io.Serializable;

import datamodel.Repository;
import datamodel.RepositoryInternalMetrics;
import exceptions.RepositoryDataSourceException;
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
import repositorydatasource.RepositoryDataSource;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricsService implements Serializable {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 6475817245020418420L;

	private static final MetricProfile DEFAULT_METRIC_PROFILE = createDefaultMetricProfile() ;
	
	private MetricProfile currentMetricProfile = DEFAULT_METRIC_PROFILE;
	
	private static MetricsService metricsService = null;
	
	private MetricsService() {}

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
     * Calculate the metrics of the repository following the current profile.
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     * @param repository
     * @throws RepositoryDataSourceException
     */
    public void calculateRepositoryMetrics(Repository repository) throws RepositoryDataSourceException {
    	RepositoryDataSource repositoryDataSource = RepositoryDataSourceService.getInstance();
    	RepositoryInternalMetrics repositoryInternalMetrics = null;
    	MetricsResults metricsResults = new MetricsResults();
    	
    	repositoryInternalMetrics = repositoryDataSource.getRepositoryInternalMetrics(repository);
    	repository.setRepositoryInternalMetrics(repositoryInternalMetrics);
    	
    	for (MetricConfiguration metricConfiguration : currentMetricProfile.getMetricConfigurationCollection()) {
			metricConfiguration.calculate(repository, metricsResults);
		}
    	repository.setMetricsResults(metricsResults);
    }
    
    public void evaluateRepositoryMetrics(Repository repository) throws RepositoryDataSourceException {
    	RepositoryDataSource repositoryDataSource = RepositoryDataSourceService.getInstance();
    	RepositoryInternalMetrics repositoryInternalMetrics = null;
    	MetricsResults metricsResults = new MetricsResults();
    	
    	repositoryInternalMetrics = repositoryDataSource.getRepositoryInternalMetrics(repository);
    	repository.setRepositoryInternalMetrics(repositoryInternalMetrics);
    	
    	for (MetricConfiguration metricConfiguration : currentMetricProfile.getMetricConfigurationCollection()) {
			metricConfiguration.calculate(repository, metricsResults);
		}
    	repository.setMetricsResults(metricsResults);
    }
    
    /**
	 * Initializes the default metric profile.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static MetricProfile createDefaultMetricProfile() {
		MetricProfile defaultMetricProfile = new MetricProfile("DEFAULT");
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricTotalNumberOfIssues()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricCommitsPerIssue()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPercentageClosedIssues()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysToCloseAnIssue()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysBetweenCommits()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricDaysBetweenFirstAndLastCommit()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricChangeActivityRange()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPeakChange()));
		return defaultMetricProfile;
	}
}
