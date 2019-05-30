package gui.views;

import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;

public class ConnectionForm {
	private Tab tab;
	private Label info;
	private FormLayout form;
	private Label result;
	private Button connectButton;
	private Div page;

	public ConnectionForm(String tabName, String info, List<FormElement> formElements) {
		// TAB
		this.tab = new Tab(tabName);
		// Info
		this.info = new Label(info);
		// Form
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
				new ResponsiveStep("200px", 1, LabelsPosition.TOP));

		for (FormElement formElement : formElements) {
			((HasSize) formElement.getComponent()).setWidthFull();
			form.addFormItem(formElement.getComponent(), formElement.getName());
		}

		Label resultInfo = new Label("");
		resultInfo.setWidthFull();
		form.add(resultInfo);
		Button button = new Button("Connect", new Icon(VaadinIcon.CONNECT));
		form.add(button);

		this.page = new Div(form);
	}
	
	public class FormElement {
		private String name;
		private Component component;
		
		public FormElement(String name, Component component) {
			this.name = name;
			this.component = component;
		}

		/**
		 * Gets the name.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 * @return the name
		 */
		protected String getName() {
			return name;
		}

		/**
		 * Gets the component.
		 * 
		 * @author Miguel Ángel León Bardavío - mlb0029
		 * @return the component
		 */
		protected Component getComponent() {
			return component;
		}
		
	}

	/**
	 * Gets the tab.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the tab
	 */
	public Tab getTab() {
		return tab;
	}

	/**
	 * Sets the tab.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param tab the tab to set
	 */
	public void setTab(Tab tab) {
		this.tab = tab;
	}

	/**
	 * Gets the info.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the info
	 */
	public Label getInfo() {
		return info;
	}

	/**
	 * Sets the info.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param info the info to set
	 */
	public void setInfo(Label info) {
		this.info = info;
	}

	/**
	 * Gets the form.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the form
	 */
	public FormLayout getForm() {
		return form;
	}

	/**
	 * Sets the form.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param form the form to set
	 */
	public void setForm(FormLayout form) {
		this.form = form;
	}

	/**
	 * Gets the result.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the result
	 */
	public Label getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param result the result to set
	 */
	public void setResult(Label result) {
		this.result = result;
	}

	/**
	 * Gets the connectButton.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the connectButton
	 */
	public Button getConnectButton() {
		return connectButton;
	}

	/**
	 * Sets the connectButton.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param connectButton the connectButton to set
	 */
	public void setConnectButton(Button connectButton) {
		this.connectButton = connectButton;
	}

	/**
	 * Gets the page.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the page
	 */
	public Div getPage() {
		return page;
	}

	/**
	 * Sets the page.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param page the page to set
	 */
	public void setPage(Div page) {
		this.page = page;
	}
}
