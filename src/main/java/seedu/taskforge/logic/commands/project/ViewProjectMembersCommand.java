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

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Shows all members assigned to the specified project.\n"
            + "Format: " + COMMAND_WORD + " " + SUBCOMMAND_WORD + " PROJECT_INDEX\n"
            + "Example: " + COMMAND_WORD + " " + SUBCOMMAND_WORD + " 2";

    public static final String MESSAGE_PROJECT_INDEX_OUT_OF_BOUNDS = "Project index is out of bounds.";
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
            throw new CommandException(MESSAGE_PROJECT_INDEX_OUT_OF_BOUNDS);
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
                .mapToObj(i -> formatMember(i, members.get(i)))
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_MEMBERS_HEADER, targetProject.title, memberString));
    }

    /**
     * Formats a project member for display.
     */
    private String formatMember(int displayIndex, Person person) {
        return String.format("%d. %s | Phone: %s | Email: %s",
                displayIndex + 1,
                person.getName().fullName,
                person.getPhone().value,
                person.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewProjectMembersCommand
                && targetIndex.equals(((ViewProjectMembersCommand) other).targetIndex));
    }
}
