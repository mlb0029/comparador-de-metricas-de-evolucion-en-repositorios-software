package gui.common;

import org.gitlab4j.api.GitLabApiException;

import repositorydatasource.GitLabRepositoyDataSourceFactory;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSourceFactory;
import repositorydatasource.ExceptionRepositoryDataSource;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoryDataSourceService {
	private static RepositoryDataSourceService instance;
	
	private IRepositoryDataSource repositoryDataSource;
	
	private RepositoryDataSourceService() throws GitLabApiException, ExceptionRepositoryDataSource {
		IRepositoryDataSourceFactory repositoryDataSourceFactory = new GitLabRepositoyDataSourceFactory();
		repositoryDataSource = repositoryDataSourceFactory.createRepositoryDataSource();
		repositoryDataSource.connect();
	}

	/**
	 * Gets the.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the instance
	 * @throws ExceptionRepositoryDataSource 
	 * @throws GitLabApiException 
	 */
	public static RepositoryDataSourceService getInstance() throws GitLabApiException, ExceptionRepositoryDataSource {
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
