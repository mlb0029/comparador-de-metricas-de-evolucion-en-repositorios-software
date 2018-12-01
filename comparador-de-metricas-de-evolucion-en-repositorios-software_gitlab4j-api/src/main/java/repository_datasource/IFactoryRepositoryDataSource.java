package repository_datasource;

/**
 * Creates repository data sources.
 * 
 * @author MALB
 * @since 01/12/2018
 *
 */
public interface IFactoryRepositoryDataSource {
	
	/**
	 * Creates repository a data source.
	 * 
	 * @param loginType Type of login.
	 * @param args Arguments to login.
	 * @return Repository data sources.
	 */
	IRepositoryDataSource createRepositoryDataSource(EnumLoginType loginType, String[] args);
}
