package gui.views;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
import org.claspina.confirmdialog.ConfirmDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;

import app.MetricsService;
import app.RepositoriesCollectionService;
import app.RepositoriesCollectionService.ImportMode;
import app.RepositoryDataSourceService;
import datamodel.Repository;
import exceptions.RepositoriesCollectionServiceException;
import exceptions.RepositoryDataSourceException;
import gui.views.addrepositoryform.AddRepositoryDialog;
import metricsengine.AMetric;
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
	
	private AddRepositoryDialog addRepositoryFormDialog = new AddRepositoryDialog();
	private TextField searchTextField = new TextField();
	private Select<RepositoryMenuItems> repositoryMenu = new Select<RepositoryMenuItems>();
	private Select<ReviewMenuItems> reviewMenu = new Select<ReviewMenuItems>();
	private Grid<Repository> repositoriesGrid = new Grid<Repository>();
	private ListDataProvider<Repository> repositoriesDataProvider = null;

	private enum RepositoryMenuItems {
		ADD("Add new"),
		IMPORT("Import"),
		EXPORT("Export"),
		EXPORT_CSV("Export to CSV");
		
		private String display;

		private RepositoryMenuItems(String display) {
			this.display = display;
		}

		public String getDisplay() {
			return display;
		}
	}
	
	private enum ReviewMenuItems {
		CREATE("Create new profile"),
		DEFAULT("Use default profile"),
		IMPORT("Import profile"),
		EXPORT("Export profile");
		
		private String display;

		private ReviewMenuItems(String display) {
			this.display = display;
		}

		public String getDisplay() {
			return display;
		}
	}
	
	/**
	 * Initializes all components of the view.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoriesListView() {
		repositoriesDataProvider = DataProvider.ofCollection(RepositoriesCollectionService.getInstance().getRepositories());
		RepositoriesCollectionService.getInstance().addRepositoriesCollectionUpdatedListener(event -> updateGrid());
		
		initializeRepositoryMenu();
		
		initializeReviewMenu();
		
		initializeSearchBar();
		
		
		searchTextField.setWidth("60%");
		repositoryMenu.setWidth("20%");
		reviewMenu.setWidth("20%");
		HorizontalLayout searchBarLayout = new HorizontalLayout(searchTextField, repositoryMenu, reviewMenu);
		searchBarLayout.setWidthFull();
		
		initializeGrid();
		
		add(searchBarLayout, repositoriesGrid);
		setSizeFull();
	}

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void initializeRepositoryMenu() {
		repositoryMenu.setItems(RepositoryMenuItems.values());
		repositoryMenu.setTextRenderer(item -> item.getDisplay());
		repositoryMenu.setEmptySelectionAllowed(false);
		repositoryMenu.setPlaceholder("Repositories");
		repositoryMenu.addValueChangeListener(event -> {
			if (!event.getHasValue().isEmpty()) {
				event.getSource().clear();
				switch (event.getValue()) {
				case ADD:
					addNewRepository();
					break;
				case IMPORT:
					FileImportDialog fileImportDialog = new FileImportDialog()
					.withCaption("Import repositories")
					.withAcceptedFileTypes(".txt")
					.withUploadListener(uploadEvent -> {
						importRepositories(uploadEvent.getInputStream());
					});
					fileImportDialog.open();
					break;
				case EXPORT:
					exportRepositories();
					break;
				case EXPORT_CSV:
					generateCSVRepositories();
					break;
				default:
					ConfirmDialog mb = ConfirmDialog
					.createWarning()
					.withCaption("Invalid selection")
					.withMessage("")
					.withOkButton();
					mb.open();
					break;
				}
			}
		});
	}

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void initializeReviewMenu() {
		reviewMenu.setItems(ReviewMenuItems.values());
		reviewMenu.setTextRenderer(item -> item.getDisplay());
		reviewMenu.setEmptySelectionAllowed(false);
		reviewMenu.setPlaceholder("Repositories");
		reviewMenu.addValueChangeListener(event -> {
			if (!event.getHasValue().isEmpty()) {
				event.getSource().clear();
				switch (event.getValue()) {
				case CREATE:
					createMetricProfile();
					break;
				case DEFAULT:
					loadDefaultMetricProfile();
					break;
				case IMPORT:
					importMetricProfile();
					break;
				case EXPORT:
					exportMetricProfile();
					break;
				default:
					ConfirmDialog mb = ConfirmDialog
						.createWarning()
						.withCaption("Invalid selection")
						.withMessage("")
						.withOkButton();
						mb.open();
					break;
				}
			}
		});
	}

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void initializeSearchBar() {
		searchTextField.setPlaceholder("Search");
		searchTextField.setClearButtonVisible(true);
		searchTextField.setValueChangeMode(ValueChangeMode.EAGER);
		searchTextField.addValueChangeListener(e -> filter());
	}

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void initializeGrid() {
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
	}

	private void addNewRepository() {
		if (RepositoryDataSourceService.getInstance().getConnectionType() != EnumConnectionType.NOT_CONNECTED)
			addRepositoryFormDialog.open();
		else {
			ConfirmDialog.createWarning()
			.withCaption("Not allowed.")
			.withMessage("Can not add a repository without connection.")
			.withOkButton()
			.open();
		}
	}
	
	private void importRepositories(InputStream inputStream) {
		RepositoriesCollectionService reCollectionService = RepositoriesCollectionService.getInstance();
		ConfirmDialog errorMessage = 
				ConfirmDialog.createError()
				.withCaption("Error")
				.withMessage("An error has occurred. Please, contact the application administrator.")
				.withOkButton();
		if (!reCollectionService.getRepositories().isEmpty()) {
			ConfirmDialog.createQuestion()
			.withCaption("Overwrite repositories")
			.withMessage("You currently have repositories. Do you want to overwrite the repositories or add them?")
			.withCancelButton()
			.withButton(new Button("Append", clickEvent -> {
				try {
					reCollectionService.importRepositories(inputStream, ImportMode.APPEND);
				} catch (RepositoriesCollectionServiceException e) {
					LOGGER.error("Error importing repositories. Exception occurred: " + e.getMessage());
					errorMessage.open();
				}
			}))
			.withButton(new Button("Overwrite", VaadinIcon.WARNING.create(), clickEvent -> {
				try {
					reCollectionService.importRepositories(inputStream, ImportMode.OVERWRITE);
				} catch (RepositoriesCollectionServiceException e) {
					LOGGER.error("Error importing repositories. Exception occurred: " + e.getMessage());
					errorMessage.open();
				}
			}))
			.open();
		} else {
			try {
				reCollectionService.importRepositories(inputStream, ImportMode.APPEND);
			} catch (RepositoriesCollectionServiceException e) {
				LOGGER.error("Error importing repositories. Exception occurred: " + e.getMessage());
				errorMessage.open();
			}
		}
	}
	
	private void exportRepositories() {
		if (RepositoriesCollectionService.getInstance().getRepositories().isEmpty()) {
			ConfirmDialog.createWarning()
			.withCaption("No repository")
			.withMessage("No repository has been added, please add at least one before exporting.")
			.withOkButton()
			.open();
		} else {
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(RepositoriesCollectionService.getInstance().exportRepositories());
				new FileExportFormDialog(objectInputStream).open();
			} catch (Exception e) {
				LOGGER.error("Error exporting a repository. Exception occurred: " + e.getMessage());
				ConfirmDialog.createError()
				.withCaption("Error")
				.withMessage("An error has occurred. Please, contact the application administrator.")
				.withOkButton()
				.open();
			}	
		}
	}
	
	private void generateCSVRepositories() {
		ConfirmDialog.createError()
		.withCaption("Error")
		.withMessage("Not implemented. Please, contact the application administrator.")
		.withOkButton()
		.open();
	}
	
	private void createMetricProfile() {
		ConfirmDialog.createError()
		.withCaption("Error")
		.withMessage("Not implemented. Please, contact the application administrator.")
		.withOkButton()
		.open();
	}
	
	private void loadDefaultMetricProfile() {
		ConfirmDialog.createError()
		.withCaption("Error")
		.withMessage("Not implemented. Please, contact the application administrator.")
		.withOkButton()
		.open();
	}
	
	private void importMetricProfile() {
		ConfirmDialog.createError()
		.withCaption("Error")
		.withMessage("Not implemented. Please, contact the application administrator.")
		.withOkButton()
		.open();
	}
	
	private void exportMetricProfile() {
		ConfirmDialog.createError()
		.withCaption("Error")
		.withMessage("Not implemented. Please, contact the application administrator.")
		.withOkButton()
		.open();
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
	
	private Grid.Column<Repository> addMetricColumn(String key, String headerText, Class<? extends AMetric> metricType) {
		Label headerLabel = new Label(headerText);
		Grid.Column<Repository> metricColumn = repositoriesGrid.addColumn(r -> getLastValueMeasuredForMetric(r, metricType))
			.setKey(key)
			.setSortable(true)
			.setComparator(Repository.getComparatorByMetric(metricType))
			.setHeader(headerLabel);
		return metricColumn;
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
				ConfirmDialog.createError()
				.withCaption("Error")
				.withMessage("An error has occurred while deleting the repository. Please, contact the application administrator.")
				.withOkButton()
				.open();
			}
		});
		return button;
	}
	
	private Button createCalculateButton(Repository repository) {
		Button button = new Button();
		button.setIcon(new Icon(VaadinIcon.CALC_BOOK));
		button.addClickListener( event -> {
			try {
				MetricsService.getMetricsService().calculateRepositoryMetrics(repository);
				updateGrid();
			} catch (RepositoryDataSourceException e) {
				if (e.getErrorCode() == RepositoryDataSourceException.REPOSITORY_NOT_FOUND) {
					LOGGER.warn("Attempt to recalculate metrics from a repository without access.");
					ConfirmDialog.createWarning()
					.withCaption("Error")
					.withMessage("The repository can not be accessed with the current connection.")
					.withOkButton()
					.open();
				} else {
					LOGGER.error("An error occurred while obtaining the metrics of the repository. Exception occurred: " + e.getMessage());
					ConfirmDialog.createError()
					.withCaption("Error")
					.withMessage("An error occurred while obtaining the metrics of the repository. Please, contact the application administrator.")
					.withOkButton()
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
			return formatStringTwoDecimals(value.getValueString());
		else if (value instanceof ValueUncalculated)
			return NOT_CALCULATED;
		else
			return value.getValueString();
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
				ConfirmDialog.createError()
				.withCaption("Error")
				.withMessage("An error occurred while formatting this number: '"+ numberString + "'.Please, contact the application administrator.")
				.withOkButton()
				.open();
			return "";
		}
	}
}
