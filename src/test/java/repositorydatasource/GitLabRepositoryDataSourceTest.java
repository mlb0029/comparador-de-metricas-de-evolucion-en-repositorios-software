package repositorydatasource;

import static org.junit.jupiter.api.Assumptions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import exceptions.RepositoryDataSourceException;
import repositorydatasource.factories.GitLabRepositoyDataSourceFactory;
import repositorydatasource.factories.IRepositoryDataSourceFactory;
import repositorydatasource.model.EnumConnectionType;
import repositorydatasource.model.Repository;

/**
 * Test for GitLabRepositoryDataSource.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class GitLabRepositoryDataSourceTest {

	/**
	 * RepositoryDataSource under test.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static IRepositoryDataSource repositoryDataSource;
	
	/**
	 * Factory to instantiate the RepositoryDataSource.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static IRepositoryDataSourceFactory repositoryDataSourceFactory;
	
	/**
	 * Username to connect to RepositoryDataSource via username and password.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static String user = "";
	
	/**
	 * Password to connect to RepositoryDataSource via username and password.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static String password = "";
	
	/**
	 * Token to connect to RepositoryDataSource via personal access token.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static String token = "";
	
	
	/**
	 * Instantiate the RepositoryDataSource.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws Exception if something goes wrong
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		repositoryDataSourceFactory = new GitLabRepositoyDataSourceFactory();
		repositoryDataSource = repositoryDataSourceFactory.createRepositoryDataSource();
				
	}

	/**
	 * Disconnects from RepositoryDataSource and closes references..
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws Exception if something goes wrong
	 */
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		if (repositoryDataSource.getConnectionType() != EnumConnectionType.NOT_CONNECTED) repositoryDataSource.disconnect();
		repositoryDataSource = null;
		repositoryDataSourceFactory = null;
	}

	/**
	 * Disconnects from RepositoryDataSource.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws Exception if something goes wrong
	 */
	@BeforeEach
	public void setUp() throws Exception {
		if (repositoryDataSource.getConnectionType() != EnumConnectionType.NOT_CONNECTED) repositoryDataSource.disconnect();
	}

	/**
	 * Disconnects from RepositoryDataSource.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws Exception if something goes wrong
	 */
	@AfterEach
	public void tearDown() throws Exception {
		if (repositoryDataSource.getConnectionType() != EnumConnectionType.NOT_CONNECTED) repositoryDataSource.disconnect();
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getGitLabRepositoryDataSource()}.
	 * <p>
	 * It ensures that there can only be one instance of RepositoryDataSource.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetGitLabRepositoryDataSource() {
		assertTrue(repositoryDataSource == GitLabRepositoryDataSource.getGitLabRepositoryDataSource(), getErrorMsg("testGetGitLabRepositoryDataSource", "Only one instance of repositoryDataSource is allowed"));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect()}.
	 * <p>
	 * It ensures that a correct connection does not throw an exception and that the connection type is 'CONNECTED'.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testConnectOK() {
		// TODO Asumir que hay conexión a Internet
		assertDoesNotThrow(() ->{ repositoryDataSource.connect();}, getErrorMsg("testConnectOK", "Exception when trying to connect"));
		assertEquals(EnumConnectionType.CONNECTED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectOK", "Connection type must be 'CONNECTED'"));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect()}.
	 * <p>
	 * It ensures that a failed connection connection throws an exception and that the connection type is 'NOT_CONNECTED'.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Disabled("There must be no internet connection")
	@Test
	public void testConnectFAILED() {
		// TODO Quitar Disabled y sustituir pos asumir que no hay conexión a internet.
		assertThrows(RepositoryDataSourceException.class, () -> {}, getErrorMsg("testConnectFAILED", "Exception must be thrown if connection error occurs"));
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectFAILED", "Connection type must be 'NOT_CONNECTED'"));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 * <p>
	 * 
	 * If user and password are specified, it ensures that no exception is thrown and that the connection type is 'LOGGED'.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testConnectUserPasswordOk() {
		assumeTrue(user != null && !user.equals("") && password != null && !password.equals(""), "The test can not be performed if a correct username and password are not specified");
		assertDoesNotThrow(() -> {
			repositoryDataSource.connect(user, password);
		}, getErrorMsg("testConnectUserPasswordOk", "Incorrect username and password or the test threw an exception when it should not"));
		assertEquals(EnumConnectionType.LOGGED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectUserPasswordOk", "Connection type must be 'LOGGED'"));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String, java.lang.String)}.
	 * <p>
	 * Test the method using a set of wrong user-password pairs that should raise an exception and the connection type is 'NOT_CONNECTED'.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@ParameterizedTest(name = "Run with User = \"{0}\" and Password = \"{1}\" must throw an exception.")
	@CsvFileSource(resources = "/testConnectUserPasswordWrong.csv", numLinesToSkip = 1, delimiter = ';', encoding = "UTF-8")
	public void testConnectUserPasswordWrong(String user, String password) {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect(user, password);
		}, getErrorMsg("testConnectUserPasswordWrong", "Wrong user-password should throw an exception"));
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectUserPasswordWrong", "Connection type must be 'NOT_CONNECTED'"));
	}
		
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 * <p>
	 * If a correct token is specified, it ensures that no exception is thrown and that the connection type is 'LOGGED'.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testConnectPivateTokenOK() {
		assumeTrue(token != null && !token.equals(""), "The test can not be performed if a correct token is not specified");
		assertDoesNotThrow(() -> {
			repositoryDataSource.connect(token);
		}, getErrorMsg("testConnectPivateTokenOK", "IWrong token or the test threw an exception when it should not"));
		assertEquals(EnumConnectionType.LOGGED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectPivateTokenOK", "Connection type must be 'LOGGED'"));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#connect(java.lang.String)}.
	 * <p>
	 * Test the method using a set of tokens that should raise an exception and the connection type is 'NOT_CONNECTED'.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@ParameterizedTest(name = "Run with Token = \"{0}\" must throw an exception.")
	@CsvFileSource(resources = "/testConnectTokenWrong.csv", numLinesToSkip = 1, delimiter = ';', encoding = "UTF-8")
	public void testConnectPivateTokenWrong(String token) {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.connect(token);
		}, getErrorMsg("testConnectPivateTokenWrong", "Wrong token should throw an exception"));
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), getErrorMsg("testConnectPivateTokenWrong", "Connection type must be 'NOT_CONNECTED'"));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#disconnect()}.
	 * <p>
	 * It is ensured that it disconnects correctly after being connected.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testDisconnectOK() {
		// TODO Assumption?
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testDisconnectOK", "Connection error"));
		}
		assertDoesNotThrow(() -> {
			repositoryDataSource.disconnect();
		}, getErrorMsg("testDisconnectOK", "An exception should not be thrown"));
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Connection type must be 'NOT_CONNECTED'");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#disconnect()}.
	 * <p>
	 * It makes sure that an exception is thrown while trying to disconnect without being connected.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testDisconnectFailed() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.disconnect();
		}, getErrorMsg("testDisconnectFailed", "No exception was thrown when trying to disconnect when it was disconnected"));
		assertEquals(EnumConnectionType.NOT_CONNECTED, repositoryDataSource.getConnectionType(), "Connection type must be 'NOT_CONNECTED'");
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * It ensures that an exception is thrown while trying to obtain a public repository when disconnected.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetPublicRepositoryWhenDisconnected() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
		}, getErrorMsg("testGetPublicRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a public repository without connection"));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * It ensures that an exception is thrown while trying to obtain a private repository when disconnected.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetPrivateRepositoryWhenDisconnected() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowResult");
		}, getErrorMsg("testGetPrivateRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a private repository without connection"));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * It ensures that an exception is thrown while trying to obtain a non-existent repository when disconnected.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetNonExistentRepositoryWhenDisconnected() {
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowRlt");
		}, getErrorMsg("testGetNonExistentRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a non-existent repository without connection."));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetPublicRepositoryWhenConnected() {
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
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetPrivateRepositoryWhenConnected() {
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
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetNonExistentRepositoryWhenConnected() {
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetNonExistentRepositoryWhenConnected", "Connection error"));
		}
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowRlt");
		}, getErrorMsg("testGetRepositoryWhenDisconnected", "An exception must be thrown when trying to obtain a non-existent repository with public connection."));
	}	
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetPublicRepositoryWhenLogged() {
		assumeTrue(token != null && !token.equals(""));
		try {			
			repositoryDataSource.connect(token);
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenLogged", "Connection error"));
		}
		try {
			Repository repository = repositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
			assertNotNull(repository, "Returns null when obtaining a public repository with loged connection");
			assertEquals(8760234, repository.getId().intValue(), "It does not return the correct ID.");
			assertEquals("https://gitlab.com/mlb0029/ListaCompra", repository.getUrl(), "It does not return the correct URL");
			assertEquals("ListaCompra", repository.getName(),"It does not return the correct Name");
		}catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenLogged", "Exception when obtaining a public repository with public connection"));
		}
		//fail( getErrorMsg("testGetPublicRepositoryWhenLogged", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}

	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testOwnPrivateGetRepositoryWhenLogged() {
		assumeTrue(token != null && !token.equals(""));
		try {			
			repositoryDataSource.connect(token);
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testOwnPrivateGetRepositoryWhenLogged", "Connection error"));
		}
		try {
			Repository repository = repositoryDataSource.getRepository("https://gitlab.com/mlb0029/KnowResult");
			assertNotNull(repository, "Returns null when obtaining a public repository with loged connection");
			assertEquals(8760239, repository.getId().intValue(), "It does not return the correct ID.");
			assertEquals("https://gitlab.com/mlb0029/KnowResult", repository.getUrl(), "It does not return the correct URL");
			assertEquals("KnowResult", repository.getName(),"It does not return the correct Name");
		}catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testOwnPrivateGetRepositoryWhenLogged", "Exception when obtaining a public repository with public connection"));
		}
		//fail( getErrorMsg("testOwnPrivateGetRepositoryWhenLogged", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Disabled("Not yet implemented")
	@Test
	public void testAnotherPrivateGetRepositoryWhenLogged() {
		assumeTrue(token != null && !token.equals(""), getErrorMsg("testAnotherPrivateGetRepositoryWhenLogged", "Username and password not entered"));
		try {
			repositoryDataSource.connect(token);
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenLogged", "Connection error"));
		}
	}
	
	/**
	 * Test method for {@link repositorydatasource.GitLabRepositoryDataSource#getRepository(java.lang.String)}.
	 * <p>
	 * 
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	@Test
	public void testGetNonExistentRepositoryWhenLogged() {
		assumeTrue(token != null && !token.equals(""));
		try {			
			repositoryDataSource.connect(token);
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetNonExistentRepositoryWhenLogged", "Connection error"));
		}
		assertThrows(RepositoryDataSourceException.class, () -> {
			repositoryDataSource.getRepository("https://gitlab.com/mlb0029/Knosult");
		}, "Must throw an exception if the repository doesn't exists");
		//fail( getErrorMsg("testGetNonExistentRepositoryWhenLogged", Constants.TestErrorMessages.NOT_IMPLEMENTED_SECURITY_REASONS));
	}
	
	
	@Test
	public void testGetRepository() {//TODO Parametrized test
		try {
			repositoryDataSource.connect();
		} catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenConnected", "Connection error"));
		}
		try {//assertAll, assertLinesMatch
			Repository repository = repositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
			assertNotNull(repository, "Returns null when obtaining a public repository with public connection");
			assertEquals(8760234, repository.getId().intValue(), "It does not return the correct ID.");
			assertEquals("https://gitlab.com/mlb0029/ListaCompra", repository.getUrl(), "It does not return the correct URL");
			assertEquals("ListaCompra", repository.getName(),"It does not return the correct Name");
			assertEquals(6, repository.getTotalNumberOfIssues().intValue(), "It does not return the correct number of issues");
			assertEquals(35, repository.getTotalNumberOfCommits().intValue(), "It does not return the correct number of commits");
			assertEquals(6, repository.getNumberOfClosedIssues().intValue(), "It does not return the correct number of closed issues");
			assertEquals(0, repository.getDaysToCloseEachIssue().size(), "It does not return the correct number of days of closed issues");//Fail
			assertEquals(35, repository.getCommitDates().size(), "It does not return the correct number commits dates");
			assertEquals(0, repository.getLifeSpanMonths().intValue(), "It does not return the correct lifespan of months");
		}catch (RepositoryDataSourceException e) {
			fail(getErrorMsg("testGetPublicRepositoryWhenConnected", "Exception when obtaining a public repository with public connection"));
		}
	}
	
	/**
	 * Generates error message by test method name and a message passed by parameter.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param testName Test method name.
	 * @param msg Error message.
	 * @return Custom error message.
	 */
	private String getErrorMsg(String testName, String msg) {
		return "Failed test: " + testName + ". Message: " + msg + ".";
	}
}
