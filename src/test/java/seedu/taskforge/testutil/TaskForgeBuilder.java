package seedu.taskforge.testutil;

import seedu.taskforge.model.TaskForge;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;

/**
 * A utility class to help with building TaskForge objects.
 * Example usage: <br>
 *     {@code TaskForge ab = new TaskForgeBuilder().withPerson("John", "Doe").build();}
 */
public class TaskForgeBuilder {

    private TaskForge taskForge;

    public TaskForgeBuilder() {
        taskForge = new TaskForge();
    }

    public TaskForgeBuilder(TaskForge taskForge) {
        this.taskForge = taskForge;
    }

    /**
     * Adds a new {@code Person} to the {@code TaskForge} that we are building.
     */
    public TaskForgeBuilder withPerson(Person person) {
        taskForge.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Project} to the {@code TaskForge} that we are building.
     */
    public TaskForgeBuilder withProject(Project project) {
        taskForge.addProject(project);
        return this;
    }

    public TaskForge build() {
        return taskForge;
    }
}
