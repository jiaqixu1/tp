package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_Y;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_Z;
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
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.testutil.AssignTaskDescriptorBuilder;
import seedu.taskforge.testutil.PersonBuilder;

public class AssignTaskCommandTest {
    private final Model model = createModelWithProjectTasks();

    private Model createModelWithProjectTasks() {
        Model seededModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        seededModel.setProject(new Project("alpha"), new Project("alpha", Arrays.asList(
                new Task(VALID_TASK_REFACTOR),
                new Task(VALID_TASK_FIX_ERROR),
                new Task(VALID_TASK_IMPLEMENT_X),
                new Task(VALID_TASK_IMPLEMENT_Y),
                new Task(VALID_TASK_IMPLEMENT_Z)
        )));
        seededModel.setProject(new Project("beta"), new Project("beta", Arrays.asList(
                new Task(VALID_TASK_REFACTOR),
                new Task(VALID_TASK_FIX_ERROR)
        )));
        return seededModel;
    }

    @Test
    public void execute_addOneTaskUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR).build();

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_FIX_ERROR).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addOneTaskFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR).build();

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_FIX_ERROR).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addOneTaskDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_REFACTOR).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addOneTaskDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_REFACTOR).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y,
                VALID_TASK_IMPLEMENT_Z, VALID_TASK_REFACTOR).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y,
                VALID_TASK_IMPLEMENT_Z, VALID_TASK_REFACTOR).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultipleTasksFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noTaskSpecifiedUnfilteredList_errorThrown() {
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, new AssignTaskDescriptor());
        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noTaskSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, new AssignTaskDescriptor());
        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_IMPLEMENT_X).build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(assignTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(outOfBoundIndex,
                new AssignTaskDescriptorBuilder().withTasks(VALID_TASK_IMPLEMENT_X).build());

        assertCommandFailure(assignTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskNotInAssignedProjects_failure() {
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks("non existent task").build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignTaskCommand, model, TaskCommand.MESSAGE_TASK_NOT_IN_ASSIGNED_PROJECTS);
    }

    @Test
    public void equals() {
        AssignTaskDescriptor firstDescriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_IMPLEMENT_X).build();
        AssignTaskDescriptor secondDescriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_IMPLEMENT_Y).build();

        AssignTaskCommand firstCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, firstDescriptor);
        AssignTaskCommand sameValuesCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, firstDescriptor);
        AssignTaskCommand differentCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, secondDescriptor);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(sameValuesCommand));
        assertFalse(firstCommand.equals(differentCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals((Object) null));
    }

    @Test
    public void assignTaskDescriptor_behaviour() {
        AssignTaskDescriptor descriptor = new AssignTaskDescriptor();
        assertFalse(descriptor.isTaskFieldEdited());

        descriptor.setTasks(Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X)));
        assertTrue(descriptor.isTaskFieldEdited());
        assertThrows(UnsupportedOperationException.class, () -> descriptor.getTasks().get().clear());
    }

}
