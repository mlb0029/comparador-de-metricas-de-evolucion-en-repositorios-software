package gui.views.connectionForms;

import com.vaadin.flow.component.icon.VaadinIcon;

import app.RepositoryDataSourceService;
import repositorydatasource.IRepositoryDataSource;
import repositorydatasource.IRepositoryDataSource.EnumConnectionType;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class NoConnectionForm extends AConnForm {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 8537961583258938107L;

	private static final String TAB_NAME = "Don't connect";
	
	private static final String DESCRIPTION = "In this way you can only review reports already created. You will not be able to add new repositories, nor calculate metrics.";
	
	private static final VaadinIcon BUTTON_ICON = VaadinIcon.ARROW_CIRCLE_RIGHT;
	
	private static final String BUTTON_TEXT = "Proceed";
	
	public NoConnectionForm() {
		super(
				TAB_NAME, 
				DESCRIPTION, 
				BUTTON_ICON, 
				BUTTON_TEXT);
	}

	/* (non-Javadoc)
	 * @see gui.views.connectionForms.AConnForm#addFormElements()
	 */
	@Override
	protected void addFormElements() {}

	@Override
	public void clearFields() {}

	@Override
	public boolean isValid() {return true;}

	@Override
	protected void connect() throws RepositoryDataSourceException {
		IRepositoryDataSource rds = RepositoryDataSourceService.getInstance().getRepositoryDataSource();
		if (!rds.getConnectionType().equals(EnumConnectionType.NOT_CONNECTED))
			RepositoryDataSourceService.getInstance().getRepositoryDataSource().disconnect();
	}

}
