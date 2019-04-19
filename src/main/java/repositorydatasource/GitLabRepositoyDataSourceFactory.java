package repositorydatasource;

/**
 * Factory of GitLab Reposistory Data Source.
 * 
 * @author MALB
 *
 */
public class GitLabRepositoyDataSourceFactory implements IRepositoryDataSourceFactory {

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSourceFactory#getRepositoryDataSource()
	 */
	@Override
	public IRepositoryDataSource getRepositoryDataSource() {
		return GitLabRepositoryDataSource.getGitLabRepositoryDataSource();
	}

}
