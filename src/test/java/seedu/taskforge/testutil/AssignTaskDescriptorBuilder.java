package seedu.taskforge.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.ProjectTaskPair;
import seedu.taskforge.model.person.Person;

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
    }

    /**
     * Sets the task indexes to the {@code AssignTaskDescriptor} being built.
     * The provided indexes are parsed as one-based integers and converted to {@code Index} objects.
     * Assumes a default project index of 1 for backward compatibility.
     *
     * @param indexes One-based string representations of task indexes to be assigned.
     * @return The {@code AssignTaskDescriptorBuilder} instance with the updated task indexes.
     */
    public AssignTaskDescriptorBuilder withTaskIndexes(String... indexes) {
        List<ProjectTaskPair> pairs = new ArrayList<>();
        for (String index : indexes) {
            // Default to project index 1 for backward compatibility
            pairs.add(new ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(Integer.parseInt(index))));
        }
        descriptor.setProjectTaskPairs(pairs);
        return this;
    }

    /**
     * Sets the project-task pairs to the {@code AssignTaskDescriptor} being built.
     *
     * @param pairs Project-task pairs to be assigned.
     * @return The {@code AssignTaskDescriptorBuilder} instance with the updated pairs.
     */
    public AssignTaskDescriptorBuilder withProjectTaskPairs(ProjectTaskPair... pairs) {
        List<ProjectTaskPair> pairList = new ArrayList<>();
        for (ProjectTaskPair pair : pairs) {
            pairList.add(pair);
        }
        descriptor.setProjectTaskPairs(pairList);
        return this;
    }

    public AssignTaskDescriptor build() {
        return descriptor;
    }
}
