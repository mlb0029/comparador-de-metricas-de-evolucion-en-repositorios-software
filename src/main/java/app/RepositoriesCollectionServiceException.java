package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesCollectionServiceException extends Exception {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 3304238974846329299L;

	/**
	 * Logger.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryDataSourceException.class);
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int REPOSITORY_ALREADY_EXISTS = 1;
	
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int NOT_EXIST_REPOSITORY = 2;
	
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
	 * Neither the error code nor the message matter.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoriesCollectionServiceException() {
		this(-1);
	}
	
	/**
	 * Predefined messages from the error code.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param errorCode
	 */
	public RepositoriesCollectionServiceException(int errorCode) {
		code = errorCode;
		
		switch (code) {
		case REPOSITORY_ALREADY_EXISTS:
			message = "The repository already exists";
			break;
		case NOT_EXIST_REPOSITORY:
			message = "The repository doesn't exists";
			break;
		default:
			message = "Unknown RepositoryDataSource error";
			break;
		}
		
		log();
	}

	/**
	 * Allows redefining the error code message.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param errorCode
	 * @param message
	 */
	public RepositoriesCollectionServiceException(int errorCode, String message) {
		this.code = errorCode;
		this.message = message;
		
		log();
	}
	
	
	/**
	 * Constructor that is useful when the error code is not needed.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param message
	 */
	public RepositoriesCollectionServiceException(String message) {
		this.message = message;
		
		log();
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

	private void log() {
		LOGGER.error(this.message);
		
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			LOGGER.info(ste.toString());
		}
	}

}
