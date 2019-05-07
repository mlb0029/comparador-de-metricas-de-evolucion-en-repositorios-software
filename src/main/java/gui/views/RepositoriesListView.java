package gui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Element;

import gui.common.RepositoriesService;
import model.Repository;

/**
 * View that allows you to work with a list of repositories.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListView extends VerticalLayout {
	
	private static final long serialVersionUID = 4840032243533665026L;

	private AddNewRepositoryFormDialog addNewRepositoryFormDialog;
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
		addNewRepositoryFormDialog = new AddNewRepositoryFormDialog();
		addNewRepositoryFormDialog.addRepositoryButtonClickListener(this::addRepositoryButtonClickListener);
		
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
		
		repositoriesDataProvider = new ListDataProvider<>(RepositoriesService.getInstance().getRepositories());
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
				.setWidth("95%")
				.setComparator(Repository::getName)
				.setHeader("Repository");
		repositoriesGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
		updateGrid();
		
		add(searchBarLayout, repositoriesGrid);
		setSizeFull();
	}

	private void addRepositoryButtonClickListener(ClickEvent<Button> event) {
		updateGrid();
	}

	private void updateGrid() {
		repositoriesGrid.getDataProvider().refreshAll();
	}

	/**
	 * Filter event.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void filter() {
		repositoriesDataProvider.addFilter(repository -> repository.getName().toLowerCase().contains(searchTextField.getValue().toLowerCase()));
//		if (searchTextField.getValue() != "") {
//			repositoriesGrid.setItems(RepositoriesListService.getInstance().getRepositories().stream().filter(r -> 
//						r.getName().contains(searchTextField.getValue()) ||
//						r.getUrl().contains(searchTextField.getValue()) ||
//						r.getId().toString().contains(searchTextField.getValue())
//					).collect(Collectors.toList())
//			);
//		} else {
//			repositoriesGrid.setItems(RepositoriesListService.getInstance().getRepositories());
//		}
	}@Override
	public Element getElement() {
		// TODO Auto-generated method stub
		return super.getElement();
	}
	private Button createRemoveButton(Repository repository) {
		Button button = new Button();
		button.setIcon(new Icon(VaadinIcon.TRASH));
		button.addClickListener( event -> {
			repositoriesDataProvider.getItems().remove(repository);
			repositoriesDataProvider.refreshAll();
		});
		return button;
	}
}
