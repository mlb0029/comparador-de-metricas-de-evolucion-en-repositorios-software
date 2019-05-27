package repositorydatasource;

import java.util.Collection;

import datamodel.Repository;
import datamodel.RepositoryInternalMetrics;
import datamodel.User;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * It defines the functions that will allow 
 * obtaining the necessary information from a repository.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public interface IRepositoryDataSource {
	
	/**
	 * Type of connection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 *
	 */
	public enum EnumConnectionType{
		/**
		 * A connection to the data source has not been established.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 */
		NOT_CONNECTED, 
		/**
		 * A connection to the data source without login has been established.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 */
		CONNECTED, 
		/**
		 * A connection to the data source with login has been established.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 */
		LOGGED
	}
	
	/**
	 * Try a connection to the data source without login and sets the connection type to CONNECTED
	 * or throws an exception.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws RepositoryDataSourceException  When problems occur when connecting.
	 */
	void connect() throws RepositoryDataSourceException;
	
	/**
	 * Try a login connection using user and password and sets the connection type to LOGGED
	 * or throw an exception.
	 * 
	 * @param username Username.
	 * @param password Password.
	 * @throws RepositoryDataSourceException When problems occur when connecting.
	 */
	void connect(String username, String password) throws RepositoryDataSourceException;
	
	/**
	 * Try a login connection using personal access token and sets the connection type to LOGGED
	 * or throw an exception.
	 * 
	 * @param token Token.
	 * @throws RepositoryDataSourceException When problems occur when connecting. 
	 */
	void connect(String token) throws RepositoryDataSourceException;
	
	/**
	 * Close any established connection. Throw an exception if there was no connection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws RepositoryDataSourceException if there was no connection.
	 */
	void disconnect() throws RepositoryDataSourceException;
	
	/**
	 * Gets type of connection.
	 * 
	 * @return Type of connection.
	 */
	EnumConnectionType getConnectionType();
	
	/**
	 * Gets the user that has logged in or null if no user is connected.
	 * Throw an exception if there is a problem to obtain the user's information.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return The user that has logged in or null if no user is connected.
	 * @throws RepositoryDataSourceException if there is a problem to obtain the user's information.
	 */
	User getCurrentUser() throws RepositoryDataSourceException;
	
	/**
	 * Get a collection of public and private repositories 
	 * of the user who is logged in. 
	 * Throws an exception if a problem occurs when obtaining the user's repositories or not user logged in.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return Get a collection of public and private repositories of the user who is logged in 
	 * or null if no user has logged in. 
	 * @throws RepositoryDataSourceException if a problem occurs when obtaining the user's repositories.
	 */
	Collection<Repository> getCurrentUserRepositories() throws RepositoryDataSourceException;
	
	
	/**
	 * It obtains the public repositories of the user whose username is passed by parameter 
	 * </br>or the public and private repositories if the user name matches 
	 * the username of the connected user.
	 * <p>
	 * Throw an exception if the user doesn't exist or problems when getting information of the user.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param username Username.
	 * @return A collection of repositories.
	 * @throws RepositoryDataSourceException If user doesn't exists.
	 */
	Collection<Repository> getAllUserRepositories(String username) throws RepositoryDataSourceException;
	
	/**
	 * Obtain a repository accessible by the logged in user 
	 * or throw an exception if the repository is not accessible.
	 * 
	 * @param repositoryURL URL of a repository.
	 * @return The repository pointed to by the url.
	 * @throws RepositoryDataSourceException When it has not been possible to obtain the repository.
	 */
	Repository getRepository(String repositoryHTTPSURL) throws RepositoryDataSourceException;
	
	/**
	 * Updates the internal metrics of the repository that is passed by parameter,
	 * if any can not be calculated, its value will be null.
	 * 
	 * @param repositoryURL URL of a repository.
	 * @return TODO
	 * @throws RepositoryDataSourceException When it has not been possible to obtain information from the repository.
	 */
	RepositoryInternalMetrics getRepositoryInternalMetrics(Repository repository) throws RepositoryDataSourceException;
}
