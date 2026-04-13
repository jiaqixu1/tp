package seedu.taskforge.logic.commands.person;

import static java.util.Objects.requireNonNull;

import seedu.taskforge.logic.commands.Command;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.TaskForge;

/**
 * Clears the TaskForge.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All data in TaskForge has been cleared. Type 'undo' to restore.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all entries.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTaskForge(new TaskForge());
        model.commitTaskForge(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
