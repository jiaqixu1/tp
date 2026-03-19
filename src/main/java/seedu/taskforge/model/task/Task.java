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

    /**
     * Constructs a {@code Task}.
     *
     * @param description A valid task name.
     */
    public Task(String description) {
        requireNonNull(description);
        checkArgument(isValidTaskDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
        isDone = false;
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
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + description + ']';
    }

}
