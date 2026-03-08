package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTaskName(String)}
 */
public class Task {

    public static final String MESSAGE_CONSTRAINTS = "Tasks names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String taskName;
    protected boolean isDone;

    /**
     * Constructs a {@code Task}.
     *
     * @param taskName A valid task name.
     */
    public Task(String taskName) {
        requireNonNull(taskName);
        checkArgument(isValidTaskName(taskName), MESSAGE_CONSTRAINTS);
        this.taskName = taskName;
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
    public static boolean isValidTaskName(String test) {
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
        return taskName.equals(otherTask.taskName);
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
//    public String toString() {
//        return '[' + taskName + ',' + (isDone ? "O" : "X") + ']';
//    }
    public String toString() {
        return '[' + taskName + ']';
    }

}
