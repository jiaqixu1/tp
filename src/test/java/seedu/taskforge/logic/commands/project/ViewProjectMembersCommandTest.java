package seedu.taskforge.logic.commands.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.taskforge.commons.core.GuiSettings;
import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ReadOnlyAddressBook;
import seedu.taskforge.model.ReadOnlyUserPrefs;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;

/**
 * Unit tests for ViewProjectMembersCommand.
 */
public class ViewProjectMembersCommandTest {

    private final Project alpha = new Project("Alpha");
    private final Project beta = new Project("Beta");

    private final Person alex = new Person(
            new Name("Alex Yeoh"),
            new Phone("87438807"),
            new Email("alexyeoh@example.com"),
            Arrays.asList(new PersonProject(0)),
            Collections.emptyList());

    private final Person bernice = new Person(
            new Name("Bernice Yu"),
            new Phone("99272758"),
            new Email("berniceyu@example.com"),
            Arrays.asList(new PersonProject(0), new PersonProject(1)),
            Collections.emptyList());

    private final Person charlotte = new Person(
            new Name("Charlotte Oliveiro"),
            new Phone("93210283"),
            new Email("charlotte@example.com"),
            Arrays.asList(new PersonProject(1)),
            Collections.emptyList());

    @Test
    public void execute_validProjectIndexWithMembers_success() throws Exception {
        ModelStub modelStub = new ModelStubWithProjectAndPersonList(
                new Project[] {alpha, beta},
                new Person[] {alex, bernice, charlotte});

        ViewProjectMembersCommand command = new ViewProjectMembersCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(modelStub);

        String expectedMessage = "Members in project Alpha:\n"
                + "1. Alex Yeoh\n"
                + "2. Bernice Yu";

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_validProjectIndexWithNoMembers_success() throws Exception {
        Project gamma = new Project("Gamma");

        ModelStub modelStub = new ModelStubWithProjectAndPersonList(
                new Project[] {alpha, beta, gamma},
                new Person[] {alex, bernice, charlotte});

        ViewProjectMembersCommand command = new ViewProjectMembersCommand(Index.fromOneBased(3));
        CommandResult result = command.execute(modelStub);

        assertEquals("There are no members in project: Gamma", result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidProjectIndex_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithProjectAndPersonList(
                new Project[] {alpha, beta},
                new Person[] {alex, bernice, charlotte});

        ViewProjectMembersCommand command = new ViewProjectMembersCommand(Index.fromOneBased(3));

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(modelStub));

        assertEquals(ViewProjectMembersCommand.MESSAGE_INVALID_PROJECT_INDEX, exception.getMessage());
    }

    @Test
    public void equals() {
        ViewProjectMembersCommand firstCommand = new ViewProjectMembersCommand(Index.fromOneBased(1));
        ViewProjectMembersCommand secondCommand = new ViewProjectMembersCommand(Index.fromOneBased(2));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(new ViewProjectMembersCommand(Index.fromOneBased(1))));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
    }

    /**
     * A default model stub that has all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProject(Project target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProject(Project target, Project editedProject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getProjectList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook(String input) {

        }

        @Override
        public String undoAddressBook() {
            return "";
        }

        @Override
        public String redoAddressBook() {
            return "";
        }

        @Override
        public boolean canUndoAddressBook() {
            return false;
        }

        @Override
        public boolean canRedoAddressBook() {
            return false;
        }
    }

    /**
     * A Model stub that contains fixed project and person lists.
     */
    private class ModelStubWithProjectAndPersonList extends ModelStub {
        private final ObservableList<Project> projects = FXCollections.observableArrayList();
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final FilteredList<Person> filteredPersons;

        ModelStubWithProjectAndPersonList(Project[] projects, Person[] persons) {
            this.projects.addAll(projects);
            this.persons.addAll(persons);
            this.filteredPersons = new FilteredList<>(this.persons);
        }

        @Override
        public ObservableList<Project> getProjectList() {
            return FXCollections.unmodifiableObservableList(projects);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            filteredPersons.setPredicate(predicate);
        }
    }
}
