package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignTaskCommand object.
 */
public class AssignTaskCommandParser implements Parser<AssignTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignTaskCommand
     * and returns an AssignTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), pe);
        }

        AssignTaskDescriptor assignTaskDescriptor = new AssignTaskDescriptor();
        parseTaskIndexesForAssign(argMultimap.getAllValues(PREFIX_INDEX))
                .ifPresent(assignTaskDescriptor::setTasksIndexes);

        if (!assignTaskDescriptor.isTaskFieldEdited()) {
            throw new ParseException(AssignTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new AssignTaskCommand(index, assignTaskDescriptor);
    }

    private Optional<List<Index>> parseTaskIndexesForAssign(Collection<String> taskIndexes) throws ParseException {
        assert taskIndexes != null;

        if (taskIndexes.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> taskSet = taskIndexes.size() == 1 && taskIndexes.contains("")
                ? Collections.emptyList() : taskIndexes;
        return Optional.of(ParserUtil.parseTaskIndexes(taskSet));
    }
}