package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.taskforge.logic.commands.project.FindProjectCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindProjectCommand object.
 */
public class FindProjectCommandParser implements Parser<FindProjectCommand> {

    @Override
    public FindProjectCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindProjectCommand.MESSAGE_USAGE));
        }

        List<String> keywords = Arrays.stream(trimmedArgs.split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());

        return new FindProjectCommand(keywords);
    }
}
