package gui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import exceptions.RepositoryDataSourceException;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.factories.GitLabRepositoyDataSourceFactory;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

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
@Theme("mytheme")
public class MainLayout extends UI {

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainLayout.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	
		/**
		 * Description.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 */
		private static final long serialVersionUID = -4728976094206071790L;
	}

	private static IRepositoryDataSource gitLabRDS;
	
	private static MetricProfile defaultMetricProfile;
	
    /**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -451691358181468519L;

	@Override
    protected void init(VaadinRequest vaadinRequest) {
		initializeRepositoryDataSource();
		initializeDefaultMetricProfile();
		setContent(initielizeLayout());
    }
	
	
    private static void initializeRepositoryDataSource() {
    	gitLabRDS = new GitLabRepositoyDataSourceFactory().createRepositoryDataSource();
    }
    
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
    
	private static Component initielizeLayout() {
		VerticalLayout layout = new VerticalLayout();

		Label lblStatus = new Label(gitLabRDS.getConnectionType().toString());
		PasswordField passwordField = new PasswordField("PA TOKEN");
		Button btnConnect = new Button("CONNECT!");
		Button.ClickListener btnConnectListener = new ClickListener() {

			/**
			 * Description.
			 * 
			 * @author Miguel Ángel León Bardavío - mlb0029
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				switch (gitLabRDS.getConnectionType()) {				
				case NOT_CONNECTED:
					try {
						if (passwordField.getValue() != null && passwordField.getValue() != "")
							gitLabRDS.connect(passwordField.getValue());
						else
							gitLabRDS.connect();
					} catch (RepositoryDataSourceException e) {
					} finally {
						lblStatus.setValue(gitLabRDS.getConnectionType().toString());
						btnConnect.setCaption("DISCONNECT!");
					}
					break;
				default:
					try {
						gitLabRDS.disconnect();
					} catch (RepositoryDataSourceException e) {
					} finally {
						lblStatus.setValue(gitLabRDS.getConnectionType().toString());
						btnConnect.setCaption("CONNECT!");
					}
					break;
				}
			}
		};
		btnConnect.addClickListener(btnConnectListener);
		layout.addComponents(lblStatus, passwordField, btnConnect);
		return layout;
	}
}
