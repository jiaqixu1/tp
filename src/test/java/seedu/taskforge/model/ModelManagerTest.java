package seedu.taskforge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalPersons.ALICE;
import static seedu.taskforge.testutil.TypicalPersons.BENSON;
import static seedu.taskforge.testutil.TypicalPersons.CARL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.GuiSettings;
import seedu.taskforge.model.person.NameContainsKeywordsPredicate;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.testutil.AddressBookBuilder;
import seedu.taskforge.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void constructor_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModelManager(null, new UserPrefs()));
    }

    @Test
    public void constructor_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModelManager(new AddressBook(), null));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasProject(null));
    }

    @Test
    public void hasProject_projectNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasProject(new Project("alpha")));
    }

    @Test
    public void hasProject_projectInAddressBook_returnsTrue() {
        Project alpha = new Project("alpha");
        modelManager.addProject(alpha);
        assertTrue(modelManager.hasProject(alpha));
    }

    @Test
    public void setAddressBook_replacesData() {
        AddressBook source = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager.setAddressBook(source);
        assertEquals(source, new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void deleteProject_existingProject_projectDeleted() {
        Project alpha = new Project("alpha");
        modelManager.addProject(alpha);

        modelManager.deleteProject(alpha);

        assertFalse(modelManager.hasProject(alpha));
    }

    @Test
    public void deleteProject_existingProject_projectDeletedFromAllPersons() {
        Project alpha = new Project("alpha");
        Project beta = new Project("beta");
        Person personWithAlphaAndBeta = new PersonBuilder().withName("Project Owner")
                .withPhone("99988877").withEmail("owner@example.com")
                .withProjects("alpha", "beta").build();

        modelManager.addProject(alpha);
        modelManager.addProject(beta);
        modelManager.addPerson(personWithAlphaAndBeta);

        modelManager.deleteProject(alpha);

        assertFalse(modelManager.hasProject(alpha));
        assertEquals(Arrays.asList(new Project("beta")), modelManager.getFilteredPersonList()
                .get(0).getProjects());
    }

    @Test
    public void setProject_existingProject_projectUpdated() {
        Project alpha = new Project("alpha");
        Project beta = new Project("beta");
        modelManager.addProject(alpha);

        modelManager.setProject(alpha, beta);

        assertFalse(modelManager.hasProject(alpha));
        assertTrue(modelManager.hasProject(beta));
    }

    @Test
    public void setPerson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(ALICE, null));
    }

    @Test
    public void addPerson_updatesFilteredList() {
        modelManager.addPerson(ALICE);
        modelManager.updateFilteredPersonList(person -> false);

        modelManager.addPerson(BENSON);

        assertEquals(Arrays.asList(ALICE, BENSON), modelManager.getFilteredPersonList());
    }

    @Test
    public void deletePerson_existingPerson_personDeleted() {
        modelManager.addPerson(ALICE);

        modelManager.deletePerson(ALICE);

        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void setPerson_validPersons_updatesPerson() {
        modelManager.addPerson(ALICE);

        modelManager.setPerson(ALICE, CARL);

        assertFalse(modelManager.hasPerson(ALICE));
        assertTrue(modelManager.hasPerson(CARL));
    }

    @Test
    public void updateFilteredPersonList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredPersonList(null));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
