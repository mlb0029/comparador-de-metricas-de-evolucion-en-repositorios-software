package repositorydatasource.model;

import java.util.Collection;
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
	private Collection<Integer> daysToCloseEachIssue;
	
	/**
	 * Dates of commits.
	 */
	private Collection<Date> commitDates;
	
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
	
	public Repository() {}

	/**
	 * Gets the url.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the name.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the id.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the totalNumberOfIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the totalNumberOfIssues
	 */
	public Integer getTotalNumberOfIssues() {
		return totalNumberOfIssues;
	}

	/**
	 * Gets the totalNumberOfCommits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the totalNumberOfCommits
	 */
	public Integer getTotalNumberOfCommits() {
		return totalNumberOfCommits;
	}

	/**
	 * Gets the numberOfClosedIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the numberOfClosedIssues
	 */
	public Integer getNumberOfClosedIssues() {
		return numberOfClosedIssues;
	}

	/**
	 * Gets the daysToCloseEachIssue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the daysToCloseEachIssue
	 */
	public Collection<Integer> getDaysToCloseEachIssue() {
		return daysToCloseEachIssue;
	}

	/**
	 * Gets the commitDates.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the commitDates
	 */
	public Collection<Date> getCommitDates() {
		return commitDates;
	}

	/**
	 * Gets the lifeSpanMonths.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the lifeSpanMonths
	 */
	public Integer getLifeSpanMonths() {
		return lifeSpanMonths;
	}

	/**
	 * Sets the url.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Sets the name.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the id.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Sets the totalNumberOfIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param totalNumberOfIssues the totalNumberOfIssues to set
	 */
	public void setTotalNumberOfIssues(Integer totalNumberOfIssues) {
		this.totalNumberOfIssues = totalNumberOfIssues;
	}

	/**
	 * Sets the totalNumberOfCommits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param totalNumberOfCommits the totalNumberOfCommits to set
	 */
	public void setTotalNumberOfCommits(Integer totalNumberOfCommits) {
		this.totalNumberOfCommits = totalNumberOfCommits;
	}

	/**
	 * Sets the numberOfClosedIssues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param numberOfClosedIssues the numberOfClosedIssues to set
	 */
	public void setNumberOfClosedIssues(Integer numberOfClosedIssues) {
		this.numberOfClosedIssues = numberOfClosedIssues;
	}

	/**
	 * Sets the setDaysToCloseEachIssue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param daysToCloseEachIssue the daysToCloseEachIssue to set
	 */
	public void setDaysToCloseEachIssue(List<Integer> daysToCloseEachIssue) {
		this.daysToCloseEachIssue = daysToCloseEachIssue;
	}

	/**
	 * Sets the commitDates.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param commitDates the commitDates to set
	 */
	public void setCommitDates(Set<Date> commitDates) {
		this.commitDates = commitDates;
	}

	/**
	 * Sets the lifeSpanMonths.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param lifeSpanMonths the lifeSpanMonths to set
	 */
	public void setLifeSpanMonths(Integer lifeSpanMonths) {
		this.lifeSpanMonths = lifeSpanMonths;
	};
}
