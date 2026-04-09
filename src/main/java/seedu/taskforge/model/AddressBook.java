package seedu.taskforge.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.person.UniquePersonList;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.project.UniqueProjectList;
import seedu.taskforge.model.task.Task;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueProjectList projects;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        projects = new UniqueProjectList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the project list with {@code projects}.
     * {@code projects} must not contain duplicate projects.
     */
    public void setProjects(List<Project> projects) {
        this.projects.setProjects(projects);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setProjects(newData.getProjectList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// project-level operations

    /**
     * Returns true if a project with the same identity as {@code project} exists in the address book.
     */
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    /**
     * Adds a project to the address book.
     * The project must not already exist in the address book.
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
        * Replaces the given project {@code target} in the list with
        * {@code editedProject}.
     * {@code target} must exist in the address book.
     * The project identity of {@code editedProject} must not be the same as
     * another existing project in the address book.
     */
    public void setProject(Project target, Project editedProject) {
        requireNonNull(editedProject);

        int targetProjectIndex = projects.asUnmodifiableObservableList().indexOf(target);
        List<Task> removedTasks = new ArrayList<>();
        if (editedProject.getTasks().size() < target.getTasks().size()) {
            removedTasks = new ArrayList<>(target.getTasks());
            removedTasks.removeAll(editedProject.getTasks());
        }
        projects.setProject(target, editedProject);
        if (!removedTasks.isEmpty()) {
            cascadeRemoveDeletedProjectTasksFromPersons(targetProjectIndex, target, removedTasks);
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProject(Project key) {
        requireNonNull(key);
        int removedProjectIndex = projects.asUnmodifiableObservableList().indexOf(key);
        projects.remove(key);
        cascadeRemoveProjectFromPersons(removedProjectIndex);
    }

    /**
     * Removes the given project from all contacts that have it in their project list.
     */
    private void cascadeRemoveProjectFromPersons(int removedProjectIndex) {
        List<Person> allPersons = new ArrayList<>(persons.asUnmodifiableObservableList());
        for (Person person : allPersons) {
            List<PersonProject> updatedProjects = new ArrayList<>();
            boolean changed = false;

            for (PersonProject personProject : person.getProjects()) {
                int projectIndex = personProject.getProjectIndex();
                if (projectIndex == removedProjectIndex) {
                    changed = true;
                    continue;
                }
                if (projectIndex > removedProjectIndex) {
                    updatedProjects.add(new PersonProject(projectIndex - 1));
                    changed = true;
                } else {
                    updatedProjects.add(personProject);
                }
            }

            if (!changed) {
                continue;
            }

            List<PersonTask> updatedTasks = new ArrayList<>();
            for (PersonTask personTask : person.getTasks()) {
                if (personTask.getProjectIndex() == removedProjectIndex) {
                    continue;
                }
                if (personTask.getProjectIndex() > removedProjectIndex) {
                    updatedTasks.add(new PersonTask(personTask.getProjectIndex() - 1,
                            personTask.getTaskIndex()));
                } else {
                    updatedTasks.add(personTask);
                }
            }

            Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                    updatedProjects, updatedTasks);
            persons.setPerson(person, updatedPerson);
        }
    }

    private void cascadeRemoveDeletedProjectTasksFromPersons(int projectIndex, Project originalProject,
                                                             List<Task> deletedProjectTasks) {
        List<Person> allPersons = new ArrayList<>(persons.asUnmodifiableObservableList());

        List<Integer> deletedTaskIndices = new ArrayList<>();
        for (Task deletedTask : deletedProjectTasks) {
            int idx = originalProject.getTasks().indexOf(deletedTask);
            if (idx >= 0) {
                deletedTaskIndices.add(idx);
            }
        }

        for (Person person : allPersons) {
            if (projectIndex < 0
                    || person.getProjects().stream().noneMatch(pp -> pp.getProjectIndex() == projectIndex)) {
                continue;
            }

            List<PersonTask> updatedTasks = new ArrayList<>();
            boolean changed = false;
            for (PersonTask personTask : person.getTasks()) {
                if (personTask.getProjectIndex() != projectIndex) {
                    updatedTasks.add(personTask);
                    continue;
                }

                if (deletedTaskIndices.contains(personTask.getTaskIndex())) {
                    changed = true;
                    continue;
                }

                int shift = 0;
                for (int deletedIdx : deletedTaskIndices) {
                    if (deletedIdx < personTask.getTaskIndex()) {
                        shift++;
                    }
                }

                if (shift > 0) {
                    changed = true;
                    updatedTasks.add(new PersonTask(projectIndex, personTask.getTaskIndex() - shift));
                } else {
                    updatedTasks.add(personTask);
                }
            }

            if (!changed) {
                continue;
            }

            Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                    person.getProjects(), updatedTasks);
            persons.setPerson(person, updatedPerson);
        }
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("projects", projects)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return projects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons) && projects.equals(otherAddressBook.projects);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(persons, projects);
    }
}
