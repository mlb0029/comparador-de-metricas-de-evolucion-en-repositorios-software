package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.ws.rs.NotSupportedException;

import app.listeners.Listener;
import app.listeners.RepositoriesCollectionUpdatedEvent;
import datamodel.Repository;
import exceptions.RepositoriesCollectionServiceException;

/**
 * It contains a set of repositories.
 * <p>
 * Duplicates are not allowed.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 */
public class RepositoriesCollectionService implements Serializable {
	
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
	private static RepositoriesCollectionService instance;
	
	/**
	 * Set of repositories.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private RepositoriesCollection repositoriesCollection = new RepositoriesCollection();
	
	private HashSet<Listener<RepositoriesCollectionUpdatedEvent>> repositoriesCollectionUpdatedListeners = new HashSet<>();
	
	private RepositoriesCollectionService() {}

	public static RepositoriesCollectionService getInstance() {
		if (instance == null) instance = new RepositoriesCollectionService();
		return instance;
	}

	public Collection<Repository> getRepositories() {
		return repositoriesCollection;
	}
	
	public void addRepository(Repository repository) throws RepositoriesCollectionServiceException {
		if (!repositoriesCollection.repositories.add(repository)) throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.REPOSITORY_ALREADY_EXISTS);
		notifyRepositoriesCollectionUpdatedListeners();
	}
	
	
	public void removeRepository(Repository repository) throws RepositoriesCollectionServiceException {
		if (!repositoriesCollection.repositories.remove(repository)) throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.NOT_EXIST_REPOSITORY);
		notifyRepositoriesCollectionUpdatedListeners();
	}
	
	public void exportRepositories () throws RepositoriesCollectionServiceException {
		try (
			FileOutputStream bos = new FileOutputStream("test.txt", false);
			ObjectOutputStream objectOut = new ObjectOutputStream(bos);
		){
			objectOut.writeObject(repositoriesCollection);
			objectOut.flush();
		} catch (Exception e) {
			throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.EXPORT_ERROR, e);
		}
	}
	
	public void importRepositories() throws RepositoriesCollectionServiceException {
		try (
			FileInputStream in = new FileInputStream("");
			ObjectInputStream objectIn = new ObjectInputStream(in);
		) {
			repositoriesCollection =  (RepositoriesCollection) objectIn.readObject();
			notifyRepositoriesCollectionUpdatedListeners();
		} catch (Exception e) {
			throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.IMPORT_ERROR, e);
		}
	}

	/**
	 * @param listener
	 * @return
	 * @see java.util.HashSet#add(java.lang.Object)
	 */
	public boolean addRepositoriesCollectionUpdatedListener(Listener<RepositoriesCollectionUpdatedEvent> listener) {
		return repositoriesCollectionUpdatedListeners.add(listener);
	}

	/**
	 * @param listener
	 * @return
	 * @see java.util.HashSet#remove(java.lang.Object)
	 */
	public boolean removeRepositoriesCollectionUpdatedListener(Listener<RepositoriesCollectionUpdatedEvent> listener) {
		return repositoriesCollectionUpdatedListeners.remove(listener);
	}
	
	private void notifyRepositoriesCollectionUpdatedListeners() {
		repositoriesCollectionUpdatedListeners.forEach(l -> l.on(new RepositoriesCollectionUpdatedEvent()));
	}
	
	//Necesario para el ListDataProvider<Repository> repositoriesDataProvider de RepositoriesListView
	private class RepositoriesCollection implements Collection<Repository>, Serializable{

		/**
		 * Description.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 */
		private static final long serialVersionUID = -1632073948461817997L;
		
		HashSet<Repository> repositories;
		
		public RepositoriesCollection() {
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
