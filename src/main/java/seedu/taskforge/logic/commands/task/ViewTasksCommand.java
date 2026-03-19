package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.task.Task;

/**
 * Views all tasks of a specific person identified by index in the currently displayed person list.
 */
public class ViewTasksCommand extends TaskCommand {

    public static final String SUBCOMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = TaskCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Views all tasks of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + TaskCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " 2";

    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_NO_TASKS = "%s has no tasks.";
    public static final String MESSAGE_TASKS_HEADER = "Tasks for %s: %s";

    private final Index targetIndex;

    public ViewTasksCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        List<Task> tasks = person.getTasks();

        if (tasks.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_TASKS, person.getName().fullName));
        }

        String taskString = tasks.stream()
                .map(Task::toString)
                .collect(Collectors.joining(" "));

        return new CommandResult(String.format(MESSAGE_TASKS_HEADER, person.getName().fullName, taskString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewTasksCommand
                && targetIndex.equals(((ViewTasksCommand) other).targetIndex));
    }
}
