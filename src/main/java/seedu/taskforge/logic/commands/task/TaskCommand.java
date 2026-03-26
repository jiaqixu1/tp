package seedu.taskforge.logic.commands.task;

import seedu.taskforge.logic.commands.Command;

/**
 * Represents a command that handles task-related operations.
 * This is an abstract base class for all specific task commands.
 */
public abstract class TaskCommand extends Command {

    public static final String COMMAND_WORD = "task";
    public static final String MESSAGE_USAGE = "task should be followed by "
            + "add/"
            + "delete/"
            + "list/"
            + "assign/"
            + "unassign/"
            + "mark/"
            + "unmark/"
            + "view";
    public static final String MESSAGE_TASK_NOT_IN_ASSIGNED_PROJECTS =
            "The task must exist in at least one project assigned to the person.";

}
