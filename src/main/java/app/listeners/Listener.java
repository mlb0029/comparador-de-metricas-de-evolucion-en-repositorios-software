package app.listeners;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public interface Listener<T> {
	void on(T event);
}
