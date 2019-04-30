package gui.views;

import java.util.stream.Collectors;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import gui.common.RepositoriesListService;
import repositorydatasource.model.Repository;

/**
 * View that allows you to work with a list of repositories.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListView extends VerticalLayout {
	
	private static final long serialVersionUID = 4840032243533665026L;

	private AddNewRepositoryFormDialog addNewRepositoryFormDialog;
	private Button addNewRepositoryButton;
	private TextField searchTextField;
	private Grid<Repository> repositoriesGrid;

	/**
	 * Initializes all components of the view.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoriesListView() {
		addNewRepositoryFormDialog = new AddNewRepositoryFormDialog();
		addNewRepositoryFormDialog.addAddButtonClickListener(this::addAddButtonClickListener);
		addNewRepositoryButton = new Button("Repository", new Icon(VaadinIcon.PLUS));
		addNewRepositoryButton.addClickListener(e -> addNewRepositoryFormDialog.open());
		
		searchTextField = new TextField();
		searchTextField.setPlaceholder("Search");
		searchTextField.setClearButtonVisible(true);
		searchTextField.setValueChangeMode(ValueChangeMode.EAGER);
		searchTextField.addValueChangeListener(e -> filter());
		searchTextField.setWidthFull();
		
		HorizontalLayout searchBarLayout = new HorizontalLayout(searchTextField, addNewRepositoryButton);
		searchBarLayout.setWidthFull();
		
		repositoriesGrid = new Grid<Repository>(Repository.class);
		repositoriesGrid.setColumns("url", "name", "id");
		updateGrid();
		
		add(searchBarLayout, repositoriesGrid);
		setSizeFull();
	}

	private void addAddButtonClickListener(ClickEvent<Button> event) {
		updateGrid();
	}
	
	/**
	 * Filter event.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void filter() {
		if (searchTextField.getValue() != "") {
			repositoriesGrid.setItems(RepositoriesListService.getInstance().getRepositories().stream().filter(r -> 
						r.getName().contains(searchTextField.getValue()) ||
						r.getUrl().contains(searchTextField.getValue()) ||
						r.getId().toString().contains(searchTextField.getValue())
					).collect(Collectors.toList())
			);
		} else {
			repositoriesGrid.setItems(RepositoriesListService.getInstance().getRepositories());
		}
	}
	
	private void updateGrid() {
		repositoriesGrid.setItems(RepositoriesListService.getInstance().getRepositories());
	}
}
