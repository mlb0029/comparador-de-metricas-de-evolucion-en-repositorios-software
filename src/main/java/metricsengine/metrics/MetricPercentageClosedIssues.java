package metricsengine.metrics;

import datamodel.Repository;
import metricsengine.MetricTemplate;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;

/**
 * Computes the percentage of closed issues.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricPercentageClosedIssues extends MetricTemplate {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -8897628050510118688L;

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
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
			"TNI,NCI:Count");
	
	/**
	 * Minimum acceptable value.
	 */
	public static final IValue DEFAULT_MIN_VALUE = new ValueDecimal(87.0);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final IValue DEFAULT_MAX_VALUE = new ValueDecimal(100.0);
	
	public static final EvaluationFunction EVALUATION_FUNCTION = 
			(measuredValue, minValue, maxValue) -> {
				try {
					Double value, min;
					value = MetricTemplate.formatTwoDecimals(((ValueDecimal) measuredValue).getValue());
					min = MetricTemplate.formatTwoDecimals(((ValueDecimal) minValue).getValue());
					if (value > min) return EvaluationResult.GOOD;
					else if (value == min) return EvaluationResult.WARNING;
					else return EvaluationResult.BAD;
				} catch (Exception e){
					return EvaluationResult.BAD;
				}
			};
	/**
	 * Constructor that initializes the metric with default values.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MetricPercentageClosedIssues() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, EVALUATION_FUNCTION);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricPercentageClosedIssues(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault, EVALUATION_FUNCTION);
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		Integer tni = repository.getRepositoryInternalMetrics().getTotalNumberOfIssues();
		Integer nci = repository.getRepositoryInternalMetrics().getNumberOfClosedIssues();
		return tni != null && 
				tni > 0 &&
				nci != null &&
				nci >= 0;
	}

	/* (non-Javadoc)
	 * @see metricsengine.metrics.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		double result = (double) repository.getRepositoryInternalMetrics().getNumberOfClosedIssues() * 100 / repository.getRepositoryInternalMetrics().getTotalNumberOfIssues() ;
		return new ValueDecimal(result);
	}

	@Override
	public EvaluationResult evaluate(IValue measuredValue) {
		return getEvaluationFunction().evaluate(measuredValue, getValueMinDefault(), getValueMaxDefault());
	}

	@Override
	public EvaluationFunction getEvaluationFunction() {
		return EVALUATION_FUNCTION;
	}

}
