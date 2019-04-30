package gui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import repositorydatasource.model.Repository;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListService {
	
	private static RepositoriesListService instance;
	
	private Collection<Repository> repositories;
	
	private RepositoriesListService() {
		repositories = new HashSet<Repository>();
		setRepositories(getTestSource());
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

	/**
	 * DELETE. Test repositories for grid.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return
	 */
	private static Collection<Repository> getTestSource(){
		Collection<Repository> repositories = new ArrayList<Repository>();
		Repository repository1 = new Repository("urlA", "Abcd", 1);
		Repository repository2 = new Repository("urlB", "bcdA", 2);
		Repository repository3 = new Repository("urlC", "cde", 3);
		Repository repository4 = new Repository("urlCD", "Efg", 4);
		Repository repository5 = new Repository("urlAD", "HIJ", 5);
		repositories.add(repository1);
		repositories.add(repository2);
		repositories.add(repository3);
		repositories.add(repository4);
		repositories.add(repository5);
		return repositories;
	}
}
