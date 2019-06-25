package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.listeners.CurrentMetricProfileChangedEvent;
import app.listeners.Listener;
import datamodel.Repository;
import datamodel.RepositoryInternalMetrics;
import exceptions.MetricsServiceException;
import exceptions.RepositoryDataSourceException;
import metricsengine.Measure;
import metricsengine.Metric;
import metricsengine.MetricConfiguration;
import metricsengine.MetricProfile;
import metricsengine.MetricsResults;
import metricsengine.numeric_value_metrics.MetricAverageDaysBetweenCommits;
import metricsengine.numeric_value_metrics.MetricAverageDaysToCloseAnIssue;
import metricsengine.numeric_value_metrics.MetricChangeActivityRange;
import metricsengine.numeric_value_metrics.MetricCommitsPerIssue;
import metricsengine.numeric_value_metrics.MetricDaysBetweenFirstAndLastCommit;
import metricsengine.numeric_value_metrics.MetricPeakChange;
import metricsengine.numeric_value_metrics.MetricPercentageClosedIssues;
import metricsengine.numeric_value_metrics.MetricTotalNumberOfIssues;
import metricsengine.values.IValue;
import metricsengine.values.NumericValue;
import repositorydatasource.RepositoryDataSource;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricsService implements Serializable {

	private static final long serialVersionUID = 6475817245020418420L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricsService.class);

	private static final MetricProfile DEFAULT_METRIC_PROFILE = createDefaultMetricProfile() ;
	
	private MetricProfile currentMetricProfile = DEFAULT_METRIC_PROFILE;
	
	private Set<Listener<CurrentMetricProfileChangedEvent>> currentMetricProfileChangedEventListeners = new HashSet<Listener<CurrentMetricProfileChangedEvent>>();
	
	private static MetricsService metricsService = null;
	
	private MetricsService() {}

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
    
    public MetricProfile getCurrentMetricProfile() {
    	MetricProfile toReturn = new MetricProfile(currentMetricProfile.getName());
    	for (MetricConfiguration mc : currentMetricProfile.getMetricConfigurationCollection()) {
			toReturn.addMetricConfiguration(mc);
		}
    	return toReturn;
    }
    
    public void setCurrentMetricProfileToDefault() {
    	MetricProfile old = currentMetricProfile;
    	currentMetricProfile = DEFAULT_METRIC_PROFILE;
    	notifyRepositoriesCollectionUpdatedListeners(old, DEFAULT_METRIC_PROFILE);
    }
    
    public void setCurrentMetricProfileToCalculated() throws MetricsServiceException {
    	try {
    		MetricProfile oldMetricProfile = currentMetricProfile;
			MetricProfile metricProfile = new MetricProfile("CALCULATED");
			Collection<Repository> repositories = RepositoriesCollectionService.getInstance().getRepositories();
			
			ArrayList<Double> datasetForMetric;
			Double q1ForMetric, q3ForMetric;
			IValue q1ForMetricConfig, q3ForMetricConfig;
			DescriptiveStatistics descriptiveStatisticsForMetric;
			
			IValue valueMeasuredForMetricInRepository;
			Double value;
			
			for (MetricConfiguration metricC : DEFAULT_METRIC_PROFILE.getMetricConfigurationCollection()) {
				datasetForMetric = new ArrayList<Double>();
				for (Repository repository : repositories) {
					valueMeasuredForMetricInRepository = getValueMeasuredForMetric(repository, metricC.getMetric().getClass());
					if (valueMeasuredForMetricInRepository != null && valueMeasuredForMetricInRepository instanceof NumericValue) {
						value = ((NumericValue) valueMeasuredForMetricInRepository).doubleValue();
						datasetForMetric.add(value);
					}
				}
				descriptiveStatisticsForMetric = new DescriptiveStatistics(datasetForMetric.stream().mapToDouble(x -> x).toArray());
				q1ForMetric = descriptiveStatisticsForMetric.getPercentile(25);
				q3ForMetric = descriptiveStatisticsForMetric.getPercentile(75);
				q1ForMetricConfig = metricC.getValueMin().valueFactory(q1ForMetric);
				q3ForMetricConfig = metricC.getValueMax().valueFactory(q3ForMetric);
				metricProfile.addMetricConfiguration(new MetricConfiguration(metricC.getMetric(), q1ForMetricConfig, q3ForMetricConfig));
			}
			currentMetricProfile = metricProfile;
			notifyRepositoriesCollectionUpdatedListeners(oldMetricProfile, metricProfile);
		} catch (Exception e) {
			LOGGER.error("Error deleting a repository. Exception occurred: " + e.getMessage());
			throw new MetricsServiceException("Error while calculating the metric profile.", e);
		}
    }
    
    
    private IValue getValueMeasuredForMetric(Repository repository, Class<? extends Metric> metricType) {
		MetricsResults mr = repository.getMetricsResults();
		if (mr == null ) return null;
		Measure measure = mr.getMeasureForTheMetric(metricType);
		if (measure == null) return null;
		IValue measuredValue = measure.getMeasuredValue();
		return measuredValue;
	}

	/**
	 * Calculate the metrics of the repository following the current profile.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repository
	 * @throws RepositoryDataSourceException
	 */
	public void obtainAndEvaluateRepositoryMetrics(Repository repository) throws RepositoryDataSourceException {
		RepositoryDataSource repositoryDataSource = RepositoryDataSourceService.getInstance();
		RepositoryInternalMetrics repositoryInternalMetrics = null;
		
		repositoryInternalMetrics = repositoryDataSource.getRepositoryInternalMetrics(repository);
		repository.setRepositoryInternalMetrics(repositoryInternalMetrics);
		
		evaluateRepositoryMetrics(repository);
	}

	public void evaluateRepositoryMetrics(Repository repository) throws RepositoryDataSourceException {
		MetricsResults metricsResults = new MetricsResults();
		    	
		for (MetricConfiguration metricConfiguration : currentMetricProfile.getMetricConfigurationCollection()) {
			metricConfiguration.calculate(repository, metricsResults);
		}
		
		repository.setMetricsResults(metricsResults);
	}
	
	public void evaluateRepositoryCollection() throws RepositoryDataSourceException {
		RepositoriesCollectionService rc = RepositoriesCollectionService.getInstance();
		for (Repository repository : rc.getRepositories()) {
			evaluateRepositoryMetrics(repository);
		}
	}
	
	/**
	 * @param listener
	 * @return
	 * @see java.util.HashSet#add(java.lang.Object)
	 */
	public boolean addCurrentMetricProfileChangedEventListener(Listener<CurrentMetricProfileChangedEvent> listener) {
		return currentMetricProfileChangedEventListeners.add(listener);
	}

	/**
	 * @param listener
	 * @return
	 * @see java.util.HashSet#remove(java.lang.Object)
	 */
	public boolean removeCurrentMetricProfileChangedEventListener(Listener<CurrentMetricProfileChangedEvent> listener) {
		return currentMetricProfileChangedEventListeners.remove(listener);
	}
	
	private void notifyRepositoriesCollectionUpdatedListeners(MetricProfile previousMetricProfile, MetricProfile newMetricProfile) {
		currentMetricProfileChangedEventListeners.forEach(l -> l.on(new CurrentMetricProfileChangedEvent(previousMetricProfile, newMetricProfile)));
	}
	
}
