package repositorydatasource;

import repositorydatasource.model.Repository;

/**
 * Repository data source interface.
 * 
 * @author MALB
 *
 */
public interface IRepositoryDataSource {
	
	/**
	 * Type of connection.
	 * 
	 * @author MALB
	 *
	 */
	public enum EnumConnectionType{
		NOT_CONNECTED, CONNECTED, LOGGED
	}
	
	/**
	 * Public connection.
	 * 
	 * @throws ExceptionRepositoryDataSource When problems occur when connecting.
	 */
	void connect() throws ExceptionRepositoryDataSource;
	
	/**
	 * Logged connection with username and password.
	 * 
	 * @param username Username.
	 * @param password Password.
	 * @throws ExceptionRepositoryDataSource When problems occur when connecting.
	 */
	void connect(String username, String password) throws ExceptionRepositoryDataSource;
	
	/**
	 * Logged connection with token.
	 * 
	 * @param token Token.
	 * @throws ExceptionRepositoryDataSource When problems occur when connecting. 
	 */
	void connect(String token) throws ExceptionRepositoryDataSource;
	
	/**
	 * Disconnect.
	 * 
	 * @throws ExceptionRepositoryDataSource If there was no connection.
	 */
	void disconnect() throws ExceptionRepositoryDataSource;
	
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
	 * @throws ExceptionRepositoryDataSource When it has not been possible to obtain the repository.
	 */
	Repository getRepository(String repositoryURL) throws ExceptionRepositoryDataSource;
}
