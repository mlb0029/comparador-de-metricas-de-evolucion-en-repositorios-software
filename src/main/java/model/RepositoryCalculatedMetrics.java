package model;

import java.util.Date;

import metricsengine.Measure;
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

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoryCalculatedMetrics {
	
	private MetricProfile profile = null;
	private Date date = null;
	
	private Measure metricTotalNumberOfIssues = null;
	private Measure metricCommitsPerIssue = null;
	private Measure metricPercentageOfClosedIssues = null;
	private Measure metricAverageDaysToCloseAnIssue = null;
	private Measure metricAverageDaysBetweenCommits = null;
	private Measure metricDaysBetweenFirstAndLastCommit = null;
	private Measure metricChangeActivityRange = null;
	private Measure metricPeakChange = null;
	
	public RepositoryCalculatedMetrics(MetricProfile metricProfile, MetricsResults metricsResults) {
		profile = metricProfile;
		date = metricsResults.getDate();
		for (Measure measure : metricsResults.getMeasures()) {
			if (measure.getMetricConfiguration().getMetric() instanceof MetricTotalNumberOfIssues) {
				metricTotalNumberOfIssues = measure;
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricCommitsPerIssue) {
				metricCommitsPerIssue = measure;
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricPercentageClosedIssues) {
				metricPercentageOfClosedIssues = measure;
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricAverageDaysToCloseAnIssue) {
				metricAverageDaysToCloseAnIssue = measure;
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricAverageDaysBetweenCommits) {
				metricAverageDaysBetweenCommits = measure;
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricDaysBetweenFirstAndLastCommit) {
				metricDaysBetweenFirstAndLastCommit = measure;
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricChangeActivityRange) {
				metricChangeActivityRange = measure;
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricPeakChange) {
				metricPeakChange = measure;
			}
		}
	}

	/**
	 * Gets the profile.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the profile
	 */
	public MetricProfile getProfile() {
		return profile;
	}

	/**
	 * Gets the date.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the metricTotalNumberOfIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricTotalNumberOfIssues
	 */
	public Measure getMetricTotalNumberOfIssues() {
		return metricTotalNumberOfIssues;
	}

	/**
	 * Gets the metricCommitsPerIssue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricCommitsPerIssue
	 */
	public Measure getMetricCommitsPerIssue() {
		return metricCommitsPerIssue;
	}

	/**
	 * Gets the metricPercentageOfClosedIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricPercentageOfClosedIssues
	 */
	public Measure getMetricPercentageOfClosedIssues() {
		return metricPercentageOfClosedIssues;
	}

	/**
	 * Gets the metricAverageDaysToCloseAnIssue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricAverageDaysToCloseAnIssue
	 */
	public Measure getMetricAverageDaysToCloseAnIssue() {
		return metricAverageDaysToCloseAnIssue;
	}

	/**
	 * Gets the metricAverageDaysBetweenCommits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricAverageDaysBetweenCommits
	 */
	public Measure getMetricAverageDaysBetweenCommits() {
		return metricAverageDaysBetweenCommits;
	}

	/**
	 * Gets the metricDaysBetweenFirstAndLastCommit.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricDaysBetweenFirstAndLastCommit
	 */
	public Measure getMetricDaysBetweenFirstAndLastCommit() {
		return metricDaysBetweenFirstAndLastCommit;
	}

	/**
	 * Gets the metricChangeActivityRange.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricChangeActivityRange
	 */
	public Measure getMetricChangeActivityRange() {
		return metricChangeActivityRange;
	}

	/**
	 * Gets the metricPeakChange.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricPeakChange
	 */
	public Measure getMetricPeakChange() {
		return metricPeakChange;
	}
}