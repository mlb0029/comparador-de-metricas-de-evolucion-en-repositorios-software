package gui.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

import gui.common.MetricsService;
import gui.common.RepositoriesService;
import gui.common.RepositoryDataSourceService;
import model.Repository;
import model.User;
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
	
	private static Logger logger = LoggerFactory.getLogger(AddNewRepositoryForm.class);
	
	private IRepositoryDataSource repositoryDataSource;

	private Image userAvatar;
	private Label connectionInfoLabel;
	private PasswordField tokenField;
	private Button connectButton;
	private Button disconnectButton;

	private TextField usernameTextField;
	private ComboBox<Repository> repositoryComboBox;
	
	private Label msgLabel;

	private Button addButton;

	public AddNewRepositoryForm() {
		try {
			/* Getting the repository data source */
			repositoryDataSource = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
			
			/* Connection info */
			userAvatar = new Image();
			userAvatar.setWidth("50px");
			userAvatar.setHeight("50px");
			userAvatar.setAlt("User Avatar");
			connectionInfoLabel = new Label();
			HorizontalLayout connectionInfoLaoyut = new HorizontalLayout(userAvatar, connectionInfoLabel);
			connectionInfoLaoyut.setWidthFull();
			
			/* Connection Form */
			tokenField = new PasswordField();
			tokenField.setClearButtonVisible(true);
			tokenField.setWidthFull();
			tokenField.setPlaceholder("Personal Access Token");
			connectButton = new Button("Connect", new Icon(VaadinIcon.LINK), this::connectButton_Click);
			connectButton.setWidthFull();
			disconnectButton = new Button("Disconnect", new Icon(VaadinIcon.UNLINK), this::disconnectButton_Click);
			disconnectButton.setWidthFull();
			
			FormLayout connectionForm = new FormLayout(connectionInfoLaoyut, tokenField, connectButton, disconnectButton);
			
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
			VerticalLayout vLayout = new VerticalLayout(connectionForm, repositorySelectorHLayout, msgLabel, addButton);
			vLayout.setSizeFull();
			add(vLayout);
			
			/* Init */
			updateState();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace(); //TODO Redirigir a página de error
		}
		
	}

	public void onAddRepositoryListener(ComponentEventListener<ClickEvent<Button>> listener) {
		addButton.addClickListener(listener);
	}

	private void connectButton_Click(ClickEvent<Button> event) {
		try {
			if (tokenField.isEmpty()) {
				repositoryDataSource.connect();
			} else {
				repositoryDataSource.connect(tokenField.getValue());
				tokenField.clear();
			}
			msgLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgLabel.setText(e.getMessage());
		} finally {
			updateState();
			resetUserNameTextField();
		}
	}

	private void disconnectButton_Click(ClickEvent<Button> event) {
		try {
			if (! repositoryDataSource.getConnectionType().equals(EnumConnectionType.NOT_CONNECTED)) {
				repositoryDataSource.disconnect();
			}
			msgLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgLabel.setText(e.getMessage());
		} finally {
			updateState();
			resetUserNameTextField();
		}
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
	
	private void updateState() {
		try {
			switch (repositoryDataSource.getConnectionType()) {
			case NOT_CONNECTED:
				userAvatar.setVisible(false);
				connectionInfoLabel.setText("No connection to GitLab");
				tokenField.setEnabled(true);
				connectButton.setVisible(true);
				disconnectButton.setVisible(false);
				usernameTextField.setEnabled(false);
				repositoryComboBox.setEnabled(false);
				addButton.setEnabled(false);
				break;
			case CONNECTED:
				userAvatar.setVisible(false);
				connectionInfoLabel.setText("Using a public connection");
				tokenField.setEnabled(false);
				connectButton.setVisible(false);
				disconnectButton.setVisible(true);
				usernameTextField.setEnabled(true);
				repositoryComboBox.setEnabled(true);
				addButton.setEnabled(true);
				break;
			case LOGGED:
				User user = RepositoryDataSourceService.getInstance().getRepositoryDataSource().getCurrentUser();
				userAvatar.setSrc((user.getAvatarUrl() != null)?user.getAvatarUrl():"");
				connectionInfoLabel.setText("Connected as: " + user.getUsername());
				tokenField.setEnabled(false);
				connectButton.setVisible(false);
				disconnectButton.setVisible(true);
				usernameTextField.setEnabled(true);
				repositoryComboBox.setEnabled(true);
				addButton.setEnabled(true);
				break;
			}
			msgLabel.setText("");
		} catch (RepositoryDataSourceException e) {
			msgLabel.setText(e.toString());
		}
	}

	private void resetUserNameTextField() {
		try {
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
