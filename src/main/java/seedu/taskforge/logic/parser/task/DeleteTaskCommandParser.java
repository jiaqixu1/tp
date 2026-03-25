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
 * Parses input arguments and creates a new DeleteTaskCommand object.
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    @Override
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
        parseTaskIndexesForDelete(argMultimap.getAllValues(PREFIX_INDEX))
                .ifPresent(deleteTaskDescriptor::setTaskIndexes);

        if (!deleteTaskDescriptor.isTaskFieldEdited()) {
            throw new ParseException(DeleteTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new DeleteTaskCommand(index, deleteTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> taskIndexes} into a {@code List<Index>} if {@code taskIndexes} is non-empty.
     * If {@code taskIndexes} contain only one element which is an empty string, it will be parsed into
     * an empty optional.
     */
    private Optional<List<Index>> parseTaskIndexesForDelete(Collection<String> taskIndexes)
            throws ParseException {
        assert taskIndexes != null;

        if (taskIndexes.isEmpty() || (taskIndexes.size() == 1 && taskIndexes.contains(""))) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTaskIndexes(taskIndexes));
    }
}
