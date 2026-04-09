package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
 * Assigns task(s) to an existing person in the address book.
 */
public class AssignTaskCommand extends TaskCommand {
    public static final String SUBCOMMAND_WORD = "assign";

    public static final String MESSAGE_SUCCESS = "Task assigned to %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + SUBCOMMAND_WORD + " PERSON_INDEX "
            + "[-pi PROJECT_INDEX -i TASK_INDEX]...";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists for this person!";
    public static final String MESSAGE_NOT_EDITED = "At least one task to assign must be provided";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "Task index is out of bound";
    public static final String MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX = "Project index is out of bound";

    private final Index index;
    private final AssignTaskDescriptor assignTaskDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param assignTaskDescriptor details to edit the person with
     */
    public AssignTaskCommand(Index index, AssignTaskDescriptor assignTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(assignTaskDescriptor);

        this.index = index;
        this.assignTaskDescriptor = new AssignTaskDescriptor(assignTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        List<ProjectTaskPair> projectTaskPairs = assignTaskDescriptor.getProjectTaskPairs()
            .orElseThrow(() -> new CommandException(MESSAGE_NOT_EDITED));
        List<PersonTask> resolvedTasksToAssign = resolveTasksFromProjectTaskPairs(projectTaskPairs, model);
        Person editedPerson = createEditedPerson(personToEdit, resolvedTasksToAssign);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook(String.format("%s %s", COMMAND_WORD, SUBCOMMAND_WORD));
        return new CommandResult(String.format(MESSAGE_SUCCESS,
            Messages.formatPersonSummary(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(
            Person personToEdit,
            List<PersonTask> tasksToAssign) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<PersonProject> personProjectList = personToEdit.getProjects();

        List<PersonTask> newTasks = new ArrayList<>(personToEdit.getTasks());
        newTasks.addAll(tasksToAssign);
        checkUniqueTasks(newTasks);

        return new Person(name, phone, email, personProjectList, newTasks);
    }

    private static List<PersonTask> resolveTasksFromProjectTaskPairs(List<ProjectTaskPair> projectTaskPairs,
            Model model) throws CommandException {
        List<Project> allProjects = new ArrayList<>(model.getProjectList());
        List<PersonTask> resolvedTasks = new ArrayList<>();

        for (ProjectTaskPair pair : projectTaskPairs) {
            int projectIndex = pair.getProjectIndex().getZeroBased();
            int taskIndex = pair.getTaskIndex().getZeroBased();

            // Validate project index
            if (projectIndex < 0 || projectIndex >= allProjects.size()) {
                throw new CommandException(MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }

            // Validate task index within the project
            List<seedu.taskforge.model.task.Task> tasksInProject = allProjects.get(projectIndex).getTasks();
            if (taskIndex < 0 || taskIndex >= tasksInProject.size()) {
                throw new CommandException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            resolvedTasks.add(new PersonTask(projectIndex, taskIndex));
        }

        return resolvedTasks;
    }

    /**
     * Checks and returns a {@code List<Task>} if there is no duplicates.
     * A {@code CommandException} is thrown if there are duplicates.
     *
     */
    public static void checkUniqueTasks(List<PersonTask> tasks) throws CommandException {
        Set<String> uniqueTaskRefs = new HashSet<>();
        boolean hasDuplicates = tasks.stream()
                .map(task -> task.getProjectIndex() + ":" + task.getTaskIndex())
                .anyMatch(key -> !uniqueTaskRefs.add(key));
        if (hasDuplicates) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignTaskCommand)) {
            return false;
        }

        AssignTaskCommand otherAssignTaskCommand = (AssignTaskCommand) other;
        return index.equals(otherAssignTaskCommand.index)
                && assignTaskDescriptor.equals(otherAssignTaskCommand.assignTaskDescriptor);
    }

    /**
     * Stores the tasks to add to the person.
     */
    public static class AssignTaskDescriptor {
        private List<ProjectTaskPair> projectTaskPairs;

        public AssignTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code projectTaskPairs} is used internally.
         */
        public AssignTaskDescriptor(AssignTaskDescriptor toCopy) {
            setProjectTaskPairs(toCopy.projectTaskPairs);
        }

        /**
         * Sets {@code projectTaskPairs} to this object's {@code projectTaskPairs}.
         * A defensive copy of {@code projectTaskPairs} is used internally.
         */
        public void setProjectTaskPairs(List<ProjectTaskPair> projectTaskPairs) {
            this.projectTaskPairs = (projectTaskPairs != null) ? new ArrayList<>(projectTaskPairs) : null;
        }

        /**
         * Returns an unmodifiable list of project-task pairs, which throws
         * {@code UnsupportedOperationException} if modification is attempted.
         * Returns {@code Optional#empty()} if {@code projectTaskPairs} is null.
         */
        public Optional<List<ProjectTaskPair>> getProjectTaskPairs() {
            return (projectTaskPairs != null)
                    ? Optional.of(Collections.unmodifiableList(projectTaskPairs))
                    : Optional.empty();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isTaskFieldEdited() {
            return CollectionUtil.isAnyNonNull(projectTaskPairs) && !projectTaskPairs.isEmpty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AssignTaskDescriptor)) {
                return false;
            }

            AssignTaskDescriptor assignTaskDescriptor = (AssignTaskDescriptor) other;
            return Objects.equals(projectTaskPairs, assignTaskDescriptor.projectTaskPairs);
        }
    }

    /**
     * Represents a pair of project index and task index.
     */
    public static class ProjectTaskPair {
        private final Index projectIndex;
        private final Index taskIndex;

        /**
         * Creates a new ProjectTaskPair with the specified project and task indices.
         *
         * @param projectIndex the index of the project (must not be null)
         * @param taskIndex the index of the task (must not be null)
         */
        public ProjectTaskPair(Index projectIndex, Index taskIndex) {
            this.projectIndex = requireNonNull(projectIndex);
            this.taskIndex = requireNonNull(taskIndex);
        }

        public Index getProjectIndex() {
            return projectIndex;
        }

        public Index getTaskIndex() {
            return taskIndex;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof ProjectTaskPair)) {
                return false;
            }

            ProjectTaskPair otherPair = (ProjectTaskPair) other;
            return projectIndex.equals(otherPair.projectIndex) && taskIndex.equals(otherPair.taskIndex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(projectIndex, taskIndex);
        }
    }
}
