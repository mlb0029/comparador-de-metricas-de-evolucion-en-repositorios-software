package app.listeners;

import datamodel.Repository;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesCollectionUpdatedEvent {

	Repository repository;
	
	boolean added;

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repository
	 * @param added
	 */
	public RepositoriesCollectionUpdatedEvent(Repository repository, boolean added) {
		this.repository = repository;
		this.added = added;
	}

	/**
	 * Gets the repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repository
	 */
	public Repository getRepository() {
		return repository;
	}

	/**
	 * Gets the added.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the added
	 */
	public boolean isAdded() {
		return added;
	}
	
	public boolean isRemoved() {
		return !added;
	}
}
