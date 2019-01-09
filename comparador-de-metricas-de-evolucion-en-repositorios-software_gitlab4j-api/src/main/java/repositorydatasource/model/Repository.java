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
	private Integer id;
	
	/**
	 * Total number of issues.
	 */
	private Integer totalNumberOfIssues;
	
	/**
	 * Total number of commits.
	 */
	private Integer totalNumberOfCommits;
	
	/**
	 * Number of closed issues.
	 */
	private Integer numberOfClosedIssues;
	
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
	private Integer lifeSpanMonths;
	
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
	public Repository(String url, String name, Integer id, Integer totalNumberOfIssues, Integer totalNumberOfCommits,
			Integer numberOfClosedIssues, List<Integer> daysToCloseEachIssue, Set<Date> commitDates, Integer lifeSpanMonths) {
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
	public Integer getId() {
		return id;
	}
	
	/**
	 * @return Total number of issues.
	 */
	public Integer getTotalNumberOfIssues() {
		return totalNumberOfIssues;
	}
	
	/**
	 * @return Total number of commits.
	 */
	public Integer getTotalNumberOfCommits() {
		return totalNumberOfCommits;
	}
	
	/**
	 * @return Number of closed issues.
	 */
	public Integer getNumberOfClosedIssues() {
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
	public Integer getLifeSpanMonths() {
		return lifeSpanMonths;
	}
}
