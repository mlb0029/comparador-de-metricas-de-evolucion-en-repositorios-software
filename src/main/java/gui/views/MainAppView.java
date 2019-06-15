package gui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import exceptions.GUIException;
import gui.views.connectionforms.CloseConnectionDialog;
import gui.views.connectionforms.ConnectionDialog;
import gui.views.connectionforms.ConnectionInfoComponent;

@StyleSheet("site.css")
@Route("")
public class MainAppView extends VerticalLayout {

	private static final long serialVersionUID = -8176239269004450857L;
	
	/**
	 * To know if it is the start of the application or a page refresh. 
	 * Since a page refresh creates a new instance of the components.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static boolean IS_INITIALIZED = false;

	private Div header = new Div();
	
	private Image brandingImage = new Image("images/logoUBU.jpg", "Logo UBU");
	
	private Label appNameLabel = new Label("Evolution metrics");
	
	private Button connectionButton = new Button();

	private ConnectionInfoComponent connectionInfoComponent = new ConnectionInfoComponent();
	
	private CloseConnectionDialog closeConnectionFormDialog = new CloseConnectionDialog();

	private Div content = new Div();
	
	private Div footer = new Div();

	private Span authorNameLabel = new Span();

	private ConnectionDialog connectionFormDialog = new ConnectionDialog();

	private RepositoriesListView repositoriesListView = new RepositoriesListView();
	
	public MainAppView() {
		try {
			setSizeFull();
			setUpHeader();
			setUpContent();
			setUpFooter();
			add(header, content, footer);
			if (!IS_INITIALIZED)
				connectionFormDialog.open();
			MainAppView.IS_INITIALIZED = true;
		} catch (Exception e) {
			Exception guiEx = new GUIException(GUIException.MAIN_INITIALIZATION_ERROR, e);// To log, don't throw
			getElement().removeAllChildren();
			getElement().appendChild(new Span("Error occurred, contact the administrator."
					+ " Error Message: " + guiEx.getMessage()).getElement());
		}
	}
	
	private void setUpHeader() {
		header.setHeight("15%");
		header.setWidthFull();
		
		brandingImage.setHeight("6em");
		brandingImage.setWidth("15%");
		
		appNameLabel.addClassName("appName");
		appNameLabel.setWidth("45%");
		
		connectionButton.setIcon(connectionInfoComponent);
		connectionButton.setMinHeight("60px");
		connectionButton.addClickListener(e -> closeConnectionFormDialog.open());
		connectionButton.setId("connectionInfoButton");
		VerticalLayout connectionButtonLayout = new VerticalLayout(connectionButton);
		connectionButtonLayout.setAlignItems(Alignment.END);
		connectionButtonLayout.setWidth("40%");
		HorizontalLayout headerHLayout = new HorizontalLayout(brandingImage, appNameLabel, connectionButtonLayout);
		header.getElement().appendChild(headerHLayout.getElement());
	}

	private void setUpContent() {
		content.setMinHeight("70%");
		content.setWidthFull();
		content.getElement().appendChild(repositoriesListView.getElement());
	}

	private void setUpFooter() {
		footer.setHeight("15%");
		footer.setWidthFull();
		HorizontalLayout footerHLayout = new HorizontalLayout();
		footerHLayout.add(authorNameLabel);
		authorNameLabel.setText("Autor: Miguel Ángel León Bardavío\nTutor: Carlos López Nozal");
		authorNameLabel.setId("Authors");
		footerHLayout.setWidthFull();
		footer.getElement().appendChild(footerHLayout.getElement());
	}
}
