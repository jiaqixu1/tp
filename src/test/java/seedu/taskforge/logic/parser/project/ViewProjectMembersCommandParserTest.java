package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.ViewProjectMembersCommand;

public class ViewProjectMembersCommandParserTest {

    private final ViewProjectMembersCommandParser parser = new ViewProjectMembersCommandParser();

    @Test
    public void parse_validArgs_returnsViewProjectMembersCommand() {
        assertParseSuccess(parser, "1",
                new ViewProjectMembersCommand(Index.fromOneBased(1)));

        assertParseSuccess(parser, "  2  ",
                new ViewProjectMembersCommand(Index.fromOneBased(2)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectMembersCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectMembersCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectMembersCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectMembersCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectMembersCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectMembersCommand.MESSAGE_USAGE));
    }

}
