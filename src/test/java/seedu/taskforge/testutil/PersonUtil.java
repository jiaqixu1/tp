package seedu.taskforge.testutil;

import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.List;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.person.AddCommand;
import seedu.taskforge.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand.UnassignProjectDescriptor;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.logic.commands.task.UnassignTaskCommand.UnassignTaskDescriptor;
import seedu.taskforge.logic.parser.CliSyntax;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

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
        person.getProjects().stream().forEach(
            s -> sb.append(PREFIX_PROJECT_TITLE + "project" + s.getProjectIndex() + " ")
        );
        person.getTasks().stream().forEach(
            s -> sb.append(PREFIX_TASK + "task" + s.getProjectIndex() + "_" + s.getTaskIndex() + " ")
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
                sb.append(CliSyntax.PREFIX_TASK).append(" ");
            } else {
                tasks.forEach(s -> sb.append(CliSyntax.PREFIX_TASK).append(s.description).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code AssignProjectDescriptor}'s details.
     */
    public static String getAssignProjectDescriptorDetails(AssignProjectDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getProjectIndexes().isPresent()) {
            List<Index> indexes = descriptor.getProjectIndexes().get();
            if (indexes.isEmpty()) {
                sb.append(PREFIX_INDEX).append(" ");
            } else {
                indexes.forEach(s -> sb.append(PREFIX_INDEX).append(s.getOneBased()).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code UnassignProjectDescriptor}'s details.
     */
    public static String getUnassignProjectDescriptorDetails(UnassignProjectDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getProjectsIndexes().isPresent()) {
            List<Index> indexes = descriptor.getProjectsIndexes().get();
            if (indexes.isEmpty()) {
                sb.append(PREFIX_INDEX).append(" ");
            } else {
                indexes.forEach(s -> sb.append(PREFIX_INDEX).append(s.getOneBased()).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code AssignTaskDescriptor}'s details.
     */
    public static String getAssignTaskDescriptorDetails(AssignTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getProjectTaskPairs().isPresent()) {
            descriptor.getProjectTaskPairs().get().forEach(pair ->
                sb.append(PREFIX_PROJECT).append(pair.getProjectIndex().getOneBased()).append(" ")
                  .append(PREFIX_INDEX).append(pair.getTaskIndex().getOneBased()).append(" ")
            );
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code UnassignTaskDescriptor}'s details.
     */
    public static String getUnassignTaskDescriptorDetails(UnassignTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getTasksIndexes().isPresent()) {
            List<Index> indexes = descriptor.getTasksIndexes().get();
            if (indexes.isEmpty()) {
                sb.append(PREFIX_INDEX).append(" ");
            } else {
                indexes.forEach(s -> sb.append(PREFIX_INDEX).append(s.getOneBased()).append(" "));
            }
        }
        return sb.toString();
    }
}
