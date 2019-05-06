package metricsengine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import metricsengine.MetricDescription.EnumTypeOfScale;
import metricsengine.metrics.MetricTotalNumberOfIssues;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;
import model.Repository;

/**
 * Test class for {@link AMetric}.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class AMetricTest {

	/**
	 * Test metric.
	 */
	private static AMetric testMetric;
	
	/**
	 * Daughter class of AMetric to perform tests.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 *
	 */
	private static class TestMetric extends AMetric{
		
		/**
		 * Description of the metric.
		 */
		public static final MetricDescription DESCRIPTION = new MetricDescription("", "", "", "", "", "", "", "", "", EnumTypeOfScale.ABSOLUTE, "");
		
		/**
		 * Minimum acceptable value.
		 */
		public static final IValue MINVALUE = new ValueInteger(0);
		
		/**
		 * Maximum acceptable value.
		 */
		public static final IValue MAXVALUE = new ValueInteger(10);
		
		/**
		 * 
		 * Constructor that initializes the metric with default values.
		 *
		 * @author Miguel Ángel León Bardavío - mlb0029
		 */
		public TestMetric() {
			super(DESCRIPTION, MINVALUE, MAXVALUE);
		}
		
		/**
		 * Constructor that only invokes the parent constructor.
		 *
		 * @author Miguel Ángel León Bardavío - mlb0029
		 * @param description Description of the metric.
		 * @param valueMinDefault Min value.
		 * @param valueMaxDefault Max value.
		 */
		public TestMetric(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
			super(description, valueMinDefault, valueMaxDefault);
		}
	
		/* (non-Javadoc)
		 * @see metricsengine.AMetric#check(repositorydatasource.model.Repository)
		 */
		@Override
		protected Boolean check(Repository repository) {
			return repository != null && repository.getId() != null && repository.getId() > 0;
		}
	
		/* (non-Javadoc)
		 * @see metricsengine.AMetric#run(repositorydatasource.model.Repository)
		 */
		@Override
		protected IValue run(Repository repository) {
			return new ValueInteger(repository.getId());
		}
	}

	/**
	 * MethodName.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		testMetric = new TestMetric();
	}

	/**
	 * Test method for {@link metricsengine.AMetric#AMetric(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithArguments")
	public void testAMetric(MetricDescription metricDescription, IValue min, IValue max) {
		AMetric testMetric = new TestMetric(metricDescription, min, max);
		assertTrue(metricDescription == testMetric.getDescription(), "Expected another description");
		assertTrue(min == testMetric.getValueMinDefault(), "Expected another min value");
		assertTrue(max == testMetric.getValueMaxDefault(), "Expected another max value");
		assertEquals(metricDescription.getName(), testMetric.getName(), "Expected another name");
	}
	
	/**
	 * Test method for {@link metricsengine.AMetric#AMetric(metricsengine.MetricDescription, metricsengine.values.IValue, metricsengine.values.IValue)}.
	* <p>
	 * Using null arguments.
	 */
	@ParameterizedTest(name = "[{index}] metricDescription = {0}, min = {1}, max = {2}")
	@MethodSource("metricsengine.metrics.ArgumentsProviders#argumentsForAMetricConstructorWithNullArguments")
	public void testAMetricNullArguments(MetricDescription metricDescription, IValue min, IValue max) {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricTotalNumberOfIssues(metricDescription, min, max);
		}, "Expected exception when null arguments");
	}
	 
	/**
	 * Test method for {@link metricsengine.AMetric#calculate(model.Repository, metricsengine.MetricConfiguration, metricsengine.MetricsResults)}.
	 * 
	 * TODO Measure Equal, ToString Method
	 */
	@Test
	@Disabled("Not Implemented")
	public void testCalculate() {
		//TODO testCalculate method
	}
	
	/**
	 * Test method for {@link metricsengine.AMetric#calculate(model.Repository, metricsengine.MetricConfiguration, metricsengine.MetricsResults)}.
	 * <p>
	 * Using null arguments.
	 */
	@ParameterizedTest(name = "[{index}] repository = {0}, min = {1}, max = {2}")
	@MethodSource
	public void testCalculateNullArgs(Repository repository, MetricConfiguration metricConfig, MetricsResults metricsResults) {	
		TestMetric testMetric = new TestMetric();
		assertThrows(IllegalArgumentException.class, () -> {
			testMetric.calculate(repository, metricConfig, metricsResults);
		}, "Expected exception when null arguments");
	}
	
	/**
	 * 
	 * Arguments for {@link #testCalculateNullArgs(Repository, MetricConfiguration, MetricsResults).}
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> testCalculateNullArgs(){
		Repository repository = new Repository("URL", "Test", 1);
		MetricConfiguration mConfiguration = new MetricConfiguration(testMetric);
		MetricsResults mResults = new MetricsResults();
		return Stream.of(
				Arguments.of(null, mConfiguration, mResults),
				Arguments.of(repository, null, mResults),
				Arguments.of(repository, mConfiguration, null),
				Arguments.of(null, null, mResults),
				Arguments.of(repository, null, null),
				Arguments.of(null, mConfiguration, null),
				Arguments.of(null, null, null)
				);
	}
}
