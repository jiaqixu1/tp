package seedu.taskforge.logic.commands.task;

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
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Delete task(s) from an existing person in the address book.
 */
public class DeleteTaskCommand extends TaskCommand {
    public static final String SUBCOMMAND_WORD = "delete";

    public static final String MESSAGE_SUCCESS = "Deleted task: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + SUBCOMMAND_WORD + " INDEX "
            + PREFIX_INDEX + " TASK_INDEX";
    public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Task index is out of bound";
    public static final String MESSAGE_NOT_EDITED = "At least one task to delete must be provided";

    private final Index index;
    private final DeleteTaskDescriptor deleteTaskDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param deleteTaskDescriptor details to edit the person with
     */
    public DeleteTaskCommand(Index index, DeleteTaskDescriptor deleteTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(deleteTaskDescriptor);

        this.index = index;
        this.deleteTaskDescriptor = new DeleteTaskDescriptor(deleteTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, deleteTaskDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(
            Person personToEdit,
            DeleteTaskDescriptor deleteTaskDescriptor) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<Project> projectList = personToEdit.getProjects();

        List<Task> newTasks = new ArrayList<>(personToEdit.getTasks());
        List<Task> tasksToDelete = new ArrayList<>();
        List<Index> indexToDelete = deleteTaskDescriptor.getTasksIndexes()
                .orElseThrow(() -> new CommandException(MESSAGE_NOT_EDITED));
        for (int i = 0; i < indexToDelete.size(); ++i) {
            int taskIndex = indexToDelete.get(i).getZeroBased();
            try {
                tasksToDelete.add(newTasks.get(taskIndex));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUND);
            }
        }
        newTasks.removeAll(tasksToDelete);

        return new Person(name, phone, email, projectList, newTasks);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }

        DeleteTaskCommand otherDeleteTaskCommand = (DeleteTaskCommand) other;
        return index.equals(otherDeleteTaskCommand.index)
                && deleteTaskDescriptor.equals(otherDeleteTaskCommand.deleteTaskDescriptor);
    }

    /**
     * Stores the tasks to delete from the person.
     */
    public static class DeleteTaskDescriptor {
        private List<Index> indexes;

        public DeleteTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tasks} is used internally.
         */
        public DeleteTaskDescriptor(DeleteTaskDescriptor toCopy) {
            setTasksIndexes(toCopy.indexes);
        }

        /**
         * Sets {@code tasks} to this object's {@code tasks}.
         * A defensive copy of {@code tasks} is used internally.
         */
        public void setTasksIndexes(List<Index> indexes) {
            this.indexes = (indexes != null) ? new ArrayList<>(indexes) : null;
        }

        /**
         * Returns an unmodifiable task set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tasks} is null.
         */
        public Optional<List<Index>> getTasksIndexes() {
            return (indexes != null) ? Optional.of(Collections.unmodifiableList(indexes)) : Optional.empty();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isTaskFieldEdited() {
            return CollectionUtil.isAnyNonNull(indexes);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteTaskDescriptor)) {
                return false;
            }

            DeleteTaskDescriptor deleteTaskDescriptor = (DeleteTaskDescriptor) other;
            return Objects.equals(indexes, deleteTaskDescriptor.indexes);
        }
    }
}
