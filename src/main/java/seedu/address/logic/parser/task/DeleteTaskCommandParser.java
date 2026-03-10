package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTaskCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns an DeleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE), pe);
        }

        DeleteTaskDescriptor DeleteTaskDescriptor = new DeleteTaskDescriptor();

        parseTasksIndexesForAdd(argMultimap.getAllValues(PREFIX_TASK_DESCRIPTION)).ifPresent(DeleteTaskDescriptor::setTasksIndexes);

        if (!DeleteTaskDescriptor.isTaskFieldEdited()) {
            throw new ParseException(DeleteTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new DeleteTaskCommand(index, DeleteTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tasks} into a {@code List<Task>} if {@code tasks} is non-empty.
     * If {@code tasks} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Task>} containing zero tasks.
     */
    private Optional<List<Index>> parseTasksIndexesForAdd(Collection<String> tasksIndexes) throws ParseException {
        assert tasksIndexes != null;

        if (tasksIndexes.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> taskSet = tasksIndexes.size() == 1 && tasksIndexes.contains("") ? Collections.emptyList() : tasksIndexes;
        return Optional.of(ParserUtil.parseTaskIndexes(taskSet));
    }
}
