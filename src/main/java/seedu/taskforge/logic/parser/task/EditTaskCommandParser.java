package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.EditTaskCommand;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.task.Task;

/**
 * Parses input arguments and creates a new EditTaskCommand object.
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    @Override
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_NAME);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_NAME);

        Index personIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTaskCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_INDEX).isEmpty() || argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        Index taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Task newTask = ParserUtil.parseTask(argMultimap.getValue(PREFIX_NAME).get());

        return new EditTaskCommand(personIndex, taskIndex, newTask);
    }
}

