package repositorydatasource.factories;

import repositorydatasource.GitLabRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource;

/**
 * Factory of GitLab Reposistory Data Source.
 * 
 * @author MALB
 *
 */
public class GitLabRepositoyDataSourceFactory implements IRepositoryDataSourceFactory {

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDaraSourceFactory#createRepositoryDataSource()
	 */
	@Override
	public IRepositoryDataSource createRepositoryDataSource() {
		return GitLabRepositoryDataSource.getGitLabRepositoryDataSource();
	}

}
