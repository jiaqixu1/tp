package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        String[] preambleSplit = argMultimap.getPreamble().trim().split("\\s+");
        if (preambleSplit.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        Index personIndex = ParserUtil.parseIndex(preambleSplit[0]);
        Index taskIndex = ParserUtil.parseIndex(preambleSplit[1]);
        Task newTask = ParserUtil.parseTask(argMultimap.getValue(PREFIX_NAME).get());

        return new EditTaskCommand(personIndex, taskIndex, newTask);
    }
}

