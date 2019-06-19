package metricsengine.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import datamodel.Repository;
import datamodel.RepositoryInternalMetrics;
import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;

/**
 * Unit test for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits}.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricAverageDaysBetweenCommitsTest {

	/**
	 * Metric under test.
	 */
	private static MetricAverageDaysBetweenCommits metricAverageDaysBetweenCommits;
	
	/**
	 * Instance the metric under test before all the tests are executed.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		metricAverageDaysBetweenCommits = new MetricAverageDaysBetweenCommits();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#MetricAverageDaysBetweenCommits()}.
	 */
	@Test
	public void testMetricAverageDaysBetweenCommits() {
		assertEquals(MetricAverageDaysBetweenCommits.DEFAULT_METRIC_DESCRIPTION, metricAverageDaysBetweenCommits.getDescription(), "Expected default static description");
		assertEquals(MetricAverageDaysBetweenCommits.DEFAULT_MIN_VALUE , metricAverageDaysBetweenCommits.getValueMinDefault(), "Expected default static min value");
		assertEquals(MetricAverageDaysBetweenCommits.DEFAULT_MAX_VALUE, metricAverageDaysBetweenCommits.getValueMaxDefault(), "Expected default static max value");
		assertEquals(MetricAverageDaysBetweenCommits.DEFAULT_METRIC_DESCRIPTION.getName(), metricAverageDaysBetweenCommits.getName(), "Expected default static name");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#MetricAverageDaysBetweenCommits(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricAverageDaysBetweenCommitsMetricDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricAverageDaysBetweenCommits = new MetricAverageDaysBetweenCommits(metricDescription, min, max);
		assertTrue(metricDescription == metricAverageDaysBetweenCommits.getDescription(), "Expected another description");
		assertTrue(min == metricAverageDaysBetweenCommits.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricAverageDaysBetweenCommits.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricAverageDaysBetweenCommits.getName(), "Expected another name");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#check(datamodel.Repository)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricAverageDaysBetweenCommitsNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricAverageDaysBetweenCommits(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#check(datamodel.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * "ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits."
	 */
	@ParameterizedTest(name = "[{index}] TNC = {0}, CD = {1}, Test Case: {3}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argsForCheckMethodInCommitDates")
	public void testCheck(Integer totalNumberOfCommits, Set<Date> commitDates, Boolean expected, String testCase) {
		Repository repository = new Repository("URL", "Test", 1);
		repository.setRepositoryInternalMetrics(new RepositoryInternalMetrics(null, totalNumberOfCommits, null, null, commitDates, null));
		assertEquals(expected, metricAverageDaysBetweenCommits.check(repository), 
				"Should return " + expected +
				" when totalNumberOfCommits=" + String.valueOf(totalNumberOfCommits) +
				", commitDates=" + commitDates +
				". Test Case: (" + testCase + ")");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#run(datamodel.Repository)}.
	 * <p>
	 * Check "run" method for values in this formula: <br/>
	 * "ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits."
	 */
	@ParameterizedTest(name = "[{index}] TNC = {0}, CD = {1}, Test Case: {3}")
	@MethodSource
	public void testRun(Integer totalNumberOfCommits, Set<Date> commitDates, IValue expected, String testCase) {
		Repository repository = new Repository("URL", "Test", 1);
		repository.setRepositoryInternalMetrics(new RepositoryInternalMetrics(null, totalNumberOfCommits, null, null, commitDates, null));
		IValue actual = metricAverageDaysBetweenCommits.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation in test case: " + testCase);
	}

	/**
	 * Arguments for {@link #testRun(Integer, Set, IValue, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits."
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return Stream of: Integer totalNumberOfCommits, Set<Date> commitDates, IValue expected, String testCase
	 * @throws ParseException When parsing Dates fail
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testRun() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Set<Date> setSameDay = new HashSet<Date>();
		setSameDay.add(dateFormat.parse("02/01/2019 10:00"));
		setSameDay.add(dateFormat.parse("02/01/2019 21:00"));
		setSameDay.add(dateFormat.parse("02/01/2019 13:42"));
		
		Set<Date> setAnyDates = new HashSet<Date>();
		setAnyDates.add(dateFormat.parse("01/01/2018 00:00"));
		setAnyDates.add(dateFormat.parse("04/02/2018 14:09"));
		setAnyDates.add(dateFormat.parse("05/02/2018 14:09"));
		
		return Stream.of(
				Arguments.of(setSameDay.size(), setSameDay, new ValueDecimal(0.0), "CD with same date"),
				Arguments.of(setAnyDates.size(), setAnyDates, new ValueDecimal(17.5), "Any CD")
		);
	}
}