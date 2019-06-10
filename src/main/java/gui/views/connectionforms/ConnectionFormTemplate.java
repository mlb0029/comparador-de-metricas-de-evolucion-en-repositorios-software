package gui.views.connectionforms;

import java.util.HashSet;
import java.util.Set;

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

import app.RepositoryDataSourceService;
import repositorydatasource.exceptions.RepositoryDataSourceException;

public abstract class ConnectionFormTemplate implements ConnectionForm{
	
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

	private static final long serialVersionUID = 7001361983934286617L;
	
	private Set<IConnectionSuccessfulListener> listeners = new HashSet<>();
	
	private Tab tab = new Tab();
	private FormLayout form = new FormLayout();
	private Label description = new Label();
	private Button button = new Button();
	private Label result = new Label();
	private Div page = new Div();

	protected ConnectionFormTemplate(String tabName, String description, VaadinIcon buttonIcon, String buttonText) {
		this.tab.setLabel(tabName);

		this.form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
				new ResponsiveStep("200px", 1, LabelsPosition.TOP));
		this.description.setText(description);
		this.form.add(this.description);
		
		addFormElements();
		
		this.result.setText("");

		if(buttonIcon != null)
			this.button.setIcon(new Icon(buttonIcon));
		this.button.setText(buttonText);
		this.button.addClickListener(e -> {
			if (isValid()) {
				try {
					connect();
					listeners.forEach(l -> l.onConnectionSuccessful(RepositoryDataSourceService.getInstance().getRepositoryDataSource().getConnectionType()));
				} catch (RepositoryDataSourceException e1) {
					result.setText(e1.getMessage());
				}
			}
		});
		this.form.add(this.button);
		
		this.result.setWidthFull();
		this.form.add(this.result);
		
		this.page.add(form);
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

	protected abstract void addFormElements();

	protected abstract void connect() throws RepositoryDataSourceException;

	/* (non-Javadoc)
	 * @see gui.views.connectionForms.IConnForm#addConnectionSuccessfulListener(gui.views.connectionForms.IConnForm.IConnectionSuccessfulListener)
	 */
	@Override
	public void addConnectionSuccessfulListener(IConnectionSuccessfulListener listener) {
		listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see gui.views.connectionForms.IConnForm#removeConnectionSuccessfulListener(gui.views.connectionForms.IConnForm.IConnectionSuccessfulListener)
	 */
	@Override
	public void removeConnectionSuccessfulListener(IConnectionSuccessfulListener listener) {
		listeners.remove(listener);
	}
}