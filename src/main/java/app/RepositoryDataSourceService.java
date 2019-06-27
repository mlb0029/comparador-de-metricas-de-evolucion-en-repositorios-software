package app;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.gitlab4j.api.GitLabApiException;

import app.listeners.ConnectionChangedEvent;
import app.listeners.Listener;
import datamodel.Repository;
import datamodel.RepositoryInternalMetrics;
import datamodel.User;
import exceptions.RepositoryDataSourceException;
import repositorydatasource.RepositoryDataSource;
import repositorydatasource.RepositoryDataSourceFactory;
import repositorydatasource.RepositoyDataSourceFactoryGitlab;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoryDataSourceService implements Serializable, RepositoryDataSource {
	
	private static final long serialVersionUID = -6197642368639361682L;

	private static RepositoryDataSourceService instance;
	
	private RepositoryDataSource repositoryDataSource;
	
	private Set<Listener<ConnectionChangedEvent>> connectionChangedEventListeners = new HashSet<>();
	
	private RepositoryDataSourceService() {
		this.repositoryDataSource = new RepositoyDataSourceFactoryGitlab().getRepositoryDataSource();
	}

	/**
	 * Gets the single instance.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the instance
	 * @throws RepositoryDataSourceException 
	 * @throws GitLabApiException 
	 */
	public static RepositoryDataSourceService getInstance() {
		if (instance == null) instance = new RepositoryDataSourceService();
		return instance;
	}
	
	/**
	 * Sets the repositoryDataSource.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repositoryDataSource the repositoryDataSource to set
	 */
	public void setRepositoryDataSource(RepositoryDataSourceFactory repositoryDataSourceFactory) {
		this.repositoryDataSource = repositoryDataSourceFactory.getRepositoryDataSource();
	}

	public void addConnectionChangedEventListener(Listener<ConnectionChangedEvent> listener) {
		connectionChangedEventListeners.add(listener);
	}
	
	public void removeConnectionChangedEventListener(Listener<ConnectionChangedEvent> listener) {
		connectionChangedEventListeners.remove(listener);
	}

	@Override
	public void connect() throws RepositoryDataSourceException {
		EnumConnectionType before = getConnectionType();
		this.repositoryDataSource.connect();
		EnumConnectionType after = getConnectionType();
		connectionChangedEventListeners.forEach(l -> l.on(new ConnectionChangedEvent(before, after)));		
	}

	@Override
	public void connect(String username, String password) throws RepositoryDataSourceException {
		EnumConnectionType before = getConnectionType();
		this.repositoryDataSource.connect(username, password);;
		EnumConnectionType after = getConnectionType();
		connectionChangedEventListeners.forEach(l -> l.on(new ConnectionChangedEvent(before, after)));
	}

	@Override
	public void connect(String token) throws RepositoryDataSourceException {
		EnumConnectionType before = getConnectionType();
		this.repositoryDataSource.connect(token);;
		EnumConnectionType after = getConnectionType();
		connectionChangedEventListeners.forEach(l -> l.on(new ConnectionChangedEvent(before, after)));
	}

	@Override
	public void disconnect() throws RepositoryDataSourceException {
		EnumConnectionType before = getConnectionType();
		this.repositoryDataSource.disconnect();
		EnumConnectionType after = getConnectionType();
		connectionChangedEventListeners.forEach(l -> l.on(new ConnectionChangedEvent(before, after)));
	}

	@Override
	public EnumConnectionType getConnectionType() {
		return this.repositoryDataSource.getConnectionType();
	}

	@Override
	public User getCurrentUser() throws RepositoryDataSourceException {
		return this.repositoryDataSource.getCurrentUser();
	}

	@Override
	public Collection<Repository> getCurrentUserRepositories() throws RepositoryDataSourceException {
		return this.repositoryDataSource.getCurrentUserRepositories();
	}

	@Override
	public Collection<Repository> getAllUserRepositories(String username) throws RepositoryDataSourceException {
		return this.repositoryDataSource.getAllUserRepositories(username);
	}

	@Override
	public Collection<Repository> getAllGroupRepositories(String groupName) throws RepositoryDataSourceException {
		return this.repositoryDataSource.getAllGroupRepositories(groupName);
	}

	@Override
	public Repository getRepository(String repositoryHTTPSURL) throws RepositoryDataSourceException {
		return this.repositoryDataSource.getRepository(repositoryHTTPSURL);
	}

	@Override
	public Repository getRepository(int repositoryId) throws RepositoryDataSourceException {
		return this.repositoryDataSource.getRepository(repositoryId);
	}

	public void updateRepository(Repository repository) throws RepositoryDataSourceException {
		Repository repo = this.repositoryDataSource.getRepository(repository.getId());
		repository.setId(repo.getId());
		repository.setName(repo.getName());
		repository.setUrl(repo.getUrl());
	}

	@Override
	public RepositoryInternalMetrics getRepositoryInternalMetrics(Repository repository)
			throws RepositoryDataSourceException {
		return this.repositoryDataSource.getRepositoryInternalMetrics(repository);
	}
}
