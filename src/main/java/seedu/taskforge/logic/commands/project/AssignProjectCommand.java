package seedu.taskforge.logic.commands.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.commons.util.CollectionUtil;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;

/**
 * Assigns a project to an existing person in the address book.
 */
public class AssignProjectCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "assign";

    public static final String MESSAGE_ASSIGN_PROJECT_SUCCESS = "Project assigned: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + SUBCOMMAND_WORD + " PERSON_INDEX "
            + PREFIX_INDEX + " PROJECT_INDEX";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists for this person!";
    public static final String MESSAGE_NOT_EDITED = "At least one project to assign must be provided";
    public static final String MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX = "The project index provided is invalid";

    private final Index personIndex;
    private final AssignProjectDescriptor assignProjectDescriptor;

    /**
     * Creates a AssignProjectCommand to assign the specified {@code Project} to the person
     * at the specified {@code Index}.
     *
     * @param personIndex The index of the person in the filtered person list to assign the project to.
     * @param assignProjectDescriptor The project to be assigned to the person.
     */
    public AssignProjectCommand(Index personIndex, AssignProjectDescriptor assignProjectDescriptor) {
        super();
        requireNonNull(personIndex);
        requireNonNull(assignProjectDescriptor);
        this.personIndex = personIndex;
        this.assignProjectDescriptor = new AssignProjectDescriptor(assignProjectDescriptor);
    }

    /**
     * Executes the command to assign a project to the specified person.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} with the success message.
     * @throws CommandException If the specified index is invalid or if any other error occurs during execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!assignProjectDescriptor.isProjectFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, assignProjectDescriptor, model);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook(String.format("%s %s", COMMAND_WORD, SUBCOMMAND_WORD));
        return new CommandResult(String.format(MESSAGE_ASSIGN_PROJECT_SUCCESS,
            Messages.formatPersonSummary(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(
            Person personToEdit,
            AssignProjectDescriptor assignProjectDescriptor,
            Model model) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<PersonTask> taskList = personToEdit.getTasks();

        List<PersonProject> newPersonProjects = new ArrayList<>(personToEdit.getProjects());
        List<Index> projectIndexesToAssign = assignProjectDescriptor.getProjectIndexes().orElseThrow(() ->
                new CommandException(MESSAGE_NOT_EDITED));
        List<Project> globalProjectList = new ArrayList<>(model.getProjectList());

        // Convert selected project indexes to PersonProjects and add them to the person's project list.
        for (Index projectIndexToAssign : projectIndexesToAssign) {
            int projectIndex = projectIndexToAssign.getZeroBased();
            if (projectIndex >= globalProjectList.size()) {
                throw new CommandException(MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }
            PersonProject personProject = new PersonProject(projectIndex);
            newPersonProjects.add(personProject);
        }

        checkUniquePersonProjects(newPersonProjects);

        return new Person(name, phone, email, newPersonProjects, taskList);
    }

    /**
     * Checks and returns a {@code List<PersonProject>} if there are no duplicates.
     * A {@code CommandException} is thrown if there are duplicates.
     */
    public static List<PersonProject> checkUniquePersonProjects(List<PersonProject> personProjects)
            throws CommandException {
        long distinctCount = personProjects.stream().distinct().count();
        if (distinctCount != personProjects.size()) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }
        return personProjects;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignProjectCommand)) {
            return false;
        }

        AssignProjectCommand otherAssignProjectCommand = (AssignProjectCommand) other;
        return personIndex.equals(otherAssignProjectCommand.personIndex)
                && assignProjectDescriptor.equals(otherAssignProjectCommand.assignProjectDescriptor);
    }

    /**
     * Stores the projects to assign to the person.
     */
    public static class AssignProjectDescriptor {
        private List<Index> indexes;

        public AssignProjectDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code projects} is used internally.
         */
        public AssignProjectDescriptor(AssignProjectDescriptor toCopy) {
            setProjectsIndexes(toCopy.indexes);
        }

        /**
         * Sets {@code projects} to this object's {@code projects}.
         * A defensive copy of {@code projects} is used internally.
         */
        public void setProjectsIndexes(List<Index> indexes) {
            this.indexes = (indexes != null) ? new ArrayList<>(indexes) : null;
        }

        /**
         * Returns an unmodifiable project set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code projects} is null.
         */
        public Optional<List<Index>> getProjectIndexes() {
            return (indexes != null) ? Optional.of(Collections.unmodifiableList(indexes)) : Optional.empty();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isProjectFieldEdited() {
            return CollectionUtil.isAnyNonNull(indexes) && !indexes.isEmpty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AssignProjectDescriptor)) {
                return false;
            }

            AssignProjectDescriptor addProjectDescriptor = (AssignProjectDescriptor) other;
            return Objects.equals(indexes, addProjectDescriptor.indexes);
        }
    }
}
