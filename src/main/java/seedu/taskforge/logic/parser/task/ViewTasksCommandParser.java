package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.ViewTasksCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses user input arguments and creates a new ViewTasksCommand object.
 */
public class ViewTasksCommandParser implements Parser<ViewTasksCommand> {

    @Override
    public ViewTasksCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewTasksCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTasksCommand.MESSAGE_USAGE_VIEW), pe);
        }
    }
}
