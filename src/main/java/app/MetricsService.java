package app;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.output.ByteArrayOutputStream;
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
import metricsengine.numeric_value_metrics.MetricAverageDaysBetweenCommitsFactory;
import metricsengine.numeric_value_metrics.MetricAverageDaysToCloseAnIssueFactory;
import metricsengine.numeric_value_metrics.MetricChangeActivityRangeFactory;
import metricsengine.numeric_value_metrics.MetricCommitsPerIssueFactory;
import metricsengine.numeric_value_metrics.MetricDaysBetweenFirstAndLastCommitFactory;
import metricsengine.numeric_value_metrics.MetricPeakChangeFactory;
import metricsengine.numeric_value_metrics.MetricPercentageClosedIssuesFactory;
import metricsengine.numeric_value_metrics.MetricTotalNumberOfIssuesFactory;
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
	
	public static final String DEFAULT_PROFILE_NAME = "DEFAULT";
	
	public static final String IMPORTED_PROFILE_NAME = "IMPORTED";
	
	public static final String NEW_PROFILE_NAME = "CALCULATED";

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
		MetricProfile defaultMetricProfile = new MetricProfile(DEFAULT_PROFILE_NAME);
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricTotalNumberOfIssuesFactory()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricCommitsPerIssueFactory()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPercentageClosedIssuesFactory()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysToCloseAnIssueFactory()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysBetweenCommitsFactory()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricDaysBetweenFirstAndLastCommitFactory()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricChangeActivityRangeFactory()));
		defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPeakChangeFactory()));
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
			MetricProfile metricProfile = new MetricProfile(NEW_PROFILE_NAME);
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
				metricProfile.addMetricConfiguration(new MetricConfiguration(metricC.getMetricFactory(), q1ForMetricConfig, q3ForMetricConfig));
			}
			currentMetricProfile = metricProfile;
			notifyRepositoriesCollectionUpdatedListeners(oldMetricProfile, metricProfile);
		} catch (Exception e) {
			LOGGER.error("Error deleting a repository. Exception occurred: " + e.getMessage());
			throw new MetricsServiceException("Error while calculating the metric profile.", e);
		}
    }
    
    
    public void importCurrentMetricProfile(InputStream inputStream) throws MetricsServiceException {
		try (
			ObjectInputStream objectIn = new ObjectInputStream(inputStream);
		) {
			MetricProfile oldMetricProfile = currentMetricProfile;
			MetricProfile mP = (MetricProfile) objectIn.readObject();
			mP.setName(IMPORTED_PROFILE_NAME);
			currentMetricProfile = mP;
			notifyRepositoriesCollectionUpdatedListeners(oldMetricProfile, currentMetricProfile);
		} catch (StreamCorruptedException e) {
			throw new MetricsServiceException(MetricsServiceException.CORRUPTED, e);
		} catch (Exception e) {
			throw new MetricsServiceException(MetricsServiceException.IMPORT_ERROR, e);
		}
	}

	public InputStream exportCurrentMetricProfile() throws MetricsServiceException {
		try (
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(bos);
		){
			objectOut.writeObject(currentMetricProfile);
			objectOut.flush();
			return bos.toInputStream();
		} catch (Exception e) {
			throw new MetricsServiceException(MetricsServiceException.EXPORT_ERROR, e);
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
