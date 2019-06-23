package app.listeners;

import java.io.Serializable;

import metricsengine.MetricProfile;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class CurrentMetricProfileChangedEvent implements Serializable, Event {

	private static final long serialVersionUID = 2616585155826316277L;

	private MetricProfile previousMetricProfile;
	
	private MetricProfile newMetricProfile;

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param previousMetricProfile
	 * @param newMetricProfile
	 */
	public CurrentMetricProfileChangedEvent(MetricProfile previousMetricProfile, MetricProfile newMetricProfile) {
		this.previousMetricProfile = previousMetricProfile;
		this.newMetricProfile = newMetricProfile;
	}

	/**
	 * Gets the previousMetricProfile.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the previousMetricProfile
	 */
	public MetricProfile getPreviousMetricProfile() {
		return previousMetricProfile;
	}

	/**
	 * Gets the newMetricProfile.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the newMetricProfile
	 */
	public MetricProfile getNewMetricProfile() {
		return newMetricProfile;
	}

}
