package seedu.taskforge.logic.commands.task;

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
import seedu.taskforge.logic.commands.Command;
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
 * Adds task(s) to an existing person in the address book.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "task-add";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_USAGE = "task-add INDEX -d TASK_DESCRIPTION";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists for this person!";
    public static final String MESSAGE_NOT_EDITED = "At least one task to add must be provided";

    private final Index index;
    private final AddTaskDescriptor addTaskDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param addTaskDescriptor details to edit the person with
     */
    public AddTaskCommand(Index index, AddTaskDescriptor addTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(addTaskDescriptor);

        this.index = index;
        this.addTaskDescriptor = new AddTaskDescriptor(addTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, addTaskDescriptor);

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
            AddTaskDescriptor addTaskDescriptor) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<Project> projectList = personToEdit.getProjects();

        List<Task> newTasks = new ArrayList<>(personToEdit.getTasks());
        newTasks.addAll(addTaskDescriptor.getTasks().orElseThrow(() -> new CommandException(MESSAGE_NOT_EDITED)));
        checkUniqueTasks(newTasks);

        return new Person(name, phone, email, projectList, newTasks);
    }

    /**
     * Checks and returns a {@code List<Task>} if there is no duplicates.
     * A {@code CommandException} is thrown if there are duplicates.
     *
     */
    public static List<Task> checkUniqueTasks(List<Task> tasks) throws CommandException {
        long distinctCount = tasks.stream().distinct().count();
        if (distinctCount != tasks.size()) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return tasks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        AddTaskCommand otherAddTaskCommand = (AddTaskCommand) other;
        return index.equals(otherAddTaskCommand.index)
                && addTaskDescriptor.equals(otherAddTaskCommand.addTaskDescriptor);
    }

    /**
     * Stores the tasks to add to the person.
     */
    public static class AddTaskDescriptor {
        private List<Task> tasks;

        public AddTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tasks} is used internally.
         */
        public AddTaskDescriptor(AddTaskDescriptor toCopy) {
            setTasks(toCopy.tasks);
        }

        /**
         * Sets {@code tasks} to this object's {@code tasks}.
         * A defensive copy of {@code tasks} is used internally.
         */
        public void setTasks(List<Task> tasks) {
            this.tasks = (tasks != null) ? new ArrayList<>(tasks) : null;
        }

        /**
         * Returns an unmodifiable task set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tasks} is null.
         */
        public Optional<List<Task>> getTasks() {
            return (tasks != null) ? Optional.of(Collections.unmodifiableList(tasks)) : Optional.empty();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isTaskFieldEdited() {
            return CollectionUtil.isAnyNonNull(tasks);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddTaskDescriptor)) {
                return false;
            }

            AddTaskDescriptor addTaskDescriptor = (AddTaskDescriptor) other;
            return Objects.equals(tasks, addTaskDescriptor.tasks);
        }
    }
}
