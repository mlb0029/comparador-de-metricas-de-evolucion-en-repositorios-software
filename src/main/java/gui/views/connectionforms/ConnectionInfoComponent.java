package gui.views.connectionforms;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import app.RepositoryDataSourceService;
import datamodel.User;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource.EnumConnectionType;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class ConnectionInfoComponent extends Div {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 5985569310011263808L;

	private Image userAvatar = new Image();
	
	private Label connectionInfoLabel = new Label();
	
	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public ConnectionInfoComponent() {
		IRepositoryDataSource rds = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
		rds.addConnectionChangeEventListener(this::update);
		update(rds.getConnectionType());
		userAvatar.setWidth("50px");
		userAvatar.setHeight("50px");
		userAvatar.setAlt("User Avatar");
		HorizontalLayout hLayout = new HorizontalLayout(userAvatar, connectionInfoLabel);
		hLayout.setVerticalComponentAlignment(Alignment.CENTER, userAvatar, connectionInfoLabel);
		hLayout.setWidthFull();
		add(hLayout);
	}

	/**
	 * Gets the userAvatar.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the userAvatar
	 */
	public Image getUserAvatar() {
		return userAvatar;
	}

	/**
	 * Gets the connectionInfo.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the connectionInfo
	 */
	public Label getConnectionInfo() {
		return connectionInfoLabel;
	}

	public void update(EnumConnectionType connectionType) {
		switch (connectionType) {
		case NOT_CONNECTED:
			userAvatar.setVisible(false);
			userAvatar.setSrc("");
			connectionInfoLabel.setText("No connection to GitLab");
			break;
		case CONNECTED:
			userAvatar.setVisible(false);
			userAvatar.setSrc("");
			connectionInfoLabel.setText("Using a public connection");
			break;
		case LOGGED:
			try {
				User user = RepositoryDataSourceService.getInstance().getRepositoryDataSource().getCurrentUser();
				userAvatar.setVisible(true);
				userAvatar.setSrc((user.getAvatarUrl() != null)?user.getAvatarUrl():"");
				connectionInfoLabel.setText("Connected as: " + user.getUsername());
			} catch (RepositoryDataSourceException e) {
				userAvatar.setVisible(false);
				userAvatar.setSrc("");
				connectionInfoLabel.setText("Connected as: [ERROR]");
				//TODO Lanzar mensaje de error
			}
			break;
		}
	}
}
