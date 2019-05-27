package gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AppView extends AppLayout {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -8176239269004450857L;

	private static AppView me;
	
	private HorizontalLayout body;
	
	private AppView() {
		body = new HorizontalLayout();
		body.setHeight("95%");
		body.setWidthFull();
		HorizontalLayout footer = new HorizontalLayout();
		footer.setHeight("5%");
		footer.setWidthFull();
		VerticalLayout vLayout = new VerticalLayout(body, footer);
		setContent(vLayout);
	}
	
	public static AppView getAppView(Component component) {
		if (me == null) me = new AppView();
		me.body.removeAll();
		if (component != null) {
			me.body.add(component);
		} else {
			me.body.add(new VerticalLayout());
		}
		return me;
	}
}
