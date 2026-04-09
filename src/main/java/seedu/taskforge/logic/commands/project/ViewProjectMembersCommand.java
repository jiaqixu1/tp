package seedu.taskforge.logic.commands.project;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;

/**
 * Views all members of a specific project identified by index in the project list.
 */
public class ViewProjectMembersCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "members";

    public static final String MESSAGE_USAGE = ProjectCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Views all members under the project identified by the index number in the project list.\n"
            + "Parameters: PROJECT_INDEX (must be a positive integer)\n"
            + "Example: " + ProjectCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " 2";

    public static final String MESSAGE_INVALID_PROJECT_INDEX = "The project index provided is invalid.";
    public static final String MESSAGE_NO_MEMBERS = "There are no members in project: %s";
    public static final String MESSAGE_MEMBERS_HEADER = "Members in project %s:\n%s";

    private final Index targetIndex;

    public ViewProjectMembersCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Project> projectList = model.getProjectList();
        if (targetIndex.getZeroBased() >= projectList.size()) {
            throw new CommandException(MESSAGE_INVALID_PROJECT_INDEX);
        }

        Project targetProject = projectList.get(targetIndex.getZeroBased());

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        int projectZeroBasedIndex = targetIndex.getZeroBased();

        List<Person> members = model.getFilteredPersonList().stream()
                .filter(person -> person.getProjects().stream()
                        .anyMatch(personProject -> personProject.getProjectIndex() == projectZeroBasedIndex))
                .collect(Collectors.toList());

        if (members.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_MEMBERS, targetProject.title));
        }

        String memberString = IntStream.range(0, members.size())
                .mapToObj(i -> (i + 1) + ". " + members.get(i).getName().fullName)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_MEMBERS_HEADER, targetProject.title, memberString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewProjectMembersCommand
                && targetIndex.equals(((ViewProjectMembersCommand) other).targetIndex));
    }
}
