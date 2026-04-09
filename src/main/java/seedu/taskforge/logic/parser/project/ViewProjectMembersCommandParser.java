package seedu.taskforge.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.ViewProjectMembersCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses user input arguments and creates a new ViewProjectMembersCommand object.
 */
public class ViewProjectMembersCommandParser implements Parser<ViewProjectMembersCommand> {

    @Override
    public ViewProjectMembersCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewProjectMembersCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectMembersCommand.MESSAGE_USAGE), pe);
        }
    }
}
