package seedu.taskforge.logic.commands;

import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.person.ClearCommand;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.TaskForge;
import seedu.taskforge.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTaskForge_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTaskForge_success() {
        Model model = new ModelManager(getTypicalTaskForge(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTaskForge(), new UserPrefs());
        expectedModel.setTaskForge(new TaskForge());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
