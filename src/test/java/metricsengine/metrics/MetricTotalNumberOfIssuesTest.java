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
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
class MetricTotalNumberOfIssuesTest {

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
	static void setUpBeforeClass() throws Exception {
		metricTotalNumberOfIssues = new MetricTotalNumberOfIssues();
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssuesTest#MetricTotalNumberOfIssues()}.
	 */
	@Test
	void testMetricTotalNumberOfIssues() {
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_METRIC_DESCRIPTION, metricTotalNumberOfIssues.getDescription());
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_MIN_VALUE , metricTotalNumberOfIssues.getValueMinDefault());
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_MAX_VALUE, metricTotalNumberOfIssues.getValueMaxDefault());
		assertEquals(MetricTotalNumberOfIssues.DEFAULT_METRIC_DESCRIPTION.getName(), metricTotalNumberOfIssues.getName());
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssuesTest#MetricTotalNumberOfIssues(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testMetricTotalNumberOfIssuesMetricDescriptionValorMinValorMax(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric metricTotalNumberOfIssues = new MetricTotalNumberOfIssues(metricDescription, min, max);
		assertTrue(metricDescription == metricTotalNumberOfIssues.getDescription());
		assertTrue(min == metricTotalNumberOfIssues.getValueMinDefault());
		assertTrue(max == metricTotalNumberOfIssues.getValueMaxDefault());
		assertEquals(metricDescription.getName(), metricTotalNumberOfIssues.getName());
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssuesTest#MetricTotalNumberOfIssues(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest
	@MethodSource("testMetricTotalNumberOfIssuesMetricDescriptionValorMinValorMax")
	void testMetricTotalNumberOfIssuesNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(null, min, max);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(metricDescription, null, max);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(metricDescription, min, null);
		});
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssuesTest#check(repositorydatasource.model.Repository)}.
	 * With repositories of which the metric can not be calculated.
	 */
	@ParameterizedTest
	@MethodSource
	void testCheckWrong(Repository repository) {
		assertFalse(metricTotalNumberOfIssues.check(repository));
		
	}
	
	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssuesTest#check(repositorydatasource.model.Repository)}.
	 * With repositories of which the metric can be calculated.
	 */
	@ParameterizedTest
	@MethodSource
	void testCheckOK(Repository repository) {
		assertTrue(metricTotalNumberOfIssues.check(repository));
		
	}

	/**
	 * Test method for {@link metricsengine.metrics.MetricTotalNumberOfIssuesTest#run(repositorydatasource.model.Repository)}.
	 */
	@ParameterizedTest
	@MethodSource("testCheckOK")
	void testRun(Repository repository, Integer expectedVal) {
		assertEquals(expectedVal.toString(), metricTotalNumberOfIssues.run(repository).valueToString());
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
	 * Arguments for tests that expect check = false.
	 * <p>
	 * Returns a repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Stream<Repository> testCheckWrong(){
		return Stream.of(
				null,
				new Repository("", "", 0, null, 0, 0, null, null, 0),
				new Repository("", "", 0, -5, 0, 0, null, null, 0)
		);
	}
	
	/**
	 * Arguments for tests that expect check = true.
	 * <p>
	 * Returns a repository and the expected value of the run method for this repository.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCheckOK(){
		return Stream.of(
				Arguments.of(new Repository("", "", 0, 0, 0, 0, null, null, 0), 0),
				Arguments.of(new Repository("", "", 0, 10, 0, 0, null, null, 0), 10),
				Arguments.of(new Repository("", "", 0, Integer.MAX_VALUE, 0, 0, null, null, 0), Integer.MAX_VALUE)	
		);
	}
}
