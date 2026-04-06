package seedu.taskforge.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.taskforge.commons.util.ToStringBuilder;

/**
 * Represents a reference to a project assigned to a person.
 * Contains only the index of the project in the global UniqueProjectList.
 * Guarantees: project_index is non-negative and valid.
 */
public class PersonProject {
    private final int projectIndex;

    /**
     * Constructs a {@code PersonProject} with the given project index.
     */
    public PersonProject(int projectIndex) {
        requireNonNull(projectIndex);
        this.projectIndex = projectIndex;
    }

    /**
     * Returns the index of the project in the global UniqueProjectList.
     */
    public int getProjectIndex() {
        return projectIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonProject)) {
            return false;
        }

        PersonProject otherPersonProject = (PersonProject) other;
        return projectIndex == otherPersonProject.projectIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("projectIndex", projectIndex)
                .toString();
    }
}
