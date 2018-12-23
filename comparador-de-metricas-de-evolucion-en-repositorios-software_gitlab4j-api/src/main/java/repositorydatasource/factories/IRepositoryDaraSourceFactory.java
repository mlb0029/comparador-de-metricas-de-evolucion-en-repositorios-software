package repositorydatasource.factories;

import repositorydatasource.IRepositoryDataSource;

/**
 * Factory interface of repository data sources.
 * 
 * @author MALB
 *
 */
public interface IRepositoryDaraSourceFactory {
	IRepositoryDataSource createRepositoryDataSource();
}
