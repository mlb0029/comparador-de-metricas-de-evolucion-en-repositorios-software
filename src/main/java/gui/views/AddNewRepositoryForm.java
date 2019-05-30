package gui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import app.MetricsService;
import app.RepositoriesService;
import app.RepositoryDataSourceService;
import datamodel.Repository;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource.EnumConnectionType;
import repositorydatasource.exceptions.RepositoryDataSourceException;

public class AddNewRepositoryForm extends Dialog {

	/**
	 * Serial.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -9068571389303968081L;
	
	private TextField usernameTextField;
	private ComboBox<Repository> repositoryComboBox;
	
	private Label msgLabel;

	private Button addButton;

	public AddNewRepositoryForm() {
		try {		
			/* Repository Form */
			usernameTextField = new TextField();
			usernameTextField.setWidth("50%");
			usernameTextField.setPlaceholder("Username");
			usernameTextField.setClearButtonVisible(true);
			usernameTextField.addValueChangeListener(event -> updateUserRepositories());
			
			repositoryComboBox = new ComboBox<Repository>();
			repositoryComboBox.setWidth("50%");
			repositoryComboBox.setPlaceholder("Repository");
			repositoryComboBox.setAllowCustomValue(false);
			repositoryComboBox.setItemLabelGenerator(repository -> repository.getName());
			
			HorizontalLayout repositorySelectorHLayout = new HorizontalLayout(usernameTextField, repositoryComboBox);
			repositorySelectorHLayout.setWidthFull();
			
			/* Message Label */
			msgLabel = new Label();
			msgLabel.setWidthFull();
			
			/* Add button */
			addButton = new Button("Add", new Icon(VaadinIcon.PLUS), this::addButton_Click);
			addButton.setWidthFull();

			/* This */
			VerticalLayout vLayout = new VerticalLayout(repositorySelectorHLayout, msgLabel, addButton);
			vLayout.setSizeFull();
			add(vLayout);
		} catch (Exception e) {
			e.printStackTrace(); //TODO Redirigir a página de error
		}
		
	}

	public void onAddRepositoryListener(ComponentEventListener<ClickEvent<Button>> listener) {
		addButton.addClickListener(listener);
	}

	private void addButton_Click(ClickEvent<Button> event) {
		if (repositoryComboBox.getOptionalValue().isPresent()) {
			Repository repositoryToAdd = repositoryComboBox.getValue();
			if (RepositoriesService.getInstance().addRepository(repositoryToAdd)) {
				try {
					MetricsService.getMetricsService().calculateMetricsRepository(repositoryToAdd);
					msgLabel.setText("");
				} catch (RepositoryDataSourceException e) {
					msgLabel.setText(e.getMessage());
				}
			} else {
				msgLabel.setText("The repository already exists.");
			}
			repositoryComboBox.clear();
		} else {
			msgLabel.setText("No repository selected");
		}
	}
	
	private void resetUserNameTextField() {
		try {
			IRepositoryDataSource repositoryDataSource = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
			if (repositoryDataSource.getConnectionType().equals(EnumConnectionType.LOGGED)) {
				usernameTextField.setValue(repositoryDataSource.getCurrentUser().getUsername());
			} else {
				usernameTextField.setValue("");
			}
			msgLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgLabel.setText(e.getMessage());
			usernameTextField.clear();
		}
	}

	private void updateUserRepositories() {
		try {
			IRepositoryDataSource repositoryDataSource = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
			if (!usernameTextField.isEmpty()) {
				repositoryComboBox.setItems(repositoryDataSource.getAllUserRepositories(usernameTextField.getValue()));
			} else {
				repositoryComboBox.setItems();
				repositoryComboBox.clear();
				resetUserNameTextField();
			}
			msgLabel.setText("");
		} catch (Exception e) {
			msgLabel.setText(e.getMessage());
			repositoryComboBox.setItems();
			resetUserNameTextField();
		}
	}
}
