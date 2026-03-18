package seedu.taskforge.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.commons.util.StringUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String project} into an {@code Project}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code project} is invalid.
     */
    public static Project parseProject(String project) throws ParseException {
        requireNonNull(project);
        String trimmedProject = project.trim();
        if (!Project.isValidProjectTitle(trimmedProject)) {
            throw new ParseException(Project.MESSAGE_CONSTRAINTS);
        }
        return new Project(trimmedProject);
    }

    /**
     * Parses a {@code String task} into a {@code Task}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code task} is invalid.
     */
    public static Task parseTask(String task) throws ParseException {
        requireNonNull(task);
        String trimmedTask = task.trim();
        if (!Task.isValidTaskDescription(trimmedTask)) {
            throw new ParseException(Task.MESSAGE_CONSTRAINTS);
        }
        return new Task(trimmedTask);
    }



    /**
     * Parses {@code Collection<String> tasks} into a {@code List<Task>}.
     */
    public static List<Task> parseTasks(Collection<String> tasks) throws ParseException {
        requireNonNull(tasks);
        final List<Task> taskSet = new ArrayList<>();
        for (String description : tasks) {
            taskSet.add(parseTask(description));
        }
        return taskSet;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseTaskIndexes(Collection<String> oneBasedIndexes) throws ParseException {
        requireNonNull(oneBasedIndexes);
        final List<Index> taskIndexSet = new ArrayList<>();
        for (String oneBasedIndex : oneBasedIndexes) {
            taskIndexSet.add(parseIndex(oneBasedIndex));
        }
        return taskIndexSet;
    }

    /**
     * Parses {@code Collection<String> tasks} into a {@code List<Task>}.
     */
    public static List<Project> parseProjects(Collection<String> projects) throws ParseException {
        requireNonNull(projects);
        final List<Project> projectSet = new ArrayList<>();
        for (String description : projects) {
            projectSet.add(parseProject(description));
        }
        return projectSet;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseProjectIndexes(Collection<String> oneBasedIndexes) throws ParseException {
        requireNonNull(oneBasedIndexes);
        final List<Index> projectIndexSet = new ArrayList<>();
        for (String oneBasedIndex : oneBasedIndexes) {
            projectIndexSet.add(parseIndex(oneBasedIndex));
        }
        return projectIndexSet;
    }
}
