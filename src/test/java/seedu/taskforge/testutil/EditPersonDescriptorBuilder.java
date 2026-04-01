package seedu.taskforge.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }


    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withProjects(String... projects) {
        List<Project> projectSet = Stream.of(projects).map(Project::new).collect(Collectors.toList());
        descriptor.setProjects(projectSet);
        return this;
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTasks(String... tasks) {
        List<Task> taskSet = Stream.of(tasks).map(Task::new).collect(Collectors.toList());
        descriptor.setTasks(taskSet);
        return this;
    }


    public EditPersonDescriptor build() {
        return descriptor;
    }

}
