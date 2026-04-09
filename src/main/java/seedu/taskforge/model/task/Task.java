package seedu.taskforge.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task in the address book.
 * Guarantees: immutable; description is valid as declared in {@link #isValidTaskDescription(String)}
 */
public class Task {

    public static final String MESSAGE_CONSTRAINTS = "Task name should be alphanumeric"
            + " between 1 to 64 characters";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9 ]{1,64}$";

    public final String description;
    protected boolean isDone;
    private final String projectTitle;

    /**
     * Constructs a {@code Task}.
     *
     * @param description A valid task name.
     */
    public Task(String description) {
        this(description, null);
    }

    /**
     * Constructs a {@code Task} that optionally tracks the owning project.
     */
    public Task(String description, String projectTitle) {
        requireNonNull(description);
        checkArgument(isValidTaskDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
        this.projectTitle = projectTitle;
        isDone = false;
    }

    /**
     * Returns the project title that this task belongs to.
     */
    public String getProjectTitle() {
        return projectTitle;
    }

    /**
     * Returns whether this task belongs to the project with the given title.
     */
    public boolean belongsToProject(String targetProjectTitle) {
        return projectTitle != null && projectTitle.equals(targetProjectTitle);
    }

    /**
     * Sets this task as done.
     */
    public void setDone() {
        isDone = true;
    }

    /**
     * Sets this task as not done.
     */
    public void setNotDone() {
        isDone = false;
    }

    /**
     * Returns the current status of this task.
     *
     * @return boolean status.
     */
    public boolean getStatus() {
        return isDone;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidTaskDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return description.equals(otherTask.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    /**
     * Returns formatted state text for viewing.
     */
    @Override
    public String toString() {
        return '[' + description + ']';
    }

}
