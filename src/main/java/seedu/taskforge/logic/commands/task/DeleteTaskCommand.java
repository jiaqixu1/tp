package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.commons.util.CollectionUtil;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task from the task list of an existing project in the TaskForge
 */
public class DeleteTaskCommand extends TaskCommand {
    public static final String SUBCOMMAND_WORD = "delete";

    public static final String MESSAGE_SUCCESS = "Task deleted from project: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + SUBCOMMAND_WORD + " PROJECT_INDEX -i TASK_INDEX";
    public static final String MESSAGE_NOT_EDITED = "At least one task to delete must be provided";
    public static final String MESSAGE_PROJECT_INDEX_OUT_OF_BOUNDS = "Project index is out of bounds.";
    public static final String MESSAGE_TASK_INDEX_OUT_OF_BOUNDS = "Task index is out of bounds.";

    private final Index projectIndex;
    private final DeleteTaskDescriptor deleteTaskDescriptor;

    /**
     * Creates a DeleteTaskCommand to delete a task from a project.
     */
    public DeleteTaskCommand(Index projectIndex, DeleteTaskDescriptor deleteTaskDescriptor) {
        requireNonNull(projectIndex);
        requireNonNull(deleteTaskDescriptor);

        this.projectIndex = projectIndex;
        this.deleteTaskDescriptor = new DeleteTaskDescriptor(deleteTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> projectList = model.getProjectList();

        if (projectIndex.getZeroBased() >= projectList.size()) {
            throw new CommandException(MESSAGE_PROJECT_INDEX_OUT_OF_BOUNDS);
        }

        Project projectToEdit = projectList.get(projectIndex.getZeroBased());
        Project editedProject = createEditedProject(projectToEdit, deleteTaskDescriptor);

        model.setProject(projectToEdit, editedProject);
        model.commitTaskForge(String.format("%s %s", COMMAND_WORD, SUBCOMMAND_WORD));
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedProject));
    }

    private static Project createEditedProject(Project projectToEdit,
                                               DeleteTaskDescriptor deleteTaskDescriptor) throws CommandException {
        assert projectToEdit != null;

        Project editedProject = new Project(projectToEdit.title, projectToEdit.getTasks());
        List<Index> indexesToDelete = deleteTaskDescriptor.getTaskIndexes()
                .orElseThrow(() -> new CommandException(MESSAGE_NOT_EDITED));

        List<Task> tasksToDelete = new ArrayList<>();
        for (Index index : indexesToDelete) {
            int taskIndex = index.getZeroBased();
            try {
                tasksToDelete.add(editedProject.getTasks().get(taskIndex));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_TASK_INDEX_OUT_OF_BOUNDS);
            }
        }

        try {
            for (Task task : tasksToDelete) {
                editedProject.getUniqueTaskList().remove(task);
            }
        } catch (TaskNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TASK_INDEX_OUT_OF_BOUNDS);
        }

        return editedProject;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }

        DeleteTaskCommand otherDeleteTaskCommand = (DeleteTaskCommand) other;
        return projectIndex.equals(otherDeleteTaskCommand.projectIndex)
                && deleteTaskDescriptor.equals(otherDeleteTaskCommand.deleteTaskDescriptor);
    }

    /**
     * Stores the task indexes to delete from the project.
     */
    public static class DeleteTaskDescriptor {
        private List<Index> taskIndexes;

        public DeleteTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code taskIndexes} is used internally.
         */
        public DeleteTaskDescriptor(DeleteTaskDescriptor toCopy) {
            setTaskIndexes(toCopy.taskIndexes);
        }

        /**
         * Sets {@code taskIndexes} to this object's {@code taskIndexes}.
         * A defensive copy of {@code taskIndexes} is used internally.
         */
        public void setTaskIndexes(List<Index> taskIndexes) {
            this.taskIndexes = (taskIndexes != null) ? new ArrayList<>(taskIndexes) : null;
        }

        /**
         * Returns an unmodifiable task-index list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code taskIndexes} is null.
         */
        public Optional<List<Index>> getTaskIndexes() {
            return (taskIndexes != null) ? Optional.of(Collections.unmodifiableList(taskIndexes)) : Optional.empty();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isTaskFieldEdited() {
            return CollectionUtil.isAnyNonNull(taskIndexes);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof DeleteTaskDescriptor)) {
                return false;
            }

            DeleteTaskDescriptor deleteTaskDescriptor = (DeleteTaskDescriptor) other;
            return Objects.equals(taskIndexes, deleteTaskDescriptor.taskIndexes);
        }
    }
}
