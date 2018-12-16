package metricsengine.metrics;

import metricsengine.AMetric;
import metricsengine.EnumTypeOfScale;
import metricsengine.IValue;
import metricsengine.Measure;
import metricsengine.MetricDescription;
import repositorydatasource.model.Repository;

public class MetricTotalNumberOfIssues extends AMetric {

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
	 * @see metricsengine.IMetric#calculate(repositorydatasource.model.Repository)
	 */
	@Override
	public Measure calculate(Repository repository) {
		// TODO Auto-generated method stub
		return null;
	}

}
