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
	private static Logger logger = LoggerFactory.getLogger(RepositoryDataSourceException.class);
	
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
	 * Neither the error code nor the message matter.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricsServiceException() {
		super();
	}
	
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
	public MetricsServiceException(int errorCode, String message) {
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
	public MetricsServiceException(String message) {
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
