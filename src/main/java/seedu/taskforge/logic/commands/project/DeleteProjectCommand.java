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
 * Deletes a project from an existing person in the address book.
 */
public class DeleteProjectCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SUBCOMMAND_WORD + " INDEX -pi PROJECT_INDEX";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";

    public static final String MESSAGE_NOT_EDITED = "At least one project to delete must be provided";

    public static final String MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX = "The project index provided is invalid";

    private final Index index;
    private final DeleteProjectDescriptor deleteProjectDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param deleteProjectDescriptor details to edit the person with
     */
    public DeleteProjectCommand(Index index, DeleteProjectDescriptor deleteProjectDescriptor) {
        requireNonNull(index);
        requireNonNull(deleteProjectDescriptor);

        this.index = index;
        this.deleteProjectDescriptor = new DeleteProjectDescriptor(deleteProjectDescriptor);
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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, deleteProjectDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(
            Person personToEdit,
            DeleteProjectDescriptor deleteProjectDescriptor) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<Task> taskList = personToEdit.getTasks();

        List<Project> newProjects = new ArrayList<>(personToEdit.getProjects());
        List<Project> projectsToDelete = new ArrayList<>();
        List<Index> indexToDelete = deleteProjectDescriptor.getProjectsIndexes()
                .orElseThrow(() -> new CommandException(MESSAGE_NOT_EDITED));
        for (int i = 0; i < indexToDelete.size(); ++i) {
            int projectIndex = indexToDelete.get(i).getZeroBased();
            try {
                projectsToDelete.add(newProjects.get(projectIndex));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }
        }
        newProjects.removeAll(projectsToDelete);

        return new Person(name, phone, email, newProjects, taskList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteProjectCommand)) {
            return false;
        }

        DeleteProjectCommand otherDeleteProjectCommand = (DeleteProjectCommand) other;
        return index.equals(otherDeleteProjectCommand.index)
                && deleteProjectDescriptor.equals(otherDeleteProjectCommand.deleteProjectDescriptor);
    }

    /**
     * Stores the projects to delete from the person.
     */
    public static class DeleteProjectDescriptor {
        private List<Index> indexes;

        public DeleteProjectDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code projects} is used internally.
         */
        public DeleteProjectDescriptor(DeleteProjectDescriptor toCopy) {
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
        public Optional<List<Index>> getProjectsIndexes() {
            return (indexes != null) ? Optional.of(Collections.unmodifiableList(indexes)) : Optional.empty();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isProjectFieldEdited() {
            return CollectionUtil.isAnyNonNull(indexes);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteProjectDescriptor)) {
                return false;
            }

            DeleteProjectDescriptor deleteProjectDescriptor = (DeleteProjectDescriptor) other;
            return Objects.equals(indexes, deleteProjectDescriptor.indexes);
        }
    }
}
