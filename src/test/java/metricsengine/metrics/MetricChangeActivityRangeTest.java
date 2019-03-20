package metricsengine.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * Unit test for {@link metricsengine.metrics.MetricChangeActivityRange}.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricChangeActivityRangeTest {

	/**
	 * Metric under test.
	 */
	private static MetricChangeActivityRange metricChangeActivityRange;
	
	/**
	 * Instance the metric under test before all the tests are executed.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		metricChangeActivityRange = new MetricChangeActivityRange();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricChangeActivityRange#MetricChangeActivityRange()}.
	 */
	@Test
	public void testMetricChangeActivityRange() {
		assertEquals(MetricChangeActivityRange.DEFAULT_METRIC_DESCRIPTION, metricChangeActivityRange.getDescription(), "Expected default static description");
		assertEquals(MetricChangeActivityRange.DEFAULT_MIN_VALUE , metricChangeActivityRange.getValueMinDefault(), "Expected default static min value");
		assertEquals(MetricChangeActivityRange.DEFAULT_MAX_VALUE, metricChangeActivityRange.getValueMaxDefault(), "Expected default static max value");
		assertEquals(MetricChangeActivityRange.DEFAULT_METRIC_DESCRIPTION.getName(), metricChangeActivityRange.getName(), "Expected default static name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricChangeActivityRange#MetricChangeActivityRange(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricChangeActivityRangeMetricDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricChangeActivityRange = new MetricChangeActivityRange(metricDescription, min, max);
		assertTrue(metricDescription == metricChangeActivityRange.getDescription(), "Expected another description");
		assertTrue(min == metricChangeActivityRange.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricChangeActivityRange.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricChangeActivityRange.getName(), "Expected another name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricChangeActivityRange#MetricChangeActivityRange(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricChangeActivityRangeNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricChangeActivityRange(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricChangeActivityRange#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * "CAR = TNC / NM. CAR = Number of changes relative to the number of months in the period, TNC = Total number of commits, NM = Number of months"
	 */
	@ParameterizedTest(name = "[{index}] TNC = {0}, NM = {1}, Test Case: {3}")
	@MethodSource
	public void testCheck(Integer totalNumberOfCommits, Integer lifeSpanMonths, Boolean expected, String testCase) {
		Repository repository = new Repository("", "", 0, 0, totalNumberOfCommits, 0, null, null, lifeSpanMonths);
		assertEquals(expected, metricChangeActivityRange.check(repository), 
				"Should return " + expected +
				" when totalNumberOfCommits=" + String.valueOf(totalNumberOfCommits) +
				", numberOfMonths=" + lifeSpanMonths +
				". Test Case: (" + testCase + ")");
		assertFalse(metricChangeActivityRange.check(null), "Should return false when repository = null");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricChangeActivityRange#run(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "run" method for values in this formula: <br/>
	 * "CAR = TNC / NM. CAR = Number of changes relative to the number of months in the period, TNC = Total number of commits, NM = Number of months"
	 */
	@ParameterizedTest(name = "[{index}] TNC = {0}, NM = {1}, Test Case: {3}")
	@MethodSource
	public void testRun(Integer totalNumberOfCommits, Integer lifeSpanMonths, IValue expected, String testCase) {
		Repository repository = new Repository("", "", 0, 0, totalNumberOfCommits, 0, null, null, lifeSpanMonths);
		IValue actual = metricChangeActivityRange.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation in test case: " + testCase);
	}

	/**
	 * Arguments for {@link #testCheck(Integer, Integer, Boolean, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "CAR = TNC / NM. CAR = Number of changes relative to the number of months in the period, TNC = Total number of commits, NM = Number of months"
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return Stream of: Integer totalNumberOfCommits, Integer lifeSpanMonths, Boolean expectedValue, String testCase
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, true, "TNC, NM = Integer.MAX_VALUE"),
				Arguments.of(Integer.MAX_VALUE, 1, true, "TNC = Integer.MAX_VALUE"),
				Arguments.of(1, Integer.MAX_VALUE, true, "NM = Integer.MAX_VALUE"),
				Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE, false, "TNC, NM = Integer.MIN_VALUE"),
				Arguments.of(Integer.MIN_VALUE, 1, false, "TNC = Integer.MIN_VALUE"),
				Arguments.of(1, Integer.MIN_VALUE, false, "NM = Integer.MIN_VALUE"),
				Arguments.of(0, 0, false, "TNC, NM = 0"),
				Arguments.of(0, 1, true, "TNC = 0"),
				Arguments.of(10, 0, false, "NM = 0"),
				Arguments.of(1, 1, true, "TNC = NM. TNC, NM > 0"),
				Arguments.of(10, 5, true, "TNC > NM. TNC, NM > 0"),
				Arguments.of(12, 432, true, "TNC < NM. TNC, NM > 0"),
				Arguments.of(-1, -1, false, "TNC = TNC. TNC, NM < 0"),
				Arguments.of(-10, -5, false, "TNC < NM. TNC, NM < 0"),
				Arguments.of(-12, -432, false, "TNC > NM. TNC, NM < 0"),
				Arguments.of(-10, 1, false, "TNC < 0"),
				Arguments.of(10, -23, false, "NM < 0"),
				Arguments.of(null, null, false, "TNC, NM = null"),
				Arguments.of(null, 5, false, "TNC = null"),
				Arguments.of(1, null, false, "NM = null")	
		);
	}
	
	/**
	 * Arguments for {@link #testRun(Integer, Integer, IValue, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "CAR = TNC / NM. CAR = Number of changes relative to the number of months in the period, TNC = Total number of commits, NM = Number of months"
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return Stream of: Integer totalNumberOfCommits, Integer lifeSpanMonths, IValue expected, String testCase
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, new ValueDecimal(1.0), "TNC, NM = Integer.MAX_VALUE"),
				Arguments.of(Integer.MAX_VALUE, 1, new ValueDecimal((double) Integer.MAX_VALUE), "TNC = Integer.MAX_VALUE"),
				Arguments.of(1, Integer.MAX_VALUE, new ValueDecimal((double) 1 / Integer.MAX_VALUE), "NM = Integer.MAX_VALUE"),
				Arguments.of(0, 1, new ValueDecimal(0.0), "TNC = 0"),
				Arguments.of(1, 1, new ValueDecimal(1.0), "TNC = NM. TNC, NM > 0"),
				Arguments.of(10, 5, new ValueDecimal(2.0), "TNC > NM. TNC, NM > 0"),
				Arguments.of(12, 432, new ValueDecimal((double) 12 / 432), "TNC < NM. TNC, NM > 0")	
		);
	}
}