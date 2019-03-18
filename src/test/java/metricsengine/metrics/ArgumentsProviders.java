package metricsengine.metrics;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import metricsengine.MetricDescription;
import metricsengine.MetricDescription.EnumTypeOfScale;
import metricsengine.values.ValueDecimal;
import metricsengine.values.ValueInteger;

/**
 * The class provides methods that create arguments for the parameterized tests.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class ArgumentsProviders {

	/**
	 * Arguments for {@link metricsengine.AMetric#AMetric(MetricDescription, IValue, IValue)}
	 * <p>
	 * Returns: description, Ivalue min, Ivalue max.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return description, Ivalue min, Ivalue max.
	 */
	public static Stream<Arguments> argumentsForAMetricConstructorWithArguments(){
		MetricDescription metricDescription = new MetricDescription("Prueba", "", "","","","","","","",EnumTypeOfScale.ABSOLUTE, "");
		return Stream.of(
				Arguments.of(metricDescription, new ValueInteger(1), new ValueInteger(10)),
				Arguments.of(metricDescription, new ValueDecimal(1.0), new ValueDecimal(10.9523))
		);
	}
	
	/**
	 * Arguments for {@link metricsengine.AMetric#AMetric(MetricDescription, IValue, IValue)}
	 * <p>
	 * Returns: description, Ivalue min, Ivalue max.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return description, Ivalue min, Ivalue max.
	 */
	public static Stream<Arguments> argumentsForAMetricConstructorWithNullArguments(){
		MetricDescription metricDescription = new MetricDescription("Prueba", "", "","","","","","","",EnumTypeOfScale.ABSOLUTE, "");
		return Stream.of(
				Arguments.of(null, new ValueInteger(1), new ValueInteger(10)),
				Arguments.of(metricDescription, null, new ValueInteger(10)),
				Arguments.of(metricDescription, new ValueDecimal(1.0), null)
		);
	}
}
