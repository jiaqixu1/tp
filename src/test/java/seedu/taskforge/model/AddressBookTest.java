package seedu.taskforge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;
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
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.project.exceptions.DuplicateProjectException;
import seedu.taskforge.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getProjectList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE)
                .withProjects(VALID_PROJECT_ALPHA).withTasks(VALID_TASK_REFACTOR)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateProjects_throwsDuplicateProjectException() {
        Project alpha = new Project("alpha");
        List<Project> newProjects = Arrays.asList(alpha, alpha);
        AddressBookStub newData = new AddressBookStub(Collections.emptyList(), newProjects);

        assertThrows(DuplicateProjectException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE)
                .withProjects(VALID_PROJECT_ALPHA).withTasks(VALID_TASK_REFACTOR)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasProject(null));
    }

    @Test
    public void hasProject_projectNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasProject(new Project("alpha")));
    }

    @Test
    public void hasProject_projectInAddressBook_returnsTrue() {
        Project alpha = new Project("alpha");
        addressBook.addProject(alpha);
        assertTrue(addressBook.hasProject(alpha));
    }

    @Test
    public void removeProject_existingProject_removesProjectFromAllPersons() {
        Project alpha = new Project("alpha");
        Project beta = new Project("beta");
        Person firstPerson = new PersonBuilder().withName("First Person")
                .withPhone("11111111").withEmail("first@example.com")
                .withProjects("alpha", "beta").build();
        Person secondPerson = new PersonBuilder().withName("Second Person")
                .withPhone("22222222").withEmail("second@example.com")
                .withProjects("alpha").build();
        Person thirdPerson = new PersonBuilder().withName("Third Person")
                .withPhone("33333333").withEmail("third@example.com")
                .withProjects("beta").build();

        addressBook.addProject(alpha);
        addressBook.addProject(beta);
        addressBook.addPerson(firstPerson);
        addressBook.addPerson(secondPerson);
        addressBook.addPerson(thirdPerson);

        addressBook.removeProject(alpha);

        assertFalse(addressBook.hasProject(alpha));
        assertEquals(Arrays.asList(new Project("beta")),
                addressBook.getPersonList().get(0).getProjects());
        assertEquals(Collections.emptyList(), addressBook.getPersonList().get(1).getProjects());
        assertEquals(Arrays.asList(new Project("beta")),
                addressBook.getPersonList().get(2).getProjects());
    }

    @Test
    public void getProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getProjectList().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        AddressBook addressBookCopy = new AddressBook(addressBook);
        assertTrue(addressBook.equals(addressBookCopy));

        // same object -> returns true
        assertTrue(addressBook.equals(addressBook));

        // null -> returns false
        assertFalse(addressBook.equals(null));

        // different types -> returns false
        assertFalse(addressBook.equals(5));

        // different data -> returns false
        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addProject(new Project("alpha"));
        assertFalse(addressBook.equals(differentAddressBook));
    }

    @Test
    public void hashCodeMethod() {
        AddressBook addressBookCopy = new AddressBook(addressBook);
        assertEquals(addressBook.hashCode(), addressBookCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList()
                + ", projects=" + addressBook.getProjectList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Project> projects = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this(persons, Collections.emptyList());
        }

        AddressBookStub(Collection<Person> persons, Collection<Project> projects) {
            this.persons.setAll(persons);
            this.projects.setAll(projects);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Project> getProjectList() {
            return projects;
        }
    }

}
