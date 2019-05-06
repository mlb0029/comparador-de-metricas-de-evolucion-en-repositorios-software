package gui.common;

import java.util.Collection;
import java.util.HashSet;

import model.Repository;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListService {
	
	private static RepositoriesListService instance;
	
	private Collection<Repository> repositories;
	
	private RepositoriesListService() {
		repositories = new HashSet<Repository>();
	}

	/**
	 * Gets the.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the instance
	 */
	public static RepositoriesListService getInstance() {
		if (instance == null) instance = new RepositoriesListService();
		return instance;
	}

	/**
	 * Gets the.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repositories
	 */
	public Collection<Repository> getRepositories() {
		return repositories;
	}

	/**
	 * Sets the.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repositories the repositories to set
	 */
	public void setRepositories(Collection<Repository> repositories) {
		this.repositories = repositories;
	}
}
