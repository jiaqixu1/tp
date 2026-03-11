package seedu.taskforge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalPersons.ALICE;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.exceptions.DuplicatePersonException;
import seedu.taskforge.testutil.PersonBuilder;

public class TaskForgeTest {

    private final TaskForge taskForge = new TaskForge();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskForge.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskForge.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TaskForge newData = getTypicalAddressBook();
        taskForge.resetData(newData);
        assertEquals(newData, taskForge);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        TaskForgeStub newData = new TaskForgeStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> taskForge.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskForge.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(taskForge.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        taskForge.addPerson(ALICE);
        assertTrue(taskForge.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        taskForge.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(taskForge.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taskForge.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = TaskForge.class.getCanonicalName() + "{persons=" + taskForge.getPersonList() + "}";
        assertEquals(expected, taskForge.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class TaskForgeStub implements ReadOnlyTaskForge {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        TaskForgeStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
