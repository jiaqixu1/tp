package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.task.FindTaskCommand;

public class FindTaskCommandParserTest {

    private final FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_validArgs_returnsFindTaskCommand() {
        assertParseSuccess(parser, " report bug ",
                new FindTaskCommand(Arrays.asList("report", "bug")));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE_FIND));
    }

    @Test
    public void parse_multipleSpaces_returnsFindTaskCommand() {
        assertParseSuccess(parser, " report   bug   ui ",
                new FindTaskCommand(Arrays.asList("report", "bug", "ui")));
    }
}
