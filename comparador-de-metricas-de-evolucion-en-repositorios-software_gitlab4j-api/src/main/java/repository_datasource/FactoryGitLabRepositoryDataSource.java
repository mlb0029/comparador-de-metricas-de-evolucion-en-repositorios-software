package repository_datasource;

/**
 * Creates a GitLabs data source.
 * 
 * @author MALB
 * @since 01/12/2018
 *
 */
public class FactoryGitLabRepositoryDataSource implements IFactoryRepositoryDataSource {
	
	/**
	 * Single instance of the class.
	 */
	private static FactoryGitLabRepositoryDataSource factoryGitLabRepositoryDataSource = null;
	
	/**
	 * Private and empty constructor.
	 */
	private FactoryGitLabRepositoryDataSource() {}

	/**
	 * Return the single instance of the class.
	 * 
	 * @return Single instance of the class.
	 */
	public static FactoryGitLabRepositoryDataSource getFactoryGitLabRepositoryDataSource() {
		if (factoryGitLabRepositoryDataSource.equals(null)) {
			factoryGitLabRepositoryDataSource = new FactoryGitLabRepositoryDataSource();
		}
		return factoryGitLabRepositoryDataSource;
	}

	/* (non-Javadoc)
	 * @see repository_datasource.IFactoryRepositoryDataSource#createRepositoryDataSource(repository_datasource.EnumLoginType, java.lang.String[])
	 */
	@Override
	public IRepositoryDataSource createRepositoryDataSource(EnumLoginType loginType, String[] args) {
		return GitLabDataSource.getGitLabDataSource(loginType, args);
	}

}
