package gui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import app.RepositoryDataSourceService;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource.EnumConnectionType;
import repositorydatasource.exceptions.RepositoryDataSourceException;

public class CloseConnectionFormDialog extends Dialog {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -3169215633646184159L;

	private ConnectionInfoComponent connectionInfoComponent = new ConnectionInfoComponent();
	
	private ConnectionFormDialog connectionFormDialog = new ConnectionFormDialog();
	
	IRepositoryDataSource rds = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
	
	private Button closeConnection = new Button("Close connection", new Icon(VaadinIcon.UNLINK), 
			event ->  
	{
		if(rds.getConnectionType() != EnumConnectionType.NOT_CONNECTED) {
			try {
				rds.disconnect();
			} catch (RepositoryDataSourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		close();
		connectionFormDialog.open();
	});

	public CloseConnectionFormDialog() {
		if (rds.getConnectionType().equals(EnumConnectionType.NOT_CONNECTED)) {
			closeConnection.setText("Connect");
			closeConnection.setIcon(new Icon(VaadinIcon.CONNECT));
		}
		FormLayout formLayout = new FormLayout();
		formLayout.addFormItem(closeConnection, connectionInfoComponent);
		add(formLayout);
	}

}
