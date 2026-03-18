package seedu.taskforge.logic.commands.task;

import seedu.taskforge.logic.commands.Command;

/**
 * Represents a command that handles task-related operations.
 * This is an abstract base class for all specific task commands.
 */
public abstract class TaskCommand extends Command {

    public static final String COMMAND_WORD = "task";

    public static final String MESSAGE_USAGE = "task should be followed by add/delete!";

}
