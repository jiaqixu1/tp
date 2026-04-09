package seedu.taskforge.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.taskforge.commons.core.LogsCenter;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final ReadOnlyTaskForge taskForge;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList} and {@code ReadOnlyTaskForge}.
     */
    public PersonListPanel(ObservableList<Person> personList, ReadOnlyTaskForge taskForge) {
        super(FXML);
        this.taskForge = taskForge;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Listen to changes in the project list and refresh the person list view when a change occurs
        this.taskForge.getProjectList().addListener((ListChangeListener.Change<?> change) -> {
            personListView.refresh();
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, taskForge).getRoot());
            }
        }
    }

}
