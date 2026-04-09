package seedu.taskforge.logic.commands.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.testutil.PersonBuilder;
import seedu.taskforge.testutil.UnassignProjectDescriptorBuilder;

public class UnassignProjectCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unassignOneProjectUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        Person editedPerson = expectedPersonAfterUnassign(firstPerson, 0);

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignOneProjectFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Person editedPerson = expectedPersonAfterUnassign(firstPerson, 0);

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignOneProjectOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignOneProjectOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignMultipleProjectsOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignMultipleProjectsOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignMultipleProjectsUnfilteredList_success() {
        Index indexSecondPerson = Index.fromOneBased(2);
        Person firstPerson = model.getFilteredPersonList().get(indexSecondPerson.getZeroBased());

        Person editedPerson = expectedPersonAfterUnassign(firstPerson, 0, 1);

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexSecondPerson, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignMultipleProjectsFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Person editedPerson = expectedPersonAfterUnassign(firstPerson, 0, 1);

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noProjectSpecifiedUnfilteredList_errorThrown() {
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(
                INDEX_FIRST_PERSON, new UnassignProjectCommand.UnassignProjectDescriptor()
        );
        assertCommandFailure(unassignProjectCommand, model, UnassignProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noProjectSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(
                INDEX_FIRST_PERSON, new UnassignProjectCommand.UnassignProjectDescriptor()
        );
        assertCommandFailure(unassignProjectCommand, model, UnassignProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(unassignProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Unassigns project to a person of a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(outOfBoundIndex,
                new UnassignProjectDescriptorBuilder().withProjects("1").build());

        assertCommandFailure(unassignProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignProjectRemovesTaskReferences_success() {
        Model modelWithProjectTasks = new ModelManager(new AddressBook(), new UserPrefs());
        modelWithProjectTasks.addProject(new seedu.taskforge.model.project.Project("Alpha"));
        modelWithProjectTasks.addProject(new seedu.taskforge.model.project.Project("Beta"));

        Person person = new PersonBuilder()
                .withName("Test Person")
                .withPhone("91234567")
                .withEmail("test@example.com")
                .build();
        Person personWithProjectsAndTasks = new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                Arrays.asList(new PersonProject(0), new PersonProject(1)),
                Arrays.asList(new PersonTask(0, 0), new PersonTask(1, 0)));
        modelWithProjectTasks.addPerson(personWithProjectsAndTasks);

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("2").build();
        UnassignProjectCommand command = new UnassignProjectCommand(Index.fromOneBased(1), descriptor);

        Person expectedPerson = new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                Arrays.asList(new PersonProject(0)),
                Arrays.asList(new PersonTask(0, 0)));

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(expectedPerson));
        Model expectedModel = new ModelManager(new AddressBook(modelWithProjectTasks.getAddressBook()),
                new UserPrefs());
        expectedModel.setPerson(personWithProjectsAndTasks, expectedPerson);

        assertCommandSuccess(command, modelWithProjectTasks, expectedMessage, expectedModel);
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        UnassignProjectCommand.UnassignProjectDescriptor firstDescriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand.UnassignProjectDescriptor secondDescriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand.UnassignProjectDescriptor differentDescriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("2").build();

        UnassignProjectCommand firstCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, firstDescriptor);
        UnassignProjectCommand sameValuesCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, secondDescriptor);
        UnassignProjectCommand differentCommand = new UnassignProjectCommand(INDEX_SECOND_PERSON, differentDescriptor);

        assertEquals(firstCommand.hashCode(), sameValuesCommand.hashCode());
        assertNotEquals(firstCommand.hashCode(), differentCommand.hashCode());
    }

    @Test
    public void descriptorHashCode_sameValues_sameHashCode() {
        UnassignProjectCommand.UnassignProjectDescriptor firstDescriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand.UnassignProjectDescriptor sameValuesDescriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand.UnassignProjectDescriptor differentDescriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();

        assertEquals(firstDescriptor.hashCode(), sameValuesDescriptor.hashCode());
        assertNotEquals(firstDescriptor.hashCode(), differentDescriptor.hashCode());
    }

    private static Person expectedPersonAfterUnassign(Person person, int... projectIndexesToRemove) {
        List<PersonProject> updatedProjects = new ArrayList<>(person.getProjects());
        List<PersonProject> projectsToDelete = new ArrayList<>();
        Set<Integer> removedProjectIndexes = new HashSet<>();

        for (int projectIndex : projectIndexesToRemove) {
            PersonProject projectToDelete = updatedProjects.get(projectIndex);
            projectsToDelete.add(projectToDelete);
            removedProjectIndexes.add(projectToDelete.getProjectIndex());
        }

        updatedProjects.removeAll(projectsToDelete);

        List<PersonTask> updatedTasks = new ArrayList<>();
        for (PersonTask task : person.getTasks()) {
            if (!removedProjectIndexes.contains(task.getProjectIndex())) {
                updatedTasks.add(task);
            }
        }

        return new Person(person.getName(), person.getPhone(), person.getEmail(), updatedProjects, updatedTasks);
    }
}
