package seedu.taskforge.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.taskforge.commons.core.GuiSettings;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Project> PREDICATE_SHOW_ALL_PROJECTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' TaskForge file path.
     */
    Path getTaskForgeFilePath();

    /**
     * Sets the user prefs' TaskForge file path.
     */
    void setTaskForgeFilePath(Path taskForgeFilePath);

    /**
     * Replaces TaskForge data with the data in {@code taskForge}.
     */
    void setTaskForge(ReadOnlyTaskForge taskForge);

    /** Returns the TaskForge */
    ReadOnlyTaskForge getTaskForge();

    /**
     * Returns true if a project with the same identity as {@code project} exists in the TaskForge.
     */
    boolean hasProject(Project project);

    /**
     * Deletes the given project.
     * The project must exist in the TaskForge.
     */
    void deleteProject(Project target);

    /**
     * Adds the given project.
     * {@code project} must not already exist in the TaskForge.
     */
    void addProject(Project project);

    /**
     * Replaces the given project {@code target} with {@code editedProject}.
     * {@code target} must exist in the TaskForge.
     * The project identity of {@code editedProject} must not be the same as
     * another existing project in the TaskForge.
     */
    void setProject(Project target, Project editedProject);

    /**
     * Returns a list of all projects in the TaskForge.
     */
    ObservableList<Project> getProjectList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the TaskForge.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the TaskForge.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the TaskForge.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the TaskForge.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the TaskForge.
     */
    void setPerson(Person target, Person editedPerson);

    ObservableList<Project> getFilteredProjectList();

    void updateFilteredProjectList(Predicate<Project> predicate);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Commits the current taskforge state to the undo/redo history.
     */
    void commitTaskForge(String input);

    /**
     * Restores the previous taskforge state.
     */
    String undoTaskForge();

    /**
     * Restores a previously undone taskforge state.
     */
    String redoTaskForge();

    /**
     * Checks if an undo operation is possible.
     */
    boolean canUndoTaskForge();

    /**
     * Checks if a redo operation is possible.
     */
    boolean canRedoTaskForge();
}
