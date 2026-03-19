package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

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
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTaskCommand.MESSAGE_USAGE), pe);
        }

        DeleteTaskDescriptor deleteTaskDescriptor = new DeleteTaskDescriptor();

        parseTasksIndexesForAdd(argMultimap.getAllValues(PREFIX_INDEX))
                .ifPresent(deleteTaskDescriptor::setTasksIndexes);

        if (!deleteTaskDescriptor.isTaskFieldEdited()) {
            throw new ParseException(DeleteTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new DeleteTaskCommand(index, deleteTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tasks} into a {@code List<Task>} if {@code tasks} is non-empty.
     * If {@code tasks} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Task>} containing zero tasks.
     */
    private Optional<List<Index>> parseTasksIndexesForAdd(Collection<String> tasksIndexes)
            throws ParseException {
        assert tasksIndexes != null;

        if (tasksIndexes.isEmpty() || (tasksIndexes.size() == 1 && tasksIndexes.contains(""))) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTaskIndexes(tasksIndexes));
    }
}
