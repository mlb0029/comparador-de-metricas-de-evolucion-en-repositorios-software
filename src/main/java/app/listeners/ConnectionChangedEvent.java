package app.listeners;

import repositorydatasource.RepositoryDataSource.EnumConnectionType;

public class ConnectionChangedEvent {

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