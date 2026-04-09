package seedu.taskforge.logic.commands;

import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;

/**
 * Undoes the most recent command that modified the taskforge.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo previous command: %s";
    public static final String MESSAGE_FAILED = "Unable to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndoTaskForge()) {
            throw new CommandException(MESSAGE_FAILED);
        }
        String input = model.undoTaskForge();
        return new CommandResult(String.format(MESSAGE_SUCCESS, input));
    }
}
