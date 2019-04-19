package repositorydatasource.factories;

import static org.junit.jupiter.api.Assertions.*;

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
	 * Test method for {@link repositorydatasource.GitLabRepositoyDataSourceFactory#getRepositoryDataSource()}.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testCreateRepositoryDataSource() {
		IRepositoryDataSourceFactory rdsf = new GitLabRepositoyDataSourceFactory();
		IRepositoryDataSource rds = rdsf.getRepositoryDataSource();
		assertTrue(rds != null && rds.getConnectionType() == EnumConnectionType.NOT_CONNECTED);
	}
}
