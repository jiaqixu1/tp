package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.task.UnassignTaskCommand.UnassignTaskDescriptor;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.testutil.PersonBuilder;
import seedu.taskforge.testutil.UnassignTaskDescriptorBuilder;

public class UnassignTaskCommandTest {
    private final Model model = createModelWithProjectTasks();

    private Model createModelWithProjectTasks() {
        Model seededModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        seededModel.setProject(new Project("alpha"), new Project("alpha", Arrays.asList(
                new Task(VALID_TASK_REFACTOR),
                new Task(VALID_TASK_FIX_ERROR)
        )));
        seededModel.setProject(new Project("beta"), new Project("beta", Arrays.asList(
                new Task(VALID_TASK_REFACTOR),
                new Task(VALID_TASK_FIX_ERROR)
        )));
        return seededModel;
    }

    @Test
    public void execute_unassignOneTaskUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(UnassignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignOneTaskFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnassignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignOneTaskOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("2").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(unassignTaskCommand, model, UnassignTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_unassignOneTaskOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("2").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(unassignTaskCommand, model, UnassignTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_unassignMultipleTasksOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(unassignTaskCommand, model, UnassignTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_unassignMultipleTasksOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(unassignTaskCommand, model, UnassignTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_unassignMultipleTasksUnfilteredList_success() {
        Index indexSecondPerson = Index.fromOneBased(2);
        Person firstPerson = model.getFilteredPersonList().get(indexSecondPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(indexSecondPerson, descriptor);

        String expectedMessage = String.format(UnassignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignMultipleTasksFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnassignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noTaskSpecifiedUnfilteredList_errorThrown() {
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON,
                new UnassignTaskDescriptor());
        assertCommandFailure(unassignTaskCommand, model, UnassignTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noTaskSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON,
                new UnassignTaskDescriptor());
        assertCommandFailure(unassignTaskCommand, model, UnassignTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(unassignTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds task to a person of a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(outOfBoundIndex,
                new UnassignTaskDescriptorBuilder().withTasks("1").build());

        assertCommandFailure(unassignTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskNotInAssignedProjects_success() {
        Model modelWithMissingTask = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelWithMissingTask.setProject(new Project("alpha"), new Project("alpha", Arrays.asList(
            new Task(VALID_TASK_FIX_ERROR)
        )));

        Person firstPerson = modelWithMissingTask.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withTasks().build();
        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptorBuilder().withTasks("1").build();
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnassignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(modelWithMissingTask.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignTaskCommand, modelWithMissingTask, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnassignTaskDescriptor firstDescriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("1").build();
        UnassignTaskDescriptor secondDescriptor = new UnassignTaskDescriptorBuilder()
                .withTasks("2").build();

        UnassignTaskCommand firstCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, firstDescriptor);
        UnassignTaskCommand sameValuesCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, firstDescriptor);
        UnassignTaskCommand differentCommand = new UnassignTaskCommand(INDEX_FIRST_PERSON, secondDescriptor);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(sameValuesCommand));
        assertFalse(firstCommand.equals(differentCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals((Object) null));
    }

    @Test
    public void unassignTaskDescriptor_behaviour() {
        UnassignTaskDescriptor descriptor = new UnassignTaskDescriptor();
        assertFalse(descriptor.isTaskFieldEdited());

        descriptor.setTasksIndexes(Arrays.asList(Index.fromOneBased(1)));
        assertTrue(descriptor.isTaskFieldEdited());
        assertThrows(UnsupportedOperationException.class, () -> descriptor.getTasksIndexes().get().clear());
    }

}
