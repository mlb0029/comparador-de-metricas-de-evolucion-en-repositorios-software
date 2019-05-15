package gui.views;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;

import gui.common.MetricsService;
import gui.common.RepositoriesService;
import metricsengine.Measure;
import model.Repository;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * View that allows you to work with a list of repositories.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListView extends VerticalLayout {
	
	private static final long serialVersionUID = 4840032243533665026L;

	private AddNewRepositoryForm addNewRepositoryFormDialog;
	private TextField searchTextField;
	private Button addNewRepositoryButton;
	private Grid<Repository> repositoriesGrid;
	private ListDataProvider<Repository> repositoriesDataProvider;

	/**
	 * Initializes all components of the view.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoriesListView() {
		repositoriesDataProvider = DataProvider.ofCollection(RepositoriesService.getInstance().getRepositories());
		
		addNewRepositoryFormDialog = new AddNewRepositoryForm();
		addNewRepositoryFormDialog.onAddRepositoryListener(e -> updateGrid());
		
		addNewRepositoryButton = new Button("Repository", new Icon(VaadinIcon.PLUS));
		addNewRepositoryButton.setWidth("10%");
		addNewRepositoryButton.addClickListener(e -> addNewRepositoryFormDialog.open());
		
		searchTextField = new TextField();
		searchTextField.setPlaceholder("Search");
		searchTextField.setWidth("90%");
		searchTextField.setClearButtonVisible(true);
		searchTextField.setValueChangeMode(ValueChangeMode.EAGER);
		searchTextField.addValueChangeListener(e -> filter());
		
		HorizontalLayout searchBarLayout = new HorizontalLayout(searchTextField, addNewRepositoryButton);
		searchBarLayout.setWidthFull();
		
		repositoriesGrid = new Grid<Repository>(Repository.class);
		repositoriesGrid.setWidthFull();
		repositoriesGrid.setDataProvider(repositoriesDataProvider);
		repositoriesGrid.setColumns();
		repositoriesGrid.setSelectionMode(SelectionMode.NONE);
		repositoriesGrid.addComponentColumn(repository -> createRemoveButton(repository))
				.setWidth("5%");
		repositoriesGrid.addColumn(TemplateRenderer.<Repository> of(
				"<a href=\"[[item.url]]\">[[item.name]]</a>")
				.withProperty("url", Repository::getUrl)
				.withProperty("name", Repository::getName)
				)
				.setWidth("20%")
				.setComparator(Repository::getName)
				.setHeader("Repository");
		repositoriesGrid.addColumn(r -> formatDateShortEs(r.getLastCalculatedMetrics().getDate()))
			.setHeader("Date")
			.setWidth("8%");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricTotalNumberOfIssues()))
			.setHeader("I1");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricCommitsPerIssue()))
			.setHeader("I2");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricPercentageOfClosedIssues()))
			.setHeader("I3");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricAverageDaysToCloseAnIssue()))
			.setHeader("TI1");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricAverageDaysBetweenCommits()))
			.setHeader("TC1");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricDaysBetweenFirstAndLastCommit()))
			.setHeader("TC2");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricChangeActivityRange()))
			.setHeader("TC3");
		repositoriesGrid.addColumn(r -> formatMeasureNumberTwoDecimals(r.getLastCalculatedMetrics().getMetricPeakChange()))
			.setHeader("C1");
		repositoriesGrid.addComponentColumn(repository -> createCalculateButton(repository))
			.setWidth("5%");
		repositoriesGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
		updateGrid();
		
		add(searchBarLayout, repositoriesGrid);
		setSizeFull();
	}
	
	private void updateGrid() {
		repositoriesDataProvider.refreshAll();
	}

	/**
	 * Filter event.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void filter() {
		repositoriesDataProvider.addFilter(repository -> repository.getName().toLowerCase().contains(searchTextField.getValue().toLowerCase()));
	}

	private Button createRemoveButton(Repository repository) {
		Button button = new Button();
		button.setIcon(new Icon(VaadinIcon.TRASH));
		button.addClickListener( event -> {
			repositoriesDataProvider.getItems().remove(repository);
			updateGrid();
		});
		return button;
	}
	
	private Button createCalculateButton(Repository repository) {
		Button button = new Button();
		button.setIcon(new Icon(VaadinIcon.CALC_BOOK));
		button.addClickListener( event -> {
			try {
				MetricsService.getMetricsService().calculateMetricsRepository(repository);
				updateGrid();
			} catch (RepositoryDataSourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return button;
	}

	private String formatDateShortEs(Date date) {
		return SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.forLanguageTag("es-ES")).format(date);
	}

	private String formatMeasureNumberTwoDecimals(Measure measure) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(Double.parseDouble(measure.getMeasuredValue().toString()));
	}
}
