package gui.views.connectionForms;

import java.io.Serializable;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;

import repositorydatasource.IRepositoryDataSource.EnumConnectionType;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public interface IConnForm extends Serializable{
	
	public interface IConnectionSuccessfulListener {
		void onConnectionSuccessful(EnumConnectionType connectionType);
	}
	
	void clearFields();
	boolean isValid();
	Tab getTab();
	Div getPage();
	
	void addConnectionSuccessfulListener(IConnectionSuccessfulListener listener);
	void removeConnectionSuccessfulListener(IConnectionSuccessfulListener listener);
	
}
