package gui.views;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import repositorydatasource.IRepositoryDataSource;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class ConnectionFormDialog extends Dialog{

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -2348702400211722166L;
	
	private enum ConnectionType {
		USERNAME_AND_PASSWORD("Username and password", 1),
		PA_TOKEN("Personal Access Token", 2),
		PUBLIC_CONN("No login", 3);
		
		private final String description;
		private final int code;
		
		private ConnectionType(String description, int code) {
			this.description = description;
			this.code = code;
		}
		
		public String getDescription() {
			return description;
		}
		
		public int getCode() {
			return code;
		}
	}

	private static Logger logger = LoggerFactory.getLogger(ConnectionFormDialog.class);
	
	private static ConnectionFormDialog me;
	
	private IRepositoryDataSource repositoryDataSource;
	
	private Select<ConnectionType> connectionTypeSelect;
	private VerticalLayout connForm;
	private Button connectButton;
	
	private ConnectionFormDialog() {
		connForm = new VerticalLayout();
		VerticalLayout vLayout = new VerticalLayout(connectionTypeSelect, connForm, connectButton);
		add(vLayout);
	}
	
	private void connectButtonClick() {
		
	}
	
	private static ConnectionFormDialog getConnectionFormDialog() {
		if (me == null) me = new ConnectionFormDialog();
		return me;
	}
	
	private void initConnectionTypeSelect() {
		connectionTypeSelect.setItems(Arrays.stream(ConnectionType.values()));
		connectionTypeSelect.setItemLabelGenerator(ConnectionType::getDescription);
	}
	
	private class UserPasswordConnectionFrom extends FormLayout {

		private static final long serialVersionUID = 5156578351900901881L;
		
		private TextField txtUsername;
		private PasswordField txtPasword;
		
		private UserPasswordConnectionFrom() {
			addFormItem(txtUsername, "Username");
			addFormItem(txtPasword, "Password");
		}
		
	}
	
	private class PATokenConnectionFrom extends FormLayout {

		private static final long serialVersionUID = 5002029280703848199L;
		
		private PasswordField txtToken;
		
		private PATokenConnectionFrom() {
			addFormItem(txtToken, "Personal Access Token");
		}
	}
}
