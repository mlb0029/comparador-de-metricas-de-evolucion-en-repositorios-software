package repositorydatasource;

import static org.junit.jupiter.api.Assertions.*;

import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;

import repositorydatasource.factories.*;
import repositorydatasource.model.EnumConnectionType;

/**
 * Test for GitLabRepositoyDataSourceFactory.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
class GitLabRepositoyDataSourceFactoryTest {
	
	/**
	 * Test method for {@link repositorydatasource.factories.GitLabRepositoyDataSourceFactory#createRepositoryDataSource()}.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	void testCreateRepositoryDataSource() {
		try {
			IRepositoryDataSourceFactory rdsf = new GitLabRepositoyDataSourceFactory();
			IRepositoryDataSource rds = rdsf.createRepositoryDataSource();
			assertTrue(rds != null && rds.getConnectionType() == EnumConnectionType.NOT_CONNECTED);
		} catch (GitLabApiException e) {
			fail("Exception when creating a GitLabRepositoyDataSourceFactory. ");
		}
	}
}
