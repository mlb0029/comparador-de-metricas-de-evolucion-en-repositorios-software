package gui.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainAppView extends Composite<Div> {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -8176239269004450857L;

	private final AppLayout root = new AppLayout();
	
	private final Div content = new Div();
	
	public MainAppView() {
		try {
			setUpRoot();
			getContent().add(root);
			content.getElement().appendChild(new RepositoriesListView().getElement());
			ConnectionFormDialog connectionFormDialog = new ConnectionFormDialog();
			connectionFormDialog.open();
			//connectionFormDialog.addDialogCloseActionListener(listener) // TODO
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setUpRoot() {
		content.setHeight("95%");
		content.setWidthFull();
		HorizontalLayout footer = new HorizontalLayout();
		footer.setHeight("5%");
		footer.setWidthFull();
		VerticalLayout vLayout = new VerticalLayout(content, footer);
		Image image = new Image("images/logoUBU.jpg", "Logo UBU");
		image.setHeight("100px");
		image.setWidth("200px");
		root.setBranding(image);
		root.setContent(vLayout);
	}
}
