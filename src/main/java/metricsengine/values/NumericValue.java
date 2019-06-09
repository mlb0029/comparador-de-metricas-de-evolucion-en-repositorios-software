package metricsengine.values;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public abstract class NumericValue implements IValue {
	public abstract int intValue();
	public abstract long longValue();
	public abstract double doubleValue();
}
