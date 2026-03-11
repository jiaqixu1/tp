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
 * Deletes a project from an existing person in the address book.
 */
public class ProjectDeleteCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = ProjectCommand.COMMAND_WORD
            + " " + SUBCOMMAND_WORD + " has something wrong";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";


    private final Index targetPersonIndex;
    private final Index targetProjectIndex;

    /**
     * Creates a ProjectDeleteCommand to delete the specified {@code Project} from the person
     * at the specified {@code Index}.
     *
     * @param targetPersonIndex The index of the person in the filtered person list whose project is to be deleted.
     * @param targetProjectIndex The index of the project in the person's project list to be deleted.
     */
    public ProjectDeleteCommand(Index targetPersonIndex, Index targetProjectIndex) {
        super();
        this.targetPersonIndex = targetPersonIndex;
        this.targetProjectIndex = targetProjectIndex;
    }

    /**
     * Executes the command to delete a project from the specified person.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} with the success message containing the deleted project.
     * @throws CommandException If either the person index or project index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetPersonIndex.getZeroBased() >= lastShownList.size()
                || targetProjectIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAssign = lastShownList.get(targetPersonIndex.getZeroBased());

        List<Project> updatedProjects = new ArrayList<>(personToAssign.getProjects());
        Project deletedProject = updatedProjects.remove(targetProjectIndex.getZeroBased());

        Person updatedPerson = new Person(
                personToAssign.getName(),
                personToAssign.getPhone(),
                personToAssign.getEmail(),
                personToAssign.getAddress(),
                updatedProjects
        );

        model.setPerson(personToAssign, updatedPerson);

        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, deletedProject.toString()));

    }
}
