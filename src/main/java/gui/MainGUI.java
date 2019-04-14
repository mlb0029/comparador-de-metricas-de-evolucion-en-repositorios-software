package gui;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.annotation.WebServlet;

import com.vaadin.ui.UI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;

import gui.common.GUIConstants;

import exceptions.RepositoryDataSourceException;

import repositorydatasource.factories.GitLabRepositoyDataSourceFactory;
import repositorydatasource.model.Repository;
import repositorydatasource.rds.IRepositoryDataSource;

import metricsengine.MetricConfiguration;
import metricsengine.MetricProfile;
import metricsengine.metrics.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme(GUIConstants.MYTHEME)
public class MainGUI extends UI {

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainGUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -3944220372641205362L;
	}

	private static final long serialVersionUID = -451691358181468519L;

	/**
	 * Where to get repositories. TODO
	 */
	@SuppressWarnings("unused")
	private static IRepositoryDataSource repositoryDataSource;
	
	/**
	 * Default metrics profile.
	 */
	private static MetricProfile defaultMetricProfile;

	/**
	 * View that shows a lis of repositories.
	 */
	private static RepositoriesListView repositoryListView;
	
	
    /* (non-Javadoc)
     * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	try {
			initializeRepositoryDataSource();
			initializeDefaultMetricProfile();
			initializeRepositoriesListView();
			setContent(repositoryListView);
    	} catch (RepositoryDataSourceException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

    /**
     * Initializes the repository data source.
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     * @throws RepositoryDataSourceException
     */
    private static void initializeRepositoryDataSource() throws RepositoryDataSourceException {
    	repositoryDataSource = new GitLabRepositoyDataSourceFactory().createRepositoryDataSource();
    }
    
    /**
     * Initializes the default metric profile.
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     */
    private static void initializeDefaultMetricProfile() {
    	defaultMetricProfile = new MetricProfile("DEFAULT");
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricTotalNumberOfIssues()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricCommitsPerIssue()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPercentageClosedIssues()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysToCloseAnIssue()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricAverageDaysBetweenCommits()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricDaysBetweenFirstAndLastCommit()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricChangeActivityRange()));
    	defaultMetricProfile.addMetricConfiguration(new MetricConfiguration(new MetricPeakChange()));
    }
    
    /**
     * Initializes the repositories list view..
     * 
     * @author Miguel Ángel León Bardavío - mlb0029
     */
    private static void initializeRepositoriesListView() {
    	repositoryListView = new RepositoriesListView();
    	repositoryListView.setRepositories(getTestSource());
    }
    
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
