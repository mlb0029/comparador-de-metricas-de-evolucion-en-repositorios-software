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
 * Unit test for {@link metricsengine.metrics.MetricCommitsPerIssue}.
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
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricCommitsPerIssueDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricCommitsPerIssue = new MetricCommitsPerIssue(metricDescription, min, max);
		assertTrue(metricDescription == metricCommitsPerIssue.getDescription(), "Expected another description");
		assertTrue(min == metricCommitsPerIssue.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricCommitsPerIssue.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricCommitsPerIssue.getName(), "Expected another name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#MetricCommitsPerIssue(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricCommitsPerIssueNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricCommitsPerIssue(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * "CI = TNI/TNC. CI = Commits per issue.TNI = Total number of issues. TNC = Total number of commits"
	 */
	@ParameterizedTest(name= "[{index}]: TNI: {0}, TNC: {1}, Calculable: {2}, Test Case: {3}")
	@MethodSource
	public void testCheck(Integer totalNumberOfIssues, Integer totalNumberOfCommits, Boolean expectedValue, String testCase) {
		Repository repository = new Repository("", "", 0, totalNumberOfIssues, totalNumberOfCommits, 0, null, null, 0);
		assertEquals(expectedValue, metricCommitsPerIssue.check(repository), 
				"Should return " + expectedValue + 
				" when totalNumberOfIssues=" + String.valueOf(totalNumberOfIssues) +
				", totalNumberOfCommits=" + String.valueOf(totalNumberOfCommits) +
				". Test Case: (" + testCase + ")");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricCommitsPerIssue#run(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "run" method for values in this formula: <br/>
	 * "CI = TNI/TNC. CI = Commits per issue.TNI = Total number of issues. TNC = Total number of commits"
	 */
	@ParameterizedTest(name = "[{index}]: TNI: {0}, TNC: {1}, Test Case: {2}")
	@MethodSource
	public void testRun(Integer totalNumberOfIssues, Integer totalNumberOfCommits, String testCase) {
		Repository repository = new Repository("", "", 0, totalNumberOfIssues, totalNumberOfCommits, 0, null, null, 0);
		IValue expected = new ValueDecimal((double) (totalNumberOfIssues / totalNumberOfCommits));
		IValue actual = metricCommitsPerIssue.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation");
	}
	
	/**
	 * Arguments for {@link #testCheck(Integer, Integer, Boolean, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "CI = TNI/TNC. CI = Commits per issue.TNI = Total number of issues. TNC = Total number of commits"
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return totalNumberOfIssues, totalNumberOfCommits, expectedValue
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, true, "TNI, TNC = Integer.MAX_VALUE"),
				Arguments.of(Integer.MAX_VALUE, 1, true, "TNI = Integer.MAX_VALUE"),
				Arguments.of(1, Integer.MAX_VALUE, true, "TNC = Integer.MAX_VALUE"),
				Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE, false, "TNI, TNC = Integer.MIN_VALUE"),
				Arguments.of(Integer.MIN_VALUE, 1, false, "TNI = Integer.MIN_VALUE"),
				Arguments.of(1, Integer.MIN_VALUE, false, "TNC = Integer.MIN_VALUE"),
				Arguments.of(0, 0, false, "TNI, TNC = 0"),
				Arguments.of(0, 1, true, "TNI = 0"),
				Arguments.of(10, 0, false, "TNC = 0"),
				Arguments.of(1, 1, true, "TNI = TNC.TNI, TNC > 0"),
				Arguments.of(10, 5, true, "TNI > TNC.TNI, TNC > 0"),
				Arguments.of(12, 432, true, "TNI < TNC.TNI, TNC > 0"),
				Arguments.of(-1, -1, false, "TNI = TNC.TNI, TNC < 0"),
				Arguments.of(-10, -5, false, "TNI < TNC.TNI, TNC < 0"),
				Arguments.of(-12, -432, false, "TNI > TNC.TNI, TNC < 0"),
				Arguments.of(-10, 1, false, "TNI < 0"),
				Arguments.of(10, -23, false, "TNC < 0"),
				Arguments.of(null, null, false, "TNI, TNC = null"),
				Arguments.of(null, 5, false, "TNI = null"),
				Arguments.of(1, null, false, "TNC = null")
		);
	}
	
	/**
	 * Arguments for {@link #testRun(Integer, Integer, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "CI = TNI/TNC. CI = Commits per issue.TNI = Total number of issues. TNC = Total number of commits"
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return TNI, TNC
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, "TNI, TNC = Integer.MAX_VALUE"),
				Arguments.of(Integer.MAX_VALUE, 1, "TNI = Integer.MAX_VALUE"),
				Arguments.of(1, Integer.MAX_VALUE, "TNC = Integer.MAX_VALUE"),
				Arguments.of(0, 1, "TNI = 0"),
				Arguments.of(1, 1, "TNI = TNC.TNI, TNC > 0"),
				Arguments.of(10, 5, "TNI > TNC.TNI, TNC > 0"),
				Arguments.of(12, 432, "TNI < TNC.TNI, TNC > 0")
		);
	}
}
