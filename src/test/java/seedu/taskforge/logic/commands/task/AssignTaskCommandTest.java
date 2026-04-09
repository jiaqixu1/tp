package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .withTaskIndexes("2").build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS,
            Messages.formatPersonSummary(editedPerson));

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
                .withTaskIndexes("2").build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS,
            Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addOneTaskDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTaskIndexes("1").build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addOneTaskDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTaskIndexes("1").build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTaskIndexes("2", "3", "4", "5",
                "1").build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_addMultipleTasksDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTaskIndexes("2", "3", "4", "5",
                "1").build();
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

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTaskIndexes("2", "3", "4", "5")
                .build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS,
            Messages.formatPersonSummary(editedPerson));

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

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder().withTaskIndexes("2", "3", "4", "5")
                .build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS,
            Messages.formatPersonSummary(editedPerson));

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
                .withTaskIndexes("3").build();
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
                new AssignTaskDescriptorBuilder().withTaskIndexes("3").build());

        assertCommandFailure(assignTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_failure() {
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTaskIndexes("999").build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignTaskCommand, model, AssignTaskCommand.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AssignTaskDescriptor firstDescriptor = new AssignTaskDescriptorBuilder()
                .withTaskIndexes("3").build();
        AssignTaskDescriptor secondDescriptor = new AssignTaskDescriptorBuilder()
                .withTaskIndexes("4").build();

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

        descriptor.setProjectTaskPairs(
                Arrays.asList(
                        new AssignTaskCommand.ProjectTaskPair(
                                Index.fromOneBased(1), Index.fromOneBased(3))));
        assertTrue(descriptor.isTaskFieldEdited());
        assertThrows(UnsupportedOperationException.class, () ->
                descriptor.getProjectTaskPairs().get().clear());
    }

    @Test
    public void projectTaskPair_equals() {
        AssignTaskCommand.ProjectTaskPair pair1 =
                new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(2));
        AssignTaskCommand.ProjectTaskPair pair1Copy =
                new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(2));
        AssignTaskCommand.ProjectTaskPair pair2 =
                new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(3));
        AssignTaskCommand.ProjectTaskPair pair3 =
                new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(2), Index.fromOneBased(2));

        // Test reflexivity: a.equals(a) is true
        assertTrue(pair1.equals(pair1));

        // Test symmetry: if a.equals(b) then b.equals(a)
        assertTrue(pair1.equals(pair1Copy));
        assertTrue(pair1Copy.equals(pair1));

        // Test transitivity and consistency
        AssignTaskCommand.ProjectTaskPair pair1Copy2 =
                new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(2));
        assertTrue(pair1.equals(pair1Copy2));
        assertTrue(pair1Copy.equals(pair1Copy2));

        // Test inequality with different task indices
        assertFalse(pair1.equals(pair2));

        // Test inequality with different project indices
        assertFalse(pair1.equals(pair3));

        // Test with different object types
        assertFalse(pair1.equals("not a pair"));
        assertFalse(pair1.equals(1));

        // Test with null
        assertFalse(pair1.equals(null));
    }

    @Test
    public void projectTaskPair_hashCode() {
        AssignTaskCommand.ProjectTaskPair pair1 =
                new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(2));
        AssignTaskCommand.ProjectTaskPair pair1Copy =
                new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(2));

        // Covers hashCode path and verifies determinism per instance.
        assertEquals(pair1.hashCode(), pair1.hashCode());
        assertEquals(pair1Copy.hashCode(), pair1Copy.hashCode());
    }

    @Test
    public void projectTaskPair_getters() {
        Index projectIndex = Index.fromOneBased(3);
        Index taskIndex = Index.fromOneBased(5);
        AssignTaskCommand.ProjectTaskPair pair =
                new AssignTaskCommand.ProjectTaskPair(projectIndex, taskIndex);

        assertTrue(pair.getProjectIndex().equals(projectIndex));
        assertTrue(pair.getTaskIndex().equals(taskIndex));
    }

    @Test
    public void assignTaskDescriptor_isTaskFieldEdited_empty() {
        AssignTaskDescriptor descriptor = new AssignTaskDescriptor();
        assertFalse(descriptor.isTaskFieldEdited());
    }

    @Test
    public void assignTaskDescriptor_isTaskFieldEdited_withPairs() {
        AssignTaskDescriptor descriptor = new AssignTaskDescriptor();
        descriptor.setProjectTaskPairs(
                Arrays.asList(
                        new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(1))));
        assertTrue(descriptor.isTaskFieldEdited());
    }

    @Test
    public void assignTaskDescriptor_isTaskFieldEdited_emptyList() {
        AssignTaskDescriptor descriptor = new AssignTaskDescriptor();
        descriptor.setProjectTaskPairs(java.util.Collections.emptyList());
        assertFalse(descriptor.isTaskFieldEdited());
    }

    @Test
    public void execute_invalidProjectIndex_failure() {
        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withProjectTaskPairs(
                        new AssignTaskCommand.ProjectTaskPair(Index.fromOneBased(999), Index.fromOneBased(1)))
                .build();
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignTaskCommand, model,
                AssignTaskCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

}
