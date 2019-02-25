package metricsengine.metrics;

import metricsengine.AMetric;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import repositorydatasource.model.Repository;

/**
 * Computes the percentage of closed issues.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricPercentageClosedIssues extends AMetric {

	/**
	 * Constructor that initializes the metric with default values defined by the programmer.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricPercentageClosedIssues() {
		super(new MetricDescription(
					"I3",
					"Percentage of issues closed",
					"",
					"",
					"Process Orientation",
					"What percentage of issues have been closed?",
					"PIC = (NCI/TNI) * 100. PIC = Percentage of issues closed.TNI = Total number of issues. NCI = Number of closed issues",
					"TNI, NCI:Repository",
					"PIC >= 0 and PIC <= 100. Better large values",
					MetricDescription.EnumTypeOfScale.RATIO,
					"TNI,NCI:Count"), 
				new ValueDecimal(87.0), 
				new ValueDecimal(100.0));
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricPercentageClosedIssues(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		return repository.getTotalNumberOfIssues() != null && repository.getNumberOfClosedIssues() != null;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = (repository.getNumberOfClosedIssues() / repository.getTotalNumberOfIssues()) * 100;
		return new ValueDecimal(result);
	}

}
