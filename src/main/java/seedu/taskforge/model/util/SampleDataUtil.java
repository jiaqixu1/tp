package seedu.taskforge.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.ReadOnlyAddressBook;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    getProjectList("Interior"), getTaskList("add new feature")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    getProjectList("Manufacture", "Mobile app"), getTaskList("read notes", "add new feature")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    getProjectList("Infrastructure"), getTaskList("meeting")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    getProjectList("Manufacture"), getTaskList("research on project")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    getProjectList("Web app"), getTaskList("add new feature")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    getProjectList("Future Gen"), getTaskList("read notes"))
        };
    }

    public static Project[] getSampleProjects() {
        return new Project[] {
            new Project("Interior"),
            new Project("Manufacture"),
            new Project("Mobile app"),
            new Project("Infrastructure"),
            new Project("Web app"),
            new Project("Future gen")
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Project sampleProject : getSampleProjects()) {
            sampleAb.addProject(sampleProject);
        }
        return sampleAb;
    }

    /**
     * Returns a project list containing the list of strings given.
     */
    public static List<Project> getProjectList(String... strings) {
        return Arrays.stream(strings)
                .map(Project::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a task list containing the list of strings given.
     */
    public static List<Task> getTaskList(String... strings) {
        return Arrays.stream(strings)
                .map(Task::new)
                .collect(Collectors.toList());
    }


}
