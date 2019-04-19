package repositorydatasource.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manage all Repository Data Source errors.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoryDataSourceException extends Exception {
	
	/**
	 * Logger.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static Logger logger = LoggerFactory.getLogger(RepositoryDataSourceException.class);
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int CONNECTION_ERROR = 1;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int LOGIN_ERROR = 2;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int ALREADY_CONNECTED = 3;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int ALREADY_DISCONNECTED = 4;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int NOT_CONNECTED = 5;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int REPOSITORY_NOT_FOUND = 6;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private int code = -1;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private String message = "";
	
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Neither the error code nor the message matter.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoryDataSourceException() {
		super();
	}
	
	/**
	 * Predefined messages from the error code.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param errorCode
	 */
	public RepositoryDataSourceException(int errorCode) {
		code = errorCode;
		
		switch (code) {
		case CONNECTION_ERROR:
			message = "Connection failure: Unable to establish a connection";
			break;
		case LOGIN_ERROR:
			message = "Login failure: incorrect credentials";
			break;
		case ALREADY_CONNECTED:
			message = "Connection failure: A connection already exists";
		case ALREADY_DISCONNECTED:
			message = "Disconnection failure: Already disconnected";
			break;
		case NOT_CONNECTED:
			message = "Error: There is no connection";
			break;
		case REPOSITORY_NOT_FOUND:
			message = "Repository not found: It doesn't exist or is inaccessible due to the connection level";
			break;
		default:
			message = "Unknown RepositoryDataSource error";
			break;
		}
		
		logger.error(message);
		
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			logger.info(ste.toString());
		}
	}

	/**
	 * Allows redefining the error code message.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param errorCode
	 * @param message
	 */
	public RepositoryDataSourceException(int errorCode, String message) {
		this.code = errorCode;
		this.message = message;
		
		logger.error(message);
		
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			logger.info(ste.toString());
		}
	}
	
	
	/**
	 * Constructor that is useful when the error code is not needed.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param message
	 */
	public RepositoryDataSourceException(String message) {
		this.message = message;
		
		logger.error(message);
		
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			logger.info(ste.toString());
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the error code.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the error code.
	 */
	public int getErrorCode() {
		return code;
	}
}
