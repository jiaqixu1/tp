package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.ListTaskCommand;

public class ListTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE_LIST);

    private final ListTaskCommandParser parser = new ListTaskCommandParser();

    @Test
    public void parse_validArgs_success() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        assertParseSuccess(parser, " " + targetIndex.getOneBased(), new ListTaskCommand(targetIndex));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "alpha", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 2", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
}
