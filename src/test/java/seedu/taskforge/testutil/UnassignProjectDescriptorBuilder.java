package seedu.taskforge.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand.UnassignProjectDescriptor;

/**
 * A utility class to help with building UnassignProjectDescriptorBuilder objects.
 */
public class UnassignProjectDescriptorBuilder {
    private UnassignProjectDescriptor descriptor;

    public UnassignProjectDescriptorBuilder() {
        descriptor = new UnassignProjectDescriptor();
    }

    public UnassignProjectDescriptorBuilder(UnassignProjectDescriptor descriptor) {
        this.descriptor = new UnassignProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code UnassignProjectDescriptorBuilder} with fields containing {@code person}'s details
     */
    public UnassignProjectDescriptorBuilder(Index index) {
        descriptor = new UnassignProjectDescriptor();
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(index);
        descriptor.setProjectsIndexes(indexes);
    }

    /**
     * Parses the {@code projects} into a {@code List<Project>} and set it to the
     * {@code UnassignProjectDescriptorBuilder} that we are building.
     */
    public UnassignProjectDescriptorBuilder withProjects(String... indexes) {
        List<Index> projectIndexSet = Stream.of(indexes)
                .map(s -> Index.fromOneBased(Integer.parseInt(s)))
                .collect(Collectors.toList());
        descriptor.setProjectsIndexes(projectIndexSet);
        return this;
    }

    public UnassignProjectDescriptor build() {
        return descriptor;
    }
}
