package app.listeners;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class FileUploadEvent implements Serializable {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -4113645939457570245L;

	private String fileName;

	private InputStream inputStream;

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param fileName
	 * @param inputStream
	 */
	public FileUploadEvent(String fileName, InputStream inputStream) {
		this.fileName = fileName;
		this.inputStream = inputStream;
	}

	/**
	 * Gets the serialversionuid.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the fileName.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Gets the inputStream.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

}
