package seedu.taskforge.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.model.person.Person;

/**
 * A utility class to help with building AssignProjectDescriptor objects.
 */
public class AssignProjectDescriptorBuilder {
    private AssignProjectDescriptor descriptor;

    public AssignProjectDescriptorBuilder() {
        descriptor = new AssignProjectDescriptor();
    }

    public AssignProjectDescriptorBuilder(AssignProjectDescriptor descriptor) {
        this.descriptor = new AssignProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code AssignProjectDescriptor} with fields containing {@code person}'s details
     */
    public AssignProjectDescriptorBuilder(Person person) {
        descriptor = new AssignProjectDescriptor();
    }


    /**
     * Sets the project indexes in the {@code AssignProjectDescriptor} using the provided string representations
     * of indexes. Each string is parsed as a one-based index, converted to an {@code Index} object, and stored in
     * a list.
     *
     * @param indexes A varargs string array where each element represents a one-based project index.
     * @return The current {@code AssignProjectDescriptorBuilder} instance with updated project indexes.
     */
    public AssignProjectDescriptorBuilder withProjectIndexes(String... indexes) {
        List<Index> projectIndexSet = Stream.of(indexes)
                .map(s -> Index.fromOneBased(Integer.parseInt(s)))
                .collect(Collectors.toList());
        descriptor.setProjectsIndexes(projectIndexSet);
        return this;
    }

    public AssignProjectDescriptor build() {
        return descriptor;
    }
}
