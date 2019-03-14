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
 * Unit test for {@link metricsengine.metrics.MetricCommitsPerIssue}
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricCommitsPerIssueTest {

	/**
	 * Metric under test.
	 */
	private static MetricCommitsPerIssue metricCommitsPerIssue;
	
	/**
	 * Instance the metric under test before all the tests are executed.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		metricCommitsPerIssue = new MetricCommitsPerIssue();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#MetricCommitsPerIssue()}.
	 */
	@Test
	public void testMetricCommitsPerIssue() {
		assertEquals(MetricCommitsPerIssue.DEFAULT_METRIC_DESCRIPTION, metricCommitsPerIssue.getDescription(), "Expected default static description");
		assertEquals(MetricCommitsPerIssue.DEFAULT_MIN_VALUE , metricCommitsPerIssue.getValueMinDefault(), "Expected default static min value");
		assertEquals(MetricCommitsPerIssue.DEFAULT_MAX_VALUE, metricCommitsPerIssue.getValueMaxDefault(), "Expected default static max value");
		assertEquals(MetricCommitsPerIssue.DEFAULT_METRIC_DESCRIPTION.getName(), metricCommitsPerIssue.getName(), "Expected default static name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#MetricCommitsPerIssue(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest
	@MethodSource
	public void testMetricCommitsPerIssueDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricTotalNumberOfIssues = new MetricCommitsPerIssue(metricDescription, min, max);
		assertTrue(metricDescription == metricTotalNumberOfIssues.getDescription(), "Expected another description");
		assertTrue(min == metricTotalNumberOfIssues.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricTotalNumberOfIssues.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricTotalNumberOfIssues.getName(), "Expected another name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#MetricCommitsPerIssue(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest
	@MethodSource("testMetricCommitsPerIssueDescriptionValueMinValueMax")
	public void testMetricCommitsPerIssueNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricCommitsPerIssue(null, min, max);
		}, "Expected exception when null metric description");
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricCommitsPerIssue(metricDescription, null, max);
		}, "Expected exception when null value min");
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricCommitsPerIssue(metricDescription, min, null);
		}, "Expected exception when null value max");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: CI = TNI/TNC
	 */
	@ParameterizedTest(name= "[{index}]: TNI: {0}, TNC: {1}, Calculable: {2}")
	@MethodSource
	public void testCheck(Integer totalNumberOfIssues, Integer totalNumberOfCommits, Boolean expectedValue) {
		Repository repository = new Repository("", "", 0, totalNumberOfIssues, totalNumberOfCommits, 0, null, null, 0);
		assertEquals(expectedValue, metricCommitsPerIssue.check(repository), 
				"Should return false when totalNumberOfIssues=" + totalNumberOfIssues +
				" totalNumberOfCommits=" + totalNumberOfCommits);
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#run(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check that the formula: {CI = TNI/TNC} is computed correctly.
	 */
	@ParameterizedTest(name = "[{index}]: TNI: {0}, TNC: {1}")
	@MethodSource
	public void testRun(Integer totalNumberOfIssues, Integer totalNumberOfCommits) {
		Repository repository = new Repository("", "", 0, totalNumberOfIssues, totalNumberOfCommits, 0, null, null, 0);
		IValue expected = new ValueDecimal((double) (totalNumberOfIssues / totalNumberOfCommits));
		IValue actual = metricCommitsPerIssue.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation");
	}
	
	/**
	 * Arguments for {@link #testMetricCommitsPerIssueDescriptionValueMinValueMax(MetricDescription, IValue, IValue)}
	 * <p>
	 * Returns: description, Ivalue min, Ivalue max.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testMetricCommitsPerIssueDescriptionValueMinValueMax(){
		MetricDescription metricDescription = new MetricDescription("Prueba", "", "","","","","","","",EnumTypeOfScale.ABSOLUTE, "");
		return Stream.of(
				Arguments.of(metricDescription, new ValueInteger(1), new ValueInteger(10)),
				Arguments.of(metricDescription, new ValueDecimal(1.0), new ValueDecimal(10.9523))
		);
	}
	
	/**
	 * Arguments for check tests.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return TNI, TNC, isCalculable
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, true),
				Arguments.of(Integer.MAX_VALUE, 1, true),
				Arguments.of(1, Integer.MAX_VALUE, true),
				Arguments.of(10, 5, true),
				Arguments.of(0, 1, true),
				Arguments.of(0, 0, false),
				Arguments.of(10, 0, false),
				Arguments.of(null, 5, false),
				Arguments.of(1, null, false),
				Arguments.of(null, 0, false),
				Arguments.of(0, null, false),
				Arguments.of(null, null, false)
		);
	}
	
	/**
	 * Arguments for run tests.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return TNI, TNC
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, true),
				Arguments.of(Integer.MAX_VALUE, 1, true),
				Arguments.of(1, Integer.MAX_VALUE, true),
				Arguments.of(10, 5, true),
				Arguments.of(0, 1, true)
		);
	}
}
