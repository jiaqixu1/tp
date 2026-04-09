package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Lists all tasks in a project.
 */
public class ListTaskCommand extends TaskCommand {

    public static final String SUBCOMMAND_WORD = "list";

    public static final String MESSAGE_USAGE_LIST = COMMAND_WORD + " " + SUBCOMMAND_WORD
            + " PROJECT_INDEX";
    public static final String MESSAGE_INVALID_PROJECT_INDEX = "The project index provided is invalid.";
    public static final String MESSAGE_SUCCESS = "Listed tasks for project %1$s:";

    private final Index projectIndex;

    /**
     * Creates a ListTaskCommand to list all tasks in a specified project.
     */
    public ListTaskCommand(Index projectIndex) {
        requireNonNull(projectIndex);
        this.projectIndex = projectIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (projectIndex.getZeroBased() >= model.getProjectList().size()) {
            throw new CommandException(MESSAGE_INVALID_PROJECT_INDEX);
        }

        Project project = model.getProjectList().get(projectIndex.getZeroBased());
        List<Task> tasks = project.getTasks();
        String taskList = IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + ". " + tasks.get(i).toString())
                .collect(Collectors.joining("\n"));

        String feedback = String.format(MESSAGE_SUCCESS, project.title)
                + (taskList.isEmpty() ? " None" : "\n" + taskList);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListTaskCommand
                && projectIndex.equals(((ListTaskCommand) other).projectIndex));
    }

    @Override
    public int hashCode() {
        return projectIndex.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subcommandWord", SUBCOMMAND_WORD)
                .add("projectIndex", projectIndex)
                .toString();
    }
}
