package repositorydatasource.model;

import java.util.Collection;
import java.util.Date;

/**
 * A repository data class.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class Repository {
	
	/*Data that identify the repository.*/
	
	/**
	 * HTTPS URL from the repository.
	 */
	private String url = null;
	
	/**
	 * Name of the repository.
	 */
	private String name = null;
	
	/**
	 * ID of the repository.
	 */
	private Integer id = null;
	
	/*Metrics that are obtained directly from the repository.*/
	
	/**
	 * Total number of issues.
	 */
	private Integer totalNumberOfIssues = null;
	
	/**
	 * Total number of commits.
	 */
	private Integer totalNumberOfCommits = null;
	
	/**
	 * Number of closed issues.
	 */
	private Integer numberOfClosedIssues = null;
	
	/**
	 * Days to close each issue.
	 */
	private Collection<Integer> daysToCloseEachIssue = null;
	
	/**
	 * Dates of commits.
	 */
	private Collection<Date> commitDates = null;
	
	/**
	 * Number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 */
	private Integer lifeSpanMonths = null;
	
	/*CONSTRUCTORS*/
	
	/**
	 * Constructor that defines the repository, without specifying the metrics that are obtained from it.
	 * 
	 * @param url HTTPS URL from the repository
	 * @param name Name of the repository
	 * @param id  ID of the repository
	 */
	public Repository(String url, String name, Integer id) {
		this.url = url;
		this.name = name;
		this.id = id;
	}

	/*GETTERS AND SETTERS*/
	
	/**
	 * Gets the HTTPS URL from the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the HTTPS URL from the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the name of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the ID of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the ID of the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the Total number of issues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the totalNumberOfIssues
	 */
	public Integer getTotalNumberOfIssues() {
		return totalNumberOfIssues;
	}

	/**
	 * Sets the Total number of issues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param totalNumberOfIssues the totalNumberOfIssues to set
	 */
	public void setTotalNumberOfIssues(Integer totalNumberOfIssues) {
		this.totalNumberOfIssues = totalNumberOfIssues;
	}

	/**
	 * Gets the Total number of commits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the totalNumberOfCommits
	 */
	public Integer getTotalNumberOfCommits() {
		return totalNumberOfCommits;
	}

	/**
	 * Sets the Total number of commits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param totalNumberOfCommits the totalNumberOfCommits to set
	 */
	public void setTotalNumberOfCommits(Integer totalNumberOfCommits) {
		this.totalNumberOfCommits = totalNumberOfCommits;
	}

	/**
	 * Gets the Number of closed issues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the numberOfClosedIssues
	 */
	public Integer getNumberOfClosedIssues() {
		return numberOfClosedIssues;
	}

	/**
	 * Sets the Number of closed issues.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param numberOfClosedIssues the numberOfClosedIssues to set
	 */
	public void setNumberOfClosedIssues(Integer numberOfClosedIssues) {
		this.numberOfClosedIssues = numberOfClosedIssues;
	}

	/**
	 * Gets the Days to close each issue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the daysToCloseEachIssue
	 */
	public Collection<Integer> getDaysToCloseEachIssue() {
		return daysToCloseEachIssue;
	}

	/**
	 * Sets the Days to close each issue.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param daysToCloseEachIssue the daysToCloseEachIssue to set
	 */
	public void setDaysToCloseEachIssue(Collection<Integer> daysToCloseEachIssue) {
		this.daysToCloseEachIssue = daysToCloseEachIssue;
	}

	/**
	 * Gets the Dates of commits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the commitDates
	 */
	public Collection<Date> getCommitDates() {
		return commitDates;
	}

	/**
	 * Sets the Dates of commits.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param commitDates the commitDates to set
	 */
	public void setCommitDates(Collection<Date> commitDates) {
		this.commitDates = commitDates;
	}

	/**
	 * Gets the Number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the lifeSpanMonths
	 */
	public Integer getLifeSpanMonths() {
		return lifeSpanMonths;
	}

	/**
	 * Sets the Number of months that have passed since the creation of the repository
	 * until the date of last activity.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param lifeSpanMonths the lifeSpanMonths to set
	 */
	public void setLifeSpanMonths(Integer lifeSpanMonths) {
		this.lifeSpanMonths = lifeSpanMonths;
	}
}
