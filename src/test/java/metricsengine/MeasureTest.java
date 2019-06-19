package metricsengine;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import metricsengine.metrics.MetricTotalNumberOfIssues;
import metricsengine.values.ValueInteger;

/**
 * Test class for {@link metricsengine.Measure}.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MeasureTest {

	/**
	 * Test method for {@link metricsengine.Measure#Measure(metricsengine.MetricConfiguration, metricsengine.values.IValue)}.
	 * <p>
	 * Null arguments.
	 */
	@Test
	public void testMeasureNull() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		Date date = new Date();
		
		Measure measure = new Measure(null, null);
		assertNotNull(measure);
		assertNull(measure.getMetricMeasured());
		assertNull(measure.getMeasuredValue());
		assertNotNull(measure.getDateOfMeasurement());
		assertEquals(dateFormat.format(date), dateFormat.format(measure.getDateOfMeasurement()));
	}

	/**
	 * Test method for {@link metricsengine.Measure#Measure(metricsengine.MetricConfiguration, metricsengine.values.IValue)}.
	 * <p>
	 * Not null arguments.
	 */
	@Test
	public void testMeasureNotNull() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		Date date = new Date();
		
		MetricConfiguration metricConfiguration = new MetricConfiguration(new MetricTotalNumberOfIssues());
		ValueInteger valueInteger = new ValueInteger(0);
		
		Measure measure = new Measure(metricConfiguration, valueInteger);
		assertNotNull(measure);
		assertNotNull(measure.getMetricMeasured());
		assertEquals(metricConfiguration, measure.getMetricMeasured());
		assertNotNull(measure.getMeasuredValue());
		assertEquals(valueInteger, measure.getMeasuredValue());
		assertNotNull(measure.getDateOfMeasurement());
		assertEquals(dateFormat.format(date), dateFormat.format(measure.getDateOfMeasurement()));
	}
}
