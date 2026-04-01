package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class UnmarkTaskCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unmarkTask_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonTask firstPersonTask = firstPerson.getTasks().get(0);
        Project taskProject = model.getProjectList().get(firstPersonTask.getProjectIndex());
        List<Task> updatedTasks = new ArrayList<>(taskProject.getTasks());
        updatedTasks.get(firstPersonTask.getTaskIndex()).setDone();
        model.setProject(taskProject, new Project(taskProject.title, updatedTasks));

        UnmarkTaskCommand command = new UnmarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        CommandResult commandResult = command.execute(model);

        Task unmarkedTask = model.getProjectList().get(firstPersonTask.getProjectIndex())
            .getTasks().get(firstPersonTask.getTaskIndex());
        assertFalse(unmarkedTask.getStatus());
        assertEquals(String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTask.description),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_taskAlreadyNotDone_failure() {
        UnmarkTaskCommand command = new UnmarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        assertCommandFailure(command, model, UnmarkTaskCommand.MESSAGE_TASK_ALREADY_NOT_DONE);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkTaskCommand command = new UnmarkTaskCommand(outOfBoundPersonIndex, Index.fromOneBased(1));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        int outOfBounds = firstPerson.getTasks().size() + 1;

        UnmarkTaskCommand command = new UnmarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(outOfBounds));
        assertCommandFailure(command, model, DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void equals() {
        UnmarkTaskCommand firstCommand = new UnmarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        UnmarkTaskCommand sameValuesCommand = new UnmarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        UnmarkTaskCommand differentCommand = new UnmarkTaskCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(sameValuesCommand));
        assertFalse(firstCommand.equals(differentCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
    }
}

