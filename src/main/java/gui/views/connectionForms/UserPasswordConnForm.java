package gui.views.connectionForms;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class UserPasswordConnForm extends AConnForm {

	private TextField usernameField = new TextField();
	
	private PasswordField passwordField = new PasswordField();

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param tabName
	 * @param description
	 * @param buttonIcon
	 * @param buttonText
	 */
	public UserPasswordConnForm(String tabName, String description, VaadinIcon buttonIcon, String buttonText) {
		super(
				"Username and password", 
				"In this way you can access your public and private repositories and other public repositories.", 
				VaadinIcon.CONNECT, 
				"Connect"
		);
		usernameField.setWidthFull();
		passwordField.setWidthFull();
		getFormElements().add(new FormElement("Username", usernameField));
		getFormElements().add(new FormElement("Password", usernameField));
		setForm(getFormElements());
	}
	
	
	
}
