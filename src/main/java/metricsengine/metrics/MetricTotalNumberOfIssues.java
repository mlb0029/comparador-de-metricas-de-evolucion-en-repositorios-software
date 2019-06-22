package metricsengine.metrics;

import datamodel.Repository;
import metricsengine.MetricTemplate;
import metricsengine.MetricDescription;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;

/**
 * Compute the total number of issues of a repository.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MetricTotalNumberOfIssues extends MetricTemplate {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 5961481794180264034L;

	/**
	 * Default metric description.
	 */
	public static final MetricDescription DEFAULT_METRIC_DESCRIPTION = new MetricDescription(
			"I1",
			"Total number of issues",
			"Jacek Ratzinger",
			"2007",
			"Process Orientation",
			"How many issues have been created in the repository?",
			"TNI = Total number of issues",
			"Repository",
			"TNI >= 0. Better small values",
			MetricDescription.EnumTypeOfScale.ABSOLUTE,
			"TNI : Count");
	
	/**
	 * Minimum acceptable value.
	 */
	public static final ValueInteger DEFAULT_MIN_VALUE = new ValueInteger(6);
	
	/**
	 * Maximum acceptable value.
	 */
	public static final ValueInteger DEFAULT_MAX_VALUE = new ValueInteger(44);
	
	public static final EvaluationFunction EVALUATION_FUNCTION = 
	(measuredValue, minValue, maxValue) -> {
		try {
			Integer value, min;
			value = ((ValueInteger) measuredValue).getValue();
			min = ((ValueInteger) minValue).getValue();
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
	public MetricTotalNumberOfIssues() {
		super(DEFAULT_METRIC_DESCRIPTION, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, EVALUATION_FUNCTION);
	}
	
	/**
	 * Constructor that initializes the metric with default values passed by parameter.
	 * 
	 * @param description Description of the metric.
	 * @param valueMinDefault Minimum value by default.
	 * @param valueMaxDefault Maximum value by default.
	 */
	public MetricTotalNumberOfIssues(MetricDescription description, IValue valueMinDefault, IValue valueMaxDefault) {
		super(description, valueMinDefault, valueMaxDefault, EVALUATION_FUNCTION);
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		Integer tni = repository.getRepositoryInternalMetrics().getTotalNumberOfIssues();
		return tni != null &&
				tni >= 0;
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		return new ValueInteger(repository.getRepositoryInternalMetrics().getTotalNumberOfIssues());
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
