package repositorydatasource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.gitlab4j.api.Constants.IssueState;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.IssueFilter;
import org.gitlab4j.api.models.Project;

import exceptions.RepositoryDataSourceException;
import repositorydatasource.model.EnumConnectionType;
import repositorydatasource.model.Repository;
import util.Constants;

/**
 * @author migue
 *
 */
public class GitLabRepositoryDataSource implements IRepositoryDataSource {

	/**
	 * Single instance of the class.
	 */
	private static GitLabRepositoryDataSource gitLabRepositoryDataSource;

	/**
	 * Connection type.
	 */
	private EnumConnectionType connectionType;

	/**
	 * GitLab API for connection to gitlab.
	 */
	private GitLabApi gitLabApi;
	
	private static final Logger LOGGER = Logger.getLogger(GitLabRepositoryDataSource.class.getName());

	/**
	 * Constructor that returns a not connected gitlabrepositorydatasource.
	 */
	private GitLabRepositoryDataSource() {
		connectionType = EnumConnectionType.NOT_CONNECTED;
		gitLabApi = null;
	}
	
	/**
	 * Gets the single instance of the class.
	 * 
	 * @return A gitlab repository data source.
	 */
	public static GitLabRepositoryDataSource getGitLabRepositoryDataSource() {
		if (GitLabRepositoryDataSource.gitLabRepositoryDataSource == null) GitLabRepositoryDataSource.gitLabRepositoryDataSource = new GitLabRepositoryDataSource();
		return gitLabRepositoryDataSource;
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#connect()
	 */
	@Override
	public void connect() throws RepositoryDataSourceException {
			gitLabApi = new GitLabApi(Constants.HOST_URL, "");
			connectionType = EnumConnectionType.CONNECTED;
	}
	
	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#connect(java.lang.String, java.lang.String)
	 */
	@Override
	public void connect(String username, String password) throws RepositoryDataSourceException {
		try {
			if(username == null || username == "" || password == null || password == "") {
				throw new RepositoryDataSourceException("The user or password has not been specified");
			}
			gitLabApi = GitLabApi.oauth2Login(Constants.HOST_URL, username, password.toCharArray());
			connectionType = EnumConnectionType.LOGGED;
		} catch (GitLabApiException e) {
			LOGGER.log(Level.SEVERE, "Wrong username and / or password", e);
			throw new RepositoryDataSourceException("Wrong username and / or password or another error when connecting");
		} catch (RepositoryDataSourceException e) {
			LOGGER.log(Level.SEVERE, "The user or password has not been specified", e);
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#connect(java.lang.String)
	 */
	@Override
	public void connect(String token) throws RepositoryDataSourceException {
		try {
			if(token == null || token == "") {
				throw new RepositoryDataSourceException("No token specified");
			}
			gitLabApi = new GitLabApi(Constants.HOST_URL, token);
			gitLabApi.getUserApi().getCurrentUser();
			connectionType = EnumConnectionType.LOGGED;		
		} catch (RepositoryDataSourceException e) {
			LOGGER.log(Level.SEVERE, "Attempting to login without specifying a token", e);
			throw new RepositoryDataSourceException("Attempting to login without specifying a token");
		} catch (GitLabApiException e) {
			gitLabApi = null;
			connectionType = EnumConnectionType.NOT_CONNECTED;
			LOGGER.log(Level.SEVERE, "Attempting to login with a wrong token", e);
			throw new RepositoryDataSourceException("Attempting to login with a wrong token");
		}
	}

	/* (non-Javadoc)
	 * @see repositorydatasource.IRepositoryDataSource#disconnect()
	 */
	@Override
	public void disconnect() throws RepositoryDataSourceException {
		try {
			if(gitLabApi == null || connectionType == EnumConnectionType.NOT_CONNECTED) {
				throw new RepositoryDataSourceException("There is no connection.");
			}
			gitLabApi = null;
			connectionType = EnumConnectionType.NOT_CONNECTED;
		} catch (RepositoryDataSourceException e) {
			LOGGER.log(Level.SEVERE, "Error when connecting", e);
			throw new RepositoryDataSourceException("Error when connecting");
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
	 * @see repositorydatasource.IRepositoryDataSource#getRepository(java.lang.String)
	 */
	@Override
	public Repository getRepository(String repositoryURL) {
		if (connectionType == EnumConnectionType.NOT_CONNECTED) return null;
		Repository repo;
		int projectId = obtenerIDProyecto(repositoryURL);
		String name = getRepositoryName(projectId);
		int totalNumberOfIssues = getTotalNumberOfIssues(projectId);
		int totalNumberOfCommits = getTotalNumberOfCommits(projectId);
		int numberOfClosedIssues = getNumberOfClosedIssues(projectId);
		List<Integer> daysToCloseEachIssue = getDaysToCloseEachIssue(projectId);
		Set<Date> commitDates = getCommitsDates(projectId);
		int lifeSpanMonths = getRepositoryLifeInMonths(projectId);
		repo = new Repository(
				repositoryURL, 
				name, 
				projectId, 
				totalNumberOfIssues, 
				totalNumberOfCommits,
				numberOfClosedIssues,
				daysToCloseEachIssue,
				commitDates, 
				lifeSpanMonths);
		return repo;
	}
	
//	private Boolean testConnection() {
//		return ;
//	}
	
	/**
	 * Gets the ID of a project using the Project URL.
	 * 
	 * @param repositoryURL Project URL.
	 * @return ID of a project.
	 */
	private int obtenerIDProyecto(String repositoryURL) {
		try {
			Integer retorno = -1;
			String sProyecto = repositoryURL.replaceAll(Constants.HOST_URL + "/", "");
			String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
			String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
			Project pProyecto = gitLabApi.getProjectApi().getProject(propietarioYGrupo, nombreProyecto);
			retorno = pProyecto.getId();
			return retorno;
		} catch (GitLabApiException e) {
			return -1;
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
	private int getTotalNumberOfIssues(Integer projectId) {	
		try {
			Integer totalNumberOfIssues = 0;
			Pager<Issue> pager = this.gitLabApi.getIssuesApi().getIssues(projectId, 100);
			while( pager.hasNext()) {
				totalNumberOfIssues += pager.next().size();
			}
			return totalNumberOfIssues;
		} catch (GitLabApiException e) {
			return -1;
		}
	}

	/**
	 * Gets total number of commits of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Total number of commits of a project or -1 if fail.
	 */
	private int getTotalNumberOfCommits(Integer projectId) {
		try {
			Integer totalNumberOfCommits = 0;
			Pager<Commit> pager = this.gitLabApi.getCommitsApi().getCommits(projectId, 100);
			while( pager.hasNext()) {
				totalNumberOfCommits += pager.next().size();
			}
			return totalNumberOfCommits;
		} catch (GitLabApiException e) {
			return -1;
		}
	}

	/**
	 * Gets number of closed issues of a project.
	 * 
	 * @param projectId ID of the project.
	 * @return Number of closed issues of a project or -1 if fail.
	 */
	private int getNumberOfClosedIssues(Integer projectId) {
		try {
			Integer numberOfClosedIssues = 0;
			IssueFilter issueFilter = new IssueFilter();
			issueFilter.withState(IssueState.CLOSED);
			Pager<Issue> pager = this.gitLabApi.getIssuesApi().getIssues(projectId, issueFilter, 100);
			while( pager.hasNext()) {
				numberOfClosedIssues += pager.next().size();
			}
			return numberOfClosedIssues;
		} catch (GitLabApiException e) {
			return -1;
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
			List<Integer> daysToCloseEachIssue = new ArrayList<Integer>();
			IssueFilter issueFilter = new IssueFilter();
			issueFilter.withState(IssueState.CLOSED);
			Pager<Issue> pager = this.gitLabApi.getIssuesApi().getIssues(projectId, issueFilter, 100);
			while( pager.hasNext()) {
				for (Issue issue : pager.next()) {
					if (issue.getCreatedAt() != null && issue.getClosedAt() != null) {
						long openDate = issue.getCreatedAt().getTime();
						long closeDate = issue.getClosedAt().getTime();
						int daysToCloseThissIssue = (int) ((closeDate - openDate) / (1000 * 60 * 60 * 24));
						daysToCloseEachIssue.add(daysToCloseThissIssue);
					}
				}
			}
			return daysToCloseEachIssue;
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
			Set<Date> commitsDates = new HashSet<Date>();
			Pager<Commit> pager = this.gitLabApi.getCommitsApi().getCommits(projectId, 100);
			while( pager.hasNext()) {
				commitsDates.addAll(pager.next().stream().map(commit -> commit.getCommittedDate()).collect(Collectors.toList()));
			}
			return commitsDates;
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
	 * until the date of last activity or -1 if fail.
	 */
	private int getRepositoryLifeInMonths(Integer projectId) {
		try {
			Date createdAtDate = gitLabApi.getProjectApi().getProject(projectId).getCreatedAt();
			Date lastActivityDate = gitLabApi.getProjectApi().getProject(projectId).getLastActivityAt();
			Calendar createdAtCalendar = new GregorianCalendar();
			createdAtCalendar.setTime(createdAtDate);
			Calendar lastActivityCalendar = new GregorianCalendar();
			lastActivityCalendar.setTime(lastActivityDate);
			
			int diffYear = lastActivityCalendar.get(Calendar.YEAR) - createdAtCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + lastActivityCalendar.get(Calendar.MONTH) - createdAtCalendar.get(Calendar.MONTH);
			 
			return diffMonth;
		} catch (GitLabApiException e) {
			return -1;
		}
	}
}
