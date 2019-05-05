package gui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import gui.common.RepositoriesListService;
import gui.common.RepositoryDataSourceService;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource.EnumConnectionType;
import repositorydatasource.exceptions.RepositoryDataSourceException;
import repositorydatasource.model.Repository;
import repositorydatasource.model.User;

public class AddNewRepositoryFormDialog extends Dialog {

	/**
	 * Serial.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -9068571389303968081L;
	
	private IRepositoryDataSource repositoryDataSource;

	private Details connectionDetails;
	private PasswordField tokenField;
	private Button connectButton;
	private Button disconnectButton;

	private TextField usernameTextField;
	private ComboBox<Repository> repositoryComboBox;
	
	private Label msgStatusLabel;

	private Button addButton;

	public AddNewRepositoryFormDialog() {
		repositoryDataSource = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
		
		addButton = new Button("Add", new Icon(VaadinIcon.PLUS), this::addButton_Click);
		addButton.setWidthFull();
		
		msgStatusLabel = new Label();
		
		usernameTextField = new TextField();
		usernameTextField.setPlaceholder("Username");
		usernameTextField.setClearButtonVisible(true);
		usernameTextField.addValueChangeListener(event -> updateUserRepositories());
		resetUserNameTextField();
		
		repositoryComboBox = new ComboBox<Repository>();
		repositoryComboBox.setPlaceholder("Repository");
		repositoryComboBox.setAllowCustomValue(false);
		repositoryComboBox.setItemLabelGenerator(repository -> repository.getName());
		repositoryComboBox.addValueChangeListener(e -> {
			if (!repositoryComboBox.isEmpty())
				repositoryComboBox.setInvalid(false);
			else
				repositoryComboBox.setInvalid(true);
		});
		updateUserRepositories();
		
		HorizontalLayout repositorySelectorHLayout = new HorizontalLayout(usernameTextField, repositoryComboBox);
		repositorySelectorHLayout.setWidthFull();
		
		disconnectButton = new Button("Disconnect", new Icon(VaadinIcon.UNLINK), this::disconnectButton_Click);
		disconnectButton.setWidthFull();
		connectButton = new Button("Connect", new Icon(VaadinIcon.LINK), this::connectButton_Click);
		connectButton.setWidthFull();
		tokenField = new PasswordField();
		tokenField.setClearButtonVisible(true);
		tokenField.setWidthFull();
		tokenField.setPlaceholder("Personal Access Token");
		
		FormLayout connectionForm = new FormLayout(tokenField, connectButton, disconnectButton);
		connectionDetails = new Details();
		connectionDetails.addThemeVariants(DetailsVariant.FILLED);
		connectionDetails.setContent(connectionForm);
		updateConnectionDetails();

		VerticalLayout vLayout = new VerticalLayout(connectionDetails, repositorySelectorHLayout, msgStatusLabel, addButton);
		vLayout.setSizeFull();
		add(vLayout);
	}

	public void addRepositoryButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		addButton.addClickListener(listener);
	}

	private void connectButton_Click(ClickEvent<Button> event) {
		try {
			if (tokenField.isEmpty()) {
				repositoryDataSource.connect();
			} else {
				repositoryDataSource.connect(tokenField.getValue());
			}
			msgStatusLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.getMessage());
		} finally {
			clearConnectionForm();
			connectionDetails.setOpened(false);
			updateConnectionDetails();
			resetUserNameTextField();
		}
	}

	private void disconnectButton_Click(ClickEvent<Button> event) {
		try {
			if (! repositoryDataSource.getConnectionType().equals(EnumConnectionType.NOT_CONNECTED)) {
				repositoryDataSource.disconnect();
			}
			msgStatusLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.getMessage());
		} finally {
			connectionDetails.setOpened(false);
			updateConnectionDetails();
			resetUserNameTextField();
		}
	}

	private void addButton_Click(ClickEvent<Button> event) {
		if (repositoryComboBox.getOptionalValue().isPresent()) {
			RepositoriesListService.getInstance().getRepositories().add(repositoryComboBox.getValue());
			msgStatusLabel.setText("");
			repositoryComboBox.setInvalid(false);
		} else {
			msgStatusLabel.setText("No repository selected");
			repositoryComboBox.setInvalid(true);
		}
	}
	
	private void updateConnectionDetails() {
		try {
			switch (repositoryDataSource.getConnectionType()) {
			case NOT_CONNECTED:
				connectionDetails.setSummaryText("No connection to GitLab");
				tokenField.setVisible(true);
				connectButton.setVisible(true);
				disconnectButton.setVisible(false);
				break;
			case CONNECTED:
				connectionDetails.setSummaryText("Using a public connection");
				tokenField.setVisible(false);
				connectButton.setVisible(false);
				disconnectButton.setVisible(true);
				break;
			case LOGGED:
				User user = RepositoryDataSourceService.getInstance().getRepositoryDataSource().getCurrentUser();
				Image userAvatar = new Image((user.getAvatarUrl() == null)? "": user.getAvatarUrl(), "User avatar");
				userAvatar.setHeight("50px");
				userAvatar.setWidth("50px");
				Label usernameLabel = new Label("Connected as: " + user.getUsername());
				HorizontalLayout userInfoLayout = new HorizontalLayout(userAvatar, usernameLabel);
				connectionDetails.setSummary(userInfoLayout);
				tokenField.setVisible(false);
				connectButton.setVisible(false);
				disconnectButton.setVisible(true);
				break;
			}
			msgStatusLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.toString());
		}
	}

	private void resetUserNameTextField() {
		try {
			if (repositoryDataSource.getConnectionType().equals(EnumConnectionType.LOGGED)) {
				usernameTextField.setValue(repositoryDataSource.getCurrentUser().getUsername());
			} else {
				usernameTextField.setValue("");
			}
			msgStatusLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.getMessage());
			usernameTextField.clear();
		}
	}

	private void updateUserRepositories() {
		try {
			if (!usernameTextField.isEmpty()) {
				repositoryComboBox.setItems(repositoryDataSource.getAllUserRepositories(usernameTextField.getValue()));
				usernameTextField.setInvalid(false);
			} else {
				usernameTextField.setInvalid(true);
				repositoryComboBox.setItems();
				repositoryComboBox.clear();
				resetUserNameTextField();
			}
			msgStatusLabel.setText("");
		} catch (Exception e) {
			usernameTextField.setInvalid(true);
			msgStatusLabel.setText(e.getMessage());
			repositoryComboBox.setItems();
			resetUserNameTextField();
		}
	}
	
	private void clearConnectionForm() {
		tokenField.clear();
	}
}
