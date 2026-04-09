package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_THIRD_PROJECT;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;

public class ListTaskCommandTest {

    private final Model model = new ModelManager(getTypicalTaskForge(), new UserPrefs());

    @Test
    public void execute_projectHasTasks_success() {
        ListTaskCommand command = new ListTaskCommand(INDEX_FIRST_PROJECT);
        String expectedMessage = """
            Listed tasks for project Alpha:
            1. [refactor code]
            2. [fix error in tp project]""";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_projectHasNoTasks_success() {
        ListTaskCommand command = new ListTaskCommand(INDEX_THIRD_PROJECT);
        String expectedMessage = "Listed tasks for project Project X: None";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidProjectIndex_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getProjectList().size() + 1);
        ListTaskCommand command = new ListTaskCommand(outOfBoundsIndex);
        assertCommandFailure(command, model, ListTaskCommand.MESSAGE_INVALID_PROJECT_INDEX);
    }

    @Test
    public void equals() {
        ListTaskCommand firstCommand = new ListTaskCommand(INDEX_FIRST_PROJECT);
        ListTaskCommand secondCommand = new ListTaskCommand(INDEX_THIRD_PROJECT);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(new ListTaskCommand(INDEX_FIRST_PROJECT)));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals((Object) null));
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(INDEX_FIRST_PROJECT.hashCode(),
                new ListTaskCommand(INDEX_FIRST_PROJECT).hashCode());
    }
}
