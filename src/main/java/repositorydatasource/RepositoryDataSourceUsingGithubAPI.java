package repositorydatasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


//import org.gitlab4j.api.GitLabApi;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;

import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;


//import org.gitlab4j.api.GitLabApi;
//import org.gitlab4j.api.GitLabApiException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import datamodel.Repository;
//import datamodel.RepositoryInternalMetrics;
//import datamodel.User;
import datamodel.*;

import exceptions.RepositoryDataSourceException;
import repositorydatasource.RepositoryDataSource.EnumConnectionType;

public class RepositoryDataSourceUsingGithubAPI implements RepositoryDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5228642659883160983L;

	/**
	 * 
	 */


	/**
	 * Single instance of the class.
	 */
	private static RepositoryDataSourceUsingGithubAPI instance;

	/**
	 * Connection type.
	 */
	private EnumConnectionType connectionType;

	/**
	 * API that helps to connect to Github.
	 * 
	 * @see GithubApi
	 */
	private GitHubClient githubclientApi;
	private UserService userService;
	private CommitService commitService;
	private IssueService issueService;
	private RepositoryService repositoryService;

	/**
	 * Current user.
	 * 
	 */
	private datamodel.User currentUser;

	
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryDataSourceUsingGithubAPI.class);

	private static final String HOST_URL = "https://github.com";

	/**
	 * Constructor that returns a not connected githubrepositorydatasource.
	 */
	private RepositoryDataSourceUsingGithubAPI() {
		connectionType = EnumConnectionType.NOT_CONNECTED;
		githubclientApi = null;
		currentUser = null;
	}

	/**
	 * Gets the single instance of the class.
	 * 
	 * @return A github repository data source.
	 */
	public static RepositoryDataSourceUsingGithubAPI getGithubRepositoryDataSource() {
		if (RepositoryDataSourceUsingGithubAPI.instance == null)
			RepositoryDataSourceUsingGithubAPI.instance = new RepositoryDataSourceUsingGithubAPI();
		return instance;
	}

	@Override
	public void connect() throws RepositoryDataSourceException {
		if (connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
			githubclientApi = new GitHubClient();
			currentUser = null;
			connectionType = EnumConnectionType.CONNECTED;
			LOGGER.info("Established connection with GitLab");
		} else {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_CONNECTED);
		}

	}

	@Override
	public void connect(String username, String password) throws RepositoryDataSourceException {
		try {
			if (username == null || password == null || username.isBlank() || password.isBlank())
				throw new RepositoryDataSourceException(RepositoryDataSourceException.LOGIN_ERROR);
			if (connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
				githubclientApi = new GitHubClient();
				githubclientApi.setCredentials(username, password);
				userService = new UserService(githubclientApi);
				currentUser = getCurrentUser(userService.getUser());
				connectionType = EnumConnectionType.LOGGED;
				LOGGER.info("Login to Github");
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_CONNECTED);
			}
		} catch (IOException e) {
			reset();
			throw new RepositoryDataSourceException(RepositoryDataSourceException.LOGIN_ERROR);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void connect(String token) throws RepositoryDataSourceException {
		try {
			if (connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
				githubclientApi = new GitHubClient();
				githubclientApi.setOAuth2Token(token);
				userService = new UserService(githubclientApi);
				currentUser = getCurrentUser(userService.getUser());
				connectionType = EnumConnectionType.LOGGED;
				LOGGER.info("Login to Github");
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_CONNECTED);
			}
		} catch (IOException e) {
			reset();
			throw new RepositoryDataSourceException(RepositoryDataSourceException.LOGIN_ERROR);
		} catch (RepositoryDataSourceException e) {
			throw e;
		}

	}

	@Override
	public void disconnect() throws RepositoryDataSourceException {
		if (connectionType != EnumConnectionType.NOT_CONNECTED) {
			reset();
		} else {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.ALREADY_DISCONNECTED);
		}

	}

	/**
	 * Reset the connection.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void reset() {
		connectionType = EnumConnectionType.NOT_CONNECTED;
		githubclientApi = null;
		currentUser = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repositorydatasource.IRepositoryDataSource#getConnectionType()
	 */
	@Override
	public EnumConnectionType getConnectionType() {
		return connectionType;
	}

	/**
	 * Obtains a user from the github user.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @author Carlos López Nozal - clopezno
	 * @return
	 */
	@Override
	public datamodel.User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Obtains a user from the github user.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @author Carlos López Nozal - clopezno
	 * @param githubUser
	 * @return
	 */
	private datamodel.User getCurrentUser(org.eclipse.egit.github.core.User githubUser) {
		return new datamodel.User(githubUser.getId(), githubUser.getAvatarUrl(), githubUser.getEmail(),
				githubUser.getName(),
				// gitlabUser.getUsername()
				githubUser.getLogin()

		);
	}

	@Override
	public Collection<datamodel.Repository> getCurrentUserRepositories() throws RepositoryDataSourceException {

		try {
			Collection<datamodel.Repository> resultrepositories = new ArrayList<datamodel.Repository>();
			if (connectionType != EnumConnectionType.NOT_CONNECTED) {
				repositoryService = new RepositoryService(githubclientApi);
				List<org.eclipse.egit.github.core.Repository> lRepositories = repositoryService
						.getRepositories(currentUser.getUsername());

				for (org.eclipse.egit.github.core.Repository repo : lRepositories) {
					resultrepositories
							.add(new datamodel.Repository(repo.getHtmlUrl(), repo.getName(), (int) repo.getId()));
				}
				return resultrepositories;
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.NOT_CONNECTED);
			}
		} catch (IOException e) {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.REPOSITORY_NOT_FOUND);
		}
	}

	@Override
	public Collection<datamodel.Repository> getAllUserRepositories(String userIdOrUsername)
			throws RepositoryDataSourceException {
		Collection<datamodel.Repository> repositories;
		try {
			if (currentUser != null && currentUser.getUsername().equals(userIdOrUsername)) {
				repositories = getCurrentUserRepositories();
			} else if (!connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
				repositoryService = new RepositoryService(githubclientApi);
				List<org.eclipse.egit.github.core.Repository> lRepositories = repositoryService
						.getRepositories(userIdOrUsername);
				Collection<datamodel.Repository> resultrepositories = new ArrayList<datamodel.Repository>();
				for (org.eclipse.egit.github.core.Repository repo : lRepositories) {
					resultrepositories
							.add(new datamodel.Repository(repo.getHtmlUrl(), repo.getName(), (int) repo.getId()));
				}
				return resultrepositories;
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.NOT_CONNECTED);
			}
			return repositories;
		} catch (RepositoryDataSourceException e) {
			throw e;
		} catch (IOException e) {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.USER_NOT_FOUND);
		}
	}

	@Override
	public Collection<datamodel.Repository> getAllGroupRepositories(String groupName)
			throws RepositoryDataSourceException {
		// API github for group and user work same way  
		return getAllUserRepositories(groupName);
	}
		

	@Override
	public datamodel.Repository getRepository(String repositoryHTTPSURL) throws RepositoryDataSourceException {
		Integer projectId;
		if (!connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
			projectId = getProjectId(repositoryHTTPSURL);
			String projectName = getRepositoryName(repositoryHTTPSURL);
			if (projectId != null) {
				return new datamodel.Repository(repositoryHTTPSURL, projectName, projectId);
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.REPOSITORY_NOT_FOUND);
			}
		} else {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.NOT_CONNECTED);
		}
	}

	/**
	 * Gets the ID of a project using the Project URL.
	 * 
	 * @param repositoryURL Project URL.
	 * @return ID of a project.
	 */
	private Integer getProjectId(String repositoryURL) {
		try {
			if (repositoryURL == null)
				return null;
			repositoryService = new RepositoryService(githubclientApi);
			String sProyecto = repositoryURL.replaceAll(RepositoryDataSourceUsingGithubAPI.HOST_URL + "/", "");
			String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
			String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
			Repository pProyecto = repositoryService.getRepository(propietarioYGrupo, nombreProyecto);
			return (int) pProyecto.getId();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the repository name specifying the project ID.
	 * 
	 * @param projectId ID of the project.
	 * @return Repository name or null if fail.
	 */
	private String getRepositoryName(String repositoryURL) {
		try {
			if (repositoryURL == null)
				return null;
			repositoryService = new RepositoryService(githubclientApi);
			String sProyecto = repositoryURL.replaceAll(RepositoryDataSourceUsingGithubAPI.HOST_URL + "/", "");
			String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
			String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
			Repository pProyecto = repositoryService.getRepository(propietarioYGrupo, nombreProyecto);
			return pProyecto.getName();

		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public datamodel.Repository getRepository(int repositoryId) throws RepositoryDataSourceException {
		try {
			if (!connectionType.equals(EnumConnectionType.NOT_CONNECTED)) {
				// TODO Con el Gihub API no se puede obtener el RepositorioID.
				// El ID es un dato calculado que se obtiene con la Fabrica RepositoryID

				RepositoryId repositoryID = RepositoryId.create("owner", "repository");
				repositoryService = new RepositoryService(githubclientApi);
				Repository repository = repositoryService.getRepository(repositoryID);

				// Project pProyecto = gitLabApi.getProjectApi().getProject(repositoryId);
				if (repository != null) {
					return new datamodel.Repository(repository.getUrl(), repository.getName(), repositoryId);
				} else {
					throw new RepositoryDataSourceException(RepositoryDataSourceException.REPOSITORY_NOT_FOUND);
				}
			} else {
				throw new RepositoryDataSourceException(RepositoryDataSourceException.NOT_CONNECTED);
			}
		} catch (IOException e) {
			throw new RepositoryDataSourceException(RepositoryDataSourceException.REPOSITORY_NOT_FOUND);
		}
	}

	@Override
	public RepositoryInternalMetrics getRepositoryInternalMetrics(datamodel.Repository repository)
			throws RepositoryDataSourceException {
		repositoryService = new RepositoryService(githubclientApi);

		String repositoryURL = repository.getUrl();
		String sProyecto = repositoryURL.replaceAll(RepositoryDataSourceUsingGithubAPI.HOST_URL + "/", "");
		String srepositoryName = sProyecto.split("/")[sProyecto.split("/").length - 1];
		String sowner = sProyecto.replaceAll("/" + srepositoryName, "");

		RepositoryId projectId = RepositoryId.create(sowner, srepositoryName);
		Integer totalNumberOfIssues = getTotalNumberOfIssues(projectId);
		Integer totalNumberOfCommits = getTotalNumberOfCommits(projectId);
		Integer numberOfClosedIssues = getNumberOfClosedIssues(projectId);
		List<Integer> daysToCloseEachIssue = getDaysToCloseEachIssue(projectId);
		Set<Date> commitDates = getCommitsDates(projectId);
		Integer lifeSpanMonths = getRepositoryLifeInMonths(projectId);
		RepositoryInternalMetrics repositoryInternalMetrics = new RepositoryInternalMetrics(totalNumberOfIssues,
				totalNumberOfCommits, numberOfClosedIssues, daysToCloseEachIssue, commitDates, lifeSpanMonths);
		return repositoryInternalMetrics;
	}

	/**
	 * Gets total number of issues of a project specifying the project ID.
	 * 
	 * @param projectId ID of the project.
	 * @return Total number of issues.
	 *
	 * @throws IOException
	 */
	private Integer getTotalNumberOfIssues(RepositoryId repoId) {
		try {
			issueService = new IssueService(githubclientApi);

			Map<String, String> filtro = new HashMap<String, String>();
			filtro.put("state", "all");

			return this.issueService.getIssues(repoId, filtro).size();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets total number of closed issues of a project specifying the project ID.
	 * 
	 * @param projectId ID of the project.
	 * @return Total number of issues.
	 *
	 * @throws IOException
	 */
	private Integer getNumberOfClosedIssues(RepositoryId repoId) {
		try {
			issueService = new IssueService(githubclientApi);

			Map<String, String> filtro = new HashMap<String, String>();
			filtro.put("state", "closed");

			return this.issueService.getIssues(repoId, filtro).size();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets total number of commits of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Total number of commits of a project.
	 */
	private Integer getTotalNumberOfCommits(RepositoryId repoId) {
		try {
			commitService = new CommitService(githubclientApi);
			return commitService.getCommits(repoId).size();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets days to close each issue of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Days to close each issue of a project or null if fail.
	 * @throws IOException
	 */
	private List<Integer> getDaysToCloseEachIssue(RepositoryId repoId) {
		try {
			issueService = new IssueService(githubclientApi);
			Map<String, String> filtro = new HashMap<String, String>();
			filtro.put("state", "closed");
			List<Issue> lissues = issueService.getIssues(repoId, filtro);
			List<Integer> ldaystoclose = new ArrayList<Integer>();

			for (Issue issue : lissues) {
				ldaystoclose.add((int)(issue.getClosedAt().getTime() - issue.getCreatedAt().getTime()));
			}
			return ldaystoclose;
		} catch (IOException e) {
			return null;
		}

	}

	/**
	 * Gets dates of commits of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Set of dates of commits of a project or null if fail.
	 * @throws IOException
	 */
	private Set<Date> getCommitsDates(RepositoryId repoId) {

		try {
			commitService = new CommitService(githubclientApi);
			List<RepositoryCommit> lcommits;
			lcommits = commitService.getCommits(repoId);

			Set<Date> sdates = new HashSet<Date>();

			for (int i = 0; i < lcommits.size() - 1; i++) {
				sdates.add(new Date(lcommits.get(i).getCommit().getAuthor().getDate().getTime()));
			}
			return sdates;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Computes the number of months that have passed since the creation of the
	 * repository until the date of last activity.
	 * 
	 * @param projectId ID of the project.
	 * @return Number of months that have passed since the creation of the
	 *         repository until the date of last activity.
	 */
	private Integer getRepositoryLifeInMonths(RepositoryId projectId) {
		try {
			repositoryService = new RepositoryService(githubclientApi);
			Date createdAtDate = repositoryService.getRepository(projectId).getCreatedAt();
			Date lastActivityDate = repositoryService.getRepository(projectId).getUpdatedAt();

			if (createdAtDate == null || lastActivityDate == null)
				return null;

			Calendar createdAtCalendar = new GregorianCalendar();
			createdAtCalendar.setTime(createdAtDate);
			Calendar lastActivityCalendar = new GregorianCalendar();
			lastActivityCalendar.setTime(lastActivityDate);

			int diffYear = lastActivityCalendar.get(Calendar.YEAR) - createdAtCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + lastActivityCalendar.get(Calendar.MONTH)
					- createdAtCalendar.get(Calendar.MONTH);

			return (diffMonth == 0) ? 1 : diffMonth;
		} catch (IOException e) {
			return null;
		}
	}

}
