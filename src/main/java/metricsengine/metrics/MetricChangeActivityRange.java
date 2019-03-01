/**
 * 
 */
package metricsengine.metrics;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * Computes the metric: Change Activity Range Per Month.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 */
public class MetricChangeActivityRange extends AMetric {
	/**
	 * Constructor that initializes the metric with default values defined by the programmer.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricChangeActivityRange() {
		super(new MetricDescription(
					"TC3 - Change Activity Range",
					"Number of changes relative to the number of months in the period",
					"Jacek Ratzinger",
					"2007",
					"Time constraints",
					"How many changes per month?",
					"CAR = TNC / NM. CAR = Number of changes relative to the number of months in the period, TNC = Total number of commits, NM = Number of months",
					"TNC, NM: Repository",
					"CAR > 0, better medium values",
					MetricDescription.EnumTypeOfScale.RATIO,
					"TNC:Count, NM: Count"), 
				new ValueDecimal(6.0), 
				new ValueDecimal(26.4));
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricChangeActivityRange(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		return  repository != null &&
				repository.getTotalNumberOfCommits() != null &&
				repository.getLifeSpanMonths() != null;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		Integer lifeSpanMonths = (repository.getLifeSpanMonths() == 0?repository.getLifeSpanMonths():1);
		double result = repository.getTotalNumberOfCommits() / lifeSpanMonths;
		return new ValueDecimal(result);
	}

}
