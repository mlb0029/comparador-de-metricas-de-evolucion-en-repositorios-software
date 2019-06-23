package app.listeners;

import java.io.Serializable;

import repositorydatasource.RepositoryDataSource.EnumConnectionType;

public class ConnectionChangedEvent implements Serializable, Event{

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 2055762274587204233L;

	private EnumConnectionType connectionTypeBefore;
	
	private EnumConnectionType connectionTypeAfter;
	
	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param connectionTypeBefore
	 * @param connectionTypeAfter
	 */
	public ConnectionChangedEvent(EnumConnectionType connectionTypeBefore, EnumConnectionType connectionTypeAfter) {
		this.connectionTypeBefore = connectionTypeBefore;
		this.connectionTypeAfter = connectionTypeAfter;
	}

	/**
	 * Gets the connectionTypeBefore.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the connectionTypeBefore
	 */
	public EnumConnectionType getConnectionTypeBefore() {
		return connectionTypeBefore;
	}

	/**
	 * Gets the connectionTypeAfter.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the connectionTypeAfter
	 */
	public EnumConnectionType getConnectionTypeAfter() {
		return connectionTypeAfter;
	}

}
