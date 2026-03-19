package seedu.taskforge.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.person.EditCommand;
import seedu.taskforge.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                         PREFIX_PROJECT_TITLE, PREFIX_TASK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        parseProjectsForEdit(argMultimap.getAllValues(PREFIX_PROJECT_TITLE))
                .ifPresent(editPersonDescriptor::setProjects);

        parseTasksForEdit(argMultimap.getAllValues(PREFIX_TASK))
                .ifPresent(editPersonDescriptor::setTasks);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> project} into a {@code List<Project>} if {@code projects} is non-empty.
     * If {@code projects} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Project>} containing zero tasks.
     */
    private Optional<List<Project>> parseProjectsForEdit(Collection<String> projects) throws ParseException {
        assert projects != null;

        if (projects.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> projectSet = projects.size() == 1 && projects.contains("")
                ? Collections.emptyList()
                : projects;
        return Optional.of(ParserUtil.parseProjects(projectSet));
    }

    /**
     * Parses {@code Collection<String> tasks} into a {@code List<Task>} if {@code tasks} is non-empty.
     * If {@code tasks} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Task>} containing zero tasks.
     */
    private Optional<List<Task>> parseTasksForEdit(Collection<String> tasks) throws ParseException {
        assert tasks != null;

        if (tasks.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> taskSet = tasks.size() == 1 && tasks.contains("") ? Collections.emptyList() : tasks;
        return Optional.of(ParserUtil.parseTasks(taskSet));
    }

}
