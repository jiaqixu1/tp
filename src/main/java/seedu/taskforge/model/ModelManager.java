package seedu.taskforge.model;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.taskforge.commons.core.GuiSettings;
import seedu.taskforge.commons.core.LogsCenter;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTaskForge taskForge;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Project> filteredProjects;

    /**
     * Initializes a ModelManager with the given TaskForge and userPrefs.
     */
    public ModelManager(ReadOnlyTaskForge taskForge, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(taskForge, userPrefs);

        logger.fine("Initializing with address book: " + taskForge + " and user prefs " + userPrefs);

        this.taskForge = new VersionedTaskForge(taskForge);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.taskForge.getPersonList());
        filteredProjects = new FilteredList<>(this.taskForge.getProjectList());
    }

    public ModelManager() {
        this(new TaskForge(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTaskForgeFilePath() {
        return userPrefs.getTaskForgeFilePath();
    }

    @Override
    public void setTaskForgeFilePath(Path taskForgeFilePath) {
        requireNonNull(taskForgeFilePath);
        userPrefs.setTaskForgeFilePath(taskForgeFilePath);
    }

    //=========== TaskForge ================================================================================

    @Override
    public void setTaskForge(ReadOnlyTaskForge taskForge) {
        this.taskForge.resetData(taskForge);
    }

    @Override
    public ReadOnlyTaskForge getTaskForge() {
        return taskForge;
    }

    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return taskForge.hasProject(project);
    }

    @Override
    public void deleteProject(Project target) {
        taskForge.removeProject(target);
    }

    @Override
    public void addProject(Project project) {
        addressBook.addProject(project);
    }

    @Override
    public void setProject(Project target, Project editedProject) {
        taskForge.setProject(target, editedProject);
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return taskForge.getProjectList();
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return taskForge.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        taskForge.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        taskForge.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        taskForge.setPerson(target, editedPerson);
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        requireNonNull(predicate);
        filteredProjects.setPredicate(predicate);
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        requireNonNull(predicate);
        filteredProjects.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTaskForge}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void commitTaskForge(String input) {
        taskForge.commit(input);
    }

    @Override
    public String undoTaskForge() {
        return taskForge.undo();
    }

    @Override
    public String redoTaskForge() {
        return taskForge.redo();
    }

    @Override
    public boolean canUndoTaskForge() {
        return taskForge.canUndo();
    }

    @Override
    public boolean canRedoTaskForge() {
        return taskForge.canRedo();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return taskForge.equals(otherModelManager.taskForge)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredProjects.equals(otherModelManager.filteredProjects);
    }



}
