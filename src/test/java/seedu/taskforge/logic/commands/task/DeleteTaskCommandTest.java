package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class DeleteTaskCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteOneTask_success() {
        Project firstProject = model.getProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project projectWithTasks = new Project(firstProject.title,
                Arrays.asList(new Task(VALID_TASK_FIX_ERROR), new Task(VALID_TASK_IMPLEMENT_X)));
        model.setProject(firstProject, projectWithTasks);

        Project editedProject = new Project(firstProject.title, Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X)));

        DeleteTaskCommand.DeleteTaskDescriptor descriptor = new DeleteTaskCommand.DeleteTaskDescriptor();
        descriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(1)));
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PROJECT, descriptor);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setProject(projectWithTasks, editedProject);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteOutOfBoundTask_failure() {
        Project firstProject = model.getProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project projectWithTask = new Project(firstProject.title, Arrays.asList(new Task(VALID_TASK_FIX_ERROR)));
        model.setProject(firstProject, projectWithTask);

        DeleteTaskCommand.DeleteTaskDescriptor descriptor = new DeleteTaskCommand.DeleteTaskDescriptor();
        descriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(2)));
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PROJECT, descriptor);

        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_noTaskSpecified_failure() {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PROJECT,
                new DeleteTaskCommand.DeleteTaskDescriptor());
        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidProjectIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getProjectList().size() + 1);

        DeleteTaskCommand.DeleteTaskDescriptor descriptor = new DeleteTaskCommand.DeleteTaskDescriptor();
        descriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(1)));
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_INVALID_PROJECT_INDEX);
    }

    @Test
    public void equals() {
        DeleteTaskCommand.DeleteTaskDescriptor firstDescriptor = new DeleteTaskCommand.DeleteTaskDescriptor();
        firstDescriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(1)));

        DeleteTaskCommand.DeleteTaskDescriptor secondDescriptor = new DeleteTaskCommand.DeleteTaskDescriptor();
        secondDescriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(2)));

        DeleteTaskCommand firstCommand = new DeleteTaskCommand(INDEX_FIRST_PROJECT, firstDescriptor);
        DeleteTaskCommand sameValuesCommand = new DeleteTaskCommand(INDEX_FIRST_PROJECT, firstDescriptor);
        DeleteTaskCommand differentCommand = new DeleteTaskCommand(INDEX_FIRST_PROJECT, secondDescriptor);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(sameValuesCommand));
        assertFalse(firstCommand.equals(differentCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals((Object) null));
    }

    @Test
    public void deleteTaskDescriptor_behaviour() {
        DeleteTaskCommand.DeleteTaskDescriptor descriptor = new DeleteTaskCommand.DeleteTaskDescriptor();
        assertFalse(descriptor.isTaskFieldEdited());

        descriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(1)));
        assertTrue(descriptor.isTaskFieldEdited());
        assertThrows(UnsupportedOperationException.class, () -> descriptor.getTaskIndexes().get().clear());
    }
}
