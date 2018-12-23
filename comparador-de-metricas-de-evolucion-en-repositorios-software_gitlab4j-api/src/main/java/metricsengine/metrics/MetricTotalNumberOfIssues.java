package metricsengine.metrics;

import metricsengine.metrics.MetricDescription.*;
import metricsengine.values.IValue;
import metricsengine.values.ValueInteger;
import repositorydatasource.model.Repository;

public class MetricTotalNumberOfIssues extends AMetric {

	/**
	 * Constructor that sets the default values passed by parameter.
	 * 
	 * @param valueMinDefault
	 * @param valueMaxDefault
	 */
	public MetricTotalNumberOfIssues(IValue valueMinDefault, IValue valueMaxDefault) {
		super(new MetricDescription(
				"TNI",//name
				"Total number of issues",//description,
				"",//author
				"",//year
				"Process Orientation",//category
				"How many issues have been defined in the repository?",//intention
				"TNI. TNI, total number of issues.",//formula
				"Repository",//sourceOfMeasurement
				valueMinDefault.getString() + " <= TNI <= " + valueMaxDefault.getString(),//interpretation
				EnumTypeOfScale.ABSOLUTE,//typeOfScale
				"TNI = Count."//typeOfMeasure
				),
				valueMinDefault, valueMaxDefault);
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#check(repositorydatasource.model.Repository)
	 */
	@Override
	protected Boolean check(Repository repository) {
		if (repository != null && repository.getTotalNumberOfIssues() >= 0) return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see metricsengine.AMetric#run(repositorydatasource.model.Repository)
	 */
	@Override
	protected IValue run(Repository repository) {
		return new ValueInteger(repository.getTotalNumberOfIssues());
	}

}
