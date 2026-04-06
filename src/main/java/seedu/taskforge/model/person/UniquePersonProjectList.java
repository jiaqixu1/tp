package seedu.taskforge.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskforge.model.person.exceptions.InvalidPersonProjectTypeException;
import seedu.taskforge.model.project.exceptions.DuplicateProjectException;
import seedu.taskforge.model.project.exceptions.ProjectNotFoundException;

/**
 * A list of person projects that enforces uniqueness between its elements. A person
 * project is considered unique by comparing using {@code PersonProject#equals(Object)}.
 */
public class UniquePersonProjectList implements Iterable<PersonProject> {
    private final ObservableList<PersonProject> internalList = FXCollections.observableArrayList();
    private final ObservableList<PersonProject> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns whether the list contains an equivalent person project
     */
    public boolean contains(PersonProject toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person project to the list.
     * Person project need to be checked for type validity and uniqueness.
     */
    public void add(PersonProject toAdd) {
        requireNonNull(toAdd);

        if (!(toAdd instanceof PersonProject)) {
            throw new InvalidPersonProjectTypeException();
        }

        if (contains(toAdd)) {
            throw new DuplicateProjectException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person project {@code target} in the list with {@code editedPersonProject}.
     */
    public void setPersonProject(PersonProject target, PersonProject editedPersonProject) {
        requireAllNonNull(target, editedPersonProject);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProjectNotFoundException();
        }

        if (!target.equals(editedPersonProject) && contains(editedPersonProject)) {
            throw new DuplicateProjectException();
        }

        internalList.set(index, editedPersonProject);
    }

    /**
     * Removes the equivalent person project from the list.
     * The project must exist in the list.
     */
    public void remove(PersonProject toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProjectNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    public void setPersonProjects(UniquePersonProjectList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code personProjects}.
     * {@code personProjects} must not contain duplicate person projects.
     */
    public void setPersonProjects(List<PersonProject> personProjects) {
        requireAllNonNull(personProjects);
        if (!personProjectsAreUnique(personProjects)) {
            throw new DuplicateProjectException();
        }
        internalList.setAll(personProjects);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PersonProject> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<PersonProject> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonProjectList)) {
            return false;
        }

        UniquePersonProjectList otherUniquePersonProjectList = (UniquePersonProjectList) other;
        return new HashSet<>(internalList).equals(new HashSet<>(otherUniquePersonProjectList.internalList));
    }

    @Override
    public int hashCode() {
        return new HashSet<>(internalList).hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code personProjects} contains only unique person projects.
     */
    private boolean personProjectsAreUnique(List<PersonProject> personProjects) {
        for (int i = 0; i < personProjects.size() - 1; i++) {
            for (int j = i + 1; j < personProjects.size(); j++) {
                if (personProjects.get(i).equals(personProjects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
