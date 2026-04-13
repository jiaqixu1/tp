package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.ListTaskCommand;
import seedu.taskforge.logic.parser.ParserUtil;

public class ListTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE);

    private final ListTaskCommandParser parser = new ListTaskCommandParser();

    @Test
    public void parse_validArgs_success() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        assertParseSuccess(parser, " " + targetIndex.getOneBased(), new ListTaskCommand(targetIndex));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "alpha", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "-1", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 2", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE));
    }
}
