package seedu.taskforge.logic.commands.project;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.project.ProjectContainsKeywordsPredicate;

/**
 * Finds and lists all projects whose names contain any of the given keywords.
 */
public class FindProjectCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = ProjectCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Finds all projects whose titles contain any of the specified keywords "
            + "(case-insensitive) and displays them as text.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + ProjectCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " alpha";

    public static final String MESSAGE_NO_PROJECT_FOUND = "No matching projects found.";
    public static final String MESSAGE_PROJECTS_FOUND = "Found projects:";

    private final List<String> keywords;

    public FindProjectCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Project> matchedProjects = model.getProjectList().stream()
                .filter(this::matchesAnyKeyword)
                .collect(Collectors.toList());

        if (matchedProjects.isEmpty()) {
            return new CommandResult(MESSAGE_NO_PROJECT_FOUND);
        }

        String projectsList = IntStream.range(0, matchedProjects.size())
                .mapToObj(i -> (i + 1) + ". " + matchedProjects.get(i).title)
                .collect(Collectors.joining("\n"));

        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate();
        predicate.setProjectKeywords(keywords);

        model.updateFilteredProjectList(predicate);

        return new CommandResult(MESSAGE_PROJECTS_FOUND + "\n" + projectsList);
    }

    /**
     * Returns true if the given project title contains any keyword, case-insensitive.
     */
    private boolean matchesAnyKeyword(Project project) {
        String lowerCaseTitle = project.title.toLowerCase();
        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(lowerCaseTitle::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindProjectCommand
                && keywords.equals(((FindProjectCommand) other).keywords));
    }
}
