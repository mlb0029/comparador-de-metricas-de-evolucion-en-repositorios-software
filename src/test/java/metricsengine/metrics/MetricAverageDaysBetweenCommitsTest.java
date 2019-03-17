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

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

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
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testMetricAverageDaysBetweenCommitsMetricDescriptionIValueIValue(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricAverageDaysBetweenCommits = new MetricAverageDaysBetweenCommits(metricDescription, min, max);
		assertTrue(metricDescription == metricAverageDaysBetweenCommits.getDescription(), "Expected another description");
		assertTrue(min == metricAverageDaysBetweenCommits.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricAverageDaysBetweenCommits.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricAverageDaysBetweenCommits.getName(), "Expected another name");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testMetricAverageDaysBetweenCommitsNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricAverageDaysBetweenCommits(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: <br/>
	 * "ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits."
	 */
	@ParameterizedTest
	@MethodSource
	public void testCheck(Integer totalNumberOfCommits, Set<Date> commitDates, Boolean expectedValue, String testCase) {
		Repository repository = new Repository("", "", 0, 0, totalNumberOfCommits, 0, null, commitDates, 0);
		assertEquals(expectedValue, metricAverageDaysBetweenCommits.check(repository), 
				"Should return " + expectedValue +
				" when totalNumberOfCommits=" + String.valueOf(totalNumberOfCommits) +
				", commitDates=" + commitDates +
				". Test Case: (" + testCase + ")");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricAverageDaysBetweenCommits#run(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "run" method for values in this formula: <br/>
	 * "ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits."
	 */
	@ParameterizedTest
	@MethodSource
	public void testRun(Integer totalNumberOfCommits, Set<Date> commitDates, IValue expected, String testCase) {
		Repository repository = new Repository("", "", 0, 0, totalNumberOfCommits, 0, null, commitDates, 0);
//		List<Date> lstCommitDates = commitDates.stream().sorted().collect(Collectors.toList());
		
//		long date1;
//		long date2;
//		int daysBetweenTwoDays;
//		int sumDates = 0;
//		double resultExpected;
//		
//		for (int i = 1; i < totalNumberOfCommits; i++) {
//			date1 = lstCommitDates.get(i-1).getTime();
//			date2 = lstCommitDates.get(i).getTime();
//			daysBetweenTwoDays = (int) ((date2 - date1) / (1000 * 60 * 60 * 24));
//			sumDates += daysBetweenTwoDays;
//		}
//		resultExpected = (double) (sumDates / (totalNumberOfCommits - 1));
		
		IValue actual = metricAverageDaysBetweenCommits.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation");
	}
	
	/**
	 * Arguments for {@link #testCheck(Integer, Set, Boolean, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits."
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return totalNumberOfCommits, commitDates, expectedValue, testCase
	 * @throws ParseException When parsing Dates
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Set<Date> setEmpty = new HashSet<Date>();
		
		Set<Date> setWithNulls = new HashSet<Date>();
		setWithNulls.add(dateFormat.parse("02/01/2019 10:00"));
		setWithNulls.add(null);
		setWithNulls.add(dateFormat.parse("20/02/2019 15:41"));

		Set<Date> setOneElement = new HashSet<Date>();
		setOneElement.add(dateFormat.parse("02/01/2019 10:00"));
		
		Set<Date> setSameDay = new HashSet<Date>();
		setSameDay.add(dateFormat.parse("02/01/2019 10:00"));
		setSameDay.add(dateFormat.parse("02/01/2019 21:00"));
		setSameDay.add(dateFormat.parse("02/01/2019 13:42"));
		
		Set<Date> setAnyDates = new HashSet<Date>();
		setAnyDates.add(dateFormat.parse("01/01/2018 00:00"));
		setAnyDates.add(dateFormat.parse("04/11/2016 14:09"));
		setAnyDates.add(dateFormat.parse("12/10/1492 23:55"));
		setAnyDates.add(dateFormat.parse("13/12/2010 11:55"));
		setAnyDates.add(dateFormat.parse("18/08/2018 18:22"));
		setAnyDates.add(dateFormat.parse("11/11/2014 23:59"));
		
		return Stream.of(
				Arguments.of(null, setAnyDates, false, "TNC = NULL"),
				Arguments.of(1, null, false, "CD = NULL"),
				Arguments.of(null, null, false, "TNC, CD = NULL"),
				Arguments.of(5, setAnyDates, false, "CD.size != TNC"),
				Arguments.of(setEmpty.size(), setEmpty, false, "CD.size == TNC == 0"),
				Arguments.of(setOneElement.size(), setOneElement, false, "CD.size == TNC == 1"),
				Arguments.of(setWithNulls.size(), setWithNulls, false, "CD.element = null"),
				Arguments.of(setSameDay.size(), setSameDay, true, "CD with same day"),
				Arguments.of(setAnyDates.size(), setAnyDates, true, "Any CD")
		);
	}
	
	/**
	 * Arguments for {@link #testRun(Integer, Set, String)}.
	 * <p>
	 * Test cases for the formula: <br/>
	 * "ADBC = SUM([i]-[i-1]; [i] = 1 -> [i] < TNC; CD)/(TNC-1) (in days). ADBC = Average of days between commits, CD = Vector with de commits dates, TNC = Total number of commits."
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return totalNumberOfCommits, commitDates, testCase
	 * @throws ParseException When parsing Dates
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
		setAnyDates.add(dateFormat.parse("04/11/2016 14:09"));
		
		return Stream.of(
				Arguments.of(setSameDay.size(), setSameDay, new ValueDecimal((double) 0), "CD with same date"),
				Arguments.of(setAnyDates.size(), setAnyDates, new ValueDecimal((double) 422), "Any CD")
		);
	}
}