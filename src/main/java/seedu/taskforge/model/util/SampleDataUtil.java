package seedu.taskforge.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.ReadOnlyAddressBook;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return getSamplePersons(getSampleProjects());
    }

    private static Person[] getSamplePersons(Project[] sampleProjects) {
        List<Project> sampleProjectList = Arrays.asList(sampleProjects);
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getPersonProjectList(sampleProjectList, "Interior"),
                getPersonTaskList(sampleProjectList, "Interior:add new feature")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getPersonProjectList(sampleProjectList, "Manufacture", "Mobile app"), List.of(
                    new PersonTask(1, 0),
                    new PersonTask(2, 0))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getPersonProjectList(sampleProjectList, "Infrastructure"),
                getPersonTaskList(sampleProjectList, "Infrastructure:meeting")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getPersonProjectList(sampleProjectList, "Manufacture"),
                getPersonTaskList(sampleProjectList, "Manufacture:research on project")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getPersonProjectList(sampleProjectList, "Web app"),
                getPersonTaskList(sampleProjectList, "Web app:add new feature")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getPersonProjectList(sampleProjectList, "Future Gen"),
                getPersonTaskList(sampleProjectList, "Future Gen:read notes"))
        };
    }

    public static Project[] getSampleProjects() {
        return new Project[] {
            new Project("Interior", getTaskList("add new feature")),
            new Project("Manufacture", getTaskList("read notes", "research on project")),
            new Project("Mobile app", getTaskList("add new feature")),
            new Project("Infrastructure", getTaskList("meeting")),
            new Project("Web app", getTaskList("add new feature")),
            new Project("Future Gen", getTaskList("read notes"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        Project[] sampleProjects = getSampleProjects();
        for (Project sampleProject : sampleProjects) {
            sampleAb.addProject(sampleProject);
        }

        for (Person samplePerson : getSamplePersons(sampleProjects)) {
            sampleAb.addPerson(samplePerson);
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
     * Returns a person project list containing the projects with the given {@code projectTitles}.
     */
    public static List<PersonProject> getPersonProjectList(String... projectTitles) {
        return getPersonProjectList(Arrays.asList(getSampleProjects()), projectTitles);
    }

    private static List<PersonProject> getPersonProjectList(List<Project> sampleProjects, String... projectTitles) {
        return Arrays.stream(projectTitles)
                .map(Project::new)
                .map(project -> {
                    int projectIndex = sampleProjects.indexOf(project);
                    if (projectIndex < 0) {
                        throw new IllegalArgumentException("Sample project not found: " + project);
                    }
                    return new PersonProject(projectIndex);
                })
                .collect(Collectors.toList());
    }

    private static List<PersonTask> getPersonTaskList(List<Project> sampleProjects, String... taskRefs) {
        return Arrays.stream(taskRefs)
                .map(ref -> {
                    String[] tokens = ref.split(":", 2);
                    if (tokens.length != 2) {
                        throw new IllegalArgumentException("Invalid task reference: " + ref);
                    }
                    Project project = new Project(tokens[0]);
                    int projectIndex = sampleProjects.indexOf(project);
                    if (projectIndex < 0) {
                        throw new IllegalArgumentException("Sample project not found for task reference: " + ref);
                    }
                    int taskIndex = sampleProjects.get(projectIndex).getTasks().indexOf(new Task(tokens[1]));
                    if (taskIndex < 0) {
                        throw new IllegalArgumentException("Sample task not found for task reference: " + ref);
                    }
                    return new PersonTask(projectIndex, taskIndex);
                })
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
