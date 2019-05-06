package gui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = -451691358181468519L;
	
	public MainView() {
		try {
			RepositoriesListView repositoryListView = new RepositoriesListView();
			add(repositoryListView);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}
}
