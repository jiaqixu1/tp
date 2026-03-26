package seedu.taskforge.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.model.util.SampleDataUtil;

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
    private List<Project> projects;
    private List<Task> tasks;

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
        this.projects = SampleDataUtil.getProjectList(projects);
        return this;
    }


    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTasks(String ... tasks) {
        this.tasks = SampleDataUtil.getTaskList(tasks);
        return this;
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>} and append it to the {@code Person} that we are building.
     */
    public PersonBuilder appendTasks(String ... tasks) {
        this.tasks.addAll(SampleDataUtil.getTaskList(tasks));
        return this;
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>}, set each task as done,
     * then append it to the {@code Person} that we are building.
     */
    public PersonBuilder appendDoneTasks(String ... tasks) {
        this.tasks.addAll(SampleDataUtil.getTaskList(tasks).stream().peek(Task::setDone).toList());
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
