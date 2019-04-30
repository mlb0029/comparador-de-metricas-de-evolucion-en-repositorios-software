package gui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = -451691358181468519L;
	
	public MainView() {
		try {
			RepositoriesListView repositoryListView = new RepositoriesListView();
			add(repositoryListView);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}
    
//    /**
//     * Initializes the default metric profile.
//     * 
//     * @author Miguel Ángel León Bardavío - mlb0029
//     */
//    private static void initializeDefaultMetricProfile() {
//    	defaultMetricProfile = new MetricProfile("DEFAULT");
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricTotalNumberOfIssues()));
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricCommitsPerIssue()));
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPercentageClosedIssues()));
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysToCloseAnIssue()));
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysBetweenCommits()));
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricDaysBetweenFirstAndLastCommit()));
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricChangeActivityRange()));
//    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPeakChange()));
//    }

}
