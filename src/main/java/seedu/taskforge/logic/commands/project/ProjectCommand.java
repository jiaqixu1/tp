package seedu.taskforge.logic.commands.project;

import seedu.taskforge.logic.commands.Command;

/**
 * Represents a command that handles project-related operations.
 * This is an abstract base class for all specific project commands.
 */
public abstract class ProjectCommand extends Command {

    public static final String COMMAND_WORD = "project";

    public static final String MESSAGE_USAGE = "project should be followed by add/delete/list/assign/unassign/find!";

}
