package seedu.address.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.project.AddProjectCommand.AddProjectDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * A utility class to help with building AddProjectDescriptor objects.
 */
public class AddProjectDescriptorBuilder {
    private AddProjectDescriptor descriptor;

    public AddProjectDescriptorBuilder() {
        descriptor = new AddProjectDescriptor();
    }

    public AddProjectDescriptorBuilder(AddProjectDescriptor descriptor) {
        this.descriptor = new AddProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code AddProjectDescriptor} with fields containing {@code person}'s details
     */
    public AddProjectDescriptorBuilder(Person person) {
        descriptor = new AddProjectDescriptor();
        descriptor.setProjects(person.getProjects());
    }

    /**
     * Parses the {@code projects} into a {@code List<Project>} and set it to the {@code AddProjectDescriptor}
     * that we are building.
     */
    public AddProjectDescriptorBuilder withProjects(String... projects) {
        List<Project> projectSet = Stream.of(projects).map(Project::new).collect(Collectors.toList());
        descriptor.setProjects(projectSet);
        return this;
    }

    public AddProjectDescriptor build() {
        return descriptor;
    }
}
