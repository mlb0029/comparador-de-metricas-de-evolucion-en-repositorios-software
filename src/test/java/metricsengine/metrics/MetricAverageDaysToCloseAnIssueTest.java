package metricsengine.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
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
 * Unit test for {@link metricsengine.metrics.MetricAverageDaysToCloseAnIssue}.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricAverageDaysToCloseAnIssueTest {

	/**
	 * Metric under test.
	 */
	private static MetricAverageDaysToCloseAnIssue metricAverageDaysToCloseAnIssue;
	
	/**
	 * Instance the metric under test before all the tests are executed.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		metricAverageDaysToCloseAnIssue = new MetricAverageDaysToCloseAnIssue();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysToCloseAnIssue#MetricAverageDaysToCloseAnIssue()}.
	 */
	@Test
	public void testMetricAverageDaysToCloseAnIssue() {
		assertEquals(MetricAverageDaysToCloseAnIssue.DEFAULT_METRIC_DESCRIPTION, metricAverageDaysToCloseAnIssue.getDescription(), "Expected default static description");
		assertEquals(MetricAverageDaysToCloseAnIssue.DEFAULT_MIN_VALUE , metricAverageDaysToCloseAnIssue.getValueMinDefault(), "Expected default static min value");
		assertEquals(MetricAverageDaysToCloseAnIssue.DEFAULT_MAX_VALUE, metricAverageDaysToCloseAnIssue.getValueMaxDefault(), "Expected default static max value");
		assertEquals(MetricAverageDaysToCloseAnIssue.DEFAULT_METRIC_DESCRIPTION.getName(), metricAverageDaysToCloseAnIssue.getName(), "Expected default static name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysToCloseAnIssue#MetricAverageDaysToCloseAnIssue(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricAverageDaysToCloseAnIssueDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricAverageDaysToCloseAnIssue = new MetricAverageDaysToCloseAnIssue(metricDescription, min, max);
		assertTrue(metricDescription == metricAverageDaysToCloseAnIssue.getDescription(), "Expected another description");
		assertTrue(min == metricAverageDaysToCloseAnIssue.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricAverageDaysToCloseAnIssue.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricAverageDaysToCloseAnIssue.getName(), "Expected another name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysToCloseAnIssue#MetricAverageDaysToCloseAnIssue(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricAverageDaysToCloseAnIssueNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricAverageDaysToCloseAnIssue(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysToCloseAnIssue#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * "ADCI = SUM(DCI) / NCI. ADCI = Average of days to close an issue. NCI = Number of closed issues. DCI = Vector with the days it took to close each issue."
	 */
	@ParameterizedTest(name= "[{index}]: DCI: {0}, NCI: {1}, Calculable: {2}, Test Case: {3}")
	@MethodSource
	public void testCheck(List<Integer> daysToCloseEachIssue, Integer numberOfClosedIssues, Boolean expectedValue, String testCase) {
		Repository repository = new Repository("", "", 0, 0, 0, numberOfClosedIssues, daysToCloseEachIssue, null, 0);
		assertEquals(expectedValue, metricAverageDaysToCloseAnIssue.check(repository), 
				"Should return " + expectedValue +
				" when daysToCloseEachIssue=" + String.valueOf(daysToCloseEachIssue) +
				", numberOfClosedIssues=" + numberOfClosedIssues +
				". Test Case: (" + testCase + ")");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysToCloseAnIssue#run(repositorydatasource.model.Repository)}.
	 * <p>
	 *  Check "run" method for values in this formula: <br/>
	 * "ADCI = SUM(DCI) / NCI. ADCI = Average of days to close an issue. NCI = Number of closed issues. DCI = Vector with the days it took to close each issue."
	 */
	@ParameterizedTest(name= "[{index}]: DCI: {0}, NCI: {1}, Test Case: {3}")
	@MethodSource
	public void testRun(List<Integer> daysToCloseEachIssue, Integer numberOfClosedIssues, String testCase) {
		Repository repository = new Repository("", "", 0, 0, 0, numberOfClosedIssues, daysToCloseEachIssue, null, 0);
		Integer sumDays = 0;
		for (Integer numDays : daysToCloseEachIssue) {sumDays += numDays;}
		
		IValue expected = new ValueDecimal((double) sumDays / numberOfClosedIssues);
		IValue actual = metricAverageDaysToCloseAnIssue.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation");
	}

	/**
	 * Arguments for {@link #testCheck(List, Integer, Boolean, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "ADCI = SUM(DCI) / NCI. ADCI = Average of days to close an issue. NCI = Number of closed issues. DCI = Vector with the days it took to close each issue."
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return daysToCloseEachIssue, numberOfClosedIssues, expectedValue, testCase
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		Integer[] lEmpty = {};
		Integer[] lOK = {3,65,21,75,0,12,436,1552,0};
		Integer[] lOK0 = {0,0,0,0,0};
		Integer[] lWithNegativeNumbers = {1,2,-5,2,5,8,123,-23};
		Integer[] lWithNulls = {1,5,432, 1372, null, 2, 90, null, 1};
		return Stream.of(
				Arguments.of(null, 1, false, "DCI = NULL"),
				Arguments.of(Arrays.asList(lOK), null, false, "NCI = NULL"),
				Arguments.of(null, null, false, "DCI, NCI = NULL"),
				Arguments.of(Arrays.asList(lOK), 1, false, "DCI.size != NCI"),
				Arguments.of(Arrays.asList(lEmpty), 0, false, "DCI.size == NCI == 0"),
				Arguments.of(Arrays.asList(lWithNegativeNumbers), lWithNegativeNumbers.length, false, "DCI.element = x < 0"),
				Arguments.of(Arrays.asList(lWithNulls), lWithNulls.length, false, "DCI.element = null"),
				Arguments.of(Arrays.asList(lOK), lOK.length, true, "DCI , NCI, All DCI.elements != null; All DCI.elements >= 0, NCI = DCI.size > 0"),
				Arguments.of(Arrays.asList(lOK0), lOK0.length, true, "DCI , NCI, All DCI.elements != null; All DCI.elements = 0, NCI = DCI.size > 0")
		);
	}
	
	/**
	 * Arguments for {@link #testRun(List, Integer, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "ADCI = SUM(DCI) / NCI. ADCI = Average of days to close an issue. NCI = Number of closed issues. DCI = Vector with the days it took to close each issue."
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return daysToCloseEachIssue, numberOfClosedIssues, testCase
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() {
		Integer[] lOK = {3,65,21,75,0,12,436,1552,0};
		Integer[] lOK0 = {0,0,0,0,0};
		return Stream.of(
				Arguments.of(Arrays.asList(lOK), lOK.length, "DCI , NCI, All DCI.elements != null; All DCI.elements >= 0, NCI = DCI.size > 0"),
				Arguments.of(Arrays.asList(lOK0), lOK0.length, "DCI , NCI, All DCI.elements != null; All DCI.elements = 0, NCI = DCI.size > 0")
		);
	}
}