package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricsServiceException extends Exception {
	/**
	 * Logger.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryDataSourceException.class);
	
	/**
	 * There is already a metric profile with the same name.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int METRICP_ROFILE_NAME_IN_USE = 1;
	
	
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
	 * Predefined messages from the error code.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param errorCode
	 */
	public MetricsServiceException(int errorCode) {
		code = errorCode;
		
		switch (code) {
		case METRICP_ROFILE_NAME_IN_USE:
			message = "Connection failure: Unable to establish a connection";
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
	public MetricsServiceException(int errorCode, String message) {
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
	public MetricsServiceException(String message) {
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
		LOGGER.error(message);
		
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			LOGGER.info(ste.toString());
		}
	}
}
