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
import metricsengine.MetricDescription.EnumTypeOfScale;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import metricsengine.values.ValueInteger;
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
	@MethodSource
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
	@MethodSource("testMetricPercentageClosedIssuesDescriptionValueMinValueMax")
	public void testMetricPercentageClosedIssuesNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricPercentageClosedIssues(null, min, max);
		}, "Expected exception when null metric description");
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricPercentageClosedIssues(metricDescription, null, max);
		}, "Expected exception when null value min");
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricPercentageClosedIssues(metricDescription, min, null);
		}, "Expected exception when null value max");
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
				"Should return " + expectedValue + " when totalNumberOfIssues=" + totalNumberOfIssues +
				" numberOfClosedIssues=" + numberOfClosedIssues + "(" + testCase + ")");
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
	 * Arguments for {@link #testMetricPercentageClosedIssuesDescriptionValueMinValueMax(MetricDescription, IValue, IValue)}
	 * <p>
	 * Returns: description, Ivalue min, Ivalue max.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return description, Ivalue min, Ivalue max.
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testMetricPercentageClosedIssuesDescriptionValueMinValueMax(){
		MetricDescription metricDescription = new MetricDescription("Prueba", "", "","","","","","","",EnumTypeOfScale.ABSOLUTE, "");
		return Stream.of(
				Arguments.of(metricDescription, new ValueInteger(1), new ValueInteger(10)),
				Arguments.of(metricDescription, new ValueDecimal(1.0), new ValueDecimal(10.9523))
		);
	}
	
	/**
	 * Arguments for {@link #testCheck(Integer, Integer, Boolean, String)}
	 * <p>
	 * Test cases for the formula: <br/>
	 * PIC = (NCI/TNI) * 100. PIC = Percentage of issues closed.TNI = Total number of issues. NCI = Number of closed issues
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return totalNumberOfIssues, numberOfClosedIssues, expectedValue
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, true, "Good arguments"),
				Arguments.of(Integer.MAX_VALUE, 1, true, "Good arguments"),
				Arguments.of(1, Integer.MAX_VALUE, true, "Good arguments"),
				Arguments.of(Integer.MAX_VALUE, 0, true, "Good arguments"),
				Arguments.of(5, 10, true, "Good arguments"),
				Arguments.of(1, 0, true, "Good arguments"),
				Arguments.of(Integer.MIN_VALUE, 1, false, "Negative arguments"),
				Arguments.of(1, Integer.MIN_VALUE, false, "Negative arguments"),
				Arguments.of(-5, -10, false, "Negative arguments"),
				Arguments.of(-1, -1, false, "Negative arguments"),
				Arguments.of(0, Integer.MAX_VALUE, false, "Division between zero"),
				Arguments.of(0, 0, false, "Division between zero"),
				Arguments.of(0, 10, false, "Division between zero"),
				Arguments.of(null, 5, false, "Null values"),
				Arguments.of(1, null, false, "Null values"),
				Arguments.of(null, 0, false, "Null values"),
				Arguments.of(0, null, false, "Null values"),
				Arguments.of(null, null, false, "Null values")
		);
	}
	
	/**
	 * Arguments for {@link #testRun(Integer, Integer)}
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return totalNumberOfIssues, numberOfClosedIssues
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE),
				Arguments.of(Integer.MAX_VALUE, 1),
				Arguments.of(1, Integer.MAX_VALUE),
				Arguments.of(5, 10),
				Arguments.of(1, 0),
				Arguments.of(1,1)
		);
	}
}
