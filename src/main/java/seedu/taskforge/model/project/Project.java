package seedu.taskforge.model.project;

import static seedu.taskforge.commons.util.AppUtil.checkArgument;
import static seedu.taskforge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.taskforge.model.task.Task;
import seedu.taskforge.model.task.UniqueTaskList;

/**
 * Represents a Project in the address book.
 * Guarantees: immutable; title is valid as declared in {@link #isValidProjectTitle(String)}
 */
public class Project {
    public static final String MESSAGE_CONSTRAINTS = "Project title should be alphanumeric"
            + " between 1 to 64 characters";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9 ]{1,64}$";

    public final String title;
    private final UniqueTaskList tasks;

    /**
     * Constructs a {@code Project}.
     *
     * @param title A valid project title.
     */
    public Project(String title) {
        this(title, new ArrayList<>());
    }

    /**
     * Constructs a {@code Project} with tasks.
     *
     * @param title A valid project title.
     * @param tasks A list of tasks for this project.
     */
    public Project(String title, List<Task> tasks) {
        requireAllNonNull(title, tasks);
        checkArgument(isValidProjectTitle(title), MESSAGE_CONSTRAINTS);
        this.title = capitalize(title);
        this.tasks = new UniqueTaskList();
        this.tasks.setTasks(tasks);
    }

    public static boolean isValidProjectTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the tasks for this project.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.asUnmodifiableObservableList());
    }

    /**
     * Returns the UniqueTaskList for this project.
     */
    public UniqueTaskList getUniqueTaskList() {
        return tasks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Project)) {
            return false;
        }

        Project otherProject = (Project) other;
        return title.equals(otherProject.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    /**
     * Returns formatted state text for viewing.
     */
    @Override
    public String toString() {
        return title;
    }

    private String capitalize(String title) {
        return Arrays.stream(title.toLowerCase().split(" "))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
