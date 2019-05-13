package gui.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.ws.rs.NotSupportedException;

import model.Repository;

/**
 * It contains a set of repositories.
 * <p>
 * Duplicates are not allowed.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 */
public class RepositoriesService implements Serializable {
	
	/**
	 * Serial.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 6585069143415079761L;

	/**
	 * Single instance.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static RepositoriesService instance;
	
	/**
	 * Set of repositories.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private RepositoriesCollection repositoriesCollection;
	
	private RepositoriesService() {
		repositoriesCollection = new RepositoriesCollection();
	}

	/**
	 * Gets the single instance of the class.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the instance
	 */
	public static RepositoriesService getInstance() {
		if (instance == null) instance = new RepositoriesService();
		return instance;
	}

	/**
	 * Gets the collection of repositories.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repositories
	 */
	public Collection<Repository> getRepositories() {
		return repositoriesCollection;
	}
	
	/**
	 * Adds the specified repository if it is not already present.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repository
	 * @return true if the repository doesn't exists in this collection
	 */
	public boolean addRepository(Repository repository) {
		return repositoriesCollection.repositories.add(repository);
	}
	
	
	/**
	 * Removes the specified repository if present.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repository
	 * @return true if the repository was present
	 */
	public boolean removeRepository(Repository repository) {
		return repositoriesCollection.repositories.remove(repository);
	}
	
	private class RepositoriesCollection implements Collection<Repository> {

		HashSet<Repository> repositories;
		
		RepositoriesCollection() {
			repositories = new HashSet<Repository>();
		}
		
		@Override
		public int size() {
			return repositories.size();
		}

		@Override
		public boolean isEmpty() {
			return repositories.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return repositories.contains(o);
		}

		@Override
		public Iterator<Repository> iterator() {
			return repositories.iterator();
		}

		@Override
		public Object[] toArray() {
			return repositories.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return repositories.toArray(a);
		}

		@Override
		public boolean add(Repository e) {
			throw new NotSupportedException();
		}

		@Override
		public boolean remove(Object o) {
			throw new NotSupportedException();
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return repositories.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends Repository> c) {
			throw new NotSupportedException();
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new NotSupportedException();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new NotSupportedException();
		}

		@Override
		public void clear() {
			throw new NotSupportedException();
		}
		
	}
}
