package repositorydatasource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.RepositoryDataSourceException;
import repositorydatasource.factories.GitLabRepositoyDataSourceFactory;
import repositorydatasource.factories.IRepositoryDaraSourceFactory;
import repositorydatasource.model.EnumConnectionType;

/**
 * @author migue
 *
 */
class GitLabRepositoryDataSourceTest {

	private static IRepositoryDataSource repositoryDataSource;
	private static IRepositoryDaraSourceFactory repositoryDataSourceFactory;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		repositoryDataSourceFactory = new GitLabRepositoyDataSourceFactory();
		//rds = GitLabRepositoryDataSource.getGitLabRepositoryDataSource();
		repositoryDataSource = repositoryDataSourceFactory.createRepositoryDataSource();
				
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		if (repositoryDataSource.getConnectionType() != EnumConnectionType.NOT_CONNECTED) repositoryDataSource.disconnect();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getGitLabRepositoryDataSource()}.
	 */
	@Test
	void testGetGitLabRepositoryDataSource() {
		assertTrue(repositoryDataSource == GitLabRepositoryDataSource.getGitLabRepositoryDataSource());
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect()}.
	 */
	@Test
	void testConnect() {
		try {
			repositoryDataSource.connect();
			assertEquals(EnumConnectionType.CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: testConnect when OK");
			//assertTrue(false);
		} catch (RepositoryDataSourceException e) {
			assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when there is connection error.");
		}
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectUserPasswordNull() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect(null, null);
		}, "Failed test: Connection with null user and null password.");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when there is connection error.");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectUserPasswordVoid() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect("", "");
		}, "Failed test: connect with username and password without specifying username and password.");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when there is connection error.");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectUserPasswordWrong() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect("a", "b");
		}, "Failed test: connection with wrong user and/or password");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when there is connection error.");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectUserPasswordOk() {
		fail("Not implemented for security reasons");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 */
	@Test
	void testConnectPivateTokenNull() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect(null);
		}, "Failed test: Connection with null private token.");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when there is connection error.");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 */
	@Test
	void testConnectPivateTokenVoid() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect("");
		}, "Failed test: connect with private token without specifying the token.");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when there is connection error.");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 */
	@Test
	void testConnectPivateTokenWrong() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect("¡WrongToken!");
		}, "Failed test: connection with wrong token");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when there is connection error.");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 */
	@Test
	void testConnectPivateTokenOK() {
		fail("Not implemented for security reasons");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#disconnect()}.
	 */
	@Test
	void testDisconnectFailed() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.disconnect();
		}, "Failed test: Disconnect without being connected.");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#disconnect()}.
	 */
	@Test
	void testDisconnectOK() {
		assertDoesNotThrow(() -> {
			repositoryDataSource.disconnect();
		}, "Failed test: Disconnect without being connected.");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Failed test: Indicates connection when it has been disconnected.");
	}
	
//	/**
//	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getConnectionType()}.
//	 */
//	@Test
//	void testGetConnectionType() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetRepositoryWhenDisconnected() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPrivateRepositoryWhenConnected() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPublicRepositoryWhenConnected() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testPrivateGetRepositoryWhenLogged() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPublicRepositoryWhenLogged() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetNonExistentRepositoryConnected() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetRepository() {
		fail("Not yet implemented");
	}
}
