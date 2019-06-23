package exceptions;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricsServiceException extends ApplicationException {
	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * There is already a metric profile with the same name.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public static final int METRICP_ROFILE_NAME_IN_USE = 1;

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param errorCode
	 */
	public MetricsServiceException(int errorCode) {
		super(errorCode);
	}


	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param message
	 */
	public MetricsServiceException(String message) {
		super(message);
	}


	public MetricsServiceException() {}


	public MetricsServiceException(int errorCode, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(errorCode, cause, enableSuppression, writableStackTrace);
	}


	public MetricsServiceException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}


	public MetricsServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


	public MetricsServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetricsServiceException(Throwable cause) {
		super(cause);
	}


	@Override
	protected void generateMessage() {
		switch (code) {
		case METRICP_ROFILE_NAME_IN_USE:
			message = "Connection failure: Unable to establish a connection";
			break;
		default:
			message = "Unknown RepositoryDataSource error";
			break;
		}
	}
}
