package app;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.ws.rs.NotSupportedException;

import org.apache.commons.io.output.ByteArrayOutputStream;

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
		if (!repositoriesCollection.collection.add(repository)) throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.REPOSITORY_ALREADY_EXISTS);
		notifyRepositoriesCollectionUpdatedListeners();
	}
	
	public void removeRepository(Repository repository) throws RepositoriesCollectionServiceException {
		if (!repositoriesCollection.collection.remove(repository)) throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.NOT_EXIST_REPOSITORY);
		notifyRepositoriesCollectionUpdatedListeners();
	}
	
	public InputStream exportRepositories () throws RepositoriesCollectionServiceException {
		try (
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(bos);
		){
			objectOut.writeObject(repositoriesCollection.collection);
			objectOut.flush();
			return bos.toInputStream();
		} catch (Exception e) {
			throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.EXPORT_ERROR, e);
		}
	}
	
	public enum ImportMode {OVERWRITE, APPEND}
	
	@SuppressWarnings("unchecked")
	public void importRepositories(InputStream inputStream, ImportMode importMode) throws RepositoriesCollectionServiceException {
		try (
			ObjectInputStream objectIn = new ObjectInputStream(inputStream);
		) {
			HashSet<Repository> repositories = (HashSet<Repository>) objectIn.readObject();
			if(importMode == ImportMode.OVERWRITE) repositoriesCollection.collection.clear();
			repositoriesCollection.collection.addAll(repositories);
			notifyRepositoriesCollectionUpdatedListeners();
		} catch (StreamCorruptedException e) {
			throw new RepositoriesCollectionServiceException(RepositoriesCollectionServiceException.IMPORT_ERROR, e);
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

	private class RepositoriesCollection implements Collection<Repository> {

		private HashSet<Repository> collection = new HashSet<Repository>();
		
		@Override
		public int size() {
			return collection.size();
		}

		@Override
		public boolean isEmpty() {
			return collection.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return collection.contains(o);
		}

		@Override
		public Iterator<Repository> iterator() {
			return collection.iterator();
		}

		@Override
		public Object[] toArray() {
			return collection.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return collection.toArray(a);
		}

		@Override
		public boolean add(Repository e) {
			throw new NotSupportedException("Not allowed. Use RepositoriesCollectionService instead.");
		}

		@Override
		public boolean remove(Object o) {
			throw new NotSupportedException("Not allowed. Use RepositoriesCollectionService instead.");
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return collection.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends Repository> c) {
			throw new NotSupportedException("Not allowed. Use RepositoriesCollectionService instead.");
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new NotSupportedException("Not allowed. Use RepositoriesCollectionService instead.");
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new NotSupportedException("Not allowed. Use RepositoriesCollectionService instead.");
		}

		@Override
		public void clear() {
			throw new NotSupportedException("Not allowed. Use RepositoriesCollectionService instead.");
		}
	}
}
