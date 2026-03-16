package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.project.DeleteProjectCommand.DeleteProjectDescriptor;

/**
 * A utility class to help with building DeleteProjectDescriptorBuilder objects.
 */
public class DeleteProjectDescriptorBuilder {
    private DeleteProjectDescriptor descriptor;

    public DeleteProjectDescriptorBuilder() {
        descriptor = new DeleteProjectDescriptor();
    }

    public DeleteProjectDescriptorBuilder(DeleteProjectDescriptor descriptor) {
        this.descriptor = new DeleteProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code DeleteProjectDescriptorBuilder} with fields containing {@code person}'s details
     */
    public DeleteProjectDescriptorBuilder(Index index) {
        descriptor = new DeleteProjectDescriptor();
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(index);
        descriptor.setProjectsIndexes(indexes);
    }

    /**
     * Parses the {@code projects} into a {@code List<Project>} and set it to the {@code DeleteProjectDescriptorBuilder}
     * that we are building.
     */
    public DeleteProjectDescriptorBuilder withProjects(String... indexes) {
        List<Index> projectIndexSet = Stream.of(indexes)
                .map(s -> Index.fromOneBased(Integer.parseInt(s)))
                .collect(Collectors.toList());
        descriptor.setProjectsIndexes(projectIndexSet);
        return this;
    }

    public DeleteProjectDescriptor build() {
        return descriptor;
    }
}
