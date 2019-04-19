package repositorydatasource;

/**
 * Factory interface of repository data sources.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public interface IRepositoryDataSourceFactory {
	/**
	 * Returns a repository data source.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return A repository data source.
	 */
	IRepositoryDataSource getRepositoryDataSource();
}
