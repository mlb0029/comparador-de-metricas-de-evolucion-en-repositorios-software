package app.listeners;

import java.io.Serializable;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public interface Listener<T extends Event> extends Serializable {
	void on(T event);
}
