package seedu.taskforge.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a Project in the address book.
 * Guarantees: immutable; title is valid as declared in {@link #isValidProjectTitle(String)}
 */
public class Project {
    public static final String MESSAGE_CONSTRAINTS = "Project title should be alphanumeric"
            + " between 1 to 64 characters";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9 ]{1,64}$";

    public final String title;

    /**
     * Constructs a {@code Project}.
     *
     * @param title A valid project title.
     */
    public Project(String title) {
        requireNonNull(title);
        checkArgument(isValidProjectTitle(title), MESSAGE_CONSTRAINTS);
        this.title = capitalize(title);
    }

    public static boolean isValidProjectTitle(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return title.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return title;
    }

    private String capitalize(String title) {
        return Arrays.stream(title.toLowerCase().split(" "))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

}
