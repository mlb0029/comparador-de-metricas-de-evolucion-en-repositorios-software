package metricsengine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import metricsengine.metrics.MetricTotalNumberOfIssues;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
class MetricConfigurationTest {

	/**
	 * MethodName.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * MethodName.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * MethodName.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * MethodName.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link metricsengine.MetricConfiguration#MetricConfiguration(metricsengine.AMetric, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@Test
	void testMetricConfigurationAMetricIValueIValue() {
		AMetric metric = new MetricTotalNumberOfIssues();
		IValue valueMin = new ValueInteger(0);
		IValue valueMax = new ValueInteger(10);
		MetricConfiguration metricConfiguration = new MetricConfiguration(metric, valueMin, valueMax);
		
		assertNotNull(metricConfiguration);
		assertEquals(metric, metricConfiguration.getMetric());
		assertEquals(valueMin, metricConfiguration.getValueMin());
		assertEquals(valueMax, metricConfiguration.getValueMax());
	}
	
	/**
	 * Test method for {@link metricsengine.MetricConfiguration#MetricConfiguration(metricsengine.AMetric, metricsengine.values.IValue, metricsengine.values.IValue)}.
	 */
	@Test
	void testMetricConfigurationAMetricIValueIValueNullArguments() {		
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricConfiguration(null, new ValueInteger(0), new ValueInteger(10));
		}, "The exception 'IllegalArgumentException' was expected. You should not create a metric configuration without metrics.");
		
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricConfiguration(new MetricTotalNumberOfIssues(), null, new ValueInteger(10));
		}, "The exception 'IllegalArgumentException' was expected. You should not create a metric configuration without configuration values.");
		
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricConfiguration(new MetricTotalNumberOfIssues(), new ValueInteger(0), null);
		}, "The exception 'IllegalArgumentException' was expected. You should not create a metric configuration without configuration values.");
	}

	/**
	 * Test method for {@link metricsengine.MetricConfiguration#MetricConfiguration(metricsengine.AMetric)}.
	 */
	@Test
	void testMetricConfigurationAMetric() {
		AMetric metric = new MetricTotalNumberOfIssues();
		IValue valueMin = metric.getValueMinDefault();
		IValue valueMax = metric.getValueMaxDefault();
		MetricConfiguration metricConfiguration = new MetricConfiguration(metric);
		
		assertNotNull(metricConfiguration);
		assertEquals(metric, metricConfiguration.getMetric());
		assertEquals(valueMin, metricConfiguration.getValueMin());
		assertEquals(valueMax, metricConfiguration.getValueMax());
	}
	
	/**
	 * Test method for {@link metricsengine.MetricConfiguration#MetricConfiguration(metricsengine.AMetric)}.
	 */
	@SuppressWarnings("unused")
	@Test
	void testMetricConfigurationAMetricNullArguments() {
		assertThrows(IllegalArgumentException.class, () -> {
			new MetricConfiguration(null);
		}, "The exception 'IllegalArgumentException' was expected. You should not create a metric configuration without metrics.");
	}

	/**
	 * Test method for {@link metricsengine.MetricConfiguration#calculate(repositorydatasource.model.Repository, metricsengine.MetricConfiguration, metricsengine.MetricsResults)}.
	 */
	@Disabled("Not yet implemented")
	@Test
	void testCalculate() {
//		AMetric metric = new MetricTotalNumberOfIssues();
//		IValue valueMin = metric.getValueMinDefault();
//		IValue valueMax = metric.getValueMaxDefault();
//		
//		MetricConfiguration metricConfiguration = new MetricConfiguration(metric);
//		
//		Repository repository = new Repository("", "", 0, 10, 0, 0, null, null, 0);
		// TODO MetricConfiguration Calculate
	}
}
