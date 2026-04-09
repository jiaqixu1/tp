package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.ListTaskCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListTaskCommand object.
 */
public class ListTaskCommandParser implements Parser<ListTaskCommand> {

    @Override
    public ListTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            Index projectIndex = ParserUtil.parseIndex(args);
            return new ListTaskCommand(projectIndex);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ListTaskCommand.MESSAGE_USAGE_LIST), pe);
        }
    }
}
