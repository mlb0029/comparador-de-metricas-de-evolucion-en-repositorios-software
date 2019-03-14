package metricsengine.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import metricsengine.*;
import metricsengine.MetricDescription.EnumTypeOfScale;
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
	@ParameterizedTest
	@MethodSource
	public void testMetricTotalNumberOfIssuesMetricDescriptionValorMinValorMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricTotalNumberOfIssues = new MetricTotalNumberOfIssues(metricDescription, min, max);
		assertTrue(metricDescription == metricTotalNumberOfIssues.getDescription(), "Expected another description");
		assertTrue(min == metricTotalNumberOfIssues.getValueMinDefault(), "Expected another min value");
		assertTrue(max == metricTotalNumberOfIssues.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), metricTotalNumberOfIssues.getName(), "Expected another name");
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#MetricTotalNumberOfIssues(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 * <p>
	 * With Null arguments.
	 */
	@ParameterizedTest
	@MethodSource("testMetricTotalNumberOfIssuesMetricDescriptionValorMinValorMax")
	public void testMetricTotalNumberOfIssuesNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(null, min, max);
		}, "Expected exception when null metric description");
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(metricDescription, null, max);
		}, "Expected exception when null value min");
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(metricDescription, min, null);
		}, "Expected exception when null value max");
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#check(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check "check" method for values in this formula: CI = TNI/TNC
	 */
	@ParameterizedTest(name= "[{index}]: TNI: {0}, TNC: {1}, Calculable: {2}")
	@MethodSource
	public void testCheck(Integer totalNumberOfIssues, Boolean expectedValue) {
		Repository repository = new Repository("", "", 0, totalNumberOfIssues, 0, 0, null, null, 0);
		assertEquals(expectedValue, metricTotalNumberOfIssues.check(repository), 
				"Should return false when totalNumberOfIssues=" + totalNumberOfIssues);
		
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssues#run(repositorydatasource.model.Repository)}.
	 * <p>
	 * Check that the formula: {TNI = Total number of issues} is computed correctly.
	 */
	@ParameterizedTest(name = "[{index}]: TNI: {0}")
	@MethodSource
	public void testRun(Integer totalNumberOfIssues) {
		Repository repository = new Repository("", "", 0, totalNumberOfIssues, 0, 0, null, null, 0);
		IValue expected = new ValueInteger((int)totalNumberOfIssues);
		IValue actual = metricTotalNumberOfIssues.run(repository);
		assertEquals(expected.valueToString(), actual.valueToString(), "Incorrect calculation");
	}

	/**
	 * Arguments for {@link #testMetricTotalNumberOfIssuesMetricDescriptionValorMinValorMax(MetricDescription, IValue, IValue)}
	 * <p>
	 * Returns: description, Ivalue min, Ivalue max.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testMetricTotalNumberOfIssuesMetricDescriptionValorMinValorMax(){
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
	 * @return TNI, isCalculable
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheck() {
		return Stream.of(
				Arguments.of(Integer.MAX_VALUE, true),
				Arguments.of(1, true),
				Arguments.of(0, true),
				Arguments.of(10,true),
				Arguments.of(Integer.MIN_VALUE, false),
				Arguments.of(-1, false),
				Arguments.of(-10, false),
				Arguments.of(null, false)
		);
	}
	
	/**
	 * Arguments for run tests.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return TNI
	 */
	@SuppressWarnings("unused")
	private static Stream<Integer> testRun() {
		return Stream.of(
				Integer.MAX_VALUE,
				1,
				0,
				10
		);
	}
}
