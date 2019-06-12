package gui.views.connectionforms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import app.RepositoryDataSourceService;
import exceptions.RepositoryDataSourceException;
import repositorydatasource.RepositoryDataSource;
import repositorydatasource.RepositoryDataSource.EnumConnectionType;

public class CloseConnectionDialog extends Dialog {

	private static final long serialVersionUID = -3169215633646184159L;

	private ConnectionInfoComponent connectionInfoComponent = new ConnectionInfoComponent();
	
	private ConnectionDialog connectionFormDialog = new ConnectionDialog();
	
	private RepositoryDataSource rds = RepositoryDataSourceService.getInstance();
	
	private Button closeConnectionButton = new Button();
	
	private Button closeDialogButton = new Button("Close", new Icon(VaadinIcon.CLOSE), event -> close());

	public CloseConnectionDialog() {
		addOpenedChangeListener(event ->{
			if(event.isOpened()) {
				if (rds.getConnectionType().equals(EnumConnectionType.NOT_CONNECTED)) {
					closeConnectionButton.setText("Connect");
					closeConnectionButton.setIcon(new Icon(VaadinIcon.CONNECT));
				} else {
					closeConnectionButton.setText("Close connection");
					closeConnectionButton.setIcon(new Icon(VaadinIcon.UNLINK));
				}							
			}
		});
		
		closeConnectionButton.addClickListener(event ->  
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
		HorizontalLayout buttonsLayout = new HorizontalLayout(closeConnectionButton, closeDialogButton);
		VerticalLayout vLayout = new VerticalLayout(connectionInfoComponent, buttonsLayout);
		vLayout.setAlignItems(Alignment.CENTER);
		add(vLayout);
	}

}
