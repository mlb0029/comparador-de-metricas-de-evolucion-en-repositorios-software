package repositorydatasource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repositorydatasource.model.Repository;

/**
 * @author migue
 *
 */
class GitLabRepositoryDataSourceTest {

	static GitLabRepositoryDataSource gitLabRepositoryDataSource;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		gitLabRepositoryDataSource = GitLabRepositoryDataSource.getGitLabRepositoryDataSource();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		gitLabRepositoryDataSource.disconnect();
		gitLabRepositoryDataSource = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		gitLabRepositoryDataSource.disconnect();
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect()}.
	 */
	@Test
	void testConnect() {
		assertTrue(gitLabRepositoryDataSource.getConnectionType() == EnumConnectionType.NOT_CONNECTED);
		int result = gitLabRepositoryDataSource.connect();
		assertTrue((result > 0 && gitLabRepositoryDataSource.getConnectionType() == EnumConnectionType.CONNECTED) || (result < 0 && gitLabRepositoryDataSource.getConnectionType() == EnumConnectionType.NOT_CONNECTED));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectSUsernamePassword() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 */
	@Test
	void testConnectToken() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetRepositoryWithPublicConnection() {
		Repository repository;
		repository = gitLabRepositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
		assertNull(repository);
		if(gitLabRepositoryDataSource.connect() > 0) {
			repository = gitLabRepositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
			assertNotNull(repository);
			assertEquals(6, repository.getTotalNumberOfIssues());
			assertEquals(35, repository.getTotalNumberOfCommits());
			assertEquals(6, repository.getNumberOfClosedIssues());
			assertEquals(6, repository.getDaysToCloseEachIssue().size());
			assertEquals(35, repository.getCommitDates().size());
			assertEquals(0, repository.getLifeSpanMonths());
		}else {
			repository = gitLabRepositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
			assertNull(repository);
		}
	}

}
