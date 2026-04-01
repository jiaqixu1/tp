package seedu.taskforge.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.person.Phone;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;
    private List<PersonProject> projects;
    private List<PersonTask> tasks;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        projects = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        projects = new ArrayList<>(personToCopy.getProjects());
        tasks = new ArrayList<>(personToCopy.getTasks());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code projects} into a {@code List<Project>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withProjects(String ... projects) {
        this.projects = new ArrayList<>();
        for (int i = 0; i < projects.length; i++) {
            this.projects.add(new PersonProject(i));
        }
        return this;
    }


    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTasks(String ... tasks) {
        this.tasks = new ArrayList<>();
        for (int i = 0; i < tasks.length; i++) {
            this.tasks.add(new PersonTask(0, i));
        }
        return this;
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>} and append it to the {@code Person} that we are building.
     */
    public PersonBuilder appendTasks(String ... tasks) {
        int startIndex = this.tasks.size();
        for (int i = 0; i < tasks.length; i++) {
            this.tasks.add(new PersonTask(0, startIndex + i));
        }
        return this;
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>}, set each task as done,
     * then append it to the {@code Person} that we are building.
     */
    public PersonBuilder appendDoneTasks(String ... tasks) {
        int startIndex = this.tasks.size();
        for (int i = 0; i < tasks.length; i++) {
            this.tasks.add(new PersonTask(0, startIndex + i));
        }
        return this;
    }



    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, projects, tasks);
    }

}
