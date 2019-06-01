package repositorydatasource;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.gitlab4j.api.Constants.IssueState;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.IssueFilter;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datamodel.Repository;
import datamodel.RepositoryInternalMetrics;
import datamodel.User;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * Implements IRepositoryDataSource that obtains the data from GitLab
 * 
 * @author migue
 *
 */
public class GitLabRepositoryDataSource implements IRepositoryDataSource {

	/**
	 * Serial.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 2565951561088053095L;
	
	private Set<IRepositoryDataSourceListener> listeners = new HashSet<>();

	/**
	 * Default Host URL.
	 */
	public static final String HOST_URL = "https://gitlab.com";

	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(GitLabRepositoryDataSource.class);

	/**
	 * Single instance of the class.
	 */
	private static GitLabRepositoryDataSource instance;

	/**
	 * Connection type.
	 */
	private EnumConnectionType connectionType;

	/**
	 * API that helps to connect to GitLab.
	 * 
	 * @see GitLabApi
	 */
	private GitLabApi gitLabApi;
	
	/**
	 * Current user.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private User currentUser;

	/**
	 * Constructor that returns a not connected gitlabrepositorydatasource.
	 */
	private GitLabRepositoryDataSource() {
		connectionType = EnumConnectionType.NOT_CONNECTED;
		gitLabApi = null;
		currentUser = null;
	}
	
