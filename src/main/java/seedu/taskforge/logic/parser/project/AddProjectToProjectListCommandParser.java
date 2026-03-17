package seedu.taskforge.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskforge.logic.commands.project.AddProjectToProjectListCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddProjectToProjectListCommand object.
 */
public class AddProjectToProjectListCommandParser implements Parser<AddProjectToProjectListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddProjectToProjectListCommand
     * and returns an AddProjectToProjectListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddProjectToProjectListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProjectToProjectListCommand.MESSAGE_USAGE));
        }

        return new AddProjectToProjectListCommand(ParserUtil.parseProject(trimmedArgs));
    }
}
