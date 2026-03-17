package seedu.taskforge.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskforge.logic.commands.project.AddProjectCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddProjectCommand object.
 */
public class AddProjectCommandParser implements Parser<AddProjectCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddProjectCommand
     * and returns an AddProjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProjectCommand.MESSAGE_USAGE));
        }

        return new AddProjectCommand(ParserUtil.parseProject(trimmedArgs));
    }
}

