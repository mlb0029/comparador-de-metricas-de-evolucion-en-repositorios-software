package gui.views;

import java.io.InputStream;

import org.claspina.confirmdialog.ConfirmDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
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
import gui.views.addrepositoryform.AddRepositoryDialog;
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

	private AddRepositoryDialog addRepositoryFormDialog = new AddRepositoryDialog();
	private TextField searchTextField = new TextField();
	private Select<RepositoryMenuItems> repositoryMenu = new Select<RepositoryMenuItems>();
	private Select<ReviewMenuItems> reviewMenu = new Select<ReviewMenuItems>();
	private RepositoriesGrid repositoriesGrid = new RepositoriesGrid();
	private ListDataProvider<Repository> repositoriesDataProvider = DataProvider.ofCollection(RepositoriesCollectionService.getInstance().getRepositories());

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
		CREATE("Evaluate with new profile"),
		DEFAULT("Evaluate with default profile"),
		IMPORT("Evaluate with imported profile"),
		EXPORT("Export actual profile");
		
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
		try {
			repositoriesDataProvider = DataProvider.ofCollection(RepositoriesCollectionService.getInstance().getRepositories());
			RepositoriesCollectionService.getInstance().addRepositoriesCollectionUpdatedListener(event -> updateGrid());
			
			initializeRepositoryMenu();
			
			initializeReviewMenu();
			
			initializeSearchBar();
			
			repositoriesGrid.setDataProvider(repositoriesDataProvider);
			updateGrid();
			
			searchTextField.setWidth("60%");
			repositoryMenu.setWidth("20%");
			reviewMenu.setWidth("20%");
			HorizontalLayout searchBarLayout = new HorizontalLayout(searchTextField, repositoryMenu, reviewMenu);
			searchBarLayout.setWidthFull();
					
			add(searchBarLayout, repositoriesGrid);
			setSizeFull();
		} catch (Exception e) {
			LOGGER.error("Error initializing RepositoriesListView. Exception occurred: " + e.getMessage());
			ConfirmDialog.createError()
			.withCaption("Error")
			.withMessage("An error has occurred. Please, contact the application administrator.")
			.withOkButton()
			.open();
		}
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
		repositoryMenu.setPlaceholder("Project management");
		repositoryMenu.addValueChangeListener(event -> {
			try {
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
			} catch (Exception e) {
				LOGGER.error("Error in repository menu. Exception occurred: " + e.getMessage());
				ConfirmDialog.createError()
				.withCaption("Error")
				.withMessage("An error has occurred. Please, contact the application administrator.")
				.withOkButton()
				.open();
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
		reviewMenu.setPlaceholder("Evaluate projects");
		reviewMenu.addValueChangeListener(event -> {
			try {
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
			} catch (Exception e) {
				LOGGER.error("Error in reviewMenu. Exception occurred: " + e.getMessage());
				ConfirmDialog.createError()
				.withCaption("Error")
				.withMessage("An error has occurred. Please, contact the application administrator.")
				.withOkButton()
				.open();
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
			.withMessage("You currently have repositories. Do you want to overwrite the repositories or append them?")
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
				InputStream in = RepositoriesCollectionService.getInstance().exportRepositories();
				new FileExportFormDialog(in).open();
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
		if (RepositoriesCollectionService.getInstance().getRepositories().isEmpty()) {
			ConfirmDialog.createWarning()
			.withCaption("No repository")
			.withMessage("No repository has been added, please add at least one before exporting.")
			.withOkButton()
			.open();
		} else {
			try {
				InputStream in = RepositoriesCollectionService.getInstance().exportRepositoriesToCSV();
				new FileExportFormDialog(in).open();
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
	
	private void createMetricProfile() {
		if(RepositoriesCollectionService.getInstance().getRepositories().isEmpty()) {
			ConfirmDialog.createWarning()
			.withCaption("No repository")
			.withMessage("No repository has been added, please add at least one.")
			.withOkButton()
			.open();
		} else {
			try {
				MetricsService ms = MetricsService.getMetricsService();		
				ms.setCurrentMetricProfileToCalculated();
				ms.evaluateRepositoryCollection();
				updateGrid();
			} catch (Exception e) {
				LOGGER.error("Error evaluating repositories with calculated profile. Exception occurred: " + e.getMessage());
				ConfirmDialog.createError()
				.withCaption("Error")
				.withMessage("An error has occurred. Please, contact the application administrator.")
				.withOkButton()
				.open();
			}
		}
	}
	
	private void loadDefaultMetricProfile() {
		try {
			MetricsService ms = MetricsService.getMetricsService();		
			ms.setCurrentMetricProfileToDefault();
			ms.evaluateRepositoryCollection();
			updateGrid();
		} catch (Exception e) {
			LOGGER.error("Error evaluating repositories with default profile. Exception occurred: " + e.getMessage());
			ConfirmDialog.createError()
			.withCaption("Error")
			.withMessage("An error has occurred. Please, contact the application administrator.")
			.withOkButton()
			.open();
		}
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
}
