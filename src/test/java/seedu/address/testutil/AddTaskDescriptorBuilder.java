package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class AddTaskDescriptorBuilder {

    private AddTaskDescriptor descriptor;

    public AddTaskDescriptorBuilder() {
        descriptor = new AddTaskDescriptor();
    }

    public AddTaskDescriptorBuilder(AddTaskDescriptor descriptor) {
        this.descriptor = new AddTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public AddTaskDescriptorBuilder(Person person) {
        descriptor = new AddTaskDescriptor();
        descriptor.setTasks(person.getTasks());
    }

    /**
     * Parses the {@code tasks} into a {@code Set<Task>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public AddTaskDescriptorBuilder withTasks(String... tasks) {
        Set<seedu.address.model.task.Task> taskSet = Stream.of(tasks).map(Task::new).collect(Collectors.toSet());
        descriptor.setTasks(taskSet);
        return this;
    }

    public AddTaskDescriptor build() {
        return descriptor;
    }
}
