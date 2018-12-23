package repositorydatasource;

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
	 * @return 1 if connected, -1 if error
	 */
	Integer connect();
	
	/**
	 * Logged connection with username and password.
	 * 
	 * @param username Username.
	 * @param password Password.
	 * @return 1 if connected, -1 if error
	 */
	Integer connect(String username, String password);
	
	/**
	 * Logged connection with token.
	 * 
	 * @param token Token.
	 * @return 1 if connected, -1 if error
	 */
	Integer connect(String token);
	
	/**
	 * Disconnect.
	 */
	void disconnect();
	
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
	 */
	Repository getRepository(String repositoryURL);
}
