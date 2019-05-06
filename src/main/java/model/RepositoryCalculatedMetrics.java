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
	MetricProfile profile = null;
	Date date = null;
	Measure metricTotalNumberOfIssues = null;
	Measure metricCommitsPerIssue = null;
	Measure metricPercentageOfClosedIssues = null;
	Measure metricAverageDaysToCloseAnIssue = null;
	Measure metricAverageDaysBetweenCommits = null;
	Measure metricDaysBetweenFirstAndLastCommit = null;
	Measure metricChangeActivityRange = null;
	Measure metricPeakChange = null;
	
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
}