package gui.views;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;

import app.MetricsService;
import app.RepositoriesService;
import datamodel.Repository;
import metricsengine.IMetric;
import metricsengine.Measure;
import metricsengine.MetricsResults;
import metricsengine.metrics.MetricAverageDaysBetweenCommits;
import metricsengine.metrics.MetricAverageDaysToCloseAnIssue;
import metricsengine.metrics.MetricChangeActivityRange;
import metricsengine.metrics.MetricCommitsPerIssue;
import metricsengine.metrics.MetricDaysBetweenFirstAndLastCommit;
import metricsengine.metrics.MetricPeakChange;
import metricsengine.metrics.MetricPercentageClosedIssues;
import metricsengine.metrics.MetricTotalNumberOfIssues;
import metricsengine.values.IValue;
import metricsengine.values.ValueDecimal;
import metricsengine.values.ValueInteger;
import metricsengine.values.ValueUncalculated;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * View that allows you to work with a list of repositories.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListView extends VerticalLayout {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoriesListView.class);
	
	private static final long serialVersionUID = 4840032243533665026L;

	private AddNewRepositoryForm addNewRepositoryFormDialog;
	private TextField searchTextField;
	private Button addNewRepositoryButton;
	private Button saveButton;
	private Button uploadButton;
	private Grid<Repository> repositoriesGrid;
	private ListDataProvider<Repository> repositoriesDataProvider;

	/**
	 * Initializes all components of the view.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoriesListView() {
		repositoriesDataProvider = DataProvider.ofCollection(RepositoriesService.getInstance().getRepositories());
		
		searchTextField = new TextField();
		searchTextField.setPlaceholder("Search");
		searchTextField.setWidth("80%");
		searchTextField.setClearButtonVisible(true);
		searchTextField.setValueChangeMode(ValueChangeMode.EAGER);
		searchTextField.addValueChangeListener(e -> filter());
		
		addNewRepositoryFormDialog = new AddNewRepositoryForm();
		addNewRepositoryFormDialog.onAddRepositoryListener(e -> updateGrid());
		
		addNewRepositoryButton = new Button("Repository", new Icon(VaadinIcon.PLUS));
		addNewRepositoryButton.setWidth("10%");
		addNewRepositoryButton.addClickListener(e -> addNewRepositoryFormDialog.open());
		
		saveButton = new Button(new Icon(VaadinIcon.CLOUD_DOWNLOAD_O));
		saveButton.setWidth("5%");
		uploadButton = new Button(new Icon(VaadinIcon.CLOUD_UPLOAD_O));
		uploadButton.setWidth("5%");
		
		HorizontalLayout searchBarLayout = new HorizontalLayout(searchTextField, addNewRepositoryButton, saveButton, uploadButton);
		searchBarLayout.setWidthFull();
		
		repositoriesGrid = new Grid<Repository>(Repository.class);
		repositoriesGrid.setWidthFull();
		repositoriesGrid.setDataProvider(repositoriesDataProvider);
		repositoriesGrid.setColumns();
		repositoriesGrid.setSelectionMode(SelectionMode.NONE);
		repositoriesGrid.addComponentColumn(repository -> createRemoveButton(repository))
				.setKey("removeButtonColumn")
				.setSortable(false)
				.setWidth("5%");
		repositoriesGrid.addComponentColumn(r -> new Anchor(r.getUrl(), r.getName()))
				.setKey("repositoryNameColumn")
				.setWidth("20%")
				.setSortable(true)
				.setComparator(Repository::getName)
				.setHeader("Repository");
		repositoriesGrid.addColumn(this::getLastMeasurementDate)
			.setKey("lastMeasurementDateColumn")
			.setHeader("Date")
			.setSortable(true)
			.setComparator(r -> r.getRepositoryInternalMetrics().getDate())
			.setWidth("8%");
		Grid.Column<Repository> i1MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricTotalNumberOfIssues.class))
			.setKey("i1MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricTotalNumberOfIssues.class))
			.setHeader("I1");
		Grid.Column<Repository> i2MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricCommitsPerIssue.class))
			.setKey("i2MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricCommitsPerIssue.class))
			.setHeader("I2");
		Grid.Column<Repository> i3MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricPercentageClosedIssues.class))
			.setKey("i3MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricPercentageClosedIssues.class))
			.setHeader("I3");
		Grid.Column<Repository> ti1MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricAverageDaysToCloseAnIssue.class))
			.setKey("ti1MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricAverageDaysToCloseAnIssue.class))
			.setHeader("TI1");
		Grid.Column<Repository> tc1MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricAverageDaysBetweenCommits.class))
			.setKey("tc1MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricAverageDaysBetweenCommits.class))
			.setHeader("TC1");
		Grid.Column<Repository> tc2MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricDaysBetweenFirstAndLastCommit.class))
			.setKey("tc2MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricDaysBetweenFirstAndLastCommit.class))
			.setHeader("TC2");
		Grid.Column<Repository> tc3MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricChangeActivityRange.class))
			.setKey("tc3MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricChangeActivityRange.class))
			.setHeader("TC3");
		Grid.Column<Repository> c1MetricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, MetricPeakChange.class))
			.setKey("c1MetricColumn")
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(MetricPeakChange.class))
			.setHeader("C1");
		repositoriesGrid.addComponentColumn(repository -> createCalculateButton(repository))
			.setKey("calculateButtonColumn")
			.setWidth("5%");
		repositoriesGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
		repositoriesGrid.setMultiSort(true);
		
		HeaderRow metricsClassification = repositoriesGrid.prependHeaderRow();
		
		Div procOrientHeader = new Div(new Span("Process Orientation"));
		procOrientHeader.getStyle().set("text-align", "right");
		procOrientHeader.setSizeFull();
		metricsClassification.join(i1MetricColumn, i2MetricColumn, i3MetricColumn).setComponent(procOrientHeader);
		
		Div timeConstraintsHeader = new Div(new Span("Time Constraints"));
		timeConstraintsHeader.getStyle().set("text-align", "right");
		timeConstraintsHeader.setSizeFull();
		metricsClassification.join(ti1MetricColumn, tc1MetricColumn, tc2MetricColumn, tc3MetricColumn, c1MetricColumn).setComponent(timeConstraintsHeader);
		
		
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

	private String getLastMeasurementDate(Repository repository) {
		String retorno = "";
		
		MetricsResults mr = repository.getLastMetricsResults();
		if (mr == null ) retorno = ValueUncalculated.VALUE;
		retorno = formatDateShortEs(mr.getLastModificationDate());
		return retorno;
	}

	private String getLastValueMeasuredForMetric(Repository repository, Class<? extends IMetric> metricType) {
		final String notCalculated = "NC";
		final String notRepository = "NR";
		String retorno = "";
		
		if (repository == null)	retorno = notRepository;
		MetricsResults mr = repository.getLastMetricsResults();
		if (mr == null ) retorno = notCalculated;
		Measure measure = mr.getMeasureForTheMetric(metricType);
		if (measure == null) retorno = notCalculated;
		IValue value = measure.getMeasuredValue();
		if(value != null) retorno = notCalculated;
		if (value instanceof ValueInteger || value instanceof ValueDecimal)
			retorno = formatStringTwoDecimals(value.valueToString());
		return retorno;
	}

	private String formatDateShortEs(Date date) {
		return SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.forLanguageTag("es-ES")).format(date);
	}

	private String formatStringTwoDecimals(String str) {
		try {
			String valueFormated = "";
			NumberFormat numberFormat = NumberFormat.getNumberInstance();
			numberFormat.setMaximumFractionDigits(2);
			if (NumberUtils.isNumber(str)) {
				valueFormated = numberFormat.format(Double.parseDouble(str));				
			}
			return valueFormated;
		} catch (Exception e) {
			LOGGER.error("Error formatting the string: " + str + ". Exception occurred: " + e.getMessage());
			throw e;
		}
	}
}
