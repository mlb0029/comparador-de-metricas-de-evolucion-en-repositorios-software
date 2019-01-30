package repositorydatasource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import exceptions.RepositoryDataSourceException;
import util.Constants;
import repositorydatasource.factories.GitLabRepositoyDataSourceFactory;
import repositorydatasource.factories.IRepositoryDataSourceFactory;
import repositorydatasource.model.EnumConnectionType;
import repositorydatasource.model.Repository;

/**
 * @author migue
 *
 */
class GitLabRepositoryDataSourceTest {

	private static IRepositoryDataSource repositoryDataSource;
	private static IRepositoryDataSourceFactory repositoryDataSourceFactory;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		repositoryDataSourceFactory = new GitLabRepositoyDataSourceFactory();
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
		assertTrue(repositoryDataSource == GitLabRepositoryDataSource.getGitLabRepositoryDataSource(), getErrorMsg("testGetGitLabRepositoryDataSource", "Only one instance of repositoryDataSource is allowed"));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect()}.
	 */
	@Test
	void testConnect_OK() {
		assertDoesNotThrow(() ->{ repositoryDataSource.connect();}, getErrorMsg("testConnectOK", "Exception when trying to connect"));
		assertEquals(EnumConnectionType.CONNECTED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectOK", Constants.TestErrorMessages.NOT_CONNECTED_WHEN_OK));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect()}.
	 */
	@Test
	void testConnect_FAILED() {
		assertThrows(RepositoryDataSourceException.class, () -> {}, getErrorMsg("testConnectFAILED", "Exception must be thrown if connection error occurs"));
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectFAILED", Constants.TestErrorMessages.CONNECTED_WHEN_PROBLEM));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectUserPasswordOk() {
		fail( getErrorMsg("testConnectUserPasswordOk", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 */
	@ParameterizedTest(name = "Run with User = \"{0}\" and Password = \"{1}\" must throw an exception.")
	@CsvFileSource(resources = "testConnectUserPasswordWrong.csv", numLinesToSkip = 1, delimiter = ';', encoding = "UTF-8")
	void testConnectUserPasswordWrong(String user, String password) {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect(user, password);
		});
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), Constants.TestErrorMessages.CONNECTED_WHEN_PROBLEM);
	}
		
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 */
	@Test
	void testConnectPivateTokenOK() {
		fail(Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS);
//		assertDoesNotThrow(() -> {
//			repositoryDataSource.connect("");
//		}, getErrorMsg("testConnectPivateTokenOK", "Must not throw an exception"));
//		assertEquals(EnumConnectionType.LOGGED, repositoryDataSource.getConnectionType(), "I would be: '" + EnumConnectionType.LOGGED + "'");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 */
	@ParameterizedTest(name = "Run with Token = \"{0}\" must throw an exception.")
	@CsvFileSource(resources = "testConnectTokenWrong.csv", numLinesToSkip = 1, delimiter = ';', encoding = "UTF-8")
	void testConnectPivateTokenWrong(String token) {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect(token);
		});
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), Constants.TestErrorMessages.CONNECTED_WHEN_PROBLEM);
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#disconnect()}.
	 */
	@Test
	void testDisconnectOK() {
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testDisconnectOK", "Connection error"));
		}
		assertDoesNotThrow(() -> {
			repositoryDataSource.disconnect();
		}, getErrorMsg("testDisconnectOK", "Error when disconnecting"));
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), Constants.TestErrorMessages.CONNECTED_WHEN_PROBLEM);
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#disconnect()}.
	 */
	@Test
	void testDisconnectFailed() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.disconnect();
		}, "No exception was thrown when trying to disconnect when it was disconnected");
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), Constants.TestErrorMessages.CONNECTED_WHEN_PROBLEM);
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPublicRepositoryWhenDisconnected() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
		}, getErrorMsg("testGetPublicRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a public repository without connection."));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPrivateRepositoryWhenDisconnected() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowResult");
		}, getErrorMsg("testGetPrivateRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a private repository without connection."));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetNonExistentRepositoryWhenDisconnected() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowRlt");
		}, getErrorMsg("testGetNonExistentRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a non-existent repository without connection."));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPublicRepositoryWhenConnected() {
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenConnected", "Connection error"));
		}
		try {
			Repository repository = repositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
			assertNotNull(repository, "Returns null when obtaining a public repository with public connection");
			assertEquals(8760234, repository.getId().intValue(), "It does not return the correct ID.");
		}catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenConnected", "Exception when obtaining a public repository with public connection"));
		}
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPrivateRepositoryWhenConnected() {
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testDisconnectOK", "Connection error"));
		}
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowResult");
		}, getErrorMsg("testGetRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a private repository with public connection."));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetNonExistentRepositoryWhenConnected() {
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testDisconnectOK", "Connection error"));
		}
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowRlt");
		}, getErrorMsg("testGetRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a non-existent repository with public connection."));
	}	
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetPublicRepositoryWhenLogged() {
		fail( getErrorMsg("testGetPublicRepositoryWhenLogged", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testOwnPrivateGetRepositoryWhenLogged() {
		fail( getErrorMsg("testPrivateGetRepositoryWhenLogged", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testAnotherPrivateGetRepositoryWhenLogged() {
		fail( getErrorMsg("testPrivateGetRepositoryWhenLogged", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 */
	@Test
	void testGetNonExistentRepositoryWhenLogged() {
		fail( getErrorMsg("testGetNonExistentRepositoryConnected", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}
	
	@Test
	void testGetRepository() {//TODO Parametrized test
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenConnected", "Connection error"));
		}
		try {
			Repository repository = repositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
			assertNotNull(repository, "Returns null when obtaining a public repository with public connection");
			assertEquals(8760234, repository.getId().intValue(), "It does not return the correct ID.");
			assertEquals("https://gitlab.com/mlb0029/ListaCompra", repository.getUrl(), "It does not return the correct URL");
			assertEquals("ListaCompra", repository.getName(),"It does not return the correct Name");
			assertEquals(6, repository.getTotalNumberOfIssues().intValue(), "It does not return the correct number of issues");
			assertEquals(35, repository.getTotalNumberOfCommits().intValue(), "It does not return the correct number of commits");
			assertEquals(6, repository.getNumberOfClosedIssues().intValue(), "It does not return the correct number of closed issues");
			assertEquals(6, repository.getDaysToCloseEachIssue().size(), "It does not return the correct number of days of closed issues");//Fail
			assertEquals(35, repository.getCommitDates().size(), "It does not return the correct number commits dates");
			assertEquals(2, repository.getLifeSpanMonths().intValue(), "It does not return the correct lifespan of months");
		}catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenConnected", "Exception when obtaining a public repository with public connection"));
		}
	}
	
	private String getErrorMsg(String testName, String msg) {
		return "Failed test: " + testName + ". Message: " + msg + ".";
	}
}
