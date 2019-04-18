package repositorydatasource.factories;

import static org.junit.jupiter.api.Assertions.*;

import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;

import repositorydatasource.GitLabRepositoyDataSourceFactory;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource.EnumConnectionType;
import repositorydatasource.IRepositoryDataSourceFactory;

/**
 * Test for GitLabRepositoyDataSourceFactory.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class GitLabRepositoyDataSourceFactoryTest {
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoyDataSourceFactory#createRepositoryDataSource()}.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testCreateRepositoryDataSource() {
		try {
			IRepositoryDataSourceFactory rdsf = new GitLabRepositoyDataSourceFactory();
			IRepositoryDataSource rds = rdsf.createRepositoryDataSource();
			assertTrue(rds != null && rds.getConnectionType() == EnumConnectionType.NOT_CONNECTED);
		} catch (GitLabApiException e) {
			fail("Exception when creating a GitLabRepositoyDataSourceFactory");
		}
	}
}