	/**
	 * Gets the single instance of the class.
	 * 
	 * @return A gitlab repository data source.
	 */
	public static GitLabRepositoryDataSource getGitLabRepositoryDataSource() {
		if (GitLabRepositoryDataSource.instance == null) GitLabRepositoryDataSource.instance = new GitLabRepositoryDataSource();
		return instance;
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#connect()
	 */
	@Override
	public void connect() throws RepositoryDataSourceException {
		if( connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
			gitLabApi = new GitLabApi(GitLabRepositoryDataSource.HOST_URL, "");
			currentUser = null;
			connectionType = EnumConnectionType.CONNECTED;
			listeners.forEach(l -> l.onConnectionChangeEvent(connectionType));
			logger.info("Established connection with GitLab");
		} else {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_CONNECTED);
		}
	}
	
	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#connect(java.lang.String, java.lang.String)
	 */
	@Override
	public void connect(String username, String password) throws RepositoryDataSourceException {
		try {
			if(username == null || password == null || username.isBlank() || password.isBlank()) throw new RepositoryDataSourceException(RepositoryDataSourceException.LOGIN_ERROR);
			if(connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
				gitLabApi = GitLabApi.oauth2Login(GitLabRepositoryDataSource.HOST_URL, username, password.toCharArray());
				currentUser = getCurrentUser(gitLabApi.getUserApi().getCurrentUser());
				connectionType = EnumConnectionType.LOGGED;
				listeners.forEach(l -> l.onConnectionChangeEvent(connectionType));
				logger.info("Login to GitLab");
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_CONNECTED);
			}
		} catch (GitLabApiException e) {
			reset();
			throw new RepositoryDataSourceException(RepositoryDataSourceException.LOGIN_ERROR);
		} catch (Exception  e) {
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#connect(java.lang.String)
	 */
	@Override
	public void connect(String token) throws RepositoryDataSourceException {
		try {
			if(connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
				gitLabApi = new GitLabApi(GitLabRepositoryDataSource.HOST_URL, token);
				currentUser = getCurrentUser(gitLabApi.getUserApi().getCurrentUser());
				connectionType = EnumConnectionType.LOGGED;
				listeners.forEach(l -> l.onConnectionChangeEvent(connectionType));
				logger.info("Login to GitLab");
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_CONNECTED);
			}
		} catch (GitLabApiException e) {
			reset();
			throw new RepositoryDataSourceException(RepositoryDataSourceException.LOGIN_ERROR);
		} catch (RepositoryDataSourceException e) {
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#disconnect()
	 */
	@Override
	public void disconnect() throws RepositoryDataSourceException {
		if (connectionType != EnumConnectionType.NOT_CONNECTED) {
			reset();
			listeners.forEach(l -> l.onConnectionChangeEvent(connectionType));
		} else {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_DISCONNECTED);
		}
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#getConnectionType()
	 */
	@Override
	public EnumConnectionType getConnectionType() {
		return connectionType;
	}
	
	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#getCurrentUser()
	 */
	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Obtains a user from the gitlab user.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param gitlabUser
	 * @return
	 */
	private User getCurrentUser(org.gitlab4j.api.models.User gitlabUser) {
		return new User(
				gitlabUser.getId(),
				gitlabUser.getAvatarUrl(),
				gitlabUser.getEmail(),
				gitlabUser.getName(),
				gitlabUser.getUsername()
		);
	}
	
	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#getAllUserRepositories()
	 */
	@Override
	public Collection<Repository> getCurrentUserRepositories() throws RepositoryDataSourceException {
		try {
			if (connectionType != EnumConnectionType.NOT_CONNECTED) {
				return gitLabApi.getProjectApi().getUserProjectsStream(
							currentUser.getId(),
							new ProjectFilter())
						.map(p -> 
							new Repository(p.getWebUrl(), p.getName(), p.getId()))
						.collect(Collectors.toList());
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.NOT_CONNECTED);
			}
		} catch (GitLabApiException e) {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.REPOSITORY_NOT_FOUND);
		}
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#getAllUserRepositories(java.lang.String)
	 */
	@Override
	public Collection<Repository> getAllUserRepositories(String username) throws RepositoryDataSourceException {
		Collection<Repository> repositories;
		try {
			if (currentUser != null && currentUser.getUsername().equals(username)) {
				repositories = getCurrentUserRepositories();
			} else if(! connectionType.equals(EnumConnectionType.NOT_CONNECTED)){
				repositories = gitLabApi.getProjectApi()
						.getUserProjectsStream(username, new ProjectFilter())
							.map(p -> new Repository(p.getWebUrl(), p.getName(), p.getId()))
							.collect(Collectors.toList());
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.NOT_CONNECTED);
			}
			return repositories;
		} catch (RepositoryDataSourceException e) {
			throw e;
		} catch (GitLabApiException e) {
			throw new RepositoryDataSourceException();
		}
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#getRepository(java.lang.String)
	 */
	@Override
	public Repository getRepository(String repositoryHTTPSURL) throws RepositoryDataSourceException{
		Integer projectId;
		if (! connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
			projectId = obtenerIDProyecto(repositoryHTTPSURL);
			if (projectId != null) {
				return new Repository(repositoryHTTPSURL, getRepositoryName(projectId), projectId);
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.REPOSITORY_NOT_FOUND);
			}
		} else {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.NOT_CONNECTED);
		}
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#updateRepositoryInternalMetrics(model.Repository)
	 */
	@Override
	public RepositoryInternalMetrics getRepositoryInternalMetrics(Repository repository) throws RepositoryDataSourceException {
		int projectId = repository.getId();
		int totalNumberOfIssues = getTotalNumberOfIssues(projectId);
		int totalNumberOfCommits = getTotalNumberOfCommits(projectId);
		int numberOfClosedIssues = getNumberOfClosedIssues(projectId);
		List<Integer> daysToCloseEachIssue = getDaysToCloseEachIssue(projectId);
		Set<Date> commitDates = getCommitsDates(projectId);
		int lifeSpanMonths = getRepositoryLifeInMonths(projectId);
		RepositoryInternalMetrics repositoryInternalMetrics = new RepositoryInternalMetrics(
				totalNumberOfIssues,
				totalNumberOfCommits,
				numberOfClosedIssues,
				daysToCloseEachIssue,
				commitDates,
				lifeSpanMonths
		);
		return repositoryInternalMetrics;
	}

	/**
	 * Reset the connection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void reset() {
		connectionType = EnumConnectionType.NOT_CONNECTED;
		gitLabApi = null;
		currentUser = null;
	}

	/**
	 * Gets the ID of a project using the Project URL.
	 * 
	 * @param repositoryURL Project URL.
	 * @return ID of a project.
	 */
	private Integer obtenerIDProyecto(String repositoryURL) {
		try {
			if(repositoryURL == null) return null;
			String sProyecto = repositoryURL.replaceAll(GitLabRepositoryDataSource.HOST_URL + "/", "");
			String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
			String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
			Project pProyecto = gitLabApi.getProjectApi().getProject(propietarioYGrupo, nombreProyecto);
			return pProyecto.getId();
		} catch (GitLabApiException e) {
			return null;
		}
	}
	
