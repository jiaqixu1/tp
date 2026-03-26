package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.task.MarkTaskCommand;

public class MarkTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE);

    private final MarkTaskCommandParser parser = new MarkTaskCommandParser();

    @Test
    public void parse_validArgs_returnsMarkTaskCommand() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_FIRST_TASK.getOneBased();
        MarkTaskCommand expectedCommand = new MarkTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 two", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 1 extra", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 1", MESSAGE_INVALID_FORMAT);
    }
}

