package seedu.taskforge.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;

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
        descriptor.setProjects(person.getProjects());
    }

    /**
     * Parses the {@code projects} into a {@code List<Project>} and set it to the {@code AssignProjectDescriptor}
     * that we are building.
     */
    public AssignProjectDescriptorBuilder withProjects(String... projects) {
        List<Project> projectSet = Stream.of(projects).map(Project::new).collect(Collectors.toList());
        descriptor.setProjects(projectSet);
        return this;
    }

    public AssignProjectDescriptor build() {
        return descriptor;
    }
}
