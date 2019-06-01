package gui.views;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;

import gui.views.connectionForms.IConnForm;
import gui.views.connectionForms.NoConnectionForm;
import gui.views.connectionForms.PATokenForm;
import gui.views.connectionForms.PublicConnectionForm;
import gui.views.connectionForms.UserPasswordConnForm;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class ConnectionFormDialog extends Dialog {

	private static final long serialVersionUID = -2348702400211722166L;

	private List<IConnForm> connectionForms = new ArrayList<>();
	
	public ConnectionFormDialog() {
		createConnectionForms();

		Tabs tabs = new Tabs();
		tabs.setOrientation(Orientation.VERTICAL);
		tabs.setWidth("30%");

		Div forms = new Div();
		forms.setWidth("70%");

		for (IConnForm iConnForm : connectionForms) {
			tabs.add(iConnForm.getTab());
			forms.add(iConnForm.getPage());
			iConnForm.addConnectionSuccessfulListener(c -> close());
		}

		tabs.addSelectedChangeListener(event -> {
			for (IConnForm iConnForm : connectionForms) {
				if (iConnForm.getTab() == event.getSource().getSelectedTab())
					iConnForm.getPage().setVisible(true);
				else {
					iConnForm.getPage().setVisible(false);
					iConnForm.clearFields();
				}
			}
		});

		HorizontalLayout connFormsHLayout = new HorizontalLayout(tabs, forms);
		connFormsHLayout.setSizeFull();
		add(connFormsHLayout);

		setWidth("550px");
		setHeight("400px");
		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);
	}

	private void createConnectionForms() {

		IConnForm userPasswordConnForm = new UserPasswordConnForm();
		userPasswordConnForm.getTab().setSelected(true);
		userPasswordConnForm.getPage().setVisible(true);
		connectionForms.add(userPasswordConnForm);

		IConnForm paTokenConnForm = new PATokenForm();
		paTokenConnForm.getTab().setSelected(false);
		paTokenConnForm.getPage().setVisible(false);
		connectionForms.add(paTokenConnForm);

		IConnForm publicConnForm = new PublicConnectionForm();
		publicConnForm.getTab().setSelected(false);
		publicConnForm.getPage().setVisible(false);
		connectionForms.add(publicConnForm);

		IConnForm noConnForm = new NoConnectionForm();
		noConnForm.getTab().setSelected(false);
		noConnForm.getPage().setVisible(false);
		connectionForms.add(noConnForm);
	}

}
