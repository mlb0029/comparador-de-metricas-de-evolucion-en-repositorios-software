package repositorydatasource;

import exceptions.RepositoryDataSourceException;
import repositorydatasource.model.EnumConnectionType;
import repositorydatasource.model.Repository;

/**
 * Repository data source interface.
 * 
 * @author MALB
 *
 */
public interface IRepositoryDataSource {
	/**
	 * Public connection.
	 * 
	 * @throws RepositoryDataSourceException When problems occur when connecting.
	 */
	void connect() throws RepositoryDataSourceException;
	
	/**
	 * Logged connection with username and password.
	 * 
	 * @param username Username.
	 * @param password Password.
	 * @throws RepositoryDataSourceException When problems occur when connecting.
	 */
	void connect(String username, String password) throws RepositoryDataSourceException;
	
	/**
	 * Logged connection with token.
	 * 
	 * @param token Token.
	 * @throws RepositoryDataSourceException When problems occur when connecting. 
	 */
	void connect(String token) throws RepositoryDataSourceException;
	
	/**
	 * Disconnect.
	 * 
	 * @throws RepositoryDataSourceException If there was no connection.
	 */
	void disconnect() throws RepositoryDataSourceException;
	
	/**
	 * Gets type of connection.
	 * 
	 * @return Type of connection.
	 */
	EnumConnectionType getConnectionType();
	
	/**
	 * Get a repository using his URL.
	 * 
	 * @param repositoryURL URL of a repository.
	 * @return Repository.
	 * @throws RepositoryDataSourceException When it has not been possible to obtain the repository.
	 */
	Repository getRepository(String repositoryURL) throws RepositoryDataSourceException;
}
