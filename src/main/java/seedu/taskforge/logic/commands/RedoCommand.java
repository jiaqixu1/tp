package seedu.taskforge.logic.commands;

import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;

/**
 * Redoes the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo previous command: %s";
    public static final String MESSAGE_FAILED = "Unable to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedoTaskForge()) {
            throw new CommandException(MESSAGE_FAILED);
        }
        String input = model.redoTaskForge();
        return new CommandResult(String.format(MESSAGE_SUCCESS, input));
    }
}
