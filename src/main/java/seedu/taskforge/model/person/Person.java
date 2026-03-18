package seedu.taskforge.model.person;

import static seedu.taskforge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final List<Project> projects;
    private final List<Task> tasks;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email,
                  List<Project> projects, List<Task> tasks) {
        requireAllNonNull(name, phone, email, projects, tasks);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.projects = Collections.unmodifiableList(new ArrayList<>(projects));
        this.tasks = Collections.unmodifiableList(new ArrayList<>(tasks));
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Returns an immutable task set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Task> getTasks() {
        return tasks;
    }


    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && projects.equals(otherPerson.projects)
                && tasks.equals(otherPerson.tasks);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, projects, tasks);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("projects", projects)
                .add("tasks", tasks)
                .toString();
    }

}
