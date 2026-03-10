package seedu.taskforge.model;

import javafx.collections.ObservableList;
import seedu.taskforge.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskForge {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
