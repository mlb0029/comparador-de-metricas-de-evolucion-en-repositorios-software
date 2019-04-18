package repositorydatasource;

import org.gitlab4j.api.GitLabApiException;

/**
 * Factory interface of repository data sources.
 * 
 * @author MALB
 *
 */
public interface IRepositoryDataSourceFactory {
	IRepositoryDataSource createRepositoryDataSource() throws GitLabApiException;
}
