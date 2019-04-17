package gui.views;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import gui.common.RepositoriesListService;
import repositorydatasource.model.Repository;

@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = -451691358181468519L;
	
	public MainView() {
		try {
			RepositoriesListService.getInstance().setRepositories(getTestSource());
			RepositoriesListView repositoryListView = new RepositoriesListView();
			add(repositoryListView);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}
	
//    /**
//     * Initializes the repository data source.
//     * 
//     * @author Miguel Ángel León Bardavío - mlb0029
//     * @throws RepositoryDataSourceException
//     */
//    private static void initializeRepositoryDataSource() throws RepositoryDataSourceException {
//    	repositoryDataSource = new GitLabRepositoyDataSourceFactory().createRepositoryDataSource();
//    }
    
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
    
    /**
     * DELETE. Test repositories for grid.
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     * @return
     */
    private static Collection<Repository> getTestSource(){
    	Collection<Repository> repositories = new ArrayList<Repository>();
    	Repository repository1 = new Repository("urlA", "Abcd", 1, 2, 3, 4, null, null, 5);
    	Repository repository2 = new Repository("urlB", "bcdA", 2, 2, 3, 4, null, null, 5);
    	Repository repository3 = new Repository("urlC", "cde", 3, 2, 3, 4, null, null, 5);
    	Repository repository4 = new Repository("urlCD", "Efg", 4, 2, 3, 4, null, null, 5);
    	Repository repository5 = new Repository("urlAD", "HIJ", 5, 2, 3, 4, null, null, 5);
    	repositories.add(repository1);
    	repositories.add(repository2);
    	repositories.add(repository3);
    	repositories.add(repository4);
    	repositories.add(repository5);
    	return repositories;
    }
}
