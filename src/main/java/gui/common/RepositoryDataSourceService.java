package gui.common;

import org.gitlab4j.api.GitLabApiException;

import repositorydatasource.GitLabRepositoyDataSourceFactory;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSourceFactory;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoryDataSourceService {
	private static RepositoryDataSourceService instance;
	
	private IRepositoryDataSource repositoryDataSource;
	
	private RepositoryDataSourceService() throws GitLabApiException, RepositoryDataSourceException {
		IRepositoryDataSourceFactory repositoryDataSourceFactory = new GitLabRepositoyDataSourceFactory();
		repositoryDataSource = repositoryDataSourceFactory.getRepositoryDataSource();
		repositoryDataSource.connect();
	}

	/**
	 * Gets the.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the instance
	 * @throws RepositoryDataSourceException 
	 * @throws GitLabApiException 
	 */
	public static RepositoryDataSourceService getInstance() throws GitLabApiException, RepositoryDataSourceException {
		if (instance == null) instance = new RepositoryDataSourceService();
		return instance;
	}

	/**
	 * Gets the.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repositoryDataSource
	 */
	public IRepositoryDataSource getRepositoryDataSource() {
		return repositoryDataSource;
	}
	
	
}
