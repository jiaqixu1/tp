package seedu.taskforge.logic.commands.project;

import static java.util.Objects.requireNonNull;
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
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Adds a project to an existing person in the address book.
 */
public class AddProjectCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "add";

    public static final String MESSAGE_ADD_PROJECT_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SUBCOMMAND_WORD + " INDEX -l PROJECT_TITLE";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists for this person!";
    public static final String MESSAGE_PROJECT_NOT_FOUND = "The project to add does not exist in the address book";
    public static final String MESSAGE_NOT_EDITED = "At least one project to add must be provided";

    private final Index index;
    private final AddProjectDescriptor addProjectDescriptor;

    /**
     * Creates a AddProjectCommand to add the specified {@code Project} to the person
     * at the specified {@code Index}.
     *
     * @param index The index of the person in the filtered person list to add the project to.
     * @param addProjectDescriptor The project to be added to the person.
     */
    public AddProjectCommand(Index index, AddProjectDescriptor addProjectDescriptor) {
        super();
        requireNonNull(index);
        requireNonNull(addProjectDescriptor);
        this.index = index;
        this.addProjectDescriptor = new AddProjectDescriptor(addProjectDescriptor);
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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!addProjectDescriptor.isProjectFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        validateProjectsExist(addProjectDescriptor.getProjects().orElseThrow(() ->
            new CommandException(MESSAGE_PROJECT_NOT_FOUND)), model);

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, addProjectDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_PROJECT_SUCCESS, Messages.format(editedPerson)));
    }

    private static void validateProjectsExist(List<Project> projects, Model model) throws CommandException {
        for (Project project : projects) {
            if (!model.hasProject(project)) {
                throw new CommandException(MESSAGE_PROJECT_NOT_FOUND);
            }
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(
            Person personToEdit,
            AddProjectDescriptor addProjectDescriptor) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<Task> taskList = personToEdit.getTasks();

        List<Project> newProjects = new ArrayList<>(personToEdit.getProjects());
        newProjects.addAll(addProjectDescriptor.getProjects().orElseThrow(() ->
                new CommandException(MESSAGE_PROJECT_NOT_FOUND)));
        checkUniqueProjects(newProjects);

        return new Person(name, phone, email, newProjects, taskList);
    }

    /**
     * Checks and returns a {@code List<Project>} if there is no duplicates.
     * A {@code CommandException} is thrown if there are duplicates.
     *
     */
    public static List<Project> checkUniqueProjects(List<Project> projects) throws CommandException {
        long distinctCount = projects.stream().distinct().count();
        if (distinctCount != projects.size()) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }
        return projects;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddProjectCommand)) {
            return false;
        }

        AddProjectCommand otherAddProjectCommand = (AddProjectCommand) other;
        return index.equals(otherAddProjectCommand.index)
                && addProjectDescriptor.equals(otherAddProjectCommand.addProjectDescriptor);
    }

    /**
     * Stores the projects to add to the person.
     */
    public static class AddProjectDescriptor {
        private List<Project> projects;

        public AddProjectDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code projects} is used internally.
         */
        public AddProjectDescriptor(AddProjectDescriptor toCopy) {
            setProjects(toCopy.projects);
        }

        /**
         * Sets {@code projects} to this object's {@code projects}.
         * A defensive copy of {@code projects} is used internally.
         */
        public void setProjects(List<Project> projects) {
            this.projects = (projects != null) ? new ArrayList<>(projects) : null;
        }

        /**
         * Returns an unmodifiable project set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code projects} is null.
         */
        public Optional<List<Project>> getProjects() {
            return (projects != null) ? Optional.of(Collections.unmodifiableList(projects)) : Optional.empty();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isProjectFieldEdited() {
            return CollectionUtil.isAnyNonNull(projects);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddProjectDescriptor)) {
                return false;
            }

            AddProjectDescriptor addProjectDescriptor = (AddProjectDescriptor) other;
            return Objects.equals(projects, addProjectDescriptor.projects);
        }
    }
}
