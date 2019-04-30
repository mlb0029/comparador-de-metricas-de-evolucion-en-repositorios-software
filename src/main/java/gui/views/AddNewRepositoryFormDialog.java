package gui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;

import gui.common.RepositoriesListService;
import gui.common.RepositoryDataSourceService;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource.EnumConnectionType;
import repositorydatasource.exceptions.RepositoryDataSourceException;
import repositorydatasource.model.Repository;
import repositorydatasource.model.User;

public class AddNewRepositoryFormDialog extends Dialog {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -9068571389303968081L;
	
	private IRepositoryDataSource repositoryDataSource;

	private Details connectionDetails;
	private FormLayout connectionForm;
	private PasswordField tokenField;
	private Button connectButton;
	private Button disconnectButton;

	private ComboBox<Repository> repositorySelectorCB;
	private String repositoryUrl;

	private Label msgStatusLabel;

	private Button addButton;

	public AddNewRepositoryFormDialog() {
		repositoryDataSource = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
		
		addButton = new Button("Add", new Icon(VaadinIcon.PLUS), this::addButton_Click);
		msgStatusLabel = new Label();
		
		repositorySelectorCB = new ComboBox<Repository>();
		repositorySelectorCB.setPlaceholder("Repository URL");
		repositorySelectorCB.addCustomValueSetListener(e -> repositoryUrl = e.getDetail());
		repositorySelectorCB.setItemLabelGenerator(e -> e.getName());
		
		disconnectButton = new Button("Disconnect", new Icon(VaadinIcon.UNLINK), this::disconnectButton_Click);
		connectButton = new Button("Connect", new Icon(VaadinIcon.LINK), this::connectButton_Click);
		tokenField = new PasswordField();
		tokenField.setClearButtonVisible(true);
		
		connectionForm = new FormLayout();
		connectionForm.addFormItem(tokenField, "Personal Access Token");
		connectionForm.add(connectButton, disconnectButton);
		connectionDetails = new Details();
		connectionDetails.setContent(connectionForm);
		updateConnectionDetails();

		VerticalLayout vLayout = new VerticalLayout(connectionDetails, repositorySelectorCB, addButton);
		add(vLayout);
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
				tokenField.setVisible(true);
				connectButton.setVisible(true);
				disconnectButton.setVisible(false);
				break;
			case LOGGED:
				User user = RepositoryDataSourceService.getInstance().getRepositoryDataSource().getCurrentUser();
				Image userAvatar = new Image(user.getAvatarUrl(), "User avatar");
				Label usernameLabel = new Label(user.getUsername());
				HorizontalLayout userInfoLayout = new HorizontalLayout(userAvatar, usernameLabel);
				connectionDetails.setSummary(userInfoLayout);
				tokenField.setVisible(false);
				connectButton.setVisible(false);
				disconnectButton.setVisible(true);
				updateUserRepositories();
				break;
			}
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.toString());
		}
	}

	private void connectButton_Click(ClickEvent<Button> event) {
		try {
			if (tokenField.isEmpty()) {
				repositoryDataSource.connect();
			} else {
				repositoryDataSource.connect(tokenField.getValue());
			}
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.getMessage());
		} finally {
			clearConnectionForm();
			updateConnectionDetails();
		}
	}

	private void disconnectButton_Click(ClickEvent<Button> event) {
		try {
			if (! repositoryDataSource.getConnectionType().equals(EnumConnectionType.NOT_CONNECTED)) {
				repositoryDataSource.disconnect();
			}
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.getMessage());
		} finally {
			updateConnectionDetails();
		}
	}

	private void addButton_Click(ClickEvent<Button> event) {
		Repository repositoryToAdd;
		try {
			if (repositorySelectorCB.getOptionalValue().isPresent()) 
				repositoryToAdd = repositorySelectorCB.getOptionalValue().get();
			else
				repositoryToAdd = repositoryDataSource.getRepository(repositoryUrl);
			RepositoriesListService.getInstance().getRepositories().add(repositoryToAdd);
		} catch (RepositoryDataSourceException e) {
			msgStatusLabel.setText(e.getMessage());
		}
	}
	
	private void clearConnectionForm() {
		tokenField.clear();
	}
	
	private void updateUserRepositories() {
		try {
			if (repositoryDataSource.getConnectionType() == EnumConnectionType.LOGGED)
				repositorySelectorCB.setItems(repositoryDataSource.getAllUserRepositories());
			else
				repositorySelectorCB.setItems();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void addAddButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		addButton.addClickListener(listener);
	}
}
