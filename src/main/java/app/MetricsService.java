package app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import datamodel.Repository;
import datamodel.RepositoryInternalMetrics;
import exceptions.MetricsServiceException;
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

	private MetricProfile defaultMetricProfile;
	
	private Map<String, MetricProfile> metricsProfileMap;
	
	private static MetricsService metricsService;
	
	private MetricsService() {
		metricsProfileMap = new HashMap<String, MetricProfile>();
		defaultMetricProfile = createDefaultMetricProfile();
		metricsProfileMap.put(defaultMetricProfile.getName(), defaultMetricProfile);
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
	
	 public MetricProfile getMetricProfileByName(String name) {
    	return metricsProfileMap.get(name);
    }
    
	 public void addMetricProfile(MetricProfile metricProfile) throws MetricsServiceException {
		 if (metricProfile == null) throw new NullPointerException();
		 if (metricsProfileMap.get(metricProfile.getName()) != null) {
			 throw new MetricsServiceException(MetricsServiceException.METRICP_ROFILE_NAME_IN_USE);
		 } else{
			 metricsProfileMap.put(metricProfile.getName(), metricProfile);
		 }
	 }
	 
    /**
     * Updates th.
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     * @param repository
     * @param metricProfile
     * @throws RepositoryDataSourceException 
     */
    public void calculateMetricsRepository(Repository repository, MetricProfile metricProfile) throws RepositoryDataSourceException {
    	//TODO
    	RepositoryDataSource repositoryDataSource = RepositoryDataSourceService.getInstance();
    	RepositoryInternalMetrics repositoryInternalMetrics = null;
    	MetricsResults metricsResults = new MetricsResults();
    	
    	repositoryInternalMetrics = repositoryDataSource.getRepositoryInternalMetrics(repository);
    	repository.setRepositoryInternalMetrics(repositoryInternalMetrics);
    	
    	for (MetricConfiguration metricConfiguration : metricProfile.getMetricConfigurationCollection()) {
			metricConfiguration.calculate(repository, metricsResults);
		}
    	repository.getMetricsResultsCollection().add(metricsResults);
    }
    
    public void calculateMetricsRepository(Repository repository) throws RepositoryDataSourceException {
    	calculateMetricsRepository(repository, defaultMetricProfile);
    }
    
    public void calculateMetricsAllRepositories(MetricProfile metricProfile) throws RepositoryDataSourceException {
    	for (Repository repository : RepositoriesCollectionService.getInstance().getRepositories()) {
			calculateMetricsRepository(repository, metricProfile);
		}
    }

	/**
	 * Initializes the default metric profile.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private MetricProfile createDefaultMetricProfile() {
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
