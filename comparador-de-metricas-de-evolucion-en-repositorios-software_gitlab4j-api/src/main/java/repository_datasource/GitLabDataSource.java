package repository_datasource;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.gitlab4j.api.Constants.IssueState;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.IssueFilter;
import org.gitlab4j.api.models.Project;

import util.Constants;

/**
 * Gets data from GitLab.
 * 
 * @author MALB
 * @since 01/12/2018
 */
public class GitLabDataSource implements IRepositoryDataSource {

	/**
	 * Sinle instance of the class.
	 */
	private static GitLabDataSource gitLabDataSource;

	/**
	 * Type of login.
	 */
	private EnumLoginType loginType;

	/**
	 * Java GitLab API: gitlab4j
	 */
	private GitLabApi gitLabApi;

	/**
	 * Constructor that establishes a connection with gitlab api.
	 * 
	 * @param loginType Autenticada o sin autenticar.
	 * @param args Arguments to login.
	 */
	private GitLabDataSource(EnumLoginType loginType, String[] args) {
		try {
			this.loginType = loginType;
			switch (loginType) {
			case NO_LOGIN:
				this.gitLabApi = new GitLabApi(Constants.HOST_URL, "");
				break;
			case USER_PASSWORD:
					this.gitLabApi = GitLabApi.oauth2Login(Constants.HOST_URL, args[0], args[1].toCharArray());
				break;
			case TOKEN:
				this.gitLabApi = new GitLabApi(Constants.HOST_URL, args[0]);
				break;
			default:
				break;
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the ID of a project using the Project URL.
	 * 
	 * @param projectURL Project URL.
	 * @return ID of a project.
	 */
	private Integer obtenerIDProyecto(String projectURL) {
		Integer retorno = -1;
		try {
			String sProyecto = projectURL.replaceAll(Constants.HOST_URL + "/", "");
			String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
			String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
			Project pProyecto;
			pProyecto = gitLabApi.getProjectApi().getProject(propietarioYGrupo, nombreProyecto);
			retorno = pProyecto.getId();
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public static GitLabDataSource getGitLabDataSource(EnumLoginType loginType, String[] args) {
		gitLabDataSource = new GitLabDataSource(loginType, args);
		return gitLabDataSource;
	}

	/**
	 * Gets the type of login.
	 * 
	 * @return Type of login.
	 */
	public EnumLoginType getLoginType() {
		return loginType;
	}

	/* (non-Javadoc)
	 * @see repository_datasource.IRepositoryDataSource#getTotalNumberOfIssues(java.lang.String)
	 */
	@Override
	public Integer getTotalNumberOfIssues(String projectURL) {	
		Integer totalNumberOfIssues = 0;
		try {
			Integer projectId = obtenerIDProyecto(projectURL);
			Pager<Issue> pager = this.gitLabApi.getIssuesApi().getIssues(projectId, 100);
			while( pager.hasNext()) {
				totalNumberOfIssues += pager.next().size();
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalNumberOfIssues;
	}

	/* (non-Javadoc)
	 * @see repository_datasource.IRepositoryDataSource#getTotalNumberOfCommits(java.lang.String)
	 */
	@Override
	public Integer getTotalNumberOfCommits(String projectURL) {
		Integer totalNumberOfCommits = 0;
		try {
			Integer projectId = obtenerIDProyecto(projectURL);
			Pager<Commit> pager = this.gitLabApi.getCommitsApi().getCommits(projectId, 100);
			while( pager.hasNext()) {
				totalNumberOfCommits += pager.next().size();
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalNumberOfCommits;
	}

	/* (non-Javadoc)
	 * @see repository_datasource.IRepositoryDataSource#getNumberOfClosedIssues(java.lang.String)
	 */
	@Override
	public Integer getNumberOfClosedIssues(String projectURL) {
		Integer numberOfClosedIssues = 0;
		try {
			Integer projectId = obtenerIDProyecto(projectURL);
			IssueFilter issueFilter = new IssueFilter();
			issueFilter.withState(IssueState.CLOSED);
			Pager<Issue> pager = this.gitLabApi.getIssuesApi().getIssues(projectId, issueFilter, 100);
			while( pager.hasNext()) {
				numberOfClosedIssues += pager.next().size();
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfClosedIssues;
	}

	/* (non-Javadoc)
	 * @see repository_datasource.IRepositoryDataSource#getDaysToCloseEachIssue(java.lang.String)
	 */
	@Override
	public List<Integer> getDaysToCloseEachIssue(String projectURL) {
		List<Integer> daysToCloseEachIssue = new ArrayList<Integer>();
		try {
			Integer projectId = obtenerIDProyecto(projectURL);
			IssueFilter issueFilter = new IssueFilter();
			issueFilter.withState(IssueState.CLOSED);
			Pager<Issue> pager = this.gitLabApi.getIssuesApi().getIssues(projectId, issueFilter, 100);
			while( pager.hasNext()) {
				for (Issue issue : pager.next()) {
					long openDate = issue.getCreatedAt().getTime();
					long closeDate = issue.getClosedAt().getTime();
					int daysToCloseThissIssue = (int) ((closeDate - openDate) / (86400000));
					daysToCloseEachIssue.add(daysToCloseThissIssue);
				}
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return daysToCloseEachIssue;
	}

	/* (non-Javadoc)
	 * @see repository_datasource.IRepositoryDataSource#getCommitsDates(java.lang.String)
	 */
	@Override
	public List<Date> getCommitsDates(String projectURL) {
		List<Date> commitsDates = new ArrayList<Date>();
		try {
			Integer projectId = obtenerIDProyecto(projectURL);
			Pager<Commit> pager = this.gitLabApi.getCommitsApi().getCommits(projectId, 100);
			while( pager.hasNext()) {
				commitsDates.addAll(pager.next().stream().map(commit -> commit.getCommittedDate()).collect(Collectors.toList()));
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commitsDates;
	}
}
