package gui;

import java.util.Collection;
import java.util.stream.Collectors;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import repositorydatasource.model.Repository;

/**
 * View that allows you to work with a list of repositories.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class RepositoriesListView extends CustomComponent {
	
	private static final long serialVersionUID = 4840032243533665026L;

	private Collection<Repository> repositories;
	
	private VerticalLayout root;
	private HorizontalLayout topBar;
	private TextField searchTextField;
	private Grid<Repository> grid;

	/**
	 * Initializes all components of the view.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoriesListView() {
		searchTextField = new TextField();
		searchTextField.setPlaceholder("Search");
		searchTextField.setValueChangeMode(ValueChangeMode.EAGER);
		searchTextField.addValueChangeListener(e -> filter());
		topBar = new HorizontalLayout(searchTextField);
		grid = new Grid<Repository>(Repository.class);
		grid.setColumns("url", "name", "id", "lifeSpanMonths");
		root = new VerticalLayout(new Label("Hola"), topBar, grid);

		setCompositionRoot(root);
		setSizeFull();
	}

	/**
	 * Filter event.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void filter() {
		if (searchTextField.getValue() != "") {
			grid.setItems(repositories.stream().filter(r -> 
						r.getName().contains(searchTextField.getValue()) ||
						r.getUrl().contains(searchTextField.getValue()) ||
						r.getId().toString().contains(searchTextField.getValue())
					).collect(Collectors.toList())
			);
		} else {
			grid.setItems(repositories);
		}
	}


	/**
	 * Gets the list of repositories.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the repositories
	 */
	public Collection<Repository> getRepositories() {
		return repositories;
	}

	/**
	 * Sets the list of repositories.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param repositories the repositories to set
	 */
	public void setRepositories(Collection<Repository> repositories) {
		this.repositories = repositories;
		grid.setItems(repositories);
	}
	
}
