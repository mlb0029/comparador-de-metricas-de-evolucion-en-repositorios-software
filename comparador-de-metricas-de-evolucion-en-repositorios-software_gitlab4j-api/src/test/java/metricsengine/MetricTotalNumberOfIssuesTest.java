/**
 * 
 */
package metricsengine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metricsengine.metrics.MetricConfiguration;
import metricsengine.metrics.MetricDescription;
import metricsengine.metrics.MetricDescription.EnumTypeOfScale;
import metricsengine.metrics.concreteMetrics.MetricTotalNumberOfIssues;
import metricsengine.values.ValueInteger;
import repositorydatasource.GitLabRepositoryDataSource;
import repositorydatasource.model.Repository;

/**
 * @author migue
 *
 */
class MetricTotalNumberOfIssuesTest {

	static GitLabRepositoryDataSource gitLabRepositoryDataSource;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		gitLabRepositoryDataSource = GitLabRepositoryDataSource.getGitLabRepositoryDataSource();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		gitLabRepositoryDataSource.disconnect();
		gitLabRepositoryDataSource = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		gitLabRepositoryDataSource.connect();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		gitLabRepositoryDataSource.disconnect();
	}

	@Test
	void test() {
		MetricTotalNumberOfIssues metric = new MetricTotalNumberOfIssues(new MetricDescription("", "", "", "", "", "", "", "", "", EnumTypeOfScale.ABSOLUTE, ""),new ValueInteger(6), new ValueInteger(44));
		assertEquals("6", metric.getValueMinDefault().getString());
		assertEquals("44", metric.getValueMaxDefault().getString());
		MetricConfiguration metricConfig = new MetricConfiguration(metric);
		assertEquals("6", metricConfig.getValueMin().getString());
		assertEquals("44", metricConfig.getValueMax().getString());
		MetricProfile metricProfile = new MetricProfile("My Profile");
		assertEquals("My Profile", metricProfile.getName());
		metricProfile.addMetricConfiguration(metricConfig);
		assertEquals(1, metricProfile.getMetricConfigurationCollection().size());
		Repository repository = gitLabRepositoryDataSource.getRepository("https://gitlab.com/mlb0029/ListaCompra");
		MetricsResults metricsResults = new MetricsResults();
		for (MetricConfiguration mc : metricProfile.getMetricConfigurationCollection()) {
			mc.calculate(repository, metricConfig, metricsResults);
		}
		assertEquals(1, metricsResults.getMeasures().size());
		assertEquals("6", metricsResults.getMeasures().iterator().next().getValue().getString());
	}

}
