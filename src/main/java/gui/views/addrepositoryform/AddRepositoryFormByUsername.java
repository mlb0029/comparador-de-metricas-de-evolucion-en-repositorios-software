package gui.views.addrepositoryform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import app.RepositoriesCollectionService;
import app.RepositoryDataSourceService;
import datamodel.Repository;
import repositorydatasource.RepositoryDataSource;
import repositorydatasource.exceptions.RepositoryDataSourceException;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class AddRepositoryFormByUsername extends AddRepositoryFormTemplate {

	private static final long serialVersionUID = 244817979729487325L;
	
	private static final String TAB_NAME = "By Username";
	
	private static final String DESCRIPTION = "Enter a username to be able to choose between one of its repositories.";

	private TextField usernameTextField;
	
	private ComboBox<Repository> repositoryComboBox;
	
	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param tabName
	 * @param description
	 * @param buttonIcon
	 * @param buttonText
	 */
	public AddRepositoryFormByUsername() {
		super(
				TAB_NAME, 
				DESCRIPTION 
		);
	}

	/* (non-Javadoc)
	 * @see gui.views.addrepositoryform.AddRepositoryForm#clearFields()
	 */
	@Override
	public void clearFields() {
		usernameTextField.clear();
		repositoryComboBox.clear();
	}

	/* (non-Javadoc)
	 * @see gui.views.addrepositoryform.AddRepositoryFormTemplate#addFormElements()
	 */
	@Override
	protected void addFormElements() {
		usernameTextField = new TextField();
		usernameTextField.setWidth("50%");
		usernameTextField.setPlaceholder("Username");
		usernameTextField.setClearButtonVisible(true);
		usernameTextField.addValueChangeListener(event -> updateUserRepositories());
		
		repositoryComboBox = new ComboBox<Repository>();
		repositoryComboBox.setWidth("50%");
		repositoryComboBox.setPlaceholder("Repository");
		repositoryComboBox.setAllowCustomValue(false);
		repositoryComboBox.setItemLabelGenerator(repository -> repository.getName());
		
		HorizontalLayout repositorySelectorHLayout = new HorizontalLayout(usernameTextField, repositoryComboBox);
		repositorySelectorHLayout.setWidthFull();
		getForm().add(repositorySelectorHLayout);
	}

	/* (non-Javadoc)
	 * @see gui.views.addrepositoryform.AddRepositoryFormTemplate#connect()
	 */
	@Override
	protected Repository createRepository() throws RepositoryDataSourceException {
		return repositoryComboBox.getOptionalValue().orElseThrow( () -> new RepositoryDataSourceException("No repository selected"));
	}

	private void updateUserRepositories() {
		try {
			RepositoryDataSource repositoryDataSource = RepositoryDataSourceService.getInstance();
			RepositoriesCollectionService repositoriesService = RepositoriesCollectionService.getInstance();
			if (!usernameTextField.isEmpty()) {
				Collection<Repository> repositories = new ArrayList<Repository>();
				repositories = repositoryDataSource.getAllUserRepositories(usernameTextField.getValue())
						.stream()
						.filter(r -> !repositoriesService.getRepositories().contains(r))
						.sorted(Repository.getComparatorByName())
						.collect(Collectors.toList());
				repositoryComboBox.setItems(repositories);
			} else {
				repositoryComboBox.setItems();
				repositoryComboBox.clear();
			}
		} catch (Exception e) {
			repositoryComboBox.setItems();
			this.getResult().setText(e.getMessage());
		}
	}
}
