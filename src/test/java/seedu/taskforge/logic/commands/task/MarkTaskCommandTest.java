package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.testutil.PersonBuilder;

public class MarkTaskCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markTask_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithTask = new PersonBuilder(firstPerson).withTasks(VALID_TASK_IMPLEMENT_X).build();
        model.setPerson(firstPerson, personWithTask);

        MarkTaskCommand command = new MarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        CommandResult commandResult = command.execute(model);

        Person updatedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(updatedPerson.getTasks().get(0).getStatus());
        assertEquals(String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS, VALID_TASK_IMPLEMENT_X),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_markTaskAlreadyDone_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Task doneTask = new Task(VALID_TASK_IMPLEMENT_X);
        doneTask.setDone();
        Person personWithDoneTask = new Person(firstPerson.getName(), firstPerson.getPhone(), firstPerson.getEmail(),
                firstPerson.getProjects(), Arrays.asList(doneTask));
        model.setPerson(firstPerson, personWithDoneTask);

        MarkTaskCommand command = new MarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        assertCommandFailure(command, model, MarkTaskCommand.MESSAGE_TASK_ALREADY_DONE);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkTaskCommand command = new MarkTaskCommand(outOfBoundPersonIndex, Index.fromOneBased(1));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithTask = new PersonBuilder(firstPerson).withTasks(VALID_TASK_IMPLEMENT_X).build();
        model.setPerson(firstPerson, personWithTask);

        MarkTaskCommand command = new MarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));
        assertCommandFailure(command, model, DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void equals() {
        MarkTaskCommand firstCommand = new MarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        MarkTaskCommand sameValuesCommand = new MarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        MarkTaskCommand differentCommand = new MarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(sameValuesCommand));
        assertFalse(firstCommand.equals(differentCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
    }
}

