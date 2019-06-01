package gui.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainAppView extends Composite<Div> {

	private static final long serialVersionUID = -8176239269004450857L;

	private VerticalLayout root = new VerticalLayout();
	
	private Div header = new Div();
	
	private Image brandingImage = new Image("images/logoUBU.jpg", "Logo UBU");
	
	private Label appNameLabel = new Label("Evolution metrics");
	
	private Button connectionButton = new Button();

	private ConnectionInfoComponent connectionInfoComponent = new ConnectionInfoComponent();
	
	private CloseConnectionFormDialog closeConnectionFormDialog = new CloseConnectionFormDialog();

	private Div content = new Div();
	
	private Div footer = new Div();

	private Label authorNameLabel = new Label("By Miguel Ángel León Bardavío");

	private ConnectionFormDialog connectionFormDialog = new ConnectionFormDialog();

	public MainAppView() {
		try {
			setUpHeader();
			setUpContent();
			setUpFooter();
			root.add(header, content, footer);
			getContent().add(root);
			connectionFormDialog.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setUpHeader() {
		header.setHeight("15%");
		header.setWidthFull();
		
		brandingImage.setHeight("100px");
		brandingImage.setWidth("200px");
		connectionButton.setIcon(connectionInfoComponent);
		connectionButton.setMinHeight("60px");
		connectionButton.addClickListener(e -> closeConnectionFormDialog.open());
		HorizontalLayout headerHLayout = new HorizontalLayout(brandingImage, appNameLabel, connectionButton);
		header.getElement().appendChild(headerHLayout.getElement());
		
	}

	private void setUpContent() {
		content.setHeight("80%");
		content.setWidthFull();
		content.getElement().appendChild(new RepositoriesListView().getElement());
	}

	private void setUpFooter() {
		footer.setHeight("5%");
		footer.setWidthFull();
		HorizontalLayout footerHLayout = new HorizontalLayout();
		footerHLayout.add(authorNameLabel);
		footerHLayout.setAlignItems(Alignment.CENTER);
		footerHLayout.setWidthFull();
		footer.getElement().appendChild(footerHLayout.getElement());
	}
	
}
