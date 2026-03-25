package seedu.taskforge.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.task.Task;

/**
 * A utility class to help with building AssignTaskDescriptor objects.
 */
public class AssignTaskDescriptorBuilder {

    private AssignTaskDescriptor descriptor;

    public AssignTaskDescriptorBuilder() {
        descriptor = new AssignTaskDescriptor();
    }

    public AssignTaskDescriptorBuilder(AssignTaskDescriptor descriptor) {
        this.descriptor = new AssignTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public AssignTaskDescriptorBuilder(Person person) {
        descriptor = new AssignTaskDescriptor();
        descriptor.setTasks(person.getTasks());
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public AssignTaskDescriptorBuilder withTasks(String... tasks) {
        List<Task> taskSet = Stream.of(tasks).map(Task::new).collect(Collectors.toList());
        descriptor.setTasks(taskSet);
        return this;
    }

    public AssignTaskDescriptor build() {
        return descriptor;
    }
}
