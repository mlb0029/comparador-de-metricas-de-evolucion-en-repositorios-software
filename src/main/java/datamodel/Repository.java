package datamodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;

import metricsengine.MetricsResults;

/**
 * A repository data class.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class Repository implements Serializable {
		
	/**
	 * Serial.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 5094587490166499680L;

	/**
	 * HTTPS URL from the repository.
	 */
	private String url = null;
	
	/**
	 * Name of the repository.
	 */
	private String name = null;
	
	/**
	 * ID of the repository.
	 */
	private Integer id = null;

	private RepositoryInternalMetrics repositoryInternalMetrics = new RepositoryInternalMetrics();
	
	private Collection<MetricsResults> metricsResultsCollection = new HashSet<MetricsResults>();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, name, url);
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
		if (!(obj instanceof Repository))
			return false;
		Repository other = (Repository) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(url, other.url);
	}

	/**
	 * Constructor that defines the repository, without specifying the metrics that are obtained from it.
	 * 
	 * @param url HTTPS URL from the repository
	 * @param name Name of the repository
	 * @param id  ID of the repository
	 */
	public Repository(String url, String name, Integer id) {
		setUrl(url);
		setName(name);
		setId(id);
	}

	/**
	 * Gets the HTTPS URL from the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the HTTPS URL from the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param url the url to set
	 */
	private void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the name of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the ID of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the ID of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param id the id to set
	 */
	private void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the repositoryInternalMetrics.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repositoryInternalMetrics
	 */
	public RepositoryInternalMetrics getRepositoryInternalMetrics() {
		return repositoryInternalMetrics;
	}

	/**
	 * Sets the repositoryInternalMetrics.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repositoryInternalMetrics the repositoryInternalMetrics to set
	 */
	public void setRepositoryInternalMetrics(RepositoryInternalMetrics repositoryInternalMetrics) {
		this.repositoryInternalMetrics = repositoryInternalMetrics;
	}

	/**
	 * Gets the metricsResultsCollection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the metricsResultsCollection
	 */
	public Collection<MetricsResults> getMetricsResultsCollection() {
		return metricsResultsCollection;
	}

	/**
	 * Sets the metricsResultsCollection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param metricsResultsCollection the metricsResultsCollection to set
	 */
	public void setMetricsResultsCollection(Collection<MetricsResults> metricsResultsCollection) {
		this.metricsResultsCollection = metricsResultsCollection;
	}

	/**
	 * Returns the last collection of metrics that have been calculated 
	 * or null if they have never been calculated.
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the last collection of metrics that have been calculated, never null
	 */
	public MetricsResults getLastMetricsResults() {
		return getMetricsResultsCollection().stream().max(Comparator.naturalOrder()).orElse(null);
	}
}