	/**
	 * Gets the repository name specifying the project ID.
	 * 
	 * @param projectId ID of the project.
	 * @return Repository name or null if fail.
	 */
	private String getRepositoryName(Integer projectId) {
		try {
			return gitLabApi.getProjectApi().getProject(projectId).getName();
		} catch (GitLabApiException e) {
			return null;
		}
	}

	/**
	 * Gets total number of issues of a project specifying the project ID.
	 * 
	 * @param projectId ID of the project.
	 * @return Total number of issues or -1 if fail.
	 */
	private Integer getTotalNumberOfIssues(Integer projectId) {	
		try {
			return (int) gitLabApi.getIssuesApi().getIssuesStream(projectId, new IssueFilter()).count();
		} catch (GitLabApiException e) {
			return null;
		}
	}

	/**
	 * Gets total number of commits of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Total number of commits of a project or -1 if fail.
	 */
	private Integer getTotalNumberOfCommits(Integer projectId) {
		try {
			return (int) gitLabApi.getCommitsApi().getCommitStream(projectId).count();
		} catch (GitLabApiException e) {
			return null;
		}
	}

	/**
	 * Gets number of closed issues of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Number of closed issues of a project or -1 if fail.
	 */
	private Integer getNumberOfClosedIssues(Integer projectId) {
		try {
			return (int) gitLabApi.getIssuesApi().getIssuesStream(projectId, new IssueFilter().withState(IssueState.CLOSED)).count();
		} catch (GitLabApiException e) {
			return null;
		}
	}

	/**
	 * Gets days to close each issue of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Days to close each issue of a project or null if fail.
	 */
	private List<Integer> getDaysToCloseEachIssue(Integer projectId) {
		try {
			return gitLabApi.getIssuesApi().getIssuesStream(projectId, new IssueFilter().withState(IssueState.CLOSED))
					.filter(issue -> issue.getCreatedAt() != null && issue.getClosedAt() != null)
					.map(issue -> (int) ((issue.getClosedAt().getTime() - issue.getCreatedAt().getTime()) / (1000 * 60 * 60 * 24 )))
					.collect(Collectors.toList());
		} catch (GitLabApiException e) {
			return null;
		}
	}

	/**
	 * Gets dates of commits of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Set of dates of commits of a project or null if fail.
	 */
	private Set<Date> getCommitsDates(Integer projectId) {
		try {
			return gitLabApi.getCommitsApi().getCommitStream(projectId)
					.map(commit -> commit.getCommittedDate())
					.collect(Collectors.toSet());
		} catch (GitLabApiException e) {
			return null;
		}
	}
	
	/**
	 * Computes the number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 * 
	 * @param projectId ID of the project.
	 * @return  Number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 */
	private Integer getRepositoryLifeInMonths(Integer projectId) {
		try {
			Date createdAtDate = gitLabApi.getProjectApi().getProject(projectId).getCreatedAt();
			Date lastActivityDate = gitLabApi.getProjectApi().getProject(projectId).getLastActivityAt();
			
			if (createdAtDate == null || lastActivityDate == null) return null;
			
			Calendar createdAtCalendar = new GregorianCalendar();
			createdAtCalendar.setTime(createdAtDate);
			Calendar lastActivityCalendar = new GregorianCalendar();
			lastActivityCalendar.setTime(lastActivityDate);
			
			int diffYear = lastActivityCalendar.get(Calendar.YEAR) - createdAtCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + lastActivityCalendar.get(Calendar.MONTH) - createdAtCalendar.get(Calendar.MONTH);
			 
			return (diffMonth == 0)?1:diffMonth;
		} catch (GitLabApiException e) {
			return null;
		}
	}

	@Override
	public void addConnectionChangeEventListener(IRepositoryDataSourceListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeConnectionChangeEventListener(IRepositoryDataSourceListener listener) {
		listeners.remove(listener);
	}
}