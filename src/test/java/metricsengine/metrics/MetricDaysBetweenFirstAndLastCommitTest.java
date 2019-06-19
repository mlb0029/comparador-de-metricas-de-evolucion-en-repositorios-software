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
import metricsengine.values.ValueInteger;

/**
 * Unit test for {@link metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit}.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricDaysBetweenFirstAndLastCommitTest {

	/**
	 * Metric under test.
	 */
	private static MetricDaysBetweenFirstAndLastCommit metricDaysBetweenFirstAndLastCommit;
	
	/**
	 * Instance the metric under test before all the tests are executed.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		metricDaysBetweenFirstAndLastCommit = new MetricDaysBetweenFirstAndLastCommit();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit#MetricDaysBetweenFirstAndLastCommit()}.
	 */
	@Test
	public void testMetricDaysBetweenFirstAndLastCommit() {
		assertEquals(MetricDaysBetweenFirstAndLastCommit.DEFAULT_METRIC_DESCRIPTION, metricDaysBetweenFirstAndLastCommit.getDescription(), "Expected default static description");
		assertEquals(MetricDaysBetweenFirstAndLastCommit.DEFAULT_MIN_VALUE , metricDaysBetweenFirstAndLastCommit.getValueMinDefault(), "Expected default static min value");
		assertEquals(MetricDaysBetweenFirstAndLastCommit.DEFAULT_MAX_VALUE, metricDaysBetweenFirstAndLastCommit.getValueMaxDefault(), "Expected default static max value");
		assertEquals(MetricDaysBetweenFirstAndLastCommit.DEFAULT_METRIC_DESCRIPTION.getName(), metricDaysBetweenFirstAndLastCommit.getName(), "Expected default static name");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit#MetricDaysBetweenFirstAndLastCommit(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricDaysBetweenFirstAndLastCommitMetricDescriptionValueMinValueMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricDaysBetweenFirstAndLastCommit = new MetricDaysBetweenFirstAndLastCommit(metricDescription, min, max);
		assertTrue(metricDescription == metricDaysBetweenFirstAndLastCommit.getDescription(), "Expected another description");
		assertTrue(min == metricDaysBetweenFirstAndLastCommit.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricDaysBetweenFirstAndLastCommit.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricDaysBetweenFirstAndLastCommit.getName(), "Expected another name");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit#MetricDaysBetweenFirstAndLastCommit(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricDaysBetweenFirstAndLastCommitNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricDaysBetweenFirstAndLastCommit(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit#check(datamodel.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * "DBFLC = Max(CD) - Min(CD) (in days). DBFLC = Days between the first and the last commit, CD = Vector with de commits dates"
	 */
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argsForCheckMethodInCommitDates")
	public void testCheck(Integer totalNumberOfCommits, Set<Date> commitDates, Boolean expectedValue, String testCase) {
		Repository repository = new Repository("URL", "Test", 1);
		repository.setRepositoryInternalMetrics(new RepositoryInternalMetrics(null, totalNumberOfCommits, null, null, commitDates, null));
		assertEquals(expectedValue, metricDaysBetweenFirstAndLastCommit.check(repository), 
				"Should return " + expectedValue +
				" when totalNumberOfCommits=" + String.valueOf(totalNumberOfCommits) +
				", commitDates=" + commitDates +
				". Test Case: (" + testCase + ")");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit#run(datamodel.Repository)}.
	 * <p>
	 * Check "run" method for values in this formula: <br/>
	 * "DBFLC = Max(CD) - Min(CD) (in days). DBFLC = Days between the first and the last commit, CD = Vector with de commits dates"
	 */
	@ParameterizedTest
	@MethodSource
	public void testRun(Integer totalNumberOfCommits, Set<Date> commitDates, IValue expected, String testCase) {
		Repository repository = new Repository("URL", "Test", 1);
		repository.setRepositoryInternalMetrics(new RepositoryInternalMetrics(null, totalNumberOfCommits, null, null, commitDates, null));
		IValue actual = metricDaysBetweenFirstAndLastCommit.run(repository);
		assertEquals(expected.getValueString(), actual.getValueString(), "Incorrect calculation");
	}
	
	/**
	 * Arguments for {@link #testRun(Integer, Set, IValue, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "DBFLC = Max(CD) - Min(CD) (in days). DBFLC = Days between the first and the last commit, CD = Vector with de commits dates"
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
				Arguments.of(setSameDay.size(), setSameDay, new ValueInteger(0), "CD with same date"),
				Arguments.of(setAnyDates.size(), setAnyDates, new ValueInteger(35), "Any CD")
		);
	}
}