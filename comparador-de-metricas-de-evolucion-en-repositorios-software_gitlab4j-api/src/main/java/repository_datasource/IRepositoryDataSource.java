package repository_datasource;

import java.util.Date;
import java.util.List;

/**
 * Allows to obtain data from a repository.
 * 
 * @author MALB.
 * @since 01/12/2018
 *
 */
public interface IRepositoryDataSource {
	
	/**
	 * Gets total number of issues of a repository.
	 * 
	 * @param projectURL URL of the project.
	 * @return Total number of issues of a repository.
	 */
	Integer getTotalNumberOfIssues(String projectURL);
	
	/**
	 * Gets total number of commits of a repository.
	 * 
	 * @param projectURL URL of the project.
	 * @return Total number of commits of a repository.
	 */
	Integer getTotalNumberOfCommits(String projectURL);
	
	/**
	 * Gets number of closed issues of a repository.
	 * 
	 * @param projectURL URL of the project.
	 * @return Number of closed issues of a repository.
	 */
	Integer getNumberOfClosedIssues(String projectURL);
	
	/**
	 * Obtain, for each issue, the number of days that remained open.
	 * 
	 * @param projectURL URL of the project.
	 * @return List of the number of days that remained open each issue.
	 */
	List<Integer> getDaysToCloseEachIssue(String projectURL);
	
	/**
	 * Get the dates of each commit.
	 * 
	 * @param projectURL URL of the project.
	 * @return List of dates of each commit.
	 */
	List<Date> getCommitsDates(String projectURL);
	
}
