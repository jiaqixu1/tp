package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.project.ProjectContainsKeywordsPredicate;
import seedu.taskforge.model.task.Task;

/**
 * Finds and lists all tasks whose names contain any of the given keywords across all projects.
 */
public class FindTaskCommand extends TaskCommand {

    public static final String SUBCOMMAND_WORD = "find";

    public static final String MESSAGE_USAGE_FIND = TaskCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Finds all tasks whose names contain any of the specified keywords "
            + "(case-insensitive) across all projects and displays them as text.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + TaskCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " report";

    public static final String MESSAGE_NO_TASK_FOUND = "No matching tasks found.";
    public static final String MESSAGE_TASKS_FOUND = "Found tasks:";

    private final List<String> keywords;

    /**
     * Creates a FindTaskCommand with task-name search keywords.
     */
    public FindTaskCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<String> matchedTaskLines = new ArrayList<>();
        for (Project project : model.getProjectList()) {
            for (Task task : project.getTasks()) {
                if (matchesAnyKeyword(task)) {
                    matchedTaskLines.add(task.description + " - " + project.title);
                }
            }
        }

        if (matchedTaskLines.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TASK_FOUND);
        }

        String taskList = IntStream.range(0, matchedTaskLines.size())
                .mapToObj(i -> (i + 1) + ". " + matchedTaskLines.get(i))
                .collect(Collectors.joining("\n"));

        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate();
        predicate.setTaskKeywords(keywords);

        model.updateFilteredProjectList(predicate);

        return new CommandResult(MESSAGE_TASKS_FOUND + "\n" + taskList);
    }

    /**
     * Returns whether the given task matches any of the keywords.
     */
    private boolean matchesAnyKeyword(Task task) {
        String lowerCaseTaskName = task.description.toLowerCase();
        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(lowerCaseTaskName::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindTaskCommand
                && keywords.equals(((FindTaskCommand) other).keywords));
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }
}
