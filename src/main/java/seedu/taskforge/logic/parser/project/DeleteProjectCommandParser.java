package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.DeleteProjectCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new
 * {@code DeleteProjectCommand} object.
 */
public class DeleteProjectCommandParser implements Parser<DeleteProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code DeleteProjectCommand} and returns a
     * {@code DeleteProjectCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteProjectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteProjectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteProjectCommand.MESSAGE_USAGE), pe);
        }
    }

}

