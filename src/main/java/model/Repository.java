package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * A repository data class.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class Repository implements Serializable{
		
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

	/**
	 * Repository internal metrics.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private RepositoryInternalMetrics internalMetrics = null;
	
	/**
	 * Repository calculated metrics.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private Collection<RepositoryCalculatedMetrics> calculatedMetricsCollection = null;
	
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
		calculatedMetricsCollection = new HashSet<RepositoryCalculatedMetrics>();
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
	 * Gets the repository internal metrics.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repository internal metrics
	 */
	public RepositoryInternalMetrics getInternalMetrics() {
		return internalMetrics;
	}

	/**
	 * Sets the repository internal metrics.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param internalMetrics the repository internal metrics to set
	 */
	public void setInternalMetrics(RepositoryInternalMetrics internalMetrics) {
		this.internalMetrics = internalMetrics;
	}

	/**
	 * Gets the calculatedMetricsCollection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the calculatedMetricsCollection
	 */
	public Collection<RepositoryCalculatedMetrics> getCalculatedMetricsCollection() {
		return calculatedMetricsCollection;
	}
}
