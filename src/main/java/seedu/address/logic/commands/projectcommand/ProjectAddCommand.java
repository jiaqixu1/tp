package seedu.address.logic.commands.projectcommand;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ProjectAddCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = ProjectCommand.COMMAND_WORD
            + " " + SUBCOMMAND_WORD + " has something wrong";

    public static final String MESSAGE_ADD_PROJECT_SUCCESS = "Added Project: %1$s";

    private final Index targetIndex;
    private final Project project;

    public ProjectAddCommand(Index targetIndex, Project project) {
        super();
        this.targetIndex = targetIndex;
        this.project = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAssign = lastShownList.get(targetIndex.getZeroBased());

        model.assignProject(personToAssign, project);

        return new CommandResult(String.format(MESSAGE_ADD_PROJECT_SUCCESS, project.toString()));
    }

}
