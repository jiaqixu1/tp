package seedu.taskforge.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.UnassignTaskCommand.UnassignTaskDescriptor;

/**
 * A utility class to help with building UnassignTaskDescriptorBuilder objects.
 */
public class UnassignTaskDescriptorBuilder {

    private UnassignTaskDescriptor descriptor;

    public UnassignTaskDescriptorBuilder() {
        descriptor = new UnassignTaskDescriptor();
    }

    public UnassignTaskDescriptorBuilder(UnassignTaskDescriptor descriptor) {
        this.descriptor = new UnassignTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code UnassignTaskDescriptorBuilder} with fields containing {@code person}'s details
     */
    public UnassignTaskDescriptorBuilder(Index index) {
        descriptor = new UnassignTaskDescriptor();
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(index);
        descriptor.setTasksIndexes(indexes);
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code UnassignTaskDescriptorBuilder}
     * that we are building.
     */
    public UnassignTaskDescriptorBuilder withTasks(String... indexes) {
        List<Index> taskIndexSet = Stream.of(indexes)
                .map(s -> Index.fromOneBased(Integer.parseInt(s)))
                .collect(Collectors.toList());
        descriptor.setTasksIndexes(taskIndexSet);
        return this;
    }

    public UnassignTaskDescriptor build() {
        return descriptor;
    }
}
