package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.project.AddProjectCommand.AddProjectDescriptor;
import seedu.address.logic.commands.project.DeleteProjectCommand.DeleteProjectDescriptor;
import seedu.address.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.address.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getProjects().stream().forEach(
            s -> sb.append(PREFIX_PROJECT_TITLE + s.title + " ")
        );
        person.getTasks().stream().forEach(
            s -> sb.append(PREFIX_TASK + s.description + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getProjects().isPresent()) {
            List<Project> projects = descriptor.getProjects().get();
            if (projects.isEmpty()) {
                sb.append(PREFIX_PROJECT_TITLE).append(" ");
            } else {
                projects.forEach(s -> sb.append(PREFIX_PROJECT_TITLE).append(s.title).append(" "));
            }
        }
        if (descriptor.getTasks().isPresent()) {
            List<Task> tasks = descriptor.getTasks().get();
            if (tasks.isEmpty()) {
                sb.append(PREFIX_TASK_DESCRIPTION).append(" ");
            } else {
                tasks.forEach(s -> sb.append(PREFIX_TASK_DESCRIPTION).append(s.description).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code AddProjectDescriptor}'s details.
     */
    public static String getAddProjectDescriptorDetails(AddProjectDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getProjects().isPresent()) {
            List<Project> projects = descriptor.getProjects().get();
            if (projects.isEmpty()) {
                sb.append(PREFIX_PROJECT_TITLE).append(" ");
            } else {
                projects.forEach(s -> sb.append(PREFIX_PROJECT_TITLE).append(s.title).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code DeleteProjectDescriptor}'s details.
     */
    public static String getDeleteProjectDescriptorDetails(DeleteProjectDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getProjectsIndexes().isPresent()) {
            List<Index> indexes = descriptor.getProjectsIndexes().get();
            if (indexes.isEmpty()) {
                sb.append(PREFIX_PROJECT_INDEX).append(" ");
            } else {
                indexes.forEach(s -> sb.append(PREFIX_PROJECT_INDEX).append(s.getOneBased()).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code AddTaskDescriptor}'s details.
     */
    public static String getAddTaskDescriptorDetails(AddTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getTasks().isPresent()) {
            List<Task> tasks = descriptor.getTasks().get();
            if (tasks.isEmpty()) {
                sb.append(PREFIX_TASK_DESCRIPTION).append(" ");
            } else {
                tasks.forEach(s -> sb.append(PREFIX_TASK_DESCRIPTION).append(s.description).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code DeleteTaskDescriptor}'s details.
     */
    public static String getDeleteTaskDescriptorDetails(DeleteTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getTasksIndexes().isPresent()) {
            List<Index> indexes = descriptor.getTasksIndexes().get();
            if (indexes.isEmpty()) {
                sb.append(PREFIX_TASK).append(" ");
            } else {
                indexes.forEach(s -> sb.append(PREFIX_TASK).append(s.getOneBased()).append(" "));
            }
        }
        return sb.toString();
    }
}
