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
 * Unit test for {@link metricsengine.metrics.MetricPercentageClosedIssues}.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricPercentageClosedIssuesTest {

	/**
	 * Metric under test.
	 */
	private static MetricPercentageClosedIssues metricPercentageClosedIssues;
	

	/**
	 * Instance the metric under test before all the tests are executed.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		metricPercentageClosedIssues = new MetricPercentageClosedIssues();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricPercentageClosedIssues#MetricPercentageClosedIssues()}.
	 */
	@Test
	public void testMetricPercentageClosedIssues() {
		assertEquals(MetricPercentageClosedIssues.DEFAULT_METRIC_DESCRIPTION, metricPercentageClosedIssues.getDescription(), "Expected default static description");
		assertEquals(MetricPercentageClosedIssues.DEFAULT_MIN_VALUE , metricPercentageClosedIssues.getValueMinDefault(), "Expected default static min value");
		assertEquals(MetricPercentageClosedIssues.DEFAULT_MAX_VALUE, metricPercentageClosedIssues.getValueMaxDefault(), "Expected default static max value");
		assertEquals(MetricPercentageClosedIssues.DEFAULT_METRIC_DESCRIPTION.getName(), metricPercentageClosedIssues.getName(), "Expected default static name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricPercentageClosedIssues#MetricPercentageClosedIssues(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricPercentageClosedIssuesDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricPercentageClosedIssues = new MetricPercentageClosedIssues(metricDescription, min, max);
		assertTrue(metricDescription == metricPercentageClosedIssues.getDescription(), "Expected another description");
		assertTrue(min == metricPercentageClosedIssues.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricPercentageClosedIssues.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricPercentageClosedIssues.getName(), "Expected another name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricPercentageClosedIssues#MetricPercentageClosedIssues(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricPercentageClosedIssuesNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricPercentageClosedIssues(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricPercentageClosedIssues#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * PIC = (NCI/TNI) * 100. PIC = Percentage of issues closed.TNI = Total number of issues. NCI = Number of closed issues
	 * 
	 */
	@ParameterizedTest(name= "[{index}]: TNI: {0}, NCI: {1}, Calculable: {2}, Test Case: {3}")
	@MethodSource
	public void testCheck(Integer totalNumberOfIssues, Integer numberOfClosedIssues, Boolean expectedValue, String testCase) {
		Repository repository = new Repository("", "", -1, totalNumberOfIssues, -1, numberOfClosedIssues, null, null, -1);
		assertEquals(expectedValue, metricPercentageClosedIssues.check(repository), 
				"Should return " + expectedValue +
				" when totalNumberOfIssues=" + totalNumberOfIssues +
				", numberOfClosedIssues=" + numberOfClosedIssues +
				". Test Case: (" + testCase + ")");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricPercentageClosedIssues#run(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "run" method for values in this formula: <br/>
	 * PIC = (NCI/TNI) * 100. PIC = Percentage of issues closed.TNI = Total number of issues. NCI = Number of closed issues
	 */
	@ParameterizedTest(name = "[{index}]: TNI: {0}, NCI: {1}")
	@MethodSource
	public void testRun(Integer totalNumberOfIssues, Integer numberOfClosedIssues) {
		Repository repository = new Repository("", "", -1, totalNumberOfIssues, -1, numberOfClosedIssues, null, null, -1);
		IValue expected = new ValueDecimal((double) ((numberOfClosedIssues / totalNumberOfIssues)) * 100);
		IValue actual = metricPercentageClosedIssues.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation");
	}

	/**
	 * Arguments for {@link #testCheck(Integer, Integer, Boolean, String)}
	 * <p>
	 * Test cases for the formula: <br/>
	 * PIC = (NCI/TNI) * 100. PIC = Percentage of issues closed.TNI = Total number of issues. NCI = Number of closed issues
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return totalNumberOfIssues, numberOfClosedIssues, expectedValue, testCase
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, true, "TNI, NCI = Integer.MAX_VALUE"),
				Arguments.of(Integer.MAX_VALUE, 1, true, "TNI = Integer.MAX_VALUE"),
				Arguments.of(1, Integer.MAX_VALUE, true, "NCI"),
				Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE, false, "TNI, NCI = Integer.MIN_VALUE"),
				Arguments.of(Integer.MIN_VALUE, 1, false, "TNI = Integer.MIN_VALUE"),
				Arguments.of(1, Integer.MIN_VALUE, false, "NCI = Integer.MIN_VALUE"),
				Arguments.of(0, 0, false, "TNI, NCI = 0"),
				Arguments.of(0, 1, false, "TNI = 0"),
				Arguments.of(1, 0, true, "NCI = 0"),
				Arguments.of(1, 1, true, "TNI = NCI.TNI, NCI > 0"),
				Arguments.of(10, 5, true, "TNI > NCI.TNI, NCI > 0"),
				Arguments.of(12, 432, true, "TNI < NCI.TNI, NCI > 0"),
				Arguments.of(-1, -1, false, "TNI = NCI.TNI, NCI < 0"),
				Arguments.of(-10, -5, false, "TNI < NCI.TNI, NCI < 0"),
				Arguments.of(-12, -432, false, "TNI > NCI.TNI, NCI < 0"),
				Arguments.of(-10, 1, false, "TNI < 0"),
				Arguments.of(10, -23, false, "NCI < 0"),
				Arguments.of(null, null, false, "TNI, NCI = null"),
				Arguments.of(null, 5, false, "TNI = null"),
				Arguments.of(1, null, false, "NCI = null")
		);
	}
	
	/**
	 * Arguments for {@link #testRun(Integer, Integer)}
	 * <p>
	 * Test cases for the formula: <br/>
	 * PIC = (NCI/TNI) * 100. PIC = Percentage of issues closed.TNI = Total number of issues. NCI = Number of closed issues
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return totalNumberOfIssues, numberOfClosedIssues
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, "TNI, NCI = MAX_VALUE"),
				Arguments.of(Integer.MAX_VALUE, 1, "TNI = MAX_VALUE"),
				Arguments.of(1, Integer.MAX_VALUE, "NCI = MAX_VALUE"),
				Arguments.of(Integer.MAX_VALUE, 0, "TNI = MAX_VALUE, NCI = 0"),
				Arguments.of(1, 0, "TNI = 1, NCI = 0"),
				Arguments.of(5, 10, "Any values NCI > TNI"),
				Arguments.of(1, 1, "TNI = NCI"),
				Arguments.of(15, 10, "Any values NCI < TNI")
		);
	}
}