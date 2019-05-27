package gui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends Div {

	private static final long serialVersionUID = -451691358181468519L;
	
	public MainView() {
		try {
			add(AppView.getAppView(RepositoriesListView.getRepositoryListView()));
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}
}
