package datamodel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
public class RepositoryCalculatedMetrics implements Serializable, Comparable<RepositoryCalculatedMetrics> {
	
	/**
	 * Serial.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -5140818567166881817L;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(date);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RepositoryCalculatedMetrics))
			return false;
		RepositoryCalculatedMetrics other = (RepositoryCalculatedMetrics) obj;
		return Objects.equals(date, other.date);
	}

	@Override
	public int compareTo(RepositoryCalculatedMetrics o) {
		return getDate().compareTo(o.getDate());
	}

	public RepositoryCalculatedMetrics() {	}
	
	public RepositoryCalculatedMetrics(MetricProfile metricProfile, MetricsResults metricsResults) {
		setProfile(metricProfile);
		setDate(metricsResults.getDate());
		for (Measure measure : metricsResults.getMeasures()) {
			if (measure.getMetricConfiguration().getMetric() instanceof MetricTotalNumberOfIssues) {
				setMetricTotalNumberOfIssues(measure);
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricCommitsPerIssue) {
				setMetricCommitsPerIssue(measure);
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricPercentageClosedIssues) {
				setMetricPercentageOfClosedIssues(measure);
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricAverageDaysToCloseAnIssue) {
				setMetricAverageDaysToCloseAnIssue(measure);
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricAverageDaysBetweenCommits) {
				setMetricAverageDaysBetweenCommits(measure);
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricDaysBetweenFirstAndLastCommit) {
				setMetricDaysBetweenFirstAndLastCommit(measure);
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricChangeActivityRange) {
				setMetricChangeActivityRange(measure);
			} else if (measure.getMetricConfiguration().getMetric() instanceof MetricPeakChange) {
				setMetricPeakChange(measure);
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
	 * Sets the profile.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param profile the profile to set
	 */
	private void setProfile(MetricProfile profile) {
		this.profile = profile;
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
	 * Sets the date.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param date the date to set
	 */
	private void setDate(Date date) {
		this.date = date;
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
	 * Sets the metricTotalNumberOfIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricTotalNumberOfIssues the metricTotalNumberOfIssues to set
	 */
	private void setMetricTotalNumberOfIssues(Measure metricTotalNumberOfIssues) {
		this.metricTotalNumberOfIssues = metricTotalNumberOfIssues;
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
	 * Sets the metricCommitsPerIssue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricCommitsPerIssue the metricCommitsPerIssue to set
	 */
	private void setMetricCommitsPerIssue(Measure metricCommitsPerIssue) {
		this.metricCommitsPerIssue = metricCommitsPerIssue;
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
	 * Sets the metricPercentageOfClosedIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricPercentageOfClosedIssues the metricPercentageOfClosedIssues to set
	 */
	private void setMetricPercentageOfClosedIssues(Measure metricPercentageOfClosedIssues) {
		this.metricPercentageOfClosedIssues = metricPercentageOfClosedIssues;
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
	 * Sets the metricAverageDaysToCloseAnIssue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricAverageDaysToCloseAnIssue the metricAverageDaysToCloseAnIssue to set
	 */
	private void setMetricAverageDaysToCloseAnIssue(Measure metricAverageDaysToCloseAnIssue) {
		this.metricAverageDaysToCloseAnIssue = metricAverageDaysToCloseAnIssue;
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
	 * Sets the metricAverageDaysBetweenCommits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricAverageDaysBetweenCommits the metricAverageDaysBetweenCommits to set
	 */
	private void setMetricAverageDaysBetweenCommits(Measure metricAverageDaysBetweenCommits) {
		this.metricAverageDaysBetweenCommits = metricAverageDaysBetweenCommits;
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
	 * Sets the metricDaysBetweenFirstAndLastCommit.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricDaysBetweenFirstAndLastCommit the metricDaysBetweenFirstAndLastCommit to set
	 */
	private void setMetricDaysBetweenFirstAndLastCommit(Measure metricDaysBetweenFirstAndLastCommit) {
		this.metricDaysBetweenFirstAndLastCommit = metricDaysBetweenFirstAndLastCommit;
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
	 * Sets the metricChangeActivityRange.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricChangeActivityRange the metricChangeActivityRange to set
	 */
	private void setMetricChangeActivityRange(Measure metricChangeActivityRange) {
		this.metricChangeActivityRange = metricChangeActivityRange;
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

	/**
	 * Sets the metricPeakChange.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricPeakChange the metricPeakChange to set
	 */
	private void setMetricPeakChange(Measure metricPeakChange) {
		this.metricPeakChange = metricPeakChange;
	}
}