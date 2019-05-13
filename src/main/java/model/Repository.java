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
public class Repository implements Serializable, Comparable<Repository>{
		
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

	private RepositoryInternalMetrics repositoryInternalMetrics = null;
	
	private Collection<RepositoryCalculatedMetrics> calculatedMetricsCollection;
	
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
		repositoryInternalMetrics = new RepositoryInternalMetrics();
		calculatedMetricsCollection = new HashSet<RepositoryCalculatedMetrics>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (obj instanceof Repository) return false;
		Repository objRepository = (Repository) obj;
		return getId().equals(objRepository.getId())
				&& getUrl().equals(objRepository.getUrl())
				&& getName().equals(objRepository.getName());
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
	 * Gets the calculatedMetricsCollection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the calculatedMetricsCollection
	 */
	public Collection<RepositoryCalculatedMetrics> getCalculatedMetricsCollection() {
		return calculatedMetricsCollection;
	}

	/**
	 * Sets the calculatedMetricsCollection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param calculatedMetricsCollection the calculatedMetricsCollection to set
	 */
	public void setCalculatedMetricsCollection(Collection<RepositoryCalculatedMetrics> calculatedMetricsCollection) {
		this.calculatedMetricsCollection = calculatedMetricsCollection;
	}

	@Override
	public int compareTo(Repository o) {
		return this.getName().compareTo(o.getName());
	}
}
