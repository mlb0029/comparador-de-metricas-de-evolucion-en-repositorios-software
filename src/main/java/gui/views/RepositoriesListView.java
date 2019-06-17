package gui.views;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.componentfactory.Tooltip;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
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
import app.RepositoriesCollectionService;
import app.RepositoryDataSourceService;
import datamodel.Repository;
import exceptions.RepositoriesCollectionServiceException;
import exceptions.RepositoryDataSourceException;
import gui.views.MessageBoxDialog.MessageBoxCaption;
import gui.views.addrepositoryform.AddRepositoryDialog;
import metricsengine.AMetric;
import metricsengine.IMetric;
import metricsengine.Measure;
import metricsengine.MetricDescription;
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
import metricsengine.values.NumericValue;
import metricsengine.values.ValueUncalculated;
import repositorydatasource.RepositoryDataSource.EnumConnectionType;

/**
 * View that allows you to work with a list of repositories.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListView extends VerticalLayout {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoriesListView.class);
	
	private static final long serialVersionUID = 4840032243533665026L;

	private static final String NOT_CALCULATED = "NC";
	
	private AddRepositoryDialog addRepositoryFormDialog;
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
		repositoriesDataProvider = DataProvider.ofCollection(RepositoriesCollectionService.getInstance().getRepositories());
		RepositoriesCollectionService.getInstance().addRepositoriesCollectionUpdatedListener(event -> updateGrid());
		
		searchTextField = new TextField();
		searchTextField.setPlaceholder("Search");
		searchTextField.setWidth("80%");
		searchTextField.setClearButtonVisible(true);
		searchTextField.setValueChangeMode(ValueChangeMode.EAGER);
		searchTextField.addValueChangeListener(e -> filter());
		
		addRepositoryFormDialog = new AddRepositoryDialog();
		addRepositoryFormDialog.setCloseOnEsc(true);
		addRepositoryFormDialog.setCloseOnOutsideClick(true);
		
		addNewRepositoryButton = new Button("Repository", new Icon(VaadinIcon.PLUS));
		addNewRepositoryButton.setWidth("10%");
		addNewRepositoryButton.addClickListener(e -> addRepositoryFormDialog.open());
		
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
		repositoriesGrid.setHeightByRows(true);
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
		Grid.Column<Repository> i1MetricColumn = addMetricColumn("i1MetricColumn", "I1", MetricTotalNumberOfIssues.class);
		Grid.Column<Repository> i2MetricColumn = addMetricColumn("i2MetricColumn", "I2", MetricCommitsPerIssue.class);
		Grid.Column<Repository> i3MetricColumn = addMetricColumn("i3MetricColumn", "I3", MetricPercentageClosedIssues.class);
		Grid.Column<Repository> ti1MetricColumn = addMetricColumn("ti1MetricColumn", "TI1", MetricAverageDaysToCloseAnIssue.class);
		Grid.Column<Repository> tc1MetricColumn = addMetricColumn("tc1MetricColumn", "TC1", MetricAverageDaysBetweenCommits.class);
		Grid.Column<Repository> tc2MetricColumn = addMetricColumn("tc2MetricColumn", "TC2", MetricDaysBetweenFirstAndLastCommit.class);
		Grid.Column<Repository> tc3MetricColumn = addMetricColumn("tc3MetricColumn", "TC3", MetricChangeActivityRange.class);
		Grid.Column<Repository> c1MetricColumn = addMetricColumn("c1MetricColumn", "C1", MetricPeakChange.class);
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
		connectionTypeStateUpdate(RepositoryDataSourceService.getInstance().getConnectionType());
		
		add(searchBarLayout, repositoriesGrid);
		setSizeFull();
		
		RepositoryDataSourceService.getInstance().addConnectionChangedEventListener(event -> {
			connectionTypeStateUpdate(event.getConnectionTypeAfter());
		});
	}

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param connectionType
	 */
	private void connectionTypeStateUpdate(EnumConnectionType connectionType) {
		if (connectionType == EnumConnectionType.NOT_CONNECTED)
			addNewRepositoryButton.setEnabled(false);
		else
			addNewRepositoryButton.setEnabled(true);
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

	private MetricDescription getMetricDescription(Class<? extends AMetric> metricType) {
		if (metricType == MetricTotalNumberOfIssues.class) {
			return MetricTotalNumberOfIssues.DEFAULT_METRIC_DESCRIPTION;
		} else if (metricType == MetricCommitsPerIssue.class) {
			return MetricCommitsPerIssue.DEFAULT_METRIC_DESCRIPTION;
		} else if (metricType == MetricPercentageClosedIssues.class) {
			return MetricPercentageClosedIssues.DEFAULT_METRIC_DESCRIPTION;
		} else if (metricType == MetricAverageDaysToCloseAnIssue.class) {
			return MetricAverageDaysToCloseAnIssue.DEFAULT_METRIC_DESCRIPTION;
		} else if (metricType == MetricAverageDaysBetweenCommits.class) {
			return MetricAverageDaysBetweenCommits.DEFAULT_METRIC_DESCRIPTION;
		} else if (metricType == MetricDaysBetweenFirstAndLastCommit.class) {
			return MetricDaysBetweenFirstAndLastCommit.DEFAULT_METRIC_DESCRIPTION;
		} else if (metricType == MetricChangeActivityRange.class) {
			return MetricChangeActivityRange.DEFAULT_METRIC_DESCRIPTION;
		} else if (metricType == MetricPeakChange.class) {
			return MetricPeakChange.DEFAULT_METRIC_DESCRIPTION;
		} else {
			return null;
		}
	}
	
	private Grid.Column<Repository> addMetricColumn(String key, String headerText, Class<? extends AMetric> metricType) {
		Label headerLabel = new Label(headerText);
		Grid.Column<Repository> metricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, metricType))
			.setKey(key)
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(metricType))
			.setHeader(headerLabel);
		MetricDescription metricDescription = getMetricDescription(metricType);
		if (metricDescription != null)
			addMetricDescriptionTooltipToHeader(headerLabel, metricDescription);
		return metricColumn;
	}
	
	private void addMetricDescriptionTooltipToHeader(Component attachComponent, MetricDescription metricDescription) {
		Tooltip tooltip = new Tooltip(attachComponent);
		Span nameDescription = new Span(metricDescription.getName() + " - " + metricDescription.getDescription());
		nameDescription.addClassName("metricNameDescription");
		Span category = new Span(metricDescription.getCategory());
		category.addClassName("metricCategory");
		VerticalLayout vLayout = new VerticalLayout(nameDescription, category);
		vLayout.addClassName("metricHeaderTooltip");
		vLayout.setSizeFull();
		tooltip.add(vLayout);
		add(tooltip);
	}
	
	private Button createRemoveButton(Repository repository) {
		Button button = new Button();
		button.setIcon(new Icon(VaadinIcon.TRASH));
		button.addClickListener( event -> {
			try {
				RepositoriesCollectionService.getInstance().removeRepository(repository);
				updateGrid();
			} catch (RepositoriesCollectionServiceException e) {
				LOGGER.error("Error deleting a repository. Exception occurred: " + e.getMessage());
				MessageBoxDialog mb = new MessageBoxDialog(
					"Error", 
					"An error has occurred while deleting the repository. Please, contact the application administrator.", 
					"OK", 
					okButtonClick -> {});
				mb.open();
			}
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
				if (e.getErrorCode() == RepositoryDataSourceException.REPOSITORY_NOT_FOUND) {
					LOGGER.warn("Attempt to recalculate metrics from a repository without access.");
					MessageBoxDialog mb = new MessageBoxDialog(
						"Access denied.", 
						"The repository can not be accessed with the current connection.", 
						"OK", 
						okButtonClick -> {}).withCaption(MessageBoxCaption.WARNING);
					mb.open();
				} else {
					new MessageBoxDialog(
							"Error", 
							"An error occurred while obtaining the metrics of the repository, contact the application administrator.", 
							"OK", 
							okClickEvent -> {})
					.withCaption(MessageBoxCaption.ERROR)
					.open();					
				}
			}
		});
		return button;
	}

	private String getLastMeasurementDate(Repository repository) {
		MetricsResults mr = repository.getLastMetricsResults();
		if (mr == null ) return ValueUncalculated.VALUE;
		return formatDateShortEs(mr.getLastModificationDate());
	}

	private String getLastValueMeasuredForMetric(Repository repository, Class<? extends IMetric> metricType) {
		MetricsResults mr = repository.getLastMetricsResults();
		if (mr == null ) return NOT_CALCULATED;
		Measure measure = mr.getMeasureForTheMetric(metricType);
		if (measure == null) return NOT_CALCULATED;
		IValue value = measure.getMeasuredValue();
		if(value == null) return NOT_CALCULATED;
		if (value instanceof NumericValue)
			return formatStringTwoDecimals(value.valueToString());
		else if (value instanceof ValueUncalculated)
			return NOT_CALCULATED;
		else
			return value.valueToString();
	}

	/**
	 * Formatea una fecha y devuelve una cadena de texto.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param date Fecha a formatear.
	 * @return Fecha con formato SHORT para España.
	 */
	private String formatDateShortEs(Date date) {
		return SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.forLanguageTag("es-ES")).format(date);
	}

	/**
	 * Formatea una cadena numérica en una cadena nuérica con dos decimales.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param numberString Número a formatear.
	 * @return
	 */
	private String formatStringTwoDecimals(String numberString) {
		try {
			String valueFormated = "";
			NumberFormat numberFormat = NumberFormat.getNumberInstance();
			numberFormat.setMaximumFractionDigits(2);
			if (NumberUtils.isNumber(numberString)) {
				valueFormated = numberFormat.format(Double.parseDouble(numberString));				
			}
			return valueFormated;
		} catch (Exception e) {
			LOGGER.error("Error formatting the string: " + numberString + ". Exception occurred: " + e.getMessage());
			MessageBoxDialog mb = new MessageBoxDialog(
					"Error", 
					"An error occurred while formatting this number: '"+ numberString + "'.Please, contact the application administrator.", 
					"OK", 
					event -> {}).withCaption(MessageBoxCaption.ERROR);
			mb.open();
			return "";
		}
	}
}
