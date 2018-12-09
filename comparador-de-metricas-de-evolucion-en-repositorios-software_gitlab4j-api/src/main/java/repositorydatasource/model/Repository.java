package repositorydatasource.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * A repository data class.
 * 
 * @author MALB
 *
 */
public class Repository {
	/**
	 * URL.
	 */
	private String url;
	
	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * ID.
	 */
	private int id;
	
	/**
	 * Total number of issues.
	 */
	private int totalNumberOfIssues;
	
	/**
	 * Total number of commits.
	 */
	private int totalNumberOfCommits;
	
	/**
	 * Number of closed issues.
	 */
	private int numberOfClosedIssues;
	
	/**
	 * Days to close each issue.
	 */
	private List<Integer> daysToCloseEachIssue;
	
	/**
	 * Dates of commits.
	 */
	private Set<Date> commitDates;
	
	/**
	 * Number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 */
	private int lifeSpanMonths;
	
	/**
	 * @param url URL.
	 * @param name Name.
	 * @param id ID.
	 * @param totalNumberOfIssues Total number of issues.
	 * @param totalNumberOfCommits Total number of commits.
	 * @param numberOfClosedIssues Number of closed issues.
	 * @param daysToCloseEachIssue Days to close each issue.
	 * @param commitDates Dates of commits.
	 * @param lifeSpanMonths Number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 */
	public Repository(String url, String name, int id, int totalNumberOfIssues, int totalNumberOfCommits,
			int numberOfClosedIssues, List<Integer> daysToCloseEachIssue, Set<Date> commitDates, int lifeSpanMonths) {
		this.url = url;
		this.name = name;
		this.id = id;
		this.totalNumberOfIssues = totalNumberOfIssues;
		this.totalNumberOfCommits = totalNumberOfCommits;
		this.numberOfClosedIssues = numberOfClosedIssues;
		this.daysToCloseEachIssue = daysToCloseEachIssue;
		this.commitDates = commitDates;
		this.lifeSpanMonths = lifeSpanMonths;
	}
	
	/**
	 * @return URL.
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @return Name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return ID.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return Total number of issues.
	 */
	public int getTotalNumberOfIssues() {
		return totalNumberOfIssues;
	}
	
	/**
	 * @return Total number of commits.
	 */
	public int getTotalNumberOfCommits() {
		return totalNumberOfCommits;
	}
	
	/**
	 * @return Number of closed issues.
	 */
	public int getNumberOfClosedIssues() {
		return numberOfClosedIssues;
	}
	
	/**
	 * @return Days to close each issue.
	 */
	public List<Integer> getDaysToCloseEachIssue() {
		return daysToCloseEachIssue;
	}
	
	/**
	 * @return Dates of commits.
	 */
	public Set<Date> getCommitDates() {
		return commitDates;
	}
	
	/**
	 * @return Number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 */
	public int getLifeSpanMonths() {
		return lifeSpanMonths;
	}
}
