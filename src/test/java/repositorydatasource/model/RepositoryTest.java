package repositorydatasource.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import repositorydatasource.model.Repository;

/**
 * Test class forn {@link repositorydatasource.model.Repository}
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoryTest {

	/**
	 * Test method for {@link repositorydatasource.model.Repository#Repository(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.List, java.util.Set, java.lang.Integer)}
	 * with nullv values.
	 */
	@Test
	public void testRepositoryNull() {
		Repository repo = new Repository(null, null, null, null, null, null, null, null, null);
		assertNull(repo.getUrl(), "Fail in null url");
		assertNull(repo.getName(), "Fail in null name");
		assertNull(repo.getId(), "Fail in id");
		assertNull(repo.getTotalNumberOfIssues(), "Fail in null total number of issues");
		assertNull(repo.getTotalNumberOfCommits(), "Fail in null total number of commits");
		assertNull(repo.getNumberOfClosedIssues(), "Fail in null number of closed issues");
		assertNull(repo.getDaysToCloseEachIssue(), "Fail in null days to close each issue");
		assertNull(repo.getCommitDates(), "Fail in null commit dates");
		assertNull(repo.getLifeSpanMonths(), "Fail in null lifespan months");
	}
	
	/**
	 * Test method for {@link repositorydatasource.model.Repository#Repository(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.List, java.util.Set, java.lang.Integer)}
	 * with values.
	 */
	@Test
	public void testRepositoryEmptyStrings() {
		Repository repo = new Repository("", "", 0, 0, 0, 0, new ArrayList<Integer>(), new HashSet<Date>(), 0);
		assertEquals("", repo.getUrl(), "Fail in url");
		assertEquals("", repo.getName(), "Fail in name");
		assertEquals(0, repo.getId().intValue(), "Fail in id");
		assertEquals(0, repo.getTotalNumberOfIssues().intValue(), "Fail in total number of issues");
		assertEquals(0, repo.getTotalNumberOfCommits().intValue(), "Fail in total number of commits");
		assertEquals(0, repo.getNumberOfClosedIssues().intValue(), "Fail in number of closed issues");
		assertEquals(new ArrayList<Integer>(), repo.getDaysToCloseEachIssue(), "Fail in days to close each issue");
		assertEquals(new HashSet<Date>(), repo.getCommitDates(), "Fail in commit dates");
		assertEquals(0, repo.getLifeSpanMonths().intValue(), "Fail in lifespan months");
	}

}
