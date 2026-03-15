package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_Y;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_Z;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddTaskDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addOneTaskUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR).build();

        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder()
                .withTasks(VALID_TASK_FIX_ERROR).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addOneTaskFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR).build();

        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder()
                .withTasks(VALID_TASK_FIX_ERROR).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addOneTaskDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder()
                .withTasks(VALID_TASK_REFACTOR).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addOneTaskDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder()
                .withTasks(VALID_TASK_REFACTOR).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y,
                VALID_TASK_IMPLEMENT_Z, VALID_TASK_REFACTOR).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y,
                VALID_TASK_IMPLEMENT_Z, VALID_TASK_REFACTOR).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();

        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultipleTasksFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();

        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder().withTasks(VALID_TASK_FIX_ERROR,
                VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noTaskSpecifiedUnfilteredList_errorThrown() {
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PERSON, new AddTaskDescriptor());
        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noTaskSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PERSON, new AddTaskDescriptor());
        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder()
                .withTasks(VALID_TASK_IMPLEMENT_X).build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        AddTaskCommand addTaskCommand = new AddTaskCommand(outOfBoundIndex,
                new AddTaskDescriptorBuilder().withTasks(VALID_TASK_IMPLEMENT_X).build());

        assertCommandFailure(addTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
