package gui.views;

import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
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

	private Details addNewRepositoryButton;
	private TextField searchTextField;
	private Grid<Repository> grid;

	/**
	 * Initializes all components of the view.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public RepositoriesListView() {
		addNewRepositoryButton = new Details("Add new!", new AddNewRepositoryForm());
		addNewRepositoryButton.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
		
		searchTextField = new TextField();
		searchTextField.setPlaceholder("Search");
		searchTextField.setClearButtonVisible(true);
		searchTextField.setValueChangeMode(ValueChangeMode.EAGER);
		searchTextField.addValueChangeListener(e -> filter());
		searchTextField.setWidthFull();
		
		grid = new Grid<Repository>(Repository.class);
		grid.setColumns("url", "name", "id", "lifeSpanMonths");
		grid.setItems(RepositoriesListService.getInstance().getRepositories());
		
		add(addNewRepositoryButton, searchTextField, grid);
		setSizeFull();
	}

	/**
	 * Filter event.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private void filter() {
		if (searchTextField.getValue() != "") {
			grid.setItems(RepositoriesListService.getInstance().getRepositories().stream().filter(r -> 
						r.getName().contains(searchTextField.getValue()) ||
						r.getUrl().contains(searchTextField.getValue()) ||
						r.getId().toString().contains(searchTextField.getValue())
					).collect(Collectors.toList())
			);
		} else {
			grid.setItems(RepositoriesListService.getInstance().getRepositories());
		}
	}
	
	private class AddNewRepositoryForm extends VerticalLayout {
		
		/**
		 * Description.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 */
		private static final long serialVersionUID = -9068571389303968081L;
		private Button linkConnectButton;
		private ComboBox<Repository> repositorySelectorCB;
		private Label msgStatusLabel;
		private Button addButton;
		
		public AddNewRepositoryForm() {
			linkConnectButton = new Button();
			repositorySelectorCB = new ComboBox<Repository>();
			repositorySelectorCB.setPlaceholder("Repository URL");
			msgStatusLabel = new Label();
			addButton = new Button("Add", new Icon(VaadinIcon.PLUS));
			
			add(linkConnectButton, repositorySelectorCB, msgStatusLabel, addButton);
		}
		
	}
	
	private class ConnectForm extends FormLayout{
		
		private Label paTokenLabel;
		private PasswordField paTokenTextField;
		private Label msgStatusLabel;
		private Button connectButton;
		
		public ConnectForm() {
			paTokenLabel = new Label("Personal Access Token");
			paTokenTextField = new PasswordField();
			msgStatusLabel = new Label();
			connectButton = new Button("CONNECT");
			addFormItem(paTokenTextField, paTokenLabel);
			add(msgStatusLabel, connectButton);
		}
	}
}
