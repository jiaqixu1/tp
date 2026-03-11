package seedu.address.logic.commands.projectcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * Adds a project to an existing person in the address book.
 */
public class ProjectAddCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = ProjectCommand.COMMAND_WORD
            + " " + SUBCOMMAND_WORD + " has something wrong";

    public static final String MESSAGE_ADD_PROJECT_SUCCESS = "Added Project: %1$s";

    private final Index targetIndex;
    private final Project project;

    /**
     * Creates a ProjectAddCommand to add the specified {@code Project} to the person
     * at the specified {@code Index}.
     *
     * @param targetIndex The index of the person in the filtered person list to add the project to.
     * @param project The project to be added to the person.
     */
    public ProjectAddCommand(Index targetIndex, Project project) {
        super();
        this.targetIndex = targetIndex;
        this.project = project;
    }

    /**
     * Executes the command to add a project to the specified person.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} with the success message.
     * @throws CommandException If the specified index is invalid or if any other error occurs during execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAssign = lastShownList.get(targetIndex.getZeroBased());

        List<Project> updatedProjects = new ArrayList<>(personToAssign.getProjects());
        updatedProjects.add(project);

        Person updatedPerson = new Person(
                personToAssign.getName(),
                personToAssign.getPhone(),
                personToAssign.getEmail(),
                personToAssign.getAddress(),
                updatedProjects
        );

        model.setPerson(personToAssign, updatedPerson);

        return new CommandResult(String.format(MESSAGE_ADD_PROJECT_SUCCESS, project.toString()));
    }

}
