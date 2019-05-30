package gui.views;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import app.RepositoryDataSourceService;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class ConnectionFormDialog extends Dialog {

	private static final long serialVersionUID = -2348702400211722166L;

	private static Logger logger = LoggerFactory.getLogger(ConnectionFormDialog.class);

	private RepositoryDataSourceService rdss = RepositoryDataSourceService.getInstance();

	private Map<Tab, Div> connectionForms = new LinkedHashMap<>();

	public ConnectionFormDialog() {
		createUserPasswdForm();
		createTokenForm();
		createPublicConnForm();
		createNoConnForm();
		Tabs tabs = new Tabs();
		tabs.setOrientation(Orientation.VERTICAL);
		tabs.setWidth("30%");
		Div forms = new Div();
		forms.setWidth("70%");
		for (Entry<Tab, Div> connForm : connectionForms.entrySet()) {
			tabs.add(connForm.getKey());
			forms.add(connForm.getValue());
			if (connForm.getKey().isSelected()) {
				connForm.getValue().setVisible(true);
			} else {
				connForm.getValue().setVisible(false);
			}
		}
		tabs.addSelectedChangeListener(event -> {
			for (Div form : connectionForms.values()) {
				form.setVisible(false);
			}
			connectionForms.get(event.getSource().getSelectedTab()).setVisible(true);
		});
		HorizontalLayout vLayout = new HorizontalLayout(tabs, forms);
		vLayout.setSizeFull();
		add(vLayout);
		setWidth("550px");
		setHeight("400px");
	}

	private void createUserPasswdForm() {
		// TAB
		Tab tab = new Tab("Username and password");
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
				new ResponsiveStep("200px", 1, LabelsPosition.TOP));
		// Info
		Label description = new Label(
				"In this way you can access your public and private repositories and other public repositories.");
		form.add(description);

		TextField usernameTxt = new TextField();
		usernameTxt.setWidthFull();
		form.addFormItem(usernameTxt, "Username");

		PasswordField passwordField = new PasswordField();
		passwordField.setWidthFull();
		form.addFormItem(passwordField, "Password");

		Label resultInfo = new Label(" ");

		Button button = new Button("Connect", new Icon(VaadinIcon.CONNECT), e -> {
			try {
				rdss.getRepositoryDataSource().connect(usernameTxt.getValue(), passwordField.getValue());
				this.close();
			} catch (RepositoryDataSourceException e1) {
				resultInfo.setText(e1.getMessage());
			}
		});
		form.add(button);

		resultInfo.setWidthFull();
		form.add(resultInfo);

		Div page = new Div(form);
		connectionForms.put(tab, page);
	}

	private void createTokenForm() {
		// TAB
		Tab tab = new Tab("Personal Access Token");

		// Form
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
				new ResponsiveStep("200px", 1, LabelsPosition.TOP));
		// Info
		Label description = new Label(
				"In this way you can access your public and private repositories and other public repositories.");
		form.add(description);

		PasswordField passwordField = new PasswordField();
		passwordField.setWidthFull();
		form.addFormItem(passwordField, "Personal Access Token");

		Label resultInfo = new Label(" ");

		Button button = new Button("Connect", new Icon(VaadinIcon.CONNECT), e -> {
			try {
				rdss.getRepositoryDataSource().connect(passwordField.getValue());
				this.close();
			} catch (RepositoryDataSourceException e1) {
				resultInfo.setText(e1.getMessage());
			}
		});
		form.add(button);

		resultInfo.setWidthFull();
		form.add(resultInfo);

		Div page = new Div(form);
		connectionForms.put(tab, page);
	}

	private void createPublicConnForm() {
		// TAB
		Tab tab = new Tab("Public connection");

		// Form
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
				new ResponsiveStep("200px", 1, LabelsPosition.TOP));
		// Info
		Label description = new Label("In this way you can only access public repositories.");
		form.add(description);

		Label resultInfo = new Label(" ");

		Button button = new Button("Connect", new Icon(VaadinIcon.CONNECT), e -> {
			try {
				rdss.getRepositoryDataSource().connect();
				this.close();
			} catch (RepositoryDataSourceException e1) {
				resultInfo.setText(e1.getMessage());
			}
		});
		form.add(button);

		resultInfo.setWidthFull();
		form.add(resultInfo);

		Div page = new Div(form);
		connectionForms.put(tab, page);
	}

	private void createNoConnForm() {
		// TAB
		Tab tab = new Tab("Don't connect");

		// Form
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
				new ResponsiveStep("200px", 1, LabelsPosition.TOP));
		// Info
		Label description = new Label("In this way you can only review reports already created. You will not be able to add new repositories, nor calculate metrics.");
		form.add(description);

		Label resultInfo = new Label(" ");

		Button button = new Button("Next", new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT), e -> {
			this.close();
		});
		form.add(button);

		resultInfo.setWidthFull();
		form.add(resultInfo);

		Div page = new Div(form);
		connectionForms.put(tab, page);
	}

}
