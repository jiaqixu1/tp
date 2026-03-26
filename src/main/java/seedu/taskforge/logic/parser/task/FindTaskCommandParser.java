package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.taskforge.logic.commands.task.FindTaskCommand;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindTaskCommand object.
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    @Override
    public FindTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTaskCommand.MESSAGE_USAGE_FIND));
        }

        List<String> keywords = Arrays.stream(trimmedArgs.split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());

        return new FindTaskCommand(keywords);
    }
}
