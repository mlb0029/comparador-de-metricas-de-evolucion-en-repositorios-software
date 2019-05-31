package gui.views.connectionForms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;

public abstract class AConnForm {
	
	private Tab tab = new Tab();
	private FormLayout form = new FormLayout();
	private List<FormElement> formElements = new ArrayList<>();
	private Label description = new Label();
	private Button button = new Button();
	private Label result = new Label();
	private Div page = new Div();

	protected AConnForm(String tabName, String description, VaadinIcon buttonIcon, String buttonText) {
		this.tab.setLabel(tabName);

		this.form = new FormLayout();
		this.form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
				new ResponsiveStep("200px", 1, LabelsPosition.TOP));
		this.description.setText(description);
		
		this.result.setText("");

		if(buttonIcon != null)
			this.button.setIcon(new Icon(buttonIcon));
		this.button.setText(buttonText);
		
		this.result.setWidthFull();
		
		this.page.add(form);
	}

	protected void setForm(List<FormElement> formElements) {
		this.form.add(this.description);
		for (FormElement formElement : formElements) {
			this.formElements.add(formElement);
			this.form.addFormItem(formElement.getComponent(), formElement.getName());
		}
		this.form.add(this.button);
		this.form.add(this.result);
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
	 * Gets the form.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the form
	 */
	public FormLayout getForm() {
		return form;
	}

	/**
	 * Gets the formElements.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the formElements
	 */
	public List<FormElement> getFormElements() {
		return formElements;
	}

	/**
	 * Gets the description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the description
	 */
	public Label getDescription() {
		return description;
	}

	/**
	 * Gets the button.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the button
	 */
	public Button getButton() {
		return button;
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
	 * Gets the page.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the page
	 */
	public Div getPage() {
		return page;
	}

	public class FormElement {
		private String name;
		private Component component;
	
		public FormElement(String name, Component component) {
			this.name = name;
			this.component = component;
		}
	
		public String getName() {
			return name;
		}
	
		public Component getComponent() {
			return component;
		}
	}
}