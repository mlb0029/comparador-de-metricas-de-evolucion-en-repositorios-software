package app;

import java.io.Serializable;

import org.gitlab4j.api.GitLabApiException;

import repositorydatasource.GitLabRepositoyDataSourceFactory;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoryDataSourceService implements Serializable {
	
	private static final long serialVersionUID = -6197642368639361682L;

	private static RepositoryDataSourceService instance;
	
	private IRepositoryDataSource repositoryDataSource;
	
	private RepositoryDataSourceService() {
		this.repositoryDataSource = new GitLabRepositoyDataSourceFactory().getRepositoryDataSource();
	}

	/**
	 * Gets the single instance.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the instance
	 * @throws RepositoryDataSourceException 
	 * @throws GitLabApiException 
	 */
	public static RepositoryDataSourceService getInstance() {
		if (instance == null) instance = new RepositoryDataSourceService();
		return instance;
	}

	/**
	 * Gets the repositoryDataSource.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repositoryDataSource
	 */
	public IRepositoryDataSource getRepositoryDataSource() {
		return repositoryDataSource;
	}
}
