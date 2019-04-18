package metricsengine.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import metricsengine.*;
import metricsengine.values.*;
import repositorydatasource.model.Repository;

/**
 * Unit test for {@link metricsengine.metrics.MetricTotalNumberOfIssues}
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricTotalNumberOfIssuesTest {

	/**
	 * Metric under test.
	 */
	private static MetricTotalNumberOfIssues metricTotalNumberOfIssues;
		
	/**
	 * Instance the metric under test before all the tests are executed.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		metricTotalNumberOfIssues = new MetricTotalNumberOfIssues();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#MetricTotalNumberOfIssues()}.
	 */
	@Test
	public void testMetricTotalNumberOfIssues() {
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_METRIC_DESCRIPTION, metricTotalNumberOfIssues.getDescription(), "Expected default static description");
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_MIN_VALUE , metricTotalNumberOfIssues.getValueMinDefault(), "Expected default static min value");
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_MAX_VALUE, metricTotalNumberOfIssues.getValueMaxDefault(), "Expected default static max value");
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_METRIC_DESCRIPTION.getName(), metricTotalNumberOfIssues.getName(), "Expected default static name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#MetricTotalNumberOfIssues(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricTotalNumberOfIssuesMetricDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricTotalNumberOfIssues = new MetricTotalNumberOfIssues(metricDescription, min, max);
		assertTrue(metricDescription == metricTotalNumberOfIssues.getDescription(), "Expected another description");
		assertTrue(min == metricTotalNumberOfIssues.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricTotalNumberOfIssues.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricTotalNumberOfIssues.getName(), "Expected another name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#MetricTotalNumberOfIssues(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricTotalNumberOfIssuesNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * "TNI = Total number of issues"
	 */
	@ParameterizedTest(name= "[{index}]: TNI: {0}, Calculable: {1}, Test Case: {2}")
	@MethodSource
	public void testCheck(Integer totalNumberOfIssues, Boolean expected, String testCase) {
		Repository repository = new Repository("URL", "Test", 1);
		repository.setTotalNumberOfIssues(totalNumberOfIssues);
		assertEquals(expected, metricTotalNumberOfIssues.check(repository), 
				"Should return " + expected +
				" when totalNumberOfIssues=" + String.valueOf(totalNumberOfIssues) +
				". Test Case: (" + testCase + ")");
		assertFalse(metricTotalNumberOfIssues.check(null), "Should return false when repository = null");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#run(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "run" method for values in this formula: <br/>
	 *"TNI = Total number of issues"
	 */
	@ParameterizedTest(name = "[{index}]: TNI = {0}, Test Case: {2}")
	@MethodSource
	public void testRun(Integer totalNumberOfIssues, IValue expected, String testCase) {
		Repository repository = new Repository("URL", "Test", 1);
		repository.setTotalNumberOfIssues(totalNumberOfIssues);
		IValue actual = metricTotalNumberOfIssues.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation in test case: " + testCase);
	}

	/**
	 * Arguments for {@link #testCheck(Integer, Boolean, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "TNI = Total number of issues"
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return Stream of: Integer totalNumberOfIssues, Boolean expected, String testCase.
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, true, "TNI = Integer.MAX_VALUE"),
				Arguments.of(10,true, "TNI > 10"),
				Arguments.of(1, true, "TNI = 1"),
				Arguments.of(0, true, "TNI = 0"),
				Arguments.of(Integer.MIN_VALUE, false, "TNI = Integer.MIN_VALUE"),
				Arguments.of(-10, false, "TNI < 0"),
				Arguments.of(-1, false, "TNI = -1"),
				Arguments.of(null, false, "TNI = null")
		);
	}
	
	/**
	 * Arguments for {@link #testRun(Integer, IValue, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "TNI = Total number of issues"
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return Stream of: Integer totalNumberOfIssues, IValue expected, String testCase.
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, new ValueInteger(Integer.MAX_VALUE), "TNI = Integer.MAX_VALUE"),
				Arguments.of(10, new ValueInteger(10), "TNI > 0"),
				Arguments.of(1, new ValueInteger(1), "TNI = 1"),
				Arguments.of(0, new ValueInteger(0), "TNI = 0")
		);
	}
}